import { useStore } from 'vuex'

import draggable from 'vuedraggable'

import TextInput from './FormElementTextInput.vue'
import NumberInput from './FormElementNumberInput.vue'

import Elements from './Elements.vue'


export const FormBuilder ={
  components: {
    Elements,
    draggable,
    TextInput,
    NumberInput,
  },
  data() {
    return {
      fields: [{
          fieldType: 'TextInput',
          label: 'Text',
          text: 'Text',
          group: 'form', //form group
          isRequired: false,
          isHelpBlockVisible: false,
          isPlaceholderVisible: true,
          isUnique: false,
          span: 8,
          labelWidth: 100,
          advancedOptions: true,
          showPassword: false,
          disabled: false,
          clearable: false,
          prepend: '',
          append: '',
          maxlength: 10,
          showWordLimit: false
        },
        {
          fieldType: 'NumberInput',
          label: 'Number',
          text: 'Number',
          group: 'form',
          isRequired: false,
          isHelpBlockVisible: false,
          isPlaceholderVisible: false,
          isUnique: false,
          span: 8,
          labelWidth: 100,
          advancedOptions: true,
          disabled: false,
          stepStrictly: false,
          step: 1,
          hasMinValue: false,
          min: 1,
          hasMaxValue: false,
          max: 10,
        },
      ],

      sortElementOptions: {
        group: {
          name: 'formbuilder',
          pull: false,
          put: true
        },
        sort: true
      },

      dropElementOptions: {
        group: {
          name: 'formbuilder',
          pull: 'clone',
          put: false
        },
        sort: false,
        filter: ".is-disabled"
      }
    }
  },
  methods: {
    deleteElement(index, form) {
      const store = useStore()
      store.state.activeField = []
      store.state.activeTabForFields = 'elements'
      form.splice(index, 1)
    }
  }
};
