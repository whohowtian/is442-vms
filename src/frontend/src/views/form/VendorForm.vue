<template>
  <div class="main__wrapper">
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
              <i>{{ formData.formEffDate }}</i>
            </el-row>

          <!-- form -->
          <template v-for="eachFormObj in formattedData" >
            <el-divider content-position="left" >{{ eachFormObj.sectionTitle }}</el-divider>
              <div class="wrapper--forms">
                <el-col v-for="field in eachFormObj.fields"  v-bind="field" class="form__group">
                  <component 
                      :is="field.fieldType" 
                      :currentField="field" 
                      v-model="field.input" 
                      class="form__field">
                  </component>
                </el-col>
              </div>          
          </template>

          <button type="button" class="btn btn-primary" @click="submitForm">Save</button>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

  
<script>
  import {FormBuilder} from './formbuilder'
  import axios from 'axios';
  import { BASE_URL } from '../../api.js';

  export default {
    components: FormBuilder.components,

    data(){
      return {
        formNo :'',
        assigned_vendor_uid:'',
        formData:[],
        formattedData:[], //data structure to match formBuilder component style
      }
    },
    async created(){
    if (localStorage.getItem('formNo')!= null){ //retrieve formNo vendorId passed from VendorView
      const dataValue = localStorage.getItem('formNo');
      const datalist = dataValue.split(',');
      this.formNo = datalist[0]
      this.assigned_vendor_uid = datalist[1]
      console.log("formNo-->",this.formNo, this.assigned_vendor_uid)
      await axios.get(`${BASE_URL}/api/form/` + this.formNo)
      .then(response => {
          const allData = response.data.data;
          this.formData = allData.formContent
          const sectionData = this.formData.formSections
          // console.log(this.formData)
          
          //store questions dict 
          for(let i=1; i<Object.keys(sectionData).length +1; i++){
            let formTitle = sectionData[i]['sectionName']
            let adminUseOnly = sectionData[i]['adminUseOnly']
            let allQn = []
            // console.log(formTitle, adminUseOnly)

            //store questions dict 
            for (let j =1; j< Object.keys(sectionData[i]['questions']).length +1 ; j++){
              const inputOptions = sectionData[i]['questions'][j]['inputOptions'] || null
              let qnDict={
                fieldType:sectionData[i]['questions'][j]['inputType'],
                label: sectionData[i]['questions'][j]['qnTitle'],
                options : inputOptions,
                input: sectionData[i].questions[j].answer, 
                //+ isRequired field
              };
              allQn.push(qnDict)
              // console.log("all Qn-->",allQn)
            }
            this.formattedData.push({ fields: allQn, sectionTitle: formTitle, AdminUseOnly:adminUseOnly});

          }
        }).catch(error => {
          console.log(error);
        });
    }
  },
  methods: {
  async submitForm() {
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
              // isRequired: field.isRequired,
              answer: field.input // get value from input property
            };
            questions[j + 1] = question;
            // console.log("question-->",question)
          }

          const section = {
            sectionName: formSection.sectionTitle,
            adminUseOnly: formSection.AdminUseOnly,
            approvalUseOnly: formSection.ApproverUseOnly,
            doScoreCalculation: false,
            questions
          };

          formSections[i + 1] = section;
        }
        const submitData = {
          id: this.formNo,
          "formContent": {formSections}
          };
         
        const statusData ={
          formID: this.formNo,
          assigned_vendor_uid :this.assigned_vendor_uid 
        }

      try{
        await axios.post(`${BASE_URL}/api/form/edit`, submitData);
        await axios.post(`${BASE_URL}/api/form/changestatus/submit`, statusData);
        
    // Display success message for a few seconds
    Swal.fire({
      icon: 'success',
      title: 'Form submitted successfully',
      timer: 3000, // Display message for 3 seconds
      timerProgressBar: true,
      didClose: () => {
        // Redirect to other page after message is closed
        window.location.href = 'VendorView'
      }
    });
  } catch (error) {
    // Display error message
    await Swal.fire({
      icon: 'error',
      title: 'Error submitting form',
      text: error.message
    });
  }
  }}
}
    </script>
    