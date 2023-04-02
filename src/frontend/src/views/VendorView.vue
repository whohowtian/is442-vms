<script>
    import NavBar from '../components/Navbar.vue';
    import { BASE_URL } from '../api.js';
    import axios from 'axios';


    export default {
        name: "VendorView",
        components: {
            NavBar
         },
        data() {
            return {
            menuItems: [
                { label: 'HOME', route: '/VendorView'  },
                { label: 'LOGOUT', route: '/'  }
            ],
            userId :'',
            userEmail:'',
            userName:'',
            Todo:[],
            Completed:[],
            activeOption:'taskTable', //default table displaying
            selectedRows: [], //tick checkbox
            selectAll: false,

            }
        },
        created() {
        //user session
        const user = JSON.parse(sessionStorage.getItem('user'));
        console.log(user)
        if(user == null){
            //hardcode pass data
            this.userId = "6426e823533ce37c3e4ddae3"
            this.userEmail = "yyteng.2019@gmail.com"
        }else{
            this.userId = user.userId
            this.userEmail = user.userEmail
            this.userName = user.name
        }
        console.log("userId-->", this.userId); 
        console.log("userEmail-->",this.userEmail); 
        this.getAllForm()
    },
        methods: {
        async getAllForm(){
            axios.get(`${BASE_URL}/api/form/all`)
            .then(response => {
                var AllForm = response.data.data;
                console.log(AllForm)
                for (const workflow of AllForm){
                    console.log(workflow)
                    var id = workflow.id
                    var task = workflow.formContent.formName
                    var status=workflow.status
                    var assigned_vendor_email = workflow.assigned_vendor_email
                    console.log(assigned_vendor_email)
                    var stage= this.addStage(status,assigned_vendor_email)[0]
                    var Mstatus= this.addStage(status,assigned_vendor_email)[1]
                    var formEffDate = new Date(workflow.formEffDate).toLocaleDateString('en-GB')   
                    var deadline = new Date(workflow.deadline).toLocaleDateString('en-GB')  
                   
                    var archived = workflow.archived

                    console.log('test',assigned_vendor_email,this.userEmail)

                    //check if login user == the vendor email
                    if(assigned_vendor_email==this.userEmail){
                        //To Do table
                        if(stage == 'Vendor' && archived == false){
                            this.Todo.push({id:id,task:task,stage:stage,status:Mstatus,formEffDate:formEffDate,deadline:deadline}) 
                        }else{
                            // Completed table
                            this.Completed.push({id:id,task:task,stage:stage,status:Mstatus,formEffDate:formEffDate,deadline:deadline}) 
                        }
                    }

                }
            })
        },
        addStage(status,assigned_vendor_email){ //add stage, Mstatus according to the status
            var stage = '';
            var Mstatus = '';
            console.log(assigned_vendor_email)
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
                if (assigned_vendor_email !='' || assigned_vendor_email != null){
                    stage = 'Vendor'
                }else{
                    stage = 'Admin'
                }
                
                Mstatus = 'Approver Rejected'
            }
            return [stage, Mstatus]
        },
        TaskToDoAction(){
            window.open('https://media.makeameme.org/created/i-pray-to-5bed2f.jpg', '_blank');
        },
        TaskCompleted(){
            window.open('http://i.imgflip.com/31fael.jpg', '_blank');
        },
        EditEachForm(formId,status){
            localStorage.setItem('formNo', [formId,status])
            window.location.href = "Form";
        },
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
    <div class="row mt-4 mb-5" id = 'welcome'>
        <div class="line">
            <p style = "font-weight:600;">WELCOME, {{ this.userName.toUpperCase() }}</p>
        </div>
    </div>
    
    <div>
    <h2>My Task</h2>
    <p>Please review the checklist below to complete any assigned tasks.</p>
    <el-tabs v-model="activeOption"  type="border-card">
        <el-tab-pane label="To Do" name="taskTable"  @tab-click="activeOption = 'taskTable'">
            <template #label>To Do({{ this.Todo.length }})</template>
        </el-tab-pane>
        <el-tab-pane label="Completed" name="CompletedtaskTable"  @tab-click="activeOption = 'CompletedtaskTable'">
            <template #label>Completed({{ this.Completed.length }})</template>
        </el-tab-pane>

        <!-- 2.1) To-do Table content -->
        <div v-if="activeOption === 'taskTable' && Todo.length >0" >
            <table class="my-table">
            <thead>
                <tr>
                    <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
                    <th>Task</th>
                    <th>Stage</th>
                    <th>Status</th>
                    <th>Vendor Assigned Date</th>
                    <th>Deadline</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in Todo" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
                <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                <td>{{ item.task }}</td>
                <td>{{ item.stage }}</td>
                <td>{{ item.status }}</td>
                <td>{{ item.formEffDate }}</td>
                <td>{{ item.deadline }}</td>
                <td >
                    <el-icon class="el-input__icon" @click="EditEachForm(item.id, item.status)">
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
                    <el-icon class="el-input__icon" @click="ViewEachForm(item.formNo)">
                            <View />
                        </el-icon>
                </td>
                </tr>
            </tbody>
            </table>
        </div>

        <div v-if="activeOption === 'CompletedtaskTable' && Completed.length ==0">There is no completed task!</div>

    </el-tabs>
    </div>



</template>