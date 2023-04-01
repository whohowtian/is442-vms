<template>
<div class="main__wrapper">
  <el-container>
    <el-main>
      <el-form>
        <!-- header -->
        <template v-for="element in formInfo">
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
        <template v-for="eachFormObj in forms">
          <el-divider content-position="left" >{{ eachFormObj.sectionTitle }}</el-divider>
            <div class="wrapper--forms">
              <el-col v-for="field in eachFormObj.fields"  v-bind="field" class="form__group">
                <component :is="field.fieldType" :currentField="field"  class="form__field">
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
import {FormBuilder} from './formbuilder'
import { store } from '../../store.js';
import axios from 'axios';
import { BASE_URL } from '../../api.js';

export default {
  components: FormBuilder.components,
  data(){
    return {
      formInfo: store._state.data.formInfo,
      forms: store._state.data.forms,
    }
  },
  methods: {
     // GET METHOD
    getData(){
      const formData =this.forms
      // console.log("Data", formData)
      // console.log("info",  this.formInfo)     
     },
    // POST METHOD
    async submitForm() {
      const formData =this.forms
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
        formNo: this.formInfo[0].formNo,
        formName: this.formInfo[0].formName,
        formEffDate: this.formInfo[0].formEffDate,
        formSections
      }
      // console.log(data)

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
                const response = axios.post(`${BASE_URL}/api/formtemplate/create`, data);
                console.log("SUCCESSFULLY POST")
                console.log(response.data); // 
              } catch (error) {
                if(error){
                  console.error("ascas",error)
                  // alert('Error: ${error}. <br/> Please Try Again Later')
                  Swal.fire({
                          icon: 'warning',
                          title: error,
                          timer: 2000,
                          timerProgressBar: true,
                          showConfirmButton: false
                      })
                }else{
                  window.location.href = "/WorkflowView";
                }
                }
            }
      })
    },
  
}
}
</script>
