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
            <el-row>
              <h6>Due in {{ element.deadlineDays }} days</h6>
            </el-row>
            <el-row v-if="element.formName !=''">
              <i>{{element.lastEdited}}</i>
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
      userId:"",
    }
  },
  created() {
    //user session
    const user = JSON.parse(sessionStorage.getItem('user'));
    if(user == null){
        //hardcode pass data
        this.userId = "6426e823533ce37c3e4ddae3"
    }else{
        this.userId = user.userId
    }
    console.log("userId-->", this.userId); 
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
            required: field.isRequired
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
        formNo: this.formInfo[0].formNo,
        formName: this.formInfo[0].formName,
        lastEdited: this.formInfo[0].lastEdited,
        deadlineDays:this.formInfo[0].deadlineDays,
        formSections,
        editedBy:this.userId
      }
      console.log(data)

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
              
                Swal.fire({
                  title: 'Success',
                  text: 'Form saved successfully!',
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

<style scoped>
.main__wrapper {
    padding-top: 30%;
    margin: 0 auto;
    max-width: 900px;
    padding: 20px;
    background-color: #f5f5f5;
  }



</style>