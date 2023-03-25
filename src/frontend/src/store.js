import { createStore } from 'vuex'

export const store = createStore({
  state: {
    formInfo:[{formNo:"",formName:"",formEffDate:new Date().toLocaleDateString()}],
    forms: [],
    activeField: [],
    activeTabForFields: 'elements',
    editableForms:[]
  }
})

export default store