<template>
  <el-form-item :label="currentField.label" :label-width="currentField.labelWidth + 'px'" :required="this.currentField.isRequired">
    <el-date-picker
      v-model="currentField.input"
      type="date"
      placeholder="Pick a day">
    </el-date-picker>
  </el-form-item>
</template>


<script>
  export default {
    name: 'DatePicker',
    props: ['currentField'],
    data() {
    	return {
    		date: ''
    	}
    }
  }
</script>
