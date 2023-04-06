<template >
      <nav class="row navbar navbar-expand-lg ">
      <div class="container-fluid">

        <a class="navbar-brand" href="#"><img src="../../assets/icons/CompanyLogo.png" alt="Logo" class="logo" ></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

      </div>
    </nav>
    <div class="main__wrapper" id="pdf-content">
        <!-- pdf function visible only if is approval -->
        <!-- <button type="button" class="btn btn-primary" @click="exportToPdf">Print</button> -->

        <el-container style=" margin-top: 5%;">
        <el-main >
            <el-form >
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
                <div class="wrapper--forms" id="form">
                    <el-col v-for="field in eachFormObj.fields"  v-bind="field" class="disabled-input" >
                    
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
        userEmail:"",
        userName:"",
        userType:"",
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
        }else{
            this.userEmail = user.userEmail
            this.userType = user.userType
            this.userName = user.name
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
          var vendorEmail = allData.assigned_vendor_email
          const sectionData = this.formData.formSections
          // console.log("allData-->",allData)

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
            // console.log(this.entityUEN)
          
          //store questions dict 
          for(let i=1; i<Object.keys(sectionData).length +1; i++){
            let formTitle = sectionData[i]['sectionName']
            let adminUseOnly = sectionData[i]['adminUseOnly']
            let approvalUseOnly = sectionData[i]['approvalViewOnly']
            let allQn = []
            // console.log(formTitle, adminUseOnly)

            //disabled field then css overwrite
            let disable_section = true


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
        })
        }).catch(error => {
          console.log(error);
        });
    }
  }
  }

</script>

<style scoped>
.main__wrapper {
    padding-top: 30%;
    margin: 0 auto;
    max-width: 800px;
    padding: 20px;
    background-color: #f5f5f5;
  }
.form__field{
    opacity: 1; 
}

.disabled-input{
    opacity: 1; 

}
#form{
  opacity: 1; 
}

.navbar {
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    height:8vh;
    background-color:#fff;
  }
  .logo {
  height: 50px;
  margin: 0px 30px;
  }
  @media (max-width: 767px) {
    .logo {
      height: 40px;
      margin: 0px;
    }
  }


</style>