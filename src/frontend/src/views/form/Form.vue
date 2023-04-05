<template>
  <div class="main__wrapper" id="pdf-content">
    <el-container>
      <el-main>
        <el-form>
          <div v-if="hasComment">
            <h6 style="color:red">Rejected Reason:</h6>
            <div class="comment-box">
              <p>{{ adminApproverComments }}</p>
            </div>
          </div>
          <!-- header -->
            <el-row>
              <h3>{{ formData.formName }}</h3>
            </el-row>
            <el-row>
              <h6>{{ formData.formNo }}</h6>
            </el-row>
            <el-row>
              <i>Fill in by: {{ formDeadline}}</i>
            </el-row>

          <!-- form -->
          <template v-for="eachFormObj in formattedData" >
            <el-divider content-position="left" >{{ eachFormObj.sectionTitle }}</el-divider>
              <div class="wrapper--forms">
                <el-col v-for="field in eachFormObj.fields"  v-bind="field" class="form__group" >
                  
                  <component 
                      :is="field.fieldType" 
                      :currentField="field" 
                      :required= "field.isRequired"
                      v-model="field.input" 
                      class="form__field">
                  </component>
                </el-col>
              </div>          
          </template>
        
          <div v-if="userType === 'VENDOR'" style="margin-top: 30px;">
            <button type="button" class="btn btn-success" @click="submitForm">Submit</button>
            <button type="button" class="btn btn-dark" style="margin-left: 30px;" @click="submitDraft">Save as Draft</button>
          </div>

          <div v-if="userType === 'APPROVER'" style="margin-top: 30px;">
            <button type="button" class="btn btn-success" @click="ApproverApprove">Approve</button>
            <button type="button" class="btn btn-danger" @click="showReasonInput = true"  style="margin-left: 30px;" >Reject</button>

            <div v-if="showReasonInput" style="margin: 30px; display: flex; flex-direction: column;">
              <label for="reason">Rejection Reason:</label>
              <textarea id="reason" v-model="rejectReason" style="resize: both; min-height: 100px; max-width: 400px;"></textarea>
              <button type="button" class="btn btn-dark" @click="ApproverReject" style="align-self: flex-start; " >Submit Reject</button>
            </div>
          </div>

          <div v-if="userType === 'ADMIN'" style="margin-top: 30px;">
            <button type="button" class="btn btn-success" @click="AdminApprove">Approve</button>
            <button type="button" class="btn btn-danger" @click="showReasonInput = true" style="margin-left: 30px;" >Reject</button>

            <div v-if="showReasonInput" style="margin: 30px; display: flex; flex-direction: column;">
              <label for="reason">Rejection Reason:</label>
              <textarea id="reason" v-model="rejectReason" style="resize: both; min-height: 100px; max-width: 400px;"></textarea>
              <button type="button" class="btn btn-dark" @click="AdminReject" style="align-self: flex-start;">Submit Reject</button>
            </div>
          </div>
        </el-form>
      </el-main>
    </el-container>

  </div>
</template>

  
<script>
  import {FormBuilder} from './formbuilder'
  import axios from 'axios';
  import { BASE_URL } from '../../api.js';
  import html2pdf from 'html2pdf.js'

  export default {
    components: FormBuilder.components,

    data(){
      return {
        formNo :'',
        entityUEN:'', //for pdf
        formDeadline:'',
        formData:[],
        formattedData:[], //data structure to match formBuilder component style
        nullField:[],
        userId:"",
        userEmail:"",
        userName:"",
        userType:"",
        showReasonInput: false,
        rejectReason: '',
        hasComment:false,
        adminApproverComments:""
      }
    },
    async created(){
    //user session
    const user = JSON.parse(sessionStorage.getItem('user'));
        if(user == null){
            //hardcode pass data
            this.userEmail = "asasas.2019@gmail.com"
            this.userType ="APPROVER"
            this.userName ="FangTingXOXO"
            this.userId = "6426e823533ce37c3e4ddae3"
        }else{
            this.userEmail = user.userEmail
            this.userType = user.userType
            this.userName = user.name
            this.userId = user.userId
        }
        console.log("userType-->", this.userType); 
        console.log("userEmail-->",this.userEmail); 
        console.log("userId-->",this.userId); 

    if (localStorage.getItem('formNo')!= null){ //retrieve formNo vendorId passed from VendorView
      const dataValue = localStorage.getItem('formNo');
      const datalist = dataValue.split(',');
      this.formNo = datalist[0]
      // this.status = datalist[1]
      console.log("formNo-->",this.formNo)
      await axios.get(`${BASE_URL}/api/form/` + this.formNo)
      .then(response => {
          const allData = response.data.data;
          this.formData = allData.formContent
          this.formDeadline = allData.deadline.slice(0, allData.deadline.indexOf('T'));
          var vendorEmail = allData.assigned_vendor_email
          const sectionData = this.formData.formSections
          if(allData.adminApproverComments && allData.adminApproverComments.trim().length>0){
            this.adminApproverComments=allData.adminApproverComments
            this.hasComment = true
          }
          axios.get(`${BASE_URL}/api/user/all`)
                .then(response => {
                  var allUser= response.data.data;
                    // console.log(allUser)
          for (const user of allUser){
                if (vendorEmail == user.email){
                    if(user.userType==="VENDOR"){
                        this.entityUEN='admin'
                    }
                    if(user.userType==="APPROVER"){
                        this.entityUEN='approver'
                    }
                    else{
                        this.entityUEN=user.entityUEN
                    }
                    
                }
              }
            console.log(this.entityUEN)
          //store questions dict 
          for(let i=1; i<Object.keys(sectionData).length +1; i++){
            let formTitle = sectionData[i]['sectionName']
            let adminUseOnly = sectionData[i]['adminUseOnly']
            let approvalUseOnly = sectionData[i]['approvalViewOnly']
            let allQn = []
            // console.log(formTitle, adminUseOnly)

            //disabled field
            // only for admin use only 
            let disable_section = false

            // for vendors
            if (this.userType =="VENDOR" && adminUseOnly==true){
              disable_section = true
            }

            if (this.userType =="VENDOR" && approvalUseOnly==true){
              disable_section = true
            }

            if (this.userType =="ADMIN" && approvalUseOnly==true){
              disable_section = true
            }

            if (this.userType =="APPROVER" && adminUseOnly==true){
              disable_section = true
            }


            //store questions dict 
            for (let j =1; j< Object.keys(sectionData[i]['questions']).length +1 ; j++){
              const inputOptions = sectionData[i]['questions'][j]['inputOptions'] || null
              let qnDict={
                fieldType:sectionData[i]['questions'][j]['inputType'],
                label: sectionData[i]['questions'][j]['qnTitle'],
                options : inputOptions,
                input: sectionData[i].questions[j].answer, 
                disabled: disable_section,
                isRequired: sectionData[i]['questions'][j]['required']
              };
              allQn.push(qnDict)
              // console.log("all Qn-->",allQn)
            }
            this.formattedData.push({ fields: allQn, sectionTitle: formTitle, AdminUseOnly:adminUseOnly,  ApproverUseOnly:approvalUseOnly });

          }
        }).catch(error => {
          console.log(error);
        })
        });
    }
  },
  methods: {
    AdminApprove(){
    const formSections = {};
    this.nullField=[] //initialise
    for (let i = 0; i < this.formattedData.length; i++) {
      if(this.formattedData[i]['AdminUseOnly'] == true){
        const formSection = this.formattedData[i];
      
      const questions = {};
      const allNull=[]
      for (let j = 0; j < formSection.fields.length; j++) {
        const field = formSection.fields[j];
        const inputOptions = field.options || null;

        if(field.input== null){
          allNull.push(field.label)
        }

        const question = {
          qnTitle: field.label,
          inputType: field.fieldType,
          inputOptions,
          required: field.isRequired,
          answer: field.input // get value from input property
        };
        questions[j + 1] = question;
        // console.log("question-->",question)
      }
      if(allNull.length>0){ //store only if there is error msg
        this.nullField.push({formSection: formSection.sectionTitle, nullQns:allNull})
        }        
        // console.log("allNull-->",allNull)
        const section = {
        sectionName: formSection.sectionTitle,
        adminUseOnly: formSection.AdminUseOnly,
        approvalViewOnly: formSection.ApproverUseOnly,
        doScoreCalculation: false,
        questions
      };
      formSections[i + 1] = section;
    }
    }
    console.log("have null?-->",this.nullField)
    if(this.nullField.length>0){
        const nullError = this.nullField.map((nullObj) => {
          return `${nullObj.formSection} - ${nullObj.nullQns.join(', ')}`
        })
        this.nullField=[]
        Swal.fire({
          icon: 'error',
          title: 'The following required fields are blank:',
          text: nullError.join('\n\n & \n\n')
        });
      }
    else{
      const submitData = {
        id: this.formNo,
        "formContent": {formSections},
        "reviewedBy": this.userId
        };

      console.log("Admin submit-->",submitData)
        Swal.fire({
        title: 'Are you sure to approve the task?',
        text: "Please check information before saving!",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: '#c7c6c5',
        confirmButtonColor: '#6A79F3',
        confirmButtonText: 'Yes, Approve!',
        cancelButtonText: 'No, Cancel',
        width: 'auto',
    }).then((result) => {
        if (result.isConfirmed) {
          try {
            const response = axios.post(`${BASE_URL}/api/form/action/adminreviewed`, submitData);
            console.log("SUCCESSFULLY POST")
            console.log(response.data);  
          
            Swal.fire({
              title: 'Success',
              text: 'Task have been successfully reviewed by you. Sent for Approver Review now',
              icon: 'success',
              timer: 3000,
              timerProgressBar: true,
              showConfirmButton: false
            }).then(() => {
              window.location.href = "/WorkflowView";
            });

          } catch (error) {
            if (error) {
              console.error("errrr", error)

              Swal.fire({
                icon: 'warning',
                title: error,
                timer: 2000,
                timerProgressBar: true,
                showConfirmButton: false
              })
            }
            }
        }
  })
      }
  },
  AdminReject(){
    const formSections = {};
    for (let i = 0; i < this.formattedData.length; i++) {
      if(this.formattedData[i]['AdminUseOnly'] == true){
        const formSection = this.formattedData[i];
      
      const questions = {};
      for (let j = 0; j < formSection.fields.length; j++) {
        const field = formSection.fields[j];
        const inputOptions = field.options || null;

        const question = {
          qnTitle: field.label,
          inputType: field.fieldType,
          inputOptions,
          required: field.isRequired,
          answer: field.input // get value from input property
        };
        questions[j + 1] = question;
        // console.log("question-->",question)
      }
      
        const section = {
        sectionName: formSection.sectionTitle,
        adminUseOnly: formSection.AdminUseOnly,
        approvalViewOnly: formSection.ApproverUseOnly,
        doScoreCalculation: false,
        questions
      };
      formSections[i + 1] = section;
    }
    }

    if(this.rejectReason.trim().length === 0){
      Swal.fire({
          icon: 'error',
          title: 'You have to fill in the rejection reason',
        });
    }else{
      const submitData = {
        id: this.formNo,
        "formContent": {formSections},
        "reviewedBy": this.userId,
        "adminApproverComments":this.rejectReason
        };

      console.log("Admin reject-->",submitData)
        Swal.fire({
        title: 'Reject the Task?',
        text: "Please check information before saving!",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: '#c7c6c5',
        confirmButtonColor: '#6A79F3',
        confirmButtonText: 'Yes, Reject!',
        cancelButtonText: 'No, Cancel',
        width: 'auto',
    }).then((result) => {
        if (result.isConfirmed) {
          try {
            const response = axios.post(`${BASE_URL}/api/form/action/adminreject`, submitData);
            console.log("SUCCESSFULLY POST")
            console.log(response.data);  
          
            Swal.fire({
              title: 'Success',
              text: 'Form have been rejected by you. Sent back to Vendor now',
              icon: 'success',
              timer: 3000,
              timerProgressBar: true,
              showConfirmButton: false
            }).then(() => {
              window.location.href = "/WorkflowView";
            });

          } catch (error) {
            if (error) {
              console.error("errrr", error)

              Swal.fire({
                icon: 'warning',
                title: error,
                timer: 2000,
                timerProgressBar: true,
                showConfirmButton: false
              })
            }
            }
        }
  })
}
      
          

  
  },
  async ApproverApprove(){
    const formSections = {};
    this.nullField=[] //initialise
    for (let i = 0; i < this.formattedData.length; i++) {
      if(this.formattedData[i]['ApproverUseOnly'] == true){
        const formSection = this.formattedData[i];
      
      const questions = {};
      const allNull=[]
      for (let j = 0; j < formSection.fields.length; j++) {
        const field = formSection.fields[j];
        const inputOptions = field.options || null;

        if(field.input== null){
          allNull.push(field.label)
        }

        const question = {
          qnTitle: field.label,
          inputType: field.fieldType,
          inputOptions,
          required: field.isRequired,
          answer: field.input // get value from input property
        };
        questions[j + 1] = question;
        // console.log("question-->",question)
      }
      if(allNull.length>0){ //store only if there is error msg
        this.nullField.push({formSection: formSection.sectionTitle, nullQns:allNull})
        }        
        // console.log("allNull-->",allNull)
        const section = {
        sectionName: formSection.sectionTitle,
        adminUseOnly: formSection.AdminUseOnly,
        approvalViewOnly: formSection.ApproverUseOnly,
        doScoreCalculation: false,
        questions
      };
      formSections[i + 1] = section;
    }
    }
    // console.log("have null?-->",this.nullField)
    if(this.nullField.length>0){
        const nullError = this.nullField.map((nullObj) => {
          return `${nullObj.formSection} - ${nullObj.nullQns.join(', ')}`
        })
        this.nullField=[]
        Swal.fire({
          icon: 'error',
          title: 'The following required fields are blank:',
          text: nullError.join('\n\n & \n\n')
        });
      }
    else{
      const submitData = {
        id: this.formNo,
        "formContent": {formSections},
        "approver": this.userId
        };

      console.log("Admin submit-->",submitData)
        Swal.fire({
        title: 'Are you sure to approve the task?',
        text: "Please check information before saving!",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: '#c7c6c5',
        confirmButtonColor: '#6A79F3',
        confirmButtonText: 'Yes, Approve!',
        cancelButtonText: 'No, Cancel',
        width: 'auto',
    }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            const response = await axios.post(`${BASE_URL}/api/form/action/approve`, submitData);
            console.log("SUCCESSFULLY POST")
            console.log(response.data);  
          } catch (error) {
            if (error) {
              console.error("errrr", error)

              Swal.fire({
                icon: 'warning',
                title: error,
                timer: 2000,
                timerProgressBar: true,
                showConfirmButton: false
              })
            }
            }
        }
  })
    this.exportToPdf()

      }
  },
  ApproverReject(){
    const formSections = {};
    for (let i = 0; i < this.formattedData.length; i++) {
      if(this.formattedData[i]['ApproverUseOnly'] == true){
        const formSection = this.formattedData[i];
      
      const questions = {};
      for (let j = 0; j < formSection.fields.length; j++) {
        const field = formSection.fields[j];
        const inputOptions = field.options || null;

        const question = {
          qnTitle: field.label,
          inputType: field.fieldType,
          inputOptions,
          required: field.isRequired,
          answer: field.input // get value from input property
        };
        questions[j + 1] = question;
        // console.log("question-->",question)
      }
      
        const section = {
        sectionName: formSection.sectionTitle,
        adminUseOnly: formSection.AdminUseOnly,
        approvalViewOnly: formSection.ApproverUseOnly,
        doScoreCalculation: false,
        questions
      };
      formSections[i + 1] = section;
    }
    }

    if(this.rejectReason.trim().length === 0){
      Swal.fire({
          icon: 'error',
          title: 'You have to fill in the rejection reason',
        });
    }else{
      const submitData = {
        id: this.formNo,
        "formContent": {formSections},
        "adminApproverComments":this.rejectReason,
        "approver": this.userId
        };

      console.log("Approver reject-->",submitData)
        Swal.fire({
        title: 'Reject the Task?',
        text: "Please check information before saving!",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: '#c7c6c5',
        confirmButtonColor: '#6A79F3',
        confirmButtonText: 'Yes, Reject!',
        cancelButtonText: 'No, Cancel',
        width: 'auto',
    }).then((result) => {
        if (result.isConfirmed) {
          try {
            const response = axios.post(`${BASE_URL}/api/form/action/approverreject`, submitData);
            console.log("SUCCESSFULLY POST")
            console.log(response.data);  
          
            Swal.fire({
              title: 'Success',
              text: 'Form have been rejected by you. Sent back to Vendor now',
              icon: 'success',
              timer: 3000,
              timerProgressBar: true,
              showConfirmButton: false
            }).then(() => {
              window.location.href = "/ApprovalView";
            });

          } catch (error) {
            if (error) {
              console.error("errrr", error)

              Swal.fire({
                icon: 'warning',
                title: error,
                timer: 2000,
                timerProgressBar: true,
                showConfirmButton: false
              })
            }
            }
        }
  })
}
      
          

  
  },

  async submitForm() {
  //submit with validation
    const formSections = {};
    this.nullField=[] //initialise
    for (let i = 0; i < this.formattedData.length; i++) {
      const formSection = this.formattedData[i];
      const questions = {};

      const allNull=[]
      for (let j = 0; j < formSection.fields.length; j++) {
        const field = formSection.fields[j];
        const inputOptions = field.options || null;

        if(field.isRequired){
          if(field.input== null){
            allNull.push(field.label)
          }else{
            console.log("none")
          }
        }
        const question = {
          qnTitle: field.label,
          inputType: field.fieldType,
          inputOptions,
          required: field.isRequired,
          answer: field.input // get value from input property
        };
        questions[j + 1] = question;
        // console.log("question-->",question)
      }
      if(allNull.length>0){ //store only if there is error msg
        this.nullField.push({formSection: formSection.sectionTitle, nullQns:allNull})
        }        
        // console.log("allNull-->",allNull)
        const section = {
        sectionName: formSection.sectionTitle,
        adminUseOnly: formSection.AdminUseOnly,
        approvalViewOnly: formSection.ApproverUseOnly,
        doScoreCalculation: false,
        questions
      };
      formSections[i + 1] = section;
    }
    console.log("have null?-->",this.nullField)
    if(this.nullField.length>0){
        const nullError = this.nullField.map((nullObj) => {
          return `${nullObj.formSection} - ${nullObj.nullQns.join(', ')}`
        })
        this.nullField=[]
        Swal.fire({
          icon: 'error',
          title: 'The following required fields are blank:',
          text: nullError.join('\n\n & \n\n')
        });
      }
      else{
        const submitData = {
          id: this.formNo,
          "formContent": {formSections},
          adminApproverComments:""  //to check again
          };

        console.log("submit-->",submitData)
        Swal.fire({
        title: 'Save the Form?',
        text: "Please check information before saving!",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: '#c7c6c5',
        confirmButtonColor: '#6A79F3',
        confirmButtonText: 'Yes, save it!',
        cancelButtonText: 'No, Cancel',
        width: 'auto',
    }).then((result) => {
        if (result.isConfirmed) {
          try {
            const response = axios.post(`${BASE_URL}/api/form/action/submit`, submitData);
            console.log("SUCCESSFULLY POST")
            console.log(response.data); // 
          
            Swal.fire({
              title: 'Success',
              text: 'Form saved successfully! It will be reviewed by Admin in the next 7 days ',
              icon: 'success',
              timer: 2000,
              timerProgressBar: true,
              showConfirmButton: false
            }).then(() => {
              window.location.href = "/VendorView";
            });

          } catch (error) {
            if (error) {
              console.error("errrr", error)

              Swal.fire({
                icon: 'warning',
                title: error,
                timer: 2000,
                timerProgressBar: true,
                showConfirmButton: false
              })
            }
            }
        }
  })
      }
          

  },
  async submitDraft() {
    //submit without validation
    const formSections = {};
    //retrieve form data
    for (let i = 0; i < this.formattedData.length; i++) {
      const formSection = this.formattedData[i];
      const questions = {};

      for (let j = 0; j < formSection.fields.length; j++) {
        const field = formSection.fields[j];
        const inputOptions = field.options || null;
        const question = {
          qnTitle: field.label,
          inputType: field.fieldType,
          inputOptions,
          required: field.isRequired,
          answer: field.input // get value from input property
        };
        questions[j + 1] = question;
        // console.log("question-->",question)
      }
        const section = {
        sectionName: formSection.sectionTitle,
        adminUseOnly: formSection.AdminUseOnly,
        approvalViewOnly: formSection.ApproverUseOnly,
        doScoreCalculation: false,
        questions
      };
      formSections[i + 1] = section;
    }
        const submitData = {
          id: this.formNo,
          "formContent": {formSections}
          };
        // console.log("submit-->",submitData)
    
        Swal.fire({
        title: 'Save the Form as Draft?',
        text: "Please check information before saving!",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: '#c7c6c5',
        confirmButtonColor: '#6A79F3',
        confirmButtonText: 'Yes, save it!',
        cancelButtonText: 'No, Cancel',
        width: 'auto',
    }).then((result) => {
        if (result.isConfirmed) {
          try {
            const response = axios.post(`${BASE_URL}/api/form/edit`, submitData);
            console.log("SUCCESSFULLY POST")
            console.log(response.data); // 
          
            Swal.fire({
              title: 'Success',
              text: 'Form saved as Draft successfully!',
              icon: 'success',
              timer: 2000,
              timerProgressBar: true,
              showConfirmButton: false
            }).then(() => {
              window.location.href = "/VendorView";
            });

          } catch (error) {
            if (error) {
              console.error("errrr", error)

              Swal.fire({
                icon: 'warning',
                title: error,
                timer: 2000,
                timerProgressBar: true,
                showConfirmButton: false
              })
            }
            }
        }
  })
  },
  async exportToPdf () {
    const PdfFilename= this.entityUEN+"_"+this.formNo
    const options = {
      margin: 1,
      image: { type: 'jpeg', quality: 0.98 },
      html2canvas: { scale: 2 },
      jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
    }
    const element = document.getElementById('pdf-content')
    const pdfBlob = await html2pdf().set(options).from(element).output('blob')
    const formData = new FormData()
    formData.append('file', pdfBlob)
    formData.append('title', PdfFilename)

    try {
      const response = await axios.post(`${BASE_URL}/api/pdf/save`, formData)
      console.log('PDF file stored in database:', response.data)
    } catch (error) {
      console.error('Error storing PDF file in database:', error.message)
    }
    try{
      await axios.get(`${BASE_URL}/api/pdf/`+ PdfFilename)
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
    }catch(error){
      console.log("nani")
    }
    Swal.fire({
      icon: 'success',
      title: "PDF have been downloaded to your PC Downloads folder",
      timer: 5000,
      timerProgressBar: true,
      showConfirmButton: false
    })
    .then(() => {
              window.location.href = "/ApprovalView";
            });
  }
}
}
    </script>
    

<style scoped>
.main__wrapper {
    padding-top: 30%;
    margin: 0 auto;
    max-width: 900px;
    padding: 20px;
    background-color: #f5f5f5;
  }

  .comment-box {
  border: 2px solid red;
  background-color: rgb(237, 162, 162);
  color: black;
  padding: 10px;
  font-size: 16px;
  margin-bottom: 20px;
}

</style>