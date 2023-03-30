<template>
<el-form-item :label="currentField.label" :label-width="currentField.labelWidth + 'px'" :required="this.currentField.isRequired">
  <el-input type="textarea" :rows="2" :placeholder="currentField.isPlaceholderVisible ? currentField.placeholder : ''" v-model="currentField.input" v-bind="attributesBinding">
  </el-input>
</el-form-item>
</template>


<script>
export default {
  name: 'LongTextInput',
  props: ['currentField'],
  data() {
    return {
      textarea: ''
    }
  },
  computed: {
    attributesBinding() {
      var attr = {};
      if (this.currentField.showWordLimit) {
        attr.maxlength = this.currentField.maxlength;
        attr['show-word-limit'] = this.currentField.showWordLimit
      }

      if (this.currentField.clearable) {
        attr.clearable = this.currentField.clearable;
      }

      if (this.currentField.showPassword) {
        attr['show-password'] = this.currentField.showPassword;
      }

      if (this.currentField.isPlaceholderVisible) {
        attr.placeholder = this.currentField.placeholder;
      }
      if (this.currentField.disabled) {
        attr.disabled = this.currentField.disabled;
      }

      return attr;
    }
  }
}
</script>
