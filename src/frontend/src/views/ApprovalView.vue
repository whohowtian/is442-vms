<script>
    import NavBar from '../components/Navbar.vue';
    import Button from '../components/Button.vue';
    import Table from "../components/Table.vue";
    import axios from 'axios';
    import { BASE_URL } from '../api.js';


    export default {
        name: "ApprovalView",
        components: {
            NavBar,
            Table,
            Button,
         },
        data() {
            return {
            menuItems: [
                { label: 'HOME', route: '/ApprovalView'  },
                { label: 'LOGOUT', route: '/'  }
            ],
            userId :'',
            userEmail:'',
            userName:'',
            activeOption:'taskTable', //default table displaying
            selectedRows: [], //tick checkbox
            selectAll: false,
            allToDo:[],
            allCompleted:[],

            }
        },
        created() {
        //user session
        const user = JSON.parse(sessionStorage.getItem('user'));
        if(user == null){
            //hardcode pass data
            this.userId = "6427c661cc076d5d8eb67025"
            this.userEmail = "kwwww@gmail.com"
            this.userName = "KWWW"

        }else{
            this.userId = user.userId
            this.userEmail = user.userEmail
            this.userName = user.name
        }
        console.log("userId-->", this.userId); 
        console.log("userEmail-->",this.userEmail, this.userName); 
        this.getAllWorkflow() //trigger Form API/APPROVED
        },
        methods: {
        addStage(status){ //add stage according to the status
            var stage = '';
            if (status == "PENDING_VENDOR"){
                stage = 'Vendor'
            }else if (status == "PENDING_REVIEW"){
                stage = 'Admin'
            }else if(status == "PENDING_APPROVAL"){
                stage = 'Approver'
            }else if(status == "APPROVED"){
                stage = 'Completed'
            }else if(status =='ADMIN_REJECTED'){
                stage = 'Vendor'
            }else if (status == "APPROVER_REJECTED"){
                stage = 'Vendor'
            }
            return stage
        },        
        async getAllWorkflow(){
            axios.get(`${BASE_URL}/api/form/formstatus/PENDING_APPROVAL`)
                .then(response => {
                    var allWorkflow = response.data.data
                    console.log(allWorkflow)
                    for (const workflow of allWorkflow){
                        var id=workflow.id
                        var task = workflow.formContent.formName
                        var formNo = workflow.formContent.formNo
                        var status=workflow.status
                        var stage=this.addStage(status)
                        var vendor=workflow.assigned_vendor_email
                        this.allToDo.push({ id: id, task: task, formNo: formNo, stage:stage, status: status,vendor: vendor})
                }
                console.log("todo-->", this.allToDo)
                })
                .catch(error => {
                console.log(error);
                });
        },
        //table styling  function
        selectAllRows() {
            this.selectedRows = this.selectAll ? [...this.allToDo] : [];
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
        ApproveEachForm(formId,status){
            localStorage.setItem('formNo', [formId,status])
            window.location.href = "Form";
        },
        },
    };
</script>

<template>
    <NavBar :items="menuItems" />
    <div class="row mt-4 mb-5" id = 'welcome'>
        <div class="line">
            <p style = "font-weight:600;">WELCOME, {{ userName }}</p>
        </div>
    </div>
    
    <div>
    <h2>My Task</h2>
    <p>Please review the checklist below to complete any assigned tasks.</p>
    <el-tabs v-model="activeOption"  type="border-card">
        <el-tab-pane label="To Do" name="taskTable"  @tab-click="activeOption = 'taskTable'">
            <template #label>To Do({{ allToDo.length }})</template>
        </el-tab-pane>
        <el-tab-pane label="Completed" name="CompletedtaskTable"  @tab-click="activeOption = 'CompletedtaskTable'">
            <template #label>Completed({{ 0 }})</template>
        </el-tab-pane>

        <!-- 2.1) To-do Table content -->
        <div v-if="activeOption === 'taskTable'">
            <table class="my-table">
            <thead>
                <tr>
                    <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
                    <th>Task</th>
                    <th>Form No</th>
                    <th>Vendor</th>
                    <th>Stage</th>
                    <th>Status</th>
                    <th>Date Assigned</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in allToDo" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
                <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                <td>{{ item.task }}</td>
                <td>{{ item.formNo }}</td>
                <td>{{ item.vendor }}</td>
                <td>{{ item.stage }}</td>
                <td>{{ item.status }}</td>
                <td>date assigned</td>
                <td >
                    <el-icon class="el-input__icon" @click="ApproveEachForm(item.id, item.status)">
                        <Edit />
                    </el-icon>
                </td>
                </tr>
            </tbody>
            </table>
        </div>

        <!-- 2.2) Completed Table content -->
        <div v-if="activeOption === 'CompletedtaskTable'">
            <!-- <Table :data="data2" :headers="headers2" :fields="fields2" icon-class="eye" @action-click="TaskCompleted" /> -->
        </div>
    </el-tabs>
    </div>



</template>