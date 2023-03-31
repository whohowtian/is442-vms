import { createStore } from 'vuex'

export const store = createStore({
  state: {
    formInfo:[{formNo:"",formName:"",lastEdited:new Date().toLocaleDateString('en-GB'), deadlineDays:0}],
    editableFormInfo:[{formNo:"",formName:"",lastEdited:new Date().toLocaleDateString('en-GB'), deadlineDays:0}],
    forms: [],
    activeField: [],
    activeTabForFields: 'elements',
    editableForms:[]
  }
})

export default store