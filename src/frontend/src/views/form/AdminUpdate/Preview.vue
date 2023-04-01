<template>
  <div class="main__wrapper">
    <el-container>
      <el-main>
        <el-form>
          <!-- header -->
          <template v-for="element in editableFormInfo">
              <div class="wrapper--forms ">
              <el-row >
                  <h3>{{ element.formName }}</h3>
              </el-row>
              <el-row>
                <h6>{{ element.formNo }}</h6>
              </el-row>

              <el-row v-if="element.formName !=''">
                <i>{{element.formEffDate}}</i>
              </el-row>           
            </div>
            </template>
          
          <!-- form -->
          <template v-for="eachFormObj in editableForms">
            <el-divider content-position="left" >{{ eachFormObj.sectionTitle }}</el-divider>
              <div class="wrapper--forms">
                <el-col v-for="field in eachFormObj.fields" v-bind="field" class="form__group">
                  <component :is="field.fieldType" 
                  :currentField="field" 
                  :disabled="true"
                  :required= true class="form__field">
                  </component>
                </el-col>
              </div>          
          </template>
  
          <button type="button" class="btn btn-primary" @click=submitForm()>Save</button>
        </el-form>
      </el-main>
    </el-container>
  </div>
  </template>
  
  <script>
  import {FormBuilder} from '../formbuilder'
  import { store } from '../../../store.js';
  import axios from 'axios';
  import { BASE_URL } from '../../../api.js';
  
  export default {
    components: FormBuilder.components,
    data(){
      return {
        editableFormInfo: store._state.data.editableFormInfo,
        editableForms: store._state.data.editableForms,
      }
    },
    async created(){ //remove in the future 
      if (localStorage.getItem('formNo')!= null){
        var formNo = localStorage.getItem('formNo');
        await axios.get(`${BASE_URL}/api/formtemplate/` + formNo)
        .then(response => {
          var allData = response.data.data;

          this.editableFormInfo = [
            {
            formNo:allData['formNo'],
            formName:allData['formName'],
            formEffDate:allData['formEffDate'],
          }]
          store._state.data.editableFormInfo = this.editableFormInfo
        })
        .catch(error => {
          console.log(error);
        });
    }
    },
    methods: {
       // GET METHOD
      getData(){
        const formData =this.editableForms
        console.log("Data", formData)
        console.log("info",  this.editableFormInfo)      },
      // POST METHOD
      async submitForm() {
        const formData =this.editableForms
        const formSections = {}
        for (let i = 0; i < formData.length; i++) {
          const formSection = formData[i]
          const questions = {}
          for (let j = 0; j < formSection.fields.length; j++) {
            const field = formSection.fields[j]
            const inputOptions = field.options || null
            const question = {
              qnTitle: field.label,
              inputType: field.fieldType,
              inputOptions,
              isRequired: field.isRequired
            }
            questions[j + 1] = question
          }
          const section = {
            sectionName: formSection.sectionTitle,
            adminUseOnly: formSection.AdminUseOnly,
            approvalUseOnly: formSection.ApproverUseOnly,
            doScoreCalculation: false,
            questions
          }
          formSections[i + 1] = section
        }
        const data = {
          formNo: this.editableFormInfo[0].formNo,
          formName: this.editableFormInfo[0].formName,
          formEffDate: this.editableFormInfo[0].formEffDate,
          formSections
        }
        console.log(data)
          try {
          const response = await axios.post(`${BASE_URL}/api/formtemplate/edit`, data);
          console.log("SUCCESSFULLY POST")
          console.log(response.data); // 
        } catch (error) {
          console.error(error);
        }
        },
    }
  
  }
  </script>
  