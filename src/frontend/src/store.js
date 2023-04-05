import { createStore } from 'vuex'

export const store = createStore({
  state: {
    formInfo:[{formNo:"",formName:"",lastEdited:new Date().toLocaleDateString('en-GB')}],
    editableFormInfo:[{formNo:"",formName:"",lastEdited:new Date().toLocaleDateString('en-GB')}],
    forms: [],
    activeField: [],
    activeTabForFields: 'elements',
    editableForms:[]
  }
})

export default store