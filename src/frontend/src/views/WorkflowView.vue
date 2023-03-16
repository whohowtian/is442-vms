<script>
import NavBar from '../components/Navbar.vue';
import Header from '../components/Header.vue';
import Button from '../components/Button.vue';
import Table from "../components/Table.vue";
import fakeWorkflowData from './fakeWorkflowData';
import fakeTaskData from './fakeTaskData';
import fakeEditFormData from './fakeEditFormData';

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
        showDropdown: false,
        menuItems: [ //for top nav bar
            { label: 'HOME', route: '/AdminView'  },
            { label: 'ACCOUNT', route: '/AccountView'  },
            { label: 'WORKFLOW', route: '/WorkflowView'  },
            { label: 'LOGOUT', route: '/'  }
        ],
        activeOption:'workflowTable', //default table displaying

        //fake data -- in future change to api endpoint
        data1:fakeWorkflowData.active, 
        headers1:["Task","Company Name","Form No.","Stage","Status","Date Assigned","Actions"],
        fields1:["task","company","formNo","stage","status","dateAssign","Actions"],

        data2:fakeWorkflowData.inactive, 
        headers2:["Task","Company Name","Form No.","Stage","Status","Date Assigned","Actions"],
        fields2:["task","company","formNo","stage","status","dateAssign","Actions"],

        data3:fakeTaskData.todo, 
        headers3:["Task","Company Name","Form No.","Date Assigned","Actions"],
        fields3:["task","company","formNo","dateAssign","Actions"],

        data4:fakeTaskData.completed, 
        headers4:["Task","Company Name","Form No.","Date Assigned","Actions"],
        fields4:["task","company","formNo","dateAssign","Actions"],

        data5:fakeEditFormData, 
        headers5:["Form","Edited By","Last Edited","Actions"],
        fields5:["form","editedby","lastEdited","Actions"],
    }
    },
    methods: {
        handleButton() {alert('what is workflow');},
        InactiveClick(item) {
    //         item.showDropdown = !item.showDropdown;
    // },
            alert("clicked for inactive item", item);
            console.log("clicked for inactive item", item);
        },
        TaskToDoAction(){
            window.open('https://media.makeameme.org/created/i-pray-to-5bed2f.jpg', '_blank');
        },
        TaskCompleted(){
            window.open('http://i.imgflip.com/31fael.jpg', '_blank');
        } 
}
};
</script>

<template>
    <NavBar :items="menuItems" />
    <Header dept= "WORKFLOW MANAGEMENT" msg= "Create new or edit existing account within your business along with assigning each a specific role "/>

    <!-- sub nav bar [WORKFLOW / TASK/ EDIT FORM] -->
    <el-tabs v-model="activeOption" @tab-click="activeOption">
        <el-tab-pane label="WORKFLOW" name="workflowTable"></el-tab-pane>
        <el-tab-pane label="MY TASK" name="taskTable"></el-tab-pane>
        <el-tab-pane label="EDIT FORM" name="formTable"></el-tab-pane>
    </el-tabs>

    <!-- 1) workflowTable -->
    <div v-if="activeOption === 'workflowTable' || activeOption === 'InActiveworkflowTable'">
        <!-- sub nav bar [Active / Inactive] -->
        <el-tabs v-model="activeOption"  type="card" @tab-click="activeOption">
            <el-tab-pane label="Active" name="workflowTable"   @tab-click="activeOption = 'workflowTable'">
                <template #label><span>Active({{ data1.length }})</span></template>
            </el-tab-pane>
            <el-tab-pane label="Inactive" name="InActiveworkflowTable"  @tab-click="activeOption = 'InActiveworkflowTable'">
                <template #label><span>Inctive({{ data2.length }})</span></template>
            </el-tab-pane>
        </el-tabs>
                
    <div class="row">
        <div class="col-lg-2 col-sm-4  ">
            <input type="text" placeholder="Search Company Name">
        </div>
        <div class="col-lg-2 col-sm-3">
            <Button @click="handleButton">+ Add Workflow</Button>
            </div>
        </div>
        
        <!-- 1.1) Active Table content -->
        <!-- previous way of hardcoding table, to be changed to table component -->
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
                <tr v-for="item in data1" :key="item.id">
                <td>{{ item.task }}</td>
                <td>{{ item.company }}</td>
                <td>{{ item.formNo }}</td>
                <td>{{ item.stage }}</td>
                <td>{{ item.status }}</td>
                <td>{{ item.dateAssign }}</td>
                <td >
                    <div  class="btn-group dropup">

                        <Button buttonStyle="none" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            ...
                        </Button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Edit</a></li>
                            <li><a class="dropdown-item" href="#">Delete</a></li>
                            <li><a class="dropdown-item" href="#">Email</a></li>
                        </ul>
                    </div>

                </td>
                </tr>
            </tbody>
            </table>
        </div>
        
        <!-- 1.2) InActive Table content -->
        <div v-if="activeOption === 'InActiveworkflowTable'">
            <Table :data="data2" :headers="headers2" :fields="fields2" icon-class="ellipsis" @action-click="InactiveClick" />
        </div>
    </div>

    <!-- 2) taskTable -->
    <div v-if="activeOption === 'taskTable'|| activeOption === 'CompletedtaskTable'">
        <h2>My Task</h2>
        <p>Please review the checklist below to complete any assigned tasks.</p>
        <!-- sub nav bar [ToDo / Completed] -->
        <div class="row">
            <div class="col-1" @click="activeOption = 'taskTable'">
                <a href="#">To Do</a><span>({{ data3.length }})</span>
            </div>
            <div class="col-1" @click="activeOption = 'CompletedtaskTable'"> 
                <a href="#" >Completed</a><span>({{ data4.length }})</span>
            </div>
            <div class="col-lg-2 col-sm-4">
                <input type="text" placeholder="Search Company Name">
            </div>
        </div>

        <!-- 2.1) To-do Table content -->
        <div v-if="activeOption === 'taskTable'">
            <Table :data="data3" :headers="headers3" :fields="fields3" icon-class="pen-square" @action-click="TaskToDoAction" />
        </div>

        <!-- 2.2) Completed Table content -->
        <div v-if="activeOption === 'CompletedtaskTable'">
            <Table :data="data4" :headers="headers4" :fields="fields4" icon-class="eye" @action-click="TaskCompleted" />
        </div>
    </div>

    <!-- 3) formTable -->
    <div v-if="activeOption === 'formTable'">
        <div>
            <Button @click="handleButton">+ Add Form</Button>
        </div>
        <Table :data="data5" :headers="headers5" :fields="fields5" icon-class="pen-square" @action-click="TaskToDoAction" />
    </div>
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