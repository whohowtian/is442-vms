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
                      :input="field.answer" 
                      @input="updateFormValues(field.label, field.answer, $event)"
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
        formData:[],
        formattedData:[], //data structure to match formBuilder component style
        formValues: {}, // object to hold form field values
      }
    },
    async created(){
    if (localStorage.getItem('formNo')!= null){
      const formNo = localStorage.getItem('formNo');
      console.log("formNo-->",formNo)
      await axios.get(`${BASE_URL}/api/form/` + formNo)
      .then(response => {
          const allData = response.data.data;
          this.formData = allData.formContent
          const sectionData = this.formData.formSections

          console.log(sectionData)
          
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
                answer: sectionData[i].questions[j].answer, // added inputName property

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
  submitForm() {
    // Create an empty object to store form values
    const formAns = {};

    // Loop through each field in the form and store the value in formData
    for (const field of Object.keys(this.formValues)) {
      formAns[field] = this.formValues[field];
    }

    // Now you can do whatever you want with the formData object
    console.log(formAns);
  },
  updateFormValues(field, fieldName, value) {
  // Update the formValues object with the new value
  this.$set(this.formValues,field ,fieldName, value);
  console.log(field)
}
    
}
}
    </script>
    