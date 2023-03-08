<template>
    <table class="my-table">
      <thead>
        <tr>
          <th v-for="(header, index) in headers" :key="index">{{ header }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in data" :key="index">
          <td v-for="(field, index) in fields" :key="index">{{ item[field] }}</td>
        </tr>
      </tbody>
    </table>
  </template>
  
<script>
  export default {
    props: {
      data: {
        type: Array,
        default: [],
      },
      headers: {
        type: Array,
        default: [],
      },
      fields: {
        type: Array,
        default: [],
      },
    },
  };
</script>

<style scoped>
.my-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
  text-align: left;
}

.my-table th,
.my-table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.my-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}
</style>