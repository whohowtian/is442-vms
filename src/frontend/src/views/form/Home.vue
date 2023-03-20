<template>
<div>
  <el-container>
    <el-main class="el-main-left">
      <div class="wrapper--forms">
        <el-form>
          <el-row>
            <el-button style="margin-bottom: 10px;" type="primary" @click="addSection">Add Section</el-button>
          </el-row>

          <el-row>
            <div class="empty-section">Please add Sections</div>
          </el-row>

          <template v-for="(eachFormObj, eachFormIndex) in forms" :key="`div-${eachFormIndex}`">
            <div  class="section-block">
              <div class="source">
                <el-row>
                  <el-col :span="18">
                    <el-input placeholder="Please input section title" v-model="eachFormObj.title" style="width: 100%;"></el-input>
                  </el-col>
                  <el-col :span="6">
                    <el-button type="danger" round style="float: right" @click="deleteSection(eachFormIndex,eachFormObj.title)">Delete Section</el-button>
                  </el-col>
                </el-row>
              </div>
              <div class="meta">
                  
                  <draggable :list="eachFormObj.fields" class="dragArea" :group="{ name:'formbuilder', pull:false, put:true }" :sort="true" ghost-class="sortable__ghost">
                    <!-- The form elements starts (on the right) -->
                    

                    <el-col v-for="(field, index) in eachFormObj.fields" :key="index" v-bind="field" class="form__group" :class="{ 'is--active': field === activeField }" >
                      
                     
                      <span class="form__selectedlabel">{{ field.fieldType }} </span>

                      <div :span ='6'>
                        <!-- <label class="form__label" v-model="form.label" v-show="form.hasOwnProperty('label')">{{ form.label }}</label> -->
                        <component :is="field.fieldType" :currentField="field" class="form__field" >
                        </component>
                      </div>

                      <!-- Actions list -->
                      <div class="form__actiongroup" style="display: flex; justify-content: flex-end; align-items: center;">
                        <el-icon class="el-input__icon" @click="deleteElement(index, eachFormObj.fields)"><Delete /></el-icon>
                      </div>
                        <!-- <el-button circle size="mini" icon="el-icon-rank" class="form__actionitem--move"></el-button> -->
                    </el-col>
                    
                  </draggable>
              </div>
            </div>
          </template>
        </el-form>
      </div>

    </el-main>

    <el-aside class="wrapper--sidebar" width="30%">
      <el-tabs type="border-card" v-model="activeTabForFields">

        <el-tab-pane name="elements" label="Elements">
          <elements />
        </el-tab-pane>

      </el-tabs>

      <!--{{ $store.activeField }}-->
    </el-aside>
  </el-container>
</div>
</template>

<script>
import { FormBuilder } from './formbuilder';
import { store } from '../../store.js';
import { Delete } from '@element-plus/icons-vue'

export default {
  name: 'Home',
  // store: ['forms', 'activeField', 'activeTabForFields'],
  // store:[store._state.data.forms,store._state.data.activeField ],
  data(){
    return{
      forms: store._state.data.forms,
      activeField: store._state.data.activeField,
      activeTabForFields: store._state.data.activeTabForFields,
      sortElementOptions:FormBuilder.props.sortElementOptions
      
    }
  },
  mounted() {
    console.log("form ->", this.forms)
    console.log("activeField ->", this.activeField)
    console.log(FormBuilder.props)
  },
  components: FormBuilder.components
  ,
  methods: {
    deleteElement(index, form) {
      form.splice(index, 1)
      // FormBuilder.deleteElement(index, form)
    },
    cloneElement(index, field, form) {
      FormBuilder.cloneElement(index, field, form)
    },
    editElementProperties(field) {
      
      console.log("form ->", this.forms)
      console.log("activeField ->", this.activeField)
      
      FormBuilder.editElementProperties(field)
    },
    addSection() {
      const formObj = {
        title: "",
        fields: []
      };
      
      this.forms.push(formObj);
      console.log("form ->", this.forms);
    },
    deleteSection(formIndex,formTitle ) {
      // console.log("form ->", this.forms);
      // console.log("formindex", formIndex);
      this.$confirm(`Are you sure to delete the section ${formTitle} ?`, 'Warning', {
          confirmButtonText: 'OK',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          // console.log("formindex", formIndex);
          this.forms.splice(formIndex, 1)
        });
      
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.empty-section {
  text-align: center;
  font-size: 40px;
  background: linear-gradient(to bottom,#FFF,#409EFF);
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
}

.dragArea {
    margin-left: auto;
    margin-right: auto;
    position: relative;
    min-height: 30vh;
    height:fit-content;
}

.el-main-left {
  height: calc(90vh);
}

.form__selectedlabel {
    display: none;
    background: #ECF5FF;
    padding: 3px 5px;
    color: black;
    font-size: 10px;
    position: absolute;
    top: -17px;
    right: 15px;
}

.form__actionitem--move {
    position: absolute;
    right: -14px;
    top: 50%;
    transform: translateY(-50%);
    visibility: hidden;

    &:active,
    &:focus,
    &:hover {
        border-color: #409EFF;
        background: #ECF5FF;
    }
}

.form__actionlist {
    position: absolute;
    margin-top: 10px;
    visibility: hidden;
    z-index: 3;
    right: 0;
    border-radius: 2px;
}

.form__group {
    margin-bottom: 25px;
    border: 1px solid transparent;
    position: relative;

    &:hover {
        border-color: #409EFF;

        .form__actionitem--move {
            visibility: visible;
        }
    }

    &.is--active {
        border-color: #409EFF;
        background: #ECF5FF;

        .form__actionlist {
            visibility: visible;
        }
        .form__selectedlabel {
            display: inline-block;
        }
    }
}

.section-block {
    border: 1px solid #ebebeb;
    border-radius: 3px;
}

.section-block .source {
    padding: 10px
}

.section-block .meta {
    background-color: #fafafa;
    border-top: 1px solid #eaeefb;
}

</style>
