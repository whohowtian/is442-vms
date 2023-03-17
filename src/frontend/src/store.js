import { createStore } from 'vuex'

const store = createStore({
  state: {
    forms: [],
    activeField: [],
    activeTabForFields: 'elements'
  }
})

export default store