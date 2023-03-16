<template>
  <div class="mainform">

  <form class="max-w-lg w-full">
    <FormTabs>
      <div class="row FormTab" >
        <div class="col-lg-2 col-sm-4  ">
        <FormTab
          name="questions"
          label="Questions"
          :elements="['questions']"
        />
        </div>
        <div class="col-lg-2 col-sm-4  ">

        <FormTab
          name="preview"
          label="Preview"
          :elements="['preview']"
        />
        </div>
      </div>

      </FormTabs>
      <FormElements>
  
        <!-- Questions -->
        <ListElement
          name="questions"
          add-text="+"
          :sort="true"
          :override-classes="{
            ListElement: {
              add: 'addButton',
              add_md: '',
            }
          }"
        >
          <template #default="{ index }">
  
            <!-- One question -->
            <ObjectElement
              :name="index"
              add-class="mb-4"
              :override-classes="{
                ElementLayout: {
                  outerWrapper: 'flex flex-wrap',
                  innerWrapper: 'inner',
                }
              }"
            >
              <!-- Question title -->
              <StaticElement name="title">
                <div class="text-xl mb-4 font-semibold">Question {{ index + 1 }}</div>
              </StaticElement>
  
              <!-- Question -->
              <TextElement
                name="label"
                placeholder="Question"
              />
  
              <!-- Question description -->
              <TextareaElement
                name="description"
                placeholder="Description"
              />
  
              <!-- Answer type -->
              <SelectElement
                name="type"
                default="text"
                label="Type"
                :items="[
                  'text', 'textarea', 'select', 'checkboxgroup', 'radiogroup'
                ]"
              />
  
              <!-- Answer options (if any) -->
              <ListElement
                name="items"
                label="Options"
                add-text="+ Add option"
                :conditions="[
                  ['questions.*.type', ['select', 'checkboxgroup', 'radiogroup']]
                ]"
              >
                <template #default="{ index }">
                  <TextElement :name="index" />
                </template>
              </ListElement>
            </ObjectElement>
          </template>
        </ListElement>
  
        <!-- Question preview -->
        <ObjectElement
          name="preview"
          add-class="w-full"
          :override-classes="{
            ElementLayout: {
              outerWrapper: 'flex flex-wrap',
              innerWrapper: 'bg-white rounded-lg py-8 px-10 shadow-box-circle',
            }
          }"
        >
          <component
            v-for="question, i in questions"
            :is="`${question.type}-element`"
            :name="i"
            :label="question.label"
            :description="question.description"
            :items="question.items"
            :key="i"
          />
        </ObjectElement>
      </FormElements>
    </form>
  </div>
  </template>
  
<script>
  import { Vueform, useVueform } from '@vueform/vueform'
  
  export default {
    mixins: [Vueform],
    setup: useVueform,
    data() {
      return {
        vueform: {
          default: {
            questions: [
              {
                type: 'select',
                label: 'How would you rate your experience?',
                description: '1 - very unsatisfied, 5 - very satisfied',
                items: [1,2,3,4,5],
              },
              {
                type: 'textarea',
                label: 'Is there anything you would like to add?',
              }
            ]
          },
          overrideClasses: {
            FormTabs: {
              container: 'flex form-mb-gutter'
            }

          },
          addClasses: {
            ElementLabel: {
              container: 'font-semibold mb-1 text-sm uppercase text-gray-500'
            }
          }
        }
      }
    },
    computed: {
      questions() {
        return this.data.questions?.map(q=>q||{})
      },
    },
  }
</script>

<style>
.mainform{
  padding: 10px 50px;
  margin: 50px;
  border: solid 3px grey;
}
.FormTab{
  display: flex;
  justify-content: center;
  font-style: bold ;
  list-style: none;
  text-align: center;
  font: bold;
}

.addButton{
  background-color: #08a008; 
  width: 50px; 
  height: 50px; 
  color: white; 
  display: flex; 
  font-size: 3.5rem; 
  font-weight: bold; 
  margin-left: auto; 
  margin-right: auto; 
  border-radius: 50%;
  border: none;
  padding-top: 8px
}

.inner{
    border-radius: 0.5rem; 
    padding-top: 2rem; 
    padding-bottom: 2rem; 
    padding-left: 2.5rem; 
    padding-right: 2.5rem;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);

}


</style>