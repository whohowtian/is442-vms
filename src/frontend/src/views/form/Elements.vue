<template>
<div class="el-tabs__inner">
  <el-row :gutter="10" class="row-bg">
    <!-- <draggable :list="fields" :clone="clone"
    class="dragArea"
    :group= "{ name:'formbuilder', pull:'clone', put:false }"
    :sort="false" 
>
      <el-col :span="12" :class="{ 'is-disabled': checkStopDragCondition(field) }" v-for="(field, index) in fields" :key="index">
        <el-button class="button__sidebar">
          {{ field.text }}
        </el-button>
      </el-col>
    </draggable> -->

        <draggable :list="fields" :clone="clone"
    class="dragArea"
    :group= "{ name:'formbuilder', pull:'clone', put:false }"
    :sort="false" 
>
      <div v-for="(field, index) in fields" :key="index" >
        <el-col :span="12" :class="{ 'is-disabled': checkStopDragCondition(field) }">
        <el-button class="button__sidebar">
          {{ field.text }}
        </el-button>
      </el-col>
        
      </div>

    </draggable>
  </el-row>
</div>
</template>

<script>
import { FormBuilder } from './formbuilder.js';
import draggable from 'vuedraggable';
import { VueDraggableNext } from 'vue-draggable-next'

import { store } from '../../store.js';
import { clone } from 'lodash';
import _ from 'lodash';

export default {
  name: 'Elements',
  // store: ['forms'],
  components: {
    // draggable,
    draggable: VueDraggableNext
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
      console.log(field.text);
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


<style lang="scss" scoped>
.button__sidebar {
    width: 100%;
    margin-bottom: 5px;

    .is-disabled & {
        opacity: 0.4;
    }
}

// Display this ghost in <main> only
 .sortable__ghost {
    position: relative;
    width: 33.33%;
    border-bottom: 2px solid #3A8EE6;
    margin-top: 2px;
    margin-bottom: 2px;

    [type="button"] {
        display: none;
    }

    &:before {
        content: "Drag it here";
        background-color: #ECF5FF;
        color: #3A8EE6;
        position: absolute;
        left: 50%;
        font-size: 10px;
        border-radius: 10px;
        line-height: 15px;
        padding: 0 10px;
        top: -6px;
        transform: translateX(-50%);
    }
}
</style>



