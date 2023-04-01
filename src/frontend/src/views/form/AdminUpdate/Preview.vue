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
              <el-row>
                <h6>Due in {{ element.deadlineDays }} days</h6>
              </el-row>
              <el-row v-if="element.formName !=''">
                <i>{{element.lastEdited}}</i>
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
                  :required= "field.required"
                  class="form__field">
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
            lastEdited:allData['lastEdited'],
            deadlineDays:allData['deadlineDays'],
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
            approvalViewOnly: formSection.ApproverUseOnly,
            doScoreCalculation: false,
            questions
          }
          formSections[i + 1] = section
        }
        const data = {
          formNo: this.editableFormInfo[0].formNo,
          formName: this.editableFormInfo[0].formName,
          lastEdited: this.editableFormInfo[0].lastEdited,
          deadlineDays:this.editableFormInfo[0].deadlineDays,
          formSections
        }
        console.log(data)

        Swal.fire({
            title: 'Save the Edited Form?',
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
                const response = axios.post(`${BASE_URL}/api/formtemplate/edit`, data);
                console.log("SUCCESSFULLY POST")
                console.log(response.data); // 
              
                Swal.fire({
                  title: 'Success',
                  text: 'Saved edited form successfully!',
                  icon: 'success',
                  timer: 2000,
                  timerProgressBar: true,
                  showConfirmButton: false
                }).then(() => {
                  window.location.href = "/WorkflowView";
                });

              } catch (error) {
                if (error) {
                  console.error("ascas", error)

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
    }
  
  }
  </script>
  