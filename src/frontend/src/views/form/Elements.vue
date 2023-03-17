<template>
<div class="el-tabs__inner">
  <el-row :gutter="10" class="row-bg">
    <draggable :list="fields" :clone="clone"
    class="dragArea"
    :group= "{ name:'formbuilder', pull:'clone', put:false }"
    :sort="false">
      <el-col :span="12" :class="{ 'is-disabled': checkStopDragCondition(field) }" v-for="(field, index) in fields" :key="index">
        <el-button class="button__sidebar">
          {{ field.text }}
        </el-button>
      </el-col>
    </draggable>
  </el-row>
</div>
</template>

<script>
import { FormBuilder } from './formbuilder.js';
import draggable from 'vuedraggable';

export default {
  name: 'Elements',
  store: ['forms'],
  components: {
    draggable
  },
  data() {
    return {
      fields: FormBuilder.$data.fields,
      dropElementOptions: FormBuilder.$data.dropElementOptions
    };
  },
  methods: {
    clone(field) {
      return _.cloneDeep(field);
    },
    checkStopDragCondition(field) {
      var form = this.forms,
        formArray = [];

        console.log(form)
      for (var key in form) {
        formArray.push(form[key]['fieldType'])
      }
      // Check if the fieldname exist in formArray
      // And when the field.isUnique too
      return _.includes(formArray, field.name) && field.isUnique;
    }
  }
}
</script>

