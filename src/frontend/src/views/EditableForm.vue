<template>
    <form class="max-w-lg w-full">
      <FormTabs>
        <FormTab
          name="questions"
          label="Questions"
          :elements="['questions']"
        />
        <FormTab
          name="preview"
          label="Preview"
          :elements="['preview']"
        />
      </FormTabs>
      <FormElements>
  
        <!-- Questions -->
        <ListElement
          name="questions"
          add-text="+"
          :sort="true"
          :override-classes="{
            ListElement: {
              add: 'bg-green-500 w-10 h-10 text-white flex items-center justify-center text-xl font-bold mx-auto rounded-full',
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
                  innerWrapper: 'bg-white rounded-lg py-8 px-10 shadow-box-circle',
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
            },
            FormTab: {
              container: 'flex-1',
              wrapper: 'py-2 px-4 flex items-center justify-center rounded-lg',
              wrapper_active: 'font-semibold bg-green-500 text-white',
              wrapper_inactive: 'text-gray-500',
            },
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