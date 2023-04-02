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
            Todo:[],
            Completed:[],

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
        this.getPendingApproval() //trigger Form API/APPROVED
        this.getLoginUserWorkflow()
        },
        methods: {       
        async getPendingApproval(){
            axios.get(`${BASE_URL}/api/form/formstatus/PENDING_APPROVAL`)
                .then(response => {
                    var allWorkflow = response.data.data
                    // console.log(allWorkflow)
                    axios.get(`${BASE_URL}/api/user/all`)
                    .then(response => {
                        var allUser= response.data.data;
                        console.log(allWorkflow)
                        for (const workflow of allWorkflow){
                            var id=workflow.id
                            var task = workflow.formContent.formName
                            var assigned_vendor_email = workflow.assigned_vendor_email
                            var VendorName= this.findVendorandCompanyName(assigned_vendor_email,allUser)[0]
                            var companyName = this.findVendorandCompanyName(assigned_vendor_email,allUser)[1]
                            var status=workflow.status
                            var stage=this.addStage(status)[0]
                            var Mstatus = this.addStage(status)[1]
                            var formEffDate=new Date(workflow.formEffDate).toLocaleDateString('en-GB') 
                            var archived = workflow.archived
                            var approvedBy = workflow.approver
                            
                            if(archived==false){
                                this.Todo.push({ id: id, task: task,assigned_vendor_email:assigned_vendor_email,VendorName:VendorName,companyName:companyName, status: Mstatus, stage:stage, formEffDate: formEffDate})
                            }

                        }
                    })
                    
                
                })
                .catch(error => {
                console.log(error);
                });
        },
        async getLoginUserWorkflow(){
            axios.get(`${BASE_URL}/api/form/all`)
            .then(response => {
                var allWorkflow = response.data.data;
                axios.get(`${BASE_URL}/api/user/all`)
                .then(response => {
                    var allUser= response.data.data;
                    this.allUser=allUser
                    
                    //data cleaning
                    for (const workflow of allWorkflow){
                        // console.log(workflow)
                        var id = workflow.id
                        var task = workflow.formContent.formName
                        var vendorEmail= workflow.assigned_vendor_email
                        var VendorName= this.findVendorandCompanyName(vendorEmail,allUser)[0]
                        var companyName = this.findVendorandCompanyName(vendorEmail,allUser)[1]
                        var formNo = workflow.formContent.formNo
                        var status=workflow.status
                        var stage= this.addStage(status)[0]
                        var Mstatus= this.addStage(status)[1]
                        var formEffDate = new Date(workflow.formEffDate).toLocaleDateString('en-GB')   
                        var approvedBy = workflow.approver


                        //mytask- completed - check reviewedBy field
                        if (approvedBy !== '' && approvedBy==this.userId){
                            this.Completed.push({ id:id,task: task, vendorEmail:vendorEmail,VendorName:VendorName,companyName:companyName,formNo: formNo, stage: stage,status: Mstatus, formEffDate:formEffDate})
                        }
                            
                    }
                    console.log("tryey",this.Completed)
                        
                    
                    
                })
            })
            .catch(error => {
                console.log(error);
            });
        },
        findVendorandCompanyName(vendorEmail,allUser){
            var companyName = '';
            var VendorName = '';
            
            for (const user of allUser){
                if (vendorEmail == user.email){
                    VendorName= user.name
                    companyName = user.entityName
                    
                }
            }
            
            return [VendorName,companyName];
        },
        addStage(status){ //add stage, Mstatus according to the status
            var stage = '';
            var Mstatus = '';
            if (status == "PENDING_VENDOR"){
                stage = 'Vendor'
                Mstatus = 'Pending'
            }else if (status == "PENDING_ADMIN"){
                stage = 'Admin'
                Mstatus = 'Pending'
            }else if(status == "PENDING_APPROVAL"){
                stage = 'Approver'
                Mstatus = 'Pending'
            }else if(status == "APPROVED"){
                stage = 'Completed'
                Mstatus = 'Approved'
            }else if(status =='ADMIN_REJECTED'){
                stage = 'Vendor'
                Mstatus = 'Admin Rejected'
            }else if (status == "APPROVER_REJECTED"){
                stage = 'Vendor'
                Mstatus = 'Approver Rejected'
            }
            return [stage, Mstatus]
        },
        //table styling  function
        selectAllRows() {
            this.selectedRows = this.selectAll ? [...this.Todo] : [];
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
        ViewEachForm(formID){ //GET Form API
            localStorage.setItem('formNo', formID)
            window.location.href = "ViewForm";
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
            <template #label>To Do({{ Todo.length }})</template>
        </el-tab-pane>
        <el-tab-pane label="Completed" name="CompletedtaskTable"  @tab-click="activeOption = 'CompletedtaskTable'">
            <template #label>Completed({{ Completed.length }})</template>
        </el-tab-pane>

        <!-- 2.1) To-do Table content -->
        <div v-if="activeOption === 'taskTable'&& Todo.length >0">
            <table class="my-table">
            <thead>
                <tr>
                    <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
                    <th>Task</th>
                    <th>Company Name</th>
                    <th>Stage</th>
                    <th>Status</th>
                    <th>FormEffDate</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in Todo" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
                <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                <td>{{ item.task }}</td>
                <td>{{ item.companyName }}</td>
                <td>{{ item.stage }}</td>
                <td>{{ item.status }}</td>
                <td>{{ item.formEffDate }}</td>
                <td >
                    <el-icon class="el-input__icon" @click="ApproveEachForm(item.id, item.status)">
                        <Edit />
                    </el-icon>
                </td>
                </tr>
            </tbody>
            </table>
        </div>
        <div v-if="activeOption === 'taskTable' && Todo.length ==0">You have no task for now!</div>

        <!-- 2.2) Completed Table content -->
        <div v-if="activeOption === 'CompletedtaskTable' && Completed.length >0">
        
        <table class="my-table">
        <thead>
            <tr>
                <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
                <th>Task</th>
                <th>Stage</th>
                <th>Status</th>
                <th>Vendor Assigned Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="item in Completed" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
            <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
            <td>{{ item.task }}</td>
            <td>{{ item.stage }}</td>
            <td>{{ item.status }}</td>
            <td>{{ item.formEffDate }}</td>
            <td >
                <el-icon class="el-input__icon" @click="ViewEachForm(item.id)">
                        <View />
                    </el-icon>
            </td>
            </tr>
        </tbody>
        </table>
        </div>

        <div v-if="activeOption === 'CompletedtaskTable' && Completed.length ==0">You have no task for now!</div>
        </el-tabs>
        </div>



</template>