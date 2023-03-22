<!-- call FoemTemplate API to populate the form -->
<template>
  <div class="main__wrapper">
   
    <el-container>
      <el-main>
        <el-form>
          <template v-for="eachFormObj in formData">
            <el-divider >{{ eachFormObj.sectionName }}</el-divider>
            
              <div class="wrapper--forms">
                <el-col v-for="field in eachFormObj.questions" v-bind="field" class="form__group">
                  <!-- v-bind with the data field in our api  (radio & select not working)  && button is not important lets assume that-->
                  <component :is="field.inputType" :currentField="field" :label="field.qnTitle"  :span="2" :buttonText="field.inputType" class="form__field">
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
    import {FormBuilder} from './formbuilder'
    import axios from 'axios';

    export default {
      components: FormBuilder.components,

      data(){
        return {
          allData:[],
          formData:[],
          options: [{
          optionLabel: "Yes",
          optionValue: "Yes"
        },
        {
          optionLabel: "No",
          optionValue: "No"
        },
        {
          optionLabel: "N.A.",
          optionValue: "N.A."
        }
      ] 
          
        }
      },
      created() {
        this.getFormTemplate()
      },
      methods:{
        getFormTemplate(){
          axios.get('http://localhost:8080/api/formtemplate/TESTING3')
            .then(response => {
              this.allData = response.data.data;
              this.formData = this.allData['formSections']
              console.log(this.formData)
            })
            .catch(error => {
              console.log(error);
            });
        }

      }
}
    </script>
    