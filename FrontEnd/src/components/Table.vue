<template>
    <table class="my-table">
      <thead>
        <tr>
          <th  class="checkbox-col">
          <input type="checkbox" @click="selectAllRows" :checked="allRowsSelected">
        </th>
          <th v-for="(header, index) in headers" :key="index">{{ header }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in data" :key="index">
        <td  class="checkbox-col">
          <input type="checkbox" v-model="selectedRows" :value="item">
        </td>
        <td v-for="(field, index) in fields" :key="index">
          <!--1. dropdown option -->
          <template v-if="field === 'Actions-Toggle'">
            <div class="dropdown" :class="{ 'is-active': item.showDropdown }">
              <div class="dropdown-trigger">
                <button data-bs-toggle="dropdown" aria-expanded="false"  @click="toggleDropdown(item)">
                  <font-awesome-icon :icon="['fas', iconClass]" />
                  </button>
              </div>
              <div class="dropdown-menu">
                <div class="dropdown-content">
                  <a href="#" class="dropdown-item">Edit</a>
                  <a href="#" class="dropdown-item">Delete</a>
                </div>
              </div>
            </div>
          </template>
          <!-- 2. simple action -->
          <template v-if="field === 'Actions'">
            <font-awesome-icon :icon="['fas', iconClass]"  @click="handleActionClick(item)"/>
          </template>
          <!-- 3. basic displaying -->
          <template v-else>
            {{ item[field] }}
          </template>
        </td>
      </tr>
      </tbody>
    </table>
  </template>
  
<script>
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
  export default {
    components: {
      FontAwesomeIcon
    },
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
      iconClass: {
        type: String,
        default: "ellipsis",
      },
    },
    data(){
      return{
        selectedRows: [],
      };
    },
    computed: {
      allRowsSelected: {
        get() {
          return this.selectedRows.length === this.data.length;
        },
        set(value) {
          if (value) {
            this.selectedRows = [...this.data];
          } else {
            this.selectedRows = [];
          }
        },
      },
    },
    methods: {
      selectAllRows() {
        this.allRowsSelected = !this.allRowsSelected;
      },
      toggleDropdown(item) {
        item.showDropdown = !item.showDropdown;
      },
      handleActionClick(item) {
        this.$emit("action-click", item);
      },
    },
};
</script>

<style scoped>
.my-table {
  margin-top: 15px;
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
.checkbox-col {
  width: 1%;
  white-space: nowrap;
}
.dropdown-menu {
    min-width: 20px;
    margin: 0;
  padding: 0;
}

.dropdown-item {
  white-space: nowrap; /* prevent text wrapping */
  margin: 0;
  padding: 0;
  
}
</style>