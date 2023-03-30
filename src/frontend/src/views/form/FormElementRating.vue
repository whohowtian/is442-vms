<template>
<el-form-item :label="currentField.label" :label-width="currentField.labelWidth + 'px'" :required="this.currentField.isRequired">
  <el-rate v-model="currentField.rateValue" :colors="currentField.colors" :texts="currentField.texts"
  :show-text="currentField.showText"
  :disabled="currentField.disabled"
  :show-score="currentField.showScore"
  :score-template="'{value} ' + currentField.scoreUnit"
  >
  </el-rate>
</el-form-item>
</template>


<script>
export default {
  name: 'Rating',
  props: ['currentField'],
  data() {
    return {
      value: null
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

<style>
.el-rate {
  padding-top: 12px;
}
</style>
