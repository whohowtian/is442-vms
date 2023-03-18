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
// import draggable from 'vuedraggable';
import { VueDraggableNext } from 'vue-draggable-next'
import { store } from '../../store.js';

export default {
  name: 'Elements',
  // store: ['forms'],
  components: {
    // draggable
    VueDraggableNext
  },
  data() {
    return {
      fields: FormBuilder.props.fields,
      // dropElementOptions: FormBuilder.$data.dropElementOptions,
      forms: store._state.data.forms,
    };
  },
  methods: {
    clone(field) {
      return _.cloneDeep(field);
    },
    checkStopDragCondition(field) {
      var form = this.forms,
        formArray = [];

        console.log(field)
      for (var key in form) {
        console.log("key",key);
        formArray.push(form[key]['fieldType'])
      }
      // Check if the fieldname exist in formArray
      // And when the field.isUnique too
      // return _.includes(formArray, field.name) && field.isUnique;
    }
  }
}
</script>

