import { useStore } from 'vuex'

import draggable from 'vuedraggable'
import { VueDraggableNext } from 'vue-draggable-next'

import { store } from '../../store.js';
import TextInput from './FormElementTextInput.vue'
import LongTextInput from './FormElementLongTextInput.vue'
import NumberInput from './FormElementNumberInput.vue'
import RadioButton from './FormElementRadioButton.vue'
import SelectList from './FormElementSelectList.vue'
import DatePicker from './FormElementDatePicker.vue'
import Rating from './FormElementRating.vue'
import Button from './FormElementButton.vue'

import Elements from './Elements.vue'
import { clone } from 'lodash';
import _ from 'lodash';

import Properties from './properties/Properties.vue'



export const FormBuilder ={
  components: {
    Elements,
    // draggable,
    VueDraggableNext,
    TextInput,
    LongTextInput,
    NumberInput,
    RadioButton,
    SelectList,
    DatePicker,
    Rating,
    Button,
    Properties
  },
  props:{
    fields: [
      {
      fieldType: 'TextInput',
      label: 'Text',
      text: 'Text',
      group: 'form', //form group
      value:"",
      isRequired: false,
      isHelpBlockVisible: false,
      isPlaceholderVisible: true,
      isUnique: false,
      span: 6,
      labelWidth: 100,
      advancedOptions: true,
      showPassword: false,
      disabled: false,
      clearable: false,
      prepend: 'dd',
      append: '',
      maxlength: 10,
      showWordLimit: false
    },
    {
      fieldType: 'NumberInput',
      label: 'Number',
      text: 'Number',
      group: 'form',
      value:"",
      isRequired: false,
      isHelpBlockVisible: false,
      isPlaceholderVisible: false,
      isUnique: false,
      span: 6,
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
    {
      fieldType: 'RadioButton',
      label: 'Radio',
      text: 'Radio',
      group: 'form',
      value:"",
      isRequired: false,
      isHelpBlockVisible: false,
      isPlaceholderVisible: false,
      isUnique: false,
      span: 6,
      labelWidth: 100,
      isFromUrl: false,
      options: [{
          optionLabel: "Yes",
          optionValue: "Yes"
        },
        {
          optionLabel: "No",
          optionValue: "No"
        },
        {
          optionLabel: "N.A.",
          optionValue: "N.A."
        }
      ],
      advancedOptions: true,
      dataUrl: '',
      labelField: 'label',
      valueField: 'value'
    },
    {
      fieldType: 'LongTextInput',
      label: 'Long Text',
      text: 'Long Text',
      group: 'form',
      value:"",
      isRequired: false,
      isHelpBlockVisible: false,
      isPlaceholderVisible: true,
      isUnique: false,
      span: 50,
      labelWidth: 100
    },
    {
      fieldType: 'SelectList',
      label: 'Select',
      text: 'Select',
      group: 'form',
      value:"",
      isRequired: false,
      isHelpBlockVisible: false,
      isPlaceholderVisible: false,
      isUnique: false,
      span: 5,
      labelWidth: 100,
      isFromUrl: false,
      dataUrl: '',
      options: [{
          optionLabel: "Option Label 1",
          optionValue: "Option 1"
        },
        {
          optionLabel: "Option Label 2",
          optionValue: "Option 2"
        }
      ],
      advancedOptions: true,
      dataUrl: '',
      labelField: 'label',
      valueField: 'value',
      disabled: false,
      clearable: false,
      multiple: false,
      filterable: false,
      remote: false
    },
    {
      fieldType: 'DatePicker',
      label: 'Date',
      text: 'Date',
      group: 'form',
      value:"",
      isRequired: false,
      isHelpBlockVisible: false,
      isPlaceholderVisible: false,
      isUnique: false,
      span: 5,
      labelWidth: 100
    },
    {
      fieldType: 'Rating',
      label: 'Rating',
      text: 'Rating',
      group: 'form',
      value:"",
      isRequired: false,
      isHelpBlockVisible: false,
      isPlaceholderVisible: false,
      isUnique: false,
      span: 5,
      labelWidth: 100,
      advancedOptions: true,
      rateValue: 0,
      showText: true,
      disabled: false,
      showScore: false,
      scoreUnit: 'Points',
      colors: ['#AAAAAA', '#F7BA2A', '#FF9900'],
      texts: ['Poor', 'Below Average', 'Average', 'Above Average', 'Good']
    },
    {
      fieldType: 'Button',
      text: 'Button',
      group: 'button',
      buttonText: 'Button',
      value:"",
      isRequired: false,
      isHelpBlockVisible: false,
      isPlaceholderVisible: false,
      isUnique: true,
      span: 5,
      labelWidth: 100
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
    
    cloneElement(index, field, form) {
      var cloned = _.cloneDeep(field) // clone deep lodash
      form.splice(index, 0, cloned)
    },
    async editElementProperties(field) {
      
      store.state.activeField= field;
      store.state.activeTabForFields="properties";
      console.log(store.state.activeField);
   

    }
  }
};
