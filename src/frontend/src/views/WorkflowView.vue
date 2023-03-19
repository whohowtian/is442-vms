<script>
import NavBar from '../components/Navbar.vue';
import Header from '../components/Header.vue';
import Button from '../components/Button.vue';
import Table from "../components/Table.vue";
import fakeWorkflowData from './fakeWorkflowData';
import fakeTaskData from './fakeTaskData';
import fakeEditFormData from './fakeEditFormData';
import { Search } from '@element-plus/icons-vue'


export default {
    name: "WorkflowView",
    components: {
        NavBar,
        Header,
        Button,
        Table,
        Search
        },
    data() {
        return {
        input1:"",
        menuItems: [ //for top nav bar
            { label: 'HOME', route: '/AdminView'  },
            { label: 'ACCOUNT', route: '/AccountView'  },
            { label: 'WORKFLOW', route: '/WorkflowView'  },
            { label: 'LOGOUT', route: '/'  }
        ],
        dropdownOptions: [ //for actions dropdown options
            { label: 'Edit', value: 'edit' },
            { label: 'Delete', value: 'delete' },
            { label: 'Email', value: 'email' },
        ],
        firstNavOption:'workflowTable', //default table displaying
        secNavOption:'ActiveWorkFlow', //default table displaying

        //fake data -- in future change to api endpoint
        data1:fakeWorkflowData.active, 
        headers1:["Task","Company Name","Form No.","Stage","Status","Date Assigned","Actions"],
        fields1:["task","company","formNo","stage","status","dateAssign","Actions-Toggle"],

        data2:fakeWorkflowData.inactive, 
        headers2:["Task","Company Name","Form No.","Stage","Status","Date Assigned","Actions"],
        fields2:["task","company","formNo","stage","status","dateAssign","Actions-Toggle"],

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
        AddWorkflow() {alert('what is workflow');},
        TaskToDoAction(){
            window.open('https://media.makeameme.org/created/i-pray-to-5bed2f.jpg', '_blank');
        },
        TaskCompleted(){
            window.open('http://i.imgflip.com/31fael.jpg', '_blank');
        } 
    },

};
</script>

<template>
    <NavBar :items="menuItems" />
    <Header dept= "WORKFLOW MANAGEMENT" msg= "Create new or edit existing account within your business along with assigning each a specific role "/>

    <!-- sub nav bar [WORKFLOW / TASK/ EDIT FORM] -->
    <el-tabs v-model="firstNavOption" @tab-click="firstNavOption">
        <el-tab-pane label="WORKFLOW" name="workflowTable"></el-tab-pane>
        <el-tab-pane label="MY TASK" name="taskTable"></el-tab-pane>
        <el-tab-pane label="EDIT FORM" name="formTable"></el-tab-pane>
    </el-tabs>

    <!-- 1) workflowTable -->
    <div v-if="firstNavOption === 'workflowTable'" >
        <!-- sub nav bar [Active / Inactive] -->
        <el-tabs v-model="secNavOption"  type="border-card" @tab-click="secNavOption"  ref="tabs" >
            <el-tab-pane label="Active" name="ActiveWorkFlow"   @tab-click="secNavOption = 'ActiveWorkFlow'">
                <template #label>Active({{ data1.length }})</template>
            </el-tab-pane>
            <el-tab-pane label="Inactive" name="InActiveworkflowTable"  @tab-click="secNavOption = 'InActiveworkflowTable'">
                <template #label>Inctive({{ data2.length }})</template>
            </el-tab-pane>


        <!-- search bar and button (still unable to fit to inline) -->
        <div class="row" >
            <div class="col-sm-2">
                <el-input v-model="input1" placeholder="Search Company Name" style="width:fit-content" size="large">
                <template #suffix>
                <el-icon class="el-input__icon"><Search /></el-icon>
                </template>
                </el-input>
            </div>
            <div class="col-sm-2">
                <Button @click="AddWorkflow">+ Add Account</Button>
            </div>
        </div>

        <!-- 1.1) Active Table content -->
        <div v-if="firstNavOption === 'workflowTable' && secNavOption !== 'InActiveworkflowTable'">
            <Table :data="data1" :headers="headers1" :fields="fields1" :options="dropdownOptions" />
        </div>
        
        <!-- 1.2) InActive Table content -->
        <div v-if="secNavOption === 'InActiveworkflowTable'">
            <Table :data="data2" :headers="headers2" :fields="fields2" :options="dropdownOptions" />
        </div>
        </el-tabs>
    </div>

    <!-- 2) taskTable -->
    <div v-if="firstNavOption === 'taskTable'">
        <p>Please review the checklist below to complete any assigned tasks.</p>
        <!-- sub nav bar [ToDo / Completed] -->
        <el-tabs v-model="secNavOption"  type="border-card" @tab-click="ToDoTable"  ref="tabs" >
            <el-tab-pane label="To Do" name="ToDoTable"   @tab-click="secNavOption = 'ToDoTable'">
                <template #label>To Do({{ data3.length }})</template>
            </el-tab-pane>
            <el-tab-pane label="Completed" name="CompletedtaskTable"  @tab-click="secNavOption = 'CompletedtaskTable'">
                <template #label>Completed({{ data4.length }})</template>
            </el-tab-pane>
            <el-input v-model="input1" placeholder="Search Company Name" style="width:fit-content" size="large">
                <template #suffix>
                <el-icon class="el-input__icon"><Search /></el-icon>
                </template>
            </el-input>      
        <!-- 2.1) To-do Table content -->
        <div v-if="firstNavOption === 'taskTable' && secNavOption !== 'CompletedtaskTable'">
            <Table :data="data3" :headers="headers3" :fields="fields3" icon-class="pen-square" @action-click="TaskToDoAction" />
        </div>

        <!-- 2.2) Completed Table content -->
        <div v-if="secNavOption === 'CompletedtaskTable'">
            <Table :data="data4" :headers="headers4" :fields="fields4" icon-class="eye" @action-click="TaskCompleted" />
        </div>
    </el-tabs >
    </div>

    <!-- 3) formTable -->
    <div v-if="firstNavOption === 'formTable'">
        <div>
            <Button @click="handleButton">+ Add Form</Button>
        </div>
        <Table :data="data5" :headers="headers5" :fields="fields5" icon-class="pen-square" @action-click="TaskToDoAction" />
    </div>
</template>

<style scoped>
</style>