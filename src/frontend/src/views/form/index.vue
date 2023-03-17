<template>
<div id="app">
  <el-tabs v-model="activeName">
    <el-tab-pane label="Design" name="Design"><home/></el-tab-pane>
    <el-tab-pane label="Preview" name="Preview"><preview/></el-tab-pane>
  </el-tabs>
</div>
</template>

<script>
import Home from './Home.vue';
import Preview from './Preview.vue';

export default {
  name: 'App',
  data() {
    return {
      activeName: "Design"
    }
  },
  components: {
    Home,
    Preview
  }
}
</script>
