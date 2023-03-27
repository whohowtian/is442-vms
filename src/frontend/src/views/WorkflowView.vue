<script>
import NavBar from '../components/Navbar.vue';
import Header from '../components/Header.vue';
import Button from '../components/Button.vue';
import Table from "../components/Table.vue";
import fakeWorkflowData from './fakeWorkflowData';
import fakeTaskData from './fakeTaskData';
import { Search, Edit } from '@element-plus/icons-vue'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { BASE_URL } from '../api.js';
import axios from 'axios';


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
        selectedRows: [], //tick checkbox
        selectAll: false,
        allFormData:[],
        allWorkflowData:[],
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
        headers2:["Task","Company Name","Form No.","Status","Date Assigned","Actions"],
        fields2:["task","company","formNo","status","dateAssign","Actions-Toggle"],

        data3:fakeTaskData.todo, 
        headers3:["Task","Company Name","Form No.","Date Assigned","Actions"],
        fields3:["task","company","formNo","dateAssign","Actions"],

        data4:fakeTaskData.completed, 
        headers4:["Task","Company Name","Form No.","Date Assigned","Actions"],
        fields4:["task","company","formNo","dateAssign","Actions"],
    search: ""
      }
    },
    created() {
        this.getAllFormAvail() //trigger FormTemplate API
        this.getAllWorkflow()
        },
    methods: {
        async getAllWorkflow(){
            axios.get(`${BASE_URL}/api/form`)
            .then(response => {
                var allWorkflow = response.data.data;
                console.log(allWorkflow)
              //data cleaning
                for (const workflow of allWorkflow){
                    var id = workflow.id
                    var task = workflow.formContent.formName
                    // var companyName = workflow.formName
                    var formNo = workflow.formContent.formNo
                    var status=workflow.status
                    var dateAssign = workflow.formContent.formEffDate
                    this.allWorkflowData.push({ id:id,task: task, companyName:"",formNo: formNo, stage: "",status: status, dateAssign:dateAssign})
                }
                console.log(this.allWorkflowData)

            })
            .catch(error => {
              console.log(error);
            });
        },
        async getAllFormAvail(){
          axios.get(`${BASE_URL}/api/formtemplate`)
            .then(response => {
              var allForm = response.data.data;
              //data cleaning
              for (const form of allForm){
                var id = form.id
                var formName = form.formName
                var formNo = form.formNo
                var lastEdited=form.formEffDate
                this.allFormData.push({ id: id, formName: formName, formNo: formNo, editedby:"", lastEdited: lastEdited})
              }
            //   console.log(this.allFormData)

            })
            .catch(error => {
              console.log(error);
            });
        },
        EditEachForm(formNo){ //GET FormTemplate API
            localStorage.setItem('formNo', formNo)
            window.location.href = "UpdateFormBuilder";
        },
        TaskCompleted(){
            window.open('http://i.imgflip.com/31fael.jpg', '_blank');
        },
        AddWorkflow() {alert('what is workflow');}, //dummy button function

        //table styling  function
        selectAllRows() {
            this.selectedRows = this.selectAll ? [...this.allFormData] : [];
        },
        toggleRowSelection(item, event) {
            if (event.target.tagName === 'TD') {
                const index = this.selectedRows.findIndex(selectedRow => selectedRow.id === item.id);
                if (index === -1) {
                this.selectedRows.push(item);
                } else {
                this.selectedRows.splice(index, 1);
                }
            }
        },
        isSelected(item) {
            return this.selectedRows.findIndex(selectedRow => selectedRow.id === item.id) !== -1;
        },
    },
    computed: {
        allRowsSelected() { //table styling function
            return this.selectedRows.length === this.allFormData.length;
        },
  },
    watch: { //table styling function
        selectAll(val) {
        this.selectedRows = val ? [...this.allFormData] : [];
        },
        selectedRows(val) {
        this.selectAll = val.length === this.allFormData.length;
        }
    },

};
</script>

<template>
    <NavBar :items="menuItems" />
    <Header dept= "WORKFLOW MANAGEMENT" msg= "Create new or edit existing account within your business along with assigning each a specific role "/>

    <!-- sub nav bar [WORKFLOW / TASK/ EDIT FORM] -->
    <el-tabs v-model="firstNavOption" >
        <el-tab-pane label="WORKFLOW" name="workflowTable"></el-tab-pane>
        <el-tab-pane label="MY TASK" name="taskTable"></el-tab-pane>
        <el-tab-pane label="EDIT FORM" name="formTable"></el-tab-pane>
    </el-tabs>

    <!-- 1) workflowTable -->
    <div v-if="firstNavOption === 'workflowTable'" >
        <!-- sub nav bar [Active / Inactive] -->
        <el-tabs v-model="secNavOption"  type="border-card" >
            <el-tab-pane label="Active" name="ActiveWorkFlow"  >
                <template #label>Active({{ allWorkflowData.length }})</template>
            </el-tab-pane>
            <el-tab-pane label="Inactive" name="InActiveworkflowTable"  @tab-click="secNavOption = 'InActiveworkflowTable'">
                <template #label>Inctive({{ data2.length }})</template>
            </el-tab-pane>

        <!-- search bar and button (still unable to fit to inline) -->
        <div class="row" >
            <div class="col-sm-2">
                <el-input placeholder="Search Company Name" style="width:fit-content" size="large">
                <template #suffix>
                <el-icon class="el-input__icon"><Search /></el-icon>
                </template>
                </el-input>
            </div>
            <div class="col-sm-2">
                <Button @click="AddWorkflow">+ Add Workflow</Button>
            </div>
        </div>
        {{ firstNavOption, secNavOption}}

         

        <!-- 1.1) Active Table content -->
        <!-- previous way of hardcoding table, to be changed to table component -->
        
        <div v-if="firstNavOption === 'workflowTable' && secNavOption !== 'InActiveworkflowTable'" >
            <table class="my-table">
            <thead>
                <tr>
                    <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
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
                <tr v-for="item in allWorkflowData" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
                <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                <td>{{ item.task }}</td>
                <td>{{ item.companyName }}</td>
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
        <div v-if="secNavOption === 'InActiveworkflowTable'">
            <Table :data="data2" :headers="headers2" :fields="fields2" :options="dropdownOptions" />
        </div>
        </el-tabs>
    </div>

    <!-- 2) taskTable -->
    <div v-if="firstNavOption === 'taskTable'">
        <p>Please review the checklist below to complete any assigned tasks.</p>
        <!-- sub nav bar [ToDo / Completed] -->
        <el-tabs v-model="secNavOption"  type="border-card" >
            <el-tab-pane label="To Do" name="ToDoTable" >
                <template #label>To Do({{ data3.length }})</template>
            </el-tab-pane>
            <el-tab-pane label="Completed" name="CompletedtaskTable"  @tab-click="secNavOption = 'CompletedtaskTable'">
                <template #label>Completed({{ data4.length }})</template>
            </el-tab-pane>
            <el-input placeholder="Search Company Name" style="width:fit-content" size="large">
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

    <!-- 3) formTable with API calling -->
    <div v-if="firstNavOption === 'formTable'">
        <div><a href="/FormBuilder"><Button>+ Add Form</Button></a></div>
        <table class="my-table">
            <thead>
            <tr>
                <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
                <th>Form</th>
                <th>Edited By</th>
                <th>Last Edited</th>
                <th>Actions</th>
            </tr>
            WAITING FOR FORMTEMPLATE API FIX

            </thead>
            <tbody>
                <tr v-for="item in allFormData" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
                    <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                    <td>{{ item.formName }}</td>
                    <td>{{ item.editedby }}</td>
                    <td>{{ item.lastEdited }}</td>
                    <td >
                        <el-icon class="el-input__icon" @click="EditEachForm(item.formNo)">
                            <Edit />
                        </el-icon>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
