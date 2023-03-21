<template>
<div class="main__wrapper">
 
  <el-container>
    <el-main>
      <el-form>
        <template v-for="(eachFormObj, eachFormIndex) in forms" :key="`divider-${eachFormIndex}`">
          
          <el-divider >{{ eachFormObj.title }}</el-divider>
          
            <div class="wrapper--forms">
              <el-col v-for="(field, index) in eachFormObj.fields" :key="index" :span="field.span" v-bind="field" class="form__group">
                <component :is="field.fieldType" :currentField="field" class="form__field">
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
import { store } from '../../store.js';

export default {
  name: 'Publish',
  components: FormBuilder.components,
  data(){
    return {
      forms: store._state.data.forms
    }
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
