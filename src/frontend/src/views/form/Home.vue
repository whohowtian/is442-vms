<template>
<div>
  <el-container>
    
    <el-main class="el-main-left">
      <div class="wrapper--forms">
        <el-form>
          <!-- header -->
          <template v-for="element in formInfo">
            <div  style="border: solid 1px gray; margin: 0.5em">
            <el-row>
              <el-form-item label="Form Title">
                <el-input v-model="element.formName" placeholder="Form Title"  style="width: 100%;"></el-input>
              </el-form-item>
              
            </el-row>
            <el-row>
              <el-form-item label="Form No">
                <el-input v-model="element.formNo" placeholder="Form No" style="width: 100%;"></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="Deadline">
                <el-input v-model.number="element.deadlineDays" placeholder="Deadline days" style="width: 100%;" type="number">
                  <template #append>days</template>
                </el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="Date">
                <el-input v-model="element.lastEdited" disabled  style="width: 100%;"></el-input>
              </el-form-item>
              
            </el-row>
            
          </div>

          </template>
          
          <el-row>
            <el-button style="margin-bottom: 10px;" type="primary" @click="addSection">Add Section</el-button>
          </el-row>

          <!-- section -->
          <template v-for="(eachFormObj, eachFormIndex) in forms" :key="`div-${eachFormIndex}`">
            <!-- {{ eachFormObj }} -->
            <div  class="section-block">
              <div class="source">
                <el-row>
                  <el-col >
                    <el-input placeholder="Please input section title" v-model="eachFormObj.sectionTitle" ></el-input>
                  </el-col>
                  <el-col >
                    <div >
                      <el-checkbox v-model="eachFormObj.AdminUseOnly" label="AdminUseOnly"  size="small" />
                      <el-checkbox v-model="eachFormObj.ApproverUseOnly"  label="ApproverUseOnly" size="small" />
                      <el-button type="danger" round style="float: right" @click="deleteSection(eachFormIndex,eachFormObj.sectionTitle)">Delete Section</el-button>
                    </div>
                  </el-col>
                </el-row>
              </div>
              <div class="meta">
                  
                  <draggable :list="eachFormObj.fields" class="dragArea" :group="{ name:'formbuilder', pull:false, put:true }" :sort="true" ghost-class="sortable__ghost">
                    <!-- The form elements starts (on the right) -->
                    

                    <el-col v-for="(field, index) in eachFormObj.fields" :key="index" v-bind="field" class="form__group" :class="{ 'is--active': field === activeField }" >
                      
                     
                      <span class="form__selectedlabel">{{ field.fieldType }} </span>
                      <div @click="editElementProperties(field)" >
                        <component :is="field.fieldType" :currentField="field"  class="form__field" >
                        </component>
                      </div>

                      <!-- Actions list -->
                      <div class="form__actiongroup" style="display: flex; justify-content: flex-end; align-items: center;">
                        <el-icon class="el-input__icon" @click="deleteElement(index, eachFormObj.fields)"><Delete /></el-icon>
                      </div>
                    </el-col>
                    
                  </draggable>
              </div>
            </div>
          </template>
        </el-form>
      </div>

    </el-main>

    <!-- element & properties -->
    <el-aside class="wrapper--sidebar" width="30%">
      <el-tabs type="border-card" v-model="activeTabForFields">
        <el-tab-pane name="elements" label="Elements">
          <elements />
        </el-tab-pane>
        <el-tab-pane name="properties" label="Properties">
          <!-- <properties v-show="Object.keys(activeField).length > 0"></properties> -->
          <div class="el-tabs__inner">
            <el-form  label-position="top" >
              
              <!-- label name -->
              <el-row>
                <el-col :span="12">
                  <el-form-item label="Label Name" v-show="activeField.hasOwnProperty('label')">
                    <el-input v-model="activeField.label">{{activeField.label}}</el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <!-- placeholder -->
              <el-form-item label="Placeholder" v-show="activeField.hasOwnProperty('isPlaceholderVisible') && activeField['isPlaceholderVisible'] == true">
                <el-row>
                  <el-col :span="19 ">
                    <el-input v-show="activeField.isPlaceholderVisible" v-model="activeField.placeholder" >
                      {{activeField.placeholder}}
                    </el-input>
                  </el-col>
                </el-row>
              </el-form-item>

              <!-- required field -->
              <el-row>
                <el-col :span="12">
                  <el-form-item label="Required field?" v-show="activeField.hasOwnProperty('isRequired')">
                    <el-switch v-model="activeField.isRequired"></el-switch>
                  </el-form-item>
                </el-col>
              </el-row>

              <!-- select & radio  -->
              <el-form-item label="Items" v-show="activeField.hasOwnProperty('items')">
                <li v-for="(item, index) in activeField.items" :key="index" class="properties__optionslist">
                  <el-row :gutter="5">
                    <el-col :span="20">
                      <el-input v-model="item.url">{{item.url}}</el-input>
                    </el-col>
                    <el-col :span="4">
                      <el-button @click="deleteOption(activeField.items, index)" v-show="activeField.items.length > 1">
                        <i class="el-icon-error"></i>
                      </el-button>
                    </el-col>
                  </el-row>
                </li>
                <el-button type="default" @click="addItem(activeField.items)">
                  <i class="el-icon-plus"></i>
                  Add more
                </el-button>
              </el-form-item>

              <el-form-item label="Options" v-if="!activeField.isFromUrl && activeField.options">
                <ul class="properties__optionsul">
                  <li class="properties__optionslist">
                    <el-row :gutter="5">
                      <el-col :span="10">
                        Label
                      </el-col>
                      <el-col :span="10">
                        Value
                      </el-col>
                      <el-col :span="4">
                      </el-col>
                    </el-row>
                  </li>
                  <li v-for="(item, index) in activeField.options" :key="index" class="properties__optionslist">
                    <el-row :gutter="5">
                      <el-col :span="10">
                        <el-input v-model="item.optionLabel">{{item.optionLabel}}</el-input>
                      </el-col>
                      <el-col :span="10">
                        <el-input v-model="item.optionValue">{{item.optionValue}}</el-input>
                      </el-col>
                      <el-col :span="4">
                          <el-icon  @click="deleteOption(activeField.options, index)" v-show="activeField.options.length > 1"><Delete /></el-icon>

                      </el-col>
                    </el-row>
                  </li>
                </ul>
                <el-button type="default" @click="addOption(activeField.options)">
                  <i class="el-icon-plus"></i>
                  Add more
                </el-button>
              </el-form-item>

              <!-- for rating-->
              <el-row v-if ="activeField.fieldType =='Rating'">
                <el-col :span="12">
                  <el-form-item label="Show Text">
                    <el-switch @change="showTextChange" v-model="activeField.showText"></el-switch>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row v-if ="activeField.fieldType =='Rating'">
                <el-col :span="24">
                  <el-form-item label="Texts" v-show="activeField.showText">
                    <el-row :gutter="3">
                      <el-col :span="4" v-for="(text, index) in activeField.texts" :key="index">
                        <el-input v-model="activeField.texts[index]"></el-input>
                      </el-col>
                    </el-row>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row v-if ="activeField.fieldType=='Rating'">
                <el-col :span="12">
                  <el-form-item label="Show Score">
                    <el-switch @change="showScoreChange" v-model="activeField.showScore"></el-switch>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="Score Unit" v-show="activeField.showScore">
                    <el-input v-model="activeField.scoreUnit"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <!-- edit button text -->
              <el-form-item label="Button text" v-show="activeField.hasOwnProperty('buttonText')">
                <el-input v-model="activeField.buttonText">
                  {{activeField.buttonText}}
                </el-input>
              </el-form-item>

            </el-form>
          </div>
        </el-tab-pane>

      </el-tabs>

      <!-- {{ $store.activeField }} -->
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
  data(){
    return{
      formInfo: store._state.data.formInfo,
      forms: store._state.data.forms,
      activeField: store._state.data.activeField,
      activeTabForFields: store._state.data.activeTabForFields,
      sortElementOptions:FormBuilder.props.sortElementOptions
      
    }
  },
  mounted() {
    // console.log("form ->", this.forms)
    // console.log("activeField ->", this.activeField)
    console.log("store-->",this.$store)
    // console.log(FormBuilder.components.Properties)
    store._state.data.forms = this.forms
    console.log(store._state.data.forms)
  },
  components: FormBuilder.components
  ,
  methods: {

    deleteOption(option, index) {
      option.splice(index, 1)
    },
    addOption(option) {
      let count = option.length + 1;
      option.push({
        optionLabel: "Option Label " + count,
        optionValue: "Option " + count
      })
    },
    showTextChange(value) {
      if (value && this.activeField.showScore) {
        this.activeField.showScore = false;
      }
    },
    showScoreChange(value) {
      if (value && this.activeField.showText) {
        this.activeField.showText = false;
      }
    },
    deleteElement(index, form) {
      form.splice(index, 1)
      this.activeField=[]
      // FormBuilder.deleteElement(index, form)
    },
    cloneElement(index, field, form) {
      FormBuilder.cloneElement(index, field, form)
    },
    async editElementProperties(field) {
      this.activeField = field;      
      // FormBuilder.methods.editElementProperties(field);

      
    },
    addSection() {
      const formObj = {
        sectionTitle: "",
        fields: [],
        AdminUseOnly: false,
        ApproverUseOnly: false
      };
      this.forms.push(formObj);
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
