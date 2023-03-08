<script>
import NavBar from '../components/Navbar.vue';
import Header from '../components/Header.vue';
import Button from '../components/Button.vue';
import Table from "../components/Table.vue";
import fakeDataWorkflow from './fakeDataWorkflow';


    export default {
        name: "WorkflowView",
        components: {
            NavBar,
            Header,
            Button,
            Table,
         },
        data() {
            return {
            menuItems: [ //for top nav bar
                { label: 'HOME', route: '/AdminView'  },
                { label: 'ACCOUNT', route: '/AccountView'  },
                { label: 'WORKFLOW', route: '/WorkflowView'  },
                { label: 'LOGOUT', route: '/'  }
            ],
            activeOption:'workflowTable', //default table displaying
            inactiveCount: 2, //hardcode for now
            data:fakeDataWorkflow, //in future change to api endpoint
            headers:["Task","Company Name","Form No.","Stage","Status","Date Assigned","Actions"],
            fields:["task","company","formNo","stage","status","dateAssign"],
            expandedItemId: null,

        }
        },
        methods: {
            handleButton() {alert('what is workflow');},
            expandDropdown(itemId) {
                console.log(itemId)
                if (this.expandedItemId === itemId) {
                this.expandedItemId = null;
                } else {
                this.expandedItemId = itemId;
                }}
         }
    };
</script>

<template>
    <NavBar :items="menuItems" />
    <Header dept= "WORKFLOW MANAGEMENT" msg= "Create new or edit existing account within your business along with assigning each a specific role "/>

    <!-- sub nav bar [WORKFLOW / TASK/ EDIT FORM] -->
    <div class="row">
        <div class="col-1" @click="activeOption = 'workflowTable'">
            <a href="#">WORKFLOW</a>
        </div>
        <div class="col-1" @click="activeOption = 'taskTable'"> 
            <a href="#" >MY TASK</a>
        </div>
        <div class="col-2" @click="activeOption = 'formTable'">
            <a  href="#">EDIT FORM</a>
        </div>
    </div>

    <!-- 1) workflowTable -->
    <div v-if="activeOption === 'workflowTable' || activeOption === 'InActiveworkflowTable'">
        <!-- sub nav bar [Active / Inactive] -->
        <div class="row">
            <div class="col-1" @click="activeOption = 'workflowTable'">
                <a href="#">Active</a><span>({{ data.length }})</span>
            </div>
            <div class="col-1" @click="activeOption = 'InActiveworkflowTable'"> 
                <a href="#" >Inactive</a><span>({{ inactiveCount }})</span>
            </div>
            <div class="col-lg-2 col-sm-4  ">
                <input type="text" placeholder="Search Company Name">
            </div>
            <div class="col-lg-2 col-sm-3">
                <Button @click="handleButton">+ Add Workflow</Button>
            </div>
        </div>

        <!-- 1.1) Active Table content -->
        <div v-if="activeOption === 'workflowTable'">
            <table class="my-table">
            <thead>
                <tr>
                    <th>Task</th>
                    <th>Company Name</th>
                    <th>Form No.</th>
                    <th>Stage</th>
                    <th>Status</th>
                    <th>Date Assigned</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in data" :key="item.id">
                <td>{{ item.id }}</td>
                <td>{{ item.company }}</td>
                <td>{{ item.formNo }}</td>
                <td>{{ item.stage }}</td>
                <td>{{ item.status }}</td>
                <td>{{ item.dateAssign }}</td>
                <td><Button  button-style="none" @click="expandDropdown(item.id)">...</Button>
                    <div v-if="expandedItemId === item.id" class="dropdown">
                        <!-- Dropdown content-->
                        <p>hahah</p>
                        <!-- <p>Details for item {{ item.id }}:</p>
                            <ul>
                            <li>Details 1</li>
                            <li>Details 2</li>
                            <li>Details 3</li>
                            </ul> -->
                    </div>
                </td>
                </tr>
            </tbody>
            </table>
        </div>
        
        <!-- 1.2) InActive Table content -->
        <div v-if="activeOption === 'InActiveworkflowTable'">
            <Table :data="data" :headers="headers" :fields="fields" />
        </div>
        
    </div>

    <!-- 2) taskTable -->
    <div v-if="activeOption === 'taskTable'">
      <h2>My Task</h2>
      <p>Please review the checklist below to complete any assigned tasks.</p>
      <table>
        <!-- Table 2 content -->

      </table>
    </div>

    <!-- 3) formTable -->
    <div v-if="activeOption === 'formTable'">
      <h2>Table 3</h2>
      <table>
        <!-- Table 3 content -->
      </table>
    </div>
    <!-- table -->
</template>

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

.dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  z-index: 1;
  padding: 10px;
  width: 200px;
}
</style>