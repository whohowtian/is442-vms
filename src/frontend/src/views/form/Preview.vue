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

        <!-- change to submitForm() to post API , put a unique formNo:"XXX" in "data"
 -->
        <button type="button" class="btn btn-primary" @click=getData()>Save</button>
      </el-form>
    </el-main>
  </el-container>
</div>
</template>

<script>
import {FormBuilder} from './formbuilder'
import { store } from '../../store.js';
import axios from 'axios';

export default {
  components: FormBuilder.components,
  data(){
    return {
      formInfo: store._state.data.formInfo,
      forms: store._state.data.forms,
      AllSections:[], //sectionName , adminUseOnly(default False), doScoreCalculation(default False), 
      AllQuestion:[]
    }
  },mounted(){
    console.log(store._state.data.forms)
    console.log(store._state.data.formInfo)
  },
  methods: {
    // calculate today date
    getToday() {
      let today = new Date();
      let year = today.getFullYear();
      let month = String(today.getMonth() + 1).padStart(2, '0');
      let day = String(today.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    },
     // GET METHOD
    getData(){
      const formData =this.forms
      console.log("Data", formData)
      },
    // POST METHOD
    async submitForm() {
      const formData =this.forms

      //store formSection dict
      for(let i=0; i<formData.length; i++){
        let formTitle = formData[i]['title']
        let allQn = []

        //store questions dict 
        for (let j =0; j< (formData[i]['fields']).length; j++){
          let qnDict={
            qnTitle:formData[i]['fields'][j]['label'],
            inputType: formData[i]['fields'][j]['fieldType'],
            isRequired: formData[i]['fields'][j]['isRequired'], //required field
          };
          allQn.push(qnDict)
        }

        this.AllSections.push({
          sectionName: formTitle,
          adminUseOnly: false,
          doScoreCalculation: false,
          questions:allQn
          }
          );
        }
      // console.log("OUTPUT", this.AllSections)

        const data = {
            "formNo": this.formNo,
            "formName": this.formName,
            "formEffDate": this.formEffDate,
            "formSections": this.AllSections,
        }
        console.log(data)
          try {
          const response = await axios.post('http://localhost:8080/api/formtemplate/create', data);
          console.log("SUCCESSFULLY POST")
          console.log(response.data); // do something with the response data
        } catch (error) {
          console.error(error);
        }
      },
  }
  // computed: {
  //   cssProps() {
  //     // Return an object that will generate css properties key
  //     // to match with the themingVars
  //     //
  //     // Example output: { '--theme-primary-color': this.themingVars.primaryColor }
  //     var result = {},
  //       themingVars = this.themingVars;

  //     for (var v in themingVars) {
  //       if (themingVars.hasOwnProperty(v)) {
  //         var newV = "--theme-" + _.kebabCase(v),
  //           suffix = "";

  //         // Add px to the value if the default value contain 'px'
  //         if (_.includes(newV, 'size')) suffix = "px"
  //         else if (_.includes(newV, 'margin')) suffix = "px"
  //         else if (_.includes(newV, 'radius')) suffix = "px"

  //         result[newV] = themingVars[v] + suffix;
  //       }
  //     }
  //     return result;
  //   }
  // }
}
</script>
