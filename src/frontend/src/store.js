import { createStore } from 'vuex'

export const store = createStore({
  state: {
    forms: [],
    activeField: [],
    activeTabForFields: 'elements'
  }
})

export default store