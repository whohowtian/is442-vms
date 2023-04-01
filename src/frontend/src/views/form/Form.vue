<template>
  <div class="main__wrapper" id="pdf-content">
    <el-container>
      <el-main>
        <el-form>
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



          <!-- pdf function visible only if is approval -->
          <button type="button" class="btn btn-primary" @click="exportToPdf">Print</button>

          <div v-if="userType === 'VENDOR'">
            <button type="button" class="btn btn-primary" @click="submitForm">Save</button>
            <button type="button" class="btn btn-primary" @click="submitDraft">Draft</button>
          </div>

          <div v-if="userType === 'APPROVER'">
            <button type="button" class="btn btn-primary" @click="ApproverApprove">Approve</button>
            <button type="button" class="btn btn-primary" @click="ApproverReject">Reject</button>
          </div>

          <div v-if="userType === 'ADMIN'">
            <button type="button" class="btn btn-primary" @click="AdminApprove">AdminApprove</button>
            <button type="button" class="btn btn-primary" @click="AdminReject">AdminReject</button>
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
        assigned_vendor_uid:'',
        formData:[],
        formattedData:[], //data structure to match formBuilder component style
        nullField:[],
        userEmail:"",
        userType:"",
        action:"",
        status:""
      }
    },
    async created(){
    //user session
    const user = JSON.parse(sessionStorage.getItem('user'));
        if(user == null){
            //hardcode pass data
            this.userEmail = "asasas.2019@gmail.com"
            this.userType ="APPROVER"
        }else{
            this.userEmail = user.userEmail
            this.userType = user.userType
        }
        console.log("userType-->", this.userType); 
        console.log("userEmail-->",this.userEmail); 

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
          const sectionData = this.formData.formSections
          console.log("allData-->",allData)
          
          //store questions dict 
          for(let i=1; i<Object.keys(sectionData).length +1; i++){
            let formTitle = sectionData[i]['sectionName']
            let adminUseOnly = sectionData[i]['adminUseOnly']
            let approvalUseOnly = sectionData[i]['approvalViewOnly']
            let allQn = []
            // console.log(formTitle, adminUseOnly)

            //disabled field
            // only for admin use only 
            let disable_section = true
            if (this.userType =="ADMIN" && adminUseOnly==true ){
                disable_section = false
            }

            // only for approver use only 
            if (this.userType =="APPROVER" && approvalUseOnly==true ){
                disable_section = false
            }

            // for vendors
            if (this.userType =="VENDOR" && adminUseOnly==false && approvalUseOnly==false){
              disable_section = false
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
        console.log("question-->",question)
      }
      if(allNull.length>0){ //store only if there is error msg
        this.nullField.push({formSection: formSection.sectionTitle, nullQns:allNull})
        }        
        console.log("allNull-->",allNull)
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
          "formContent": {formSections}
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
    }
          

  
  },
  ApproverReject(){

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
          "formContent": {formSections}
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
      await axios.get(`${BASE_URL}/api/pdf/retrieve`, {
        params: {
          fileName: PdfFilename
        }
      })
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
  }
}
}
    </script>
    