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
              <!-- <el-row>
                <h6>{{ element.revNo }}</h6>
              </el-row> -->
              <el-row v-if="element.formName !=''">
                <i>{{element.formEffDate}}</i>
              </el-row>           
            </div>
            </template>
          
          <!-- form -->
          <template v-for="eachFormObj in editableForms">
            <el-divider content-position="left" >{{ eachFormObj.sectionTitle }}</el-divider>
              <div class="wrapper--forms">
                <el-col v-for="field in eachFormObj.fields"  v-bind="field" class="form__group">
                  <component :is="field.fieldType" :currentField="field"  class="form__field">
                  </component>
                </el-col>
              </div>          
          </template>
  
          <button type="button" class="btn btn-primary" @click=getData()>Save</button>
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
    created(){
      console.log("aha->",store._state.data.editableFormInfo)
      console.log("form->",store._state.data.editableForms)
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
          const response = await axios.post(`${BASE_URL}/api/formtemplate/create`, data);
          console.log("SUCCESSFULLY POST")
          console.log(response.data); // 
        } catch (error) {
          console.error(error);
        }
        },
    }
  
  }
  </script>
  