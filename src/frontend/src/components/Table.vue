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
            <div  class="btn-group dropup">
              <Button buttonStyle="none" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                <font-awesome-icon :icon="['fas', iconClass]" />
              </Button>
              <ul class="dropdown-menu">
                <li v-for="(option, index) in options" :key="index">
                  <a href="#" class="dropdown-item">{{ option.label }}</a>
                </li>
              </ul>
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
  import Button from '../components/Button.vue';

  export default {
    components: {
      Button,
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
        options: { //dropdown options
        type: Array,
        default: () => []
      }
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