<script>
import NavBar from '../components/Navbar.vue';
import Header from '../components/Header.vue';
import Button from '../components/Button.vue';
import { Search, Edit } from '@element-plus/icons-vue'
import { BASE_URL } from '../api.js';
import axios from 'axios';


export default {
    name: "WorkflowView",
    components: {
        NavBar,
        Header,
        Button,
        Search
        },
    data() {
        return {
        selectedRows: [], //tick checkbox
        selectAll: false,
        userId :'',
        userEmail:'',
        allFormData:[],
        allUser:[],
        allVendor:[],
        allWorkflowData:[],
        ActiveWorkflow:[],
        FilterActiveWorkflow:[],
        InActiveWorkflow:[],
        FilterInactiveWorkflow:[],
        Todo:[],
        Completed:[],
        SearchCompany:'',
        SearchCompanyArchived:'',
        assignForm:'',
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
        }
    },
    created() {
        const user = JSON.parse(sessionStorage.getItem('user'));
        this.userId = user.userId
        this.userEmail = user.userEmail
        this.getAllWorkflow() //trigger Form API
        this.getAllVendor("VENDOR") // for assigning workflow- vendor
        this.FilterActiveWorkflow= this.ActiveWorkflow
        this.FilterInactiveWorkflow = this.InActiveWorkflow
        },
    methods: {
        async getAllWorkflow(){
            axios.get(`${BASE_URL}/api/form/all`)
            .then(response => {
                var allWorkflow = response.data.data;
                axios.get(`${BASE_URL}/api/user/all`)
                .then(response => {
                    var allUser= response.data.data;
                    // console.log(allUser)
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
                        var stage= this.addStage(status,vendorEmail)[0]
                        var Mstatus= this.addStage(status,vendorEmail)[1]
                        var formEffDate = new Date(workflow.formEffDate).toLocaleDateString('en-GB')   
                        var deadline = new Date(workflow.deadline).toLocaleDateString('en-GB')  
                        var archived = workflow.archived
                        var reviewedBy = workflow.reviewedBy
                        var reviewedByName = this.findUser(reviewedBy, allUser)
                        var archivedBy = workflow.archivedBy
                        var archivedByName = this.findUser(archivedBy, allUser)
                        var approvedBy = workflow.approver
                        var approvedByName = this.findUser(approvedBy, allUser)
                        var entityUEN = this.findVendorandCompanyName(vendorEmail,allUser)[2]



                        this.allWorkflowData.push({ id:id,task: task, vendorEmail:vendorEmail,VendorName:VendorName,companyName:companyName,formNo: formNo, stage: stage,status: Mstatus, formEffDate:formEffDate,deadline:deadline,reviewedBy:reviewedByName,archivedBy:archivedByName,approvedBy:approvedByName})

                        //for active workflow
                        if (archived ==false){
                            this.ActiveWorkflow.push({ id:id,task: task, vendorEmail:vendorEmail,VendorName:VendorName,companyName:companyName,formNo: formNo, stage: stage,status: Mstatus, formEffDate:formEffDate,deadline:deadline,reviewedBy:reviewedByName,approvedBy:approvedByName, entityUEN: entityUEN})

                            //mytask- todo
                            if(status== 'PENDING_ADMIN'){
                                this.Todo.push({ id:id,task: task, vendorEmail:vendorEmail,VendorName:VendorName,companyName:companyName,formNo: formNo, stage: stage,status: Mstatus, formEffDate:formEffDate,deadline:deadline})
                            }

                            //mytask- completed - check reviewedBy field
                            if (reviewedBy !== '' && reviewedBy==this.userId && status != "PENDING_ADMIN"){
                                this.Completed.push({ id:id,task: task, vendorEmail:vendorEmail,VendorName:VendorName,companyName:companyName,formNo: formNo, stage: stage,status: Mstatus, formEffDate:formEffDate})
                            }
                            
                        }else{
                            // inactive workflow
                            this.InActiveWorkflow.push({ id:id,task: task, vendorEmail:vendorEmail,VendorName:VendorName,companyName:companyName,formNo: formNo, stage: stage,status: Mstatus, formEffDate:formEffDate,deadline:deadline,reviewedBy:reviewedByName,archivedBy:archivedByName})
                        }

                        
                    }
                    this.getAllFormAvail() //trigger FormTemplate API
                    
                })
            })
            .catch(error => {
                console.log(error);
            });
        },
        async getAllFormAvail(){
            axios.get(`${BASE_URL}/api/formtemplate/all`)
            .then(response => {
                var allForm = response.data.data;
                //data cleaning
                for (const form of allForm){
                    var id = form.id
                    var formName = form.formName
                    var formNo = form.formNo
                    var lastEdited=form.lastEdited
                    var editedBy = form.editedBy
                    
                    var editedByName = this.findUser(editedBy, this.allUser)
                    
                    this.allFormData.push({ id: id, formName: formName, formNo: formNo, editedby:editedByName, lastEdited: lastEdited})
                }
            //   console.log(this.allFormData)
            })
            .catch(error => {
                console.log(error);
            });
        },
        async getAllVendor(usertype){
            axios.get(`${BASE_URL}/api/user/type/`+usertype)
            .then(response => {
                var allSelectedUser = response.data.data;
                // console.log(allSelectedUser)
                //data cleaning
                for (const vendor of allSelectedUser){
                    var vendorEmail = vendor.email
                    var vendorName = vendor.entityName
                    this.allVendor.push({ vendorEmail: vendorEmail, vendorName: vendorName})
                }
            //   console.log(this.allFormData)
            })
            .catch(error => {
                console.log(error);
            });
        },
        findUser(id, allUser){
            var username= ''
            for (const user of allUser){
                if (id == user.userId){
                    username= user.name
                    
                }
            }
            return username
        },
        findVendorandCompanyName(vendorEmail,allUser){
            var companyName = '';
            var VendorName = '';
            var entityUEN='';
            
            for (const user of allUser){
                if (vendorEmail == user.email){
                    VendorName= user.name
                    companyName = user.entityName
                    entityUEN = user.entityUEN
                    
                }
            }
            
            return [VendorName,companyName,entityUEN];
        },
        addStage(status,assigned_vendor_email){ //add stage, Mstatus according to the status
            console.log(assigned_vendor_email)
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
                if (assigned_vendor_email ==""|| assigned_vendor_email == null){
                    stage = 'Admin'
                }else{
                    stage = 'Vendor'
                }
                
                Mstatus = 'Approver Rejected'
            }
            return [stage, Mstatus]
        },
        deleteWorkflow(id,vendorID){
            
            Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    var url = `${BASE_URL}/api/form/archive`
                    axios.post(url, {
                        formID:id,archivedBy:this.userId 
                    })
                    .then(response => {
                        Swal.fire(
                        'Deleted!',
                        'Your file has been archived.',
                        'success'
                        ).then(function () {
                            location.reload();
                        })
                    })
                }
            })
        },    
        searchActivefunction(){
            this.FilterActiveWorkflow=[]
            var addedIds = [];
            for (var workflow of this.ActiveWorkflow) {
                if (workflow.companyName.toLowerCase().includes(this.SearchCompany.toLowerCase())) {
                    if (!addedIds.includes(workflow.id)) {
                        this.FilterActiveWorkflow.push(workflow);
                        addedIds.push(workflow.id);
                    }
                }
            }
            // console.log(this.FilterActiveWorkflow)
            
        },
        searchArchivedfunction(){
            this.FilterInactiveWorkflow=[]
            var addedIds = [];
            for (var workflow of this.InActiveWorkflow) {
                if (workflow.companyName.toLowerCase().includes(this.SearchCompanyArchived.toLowerCase())) {
                    if (!addedIds.includes(workflow.id)) {
                        this.FilterInactiveWorkflow.push(workflow);
                        addedIds.push(workflow.id);
                    }
                }
            }
            
            // console.log(this.FilterInactiveWorkflow)
            
        },
        ViewEachForm(formID){ //GET Form API
            localStorage.setItem('formNo', formID)
            window.location.href = "ViewForm";
        },
        EditFormTemplate(formNo){ //GET FormTemplate API
            localStorage.setItem('formNo', formNo)
            window.location.href = "UpdateFormBuilder";
        },
        EditEachForm(formNo){ //GET Form API
            localStorage.setItem('formNo', formNo)
            window.location.href = "Form";

        },
        AddWorkflow() {
            var html = `<div class="align-left"><span style="color:red">* </span>Select a Form: <select id="form"><option value="" disabled selected>Select a form</option>`
            
            //retrieve a list of formNo, formName
            for (var i=0; i<this.allFormData.length; i++){
                var formNo = this.allFormData[i].formNo
                var formName = this.allFormData[i].formName
                html += `<option value="`+formNo+`">`+formName+`</option>`
                
            }
            html += `</select><br><br>Assign to a Vendor: <select id="vendors" name="vendors"><option value="" disabled selected>Select a vendor`
            
            for (var i = 0; i < this.allVendor.length; i++){
                var vendorEmail = this.allVendor[i].vendorEmail
                var vendorName = this.allVendor[i].vendorName
                html += `<option value="`+vendorEmail+`">`+vendorName+`</option>`
            }
            html += `</option></select><br><br> <i>Note: The workflow will be automatically assigned to the admin if no vendor has been assigned</i></div>`
            
            
            Swal.fire({
            title: 'Assign New Workflow',
            html:html,
            customClass: 'swal-wide',
            showCancelButton: true,
            confirmButtonText: 'Assign',
            
            }).then((result) => {
                const selectedform = Swal.getPopup().querySelector('#form').value
                const selectedvendor = Swal.getPopup().querySelector('#vendors').value
                console.log(selectedform,selectedvendor)
                if(selectedform !==''){
                    var url = `${BASE_URL}/api/form/create`
                        axios.post(url, {
                            formNo:selectedform,assigned_vendor_email:selectedvendor
                        }).then(response => {
                            if (result.isConfirmed) {
                                Swal.fire('Assigned!','','success'
                                ).then(function () {
                                    location.reload();
                                })

                            }
                            
                        })
                }else{
                    Swal.fire('You need to select a form', '', 'info')
                }
                
            })

        }, 
        sendReminder(VendorName, vendorEmail,formNo,deadline){
            console.log("testing",VendorName, vendorEmail,formNo,deadline)
            var date_list = deadline.split('/')
            var day = date_list[0]
            var month = date_list[1]
            var year = date_list[2]
            var deadline_format = year+"-"+month+'-'+day
            console.log(deadline_format)
            Swal.fire({
            title: 'Do you want to send a reminder email to the vendor?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, sent it!'
            }).then((result) => {
                var url = `${BASE_URL}/api/email/sendReminderEmail`
                    axios.post(url, {
                        name:VendorName,email:vendorEmail,formName:formNo,deadline:deadline_format
                    }).then(response => {
                        if (result.isConfirmed) {
                    Swal.fire(
                    'Sent!',
                    'The reminder email has been sent.',
                    'success'
                    )
                }

                    })

                
            })


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
        SavePDF(formNo, UEN){
            var filename = UEN+"_"+ formNo

            Swal.fire({
            title: 'Do you want to save the form as PDF?',
            icon: "warning",
            showCancelButton: true,
            cancelButtonColor: '#c7c6c5',
            confirmButtonColor: '#6A79F3',
            confirmButtonText: 'Yes, Save PDF!',
            cancelButtonText: 'No, Cancel',
            width: 'auto',
            }).then(async (result) => {
                if (result.isConfirmed) {
                try {
                    const response = await axios.get(`${BASE_URL}/api/pdf/`+ filename)
                    console.log("SUCCESSFULLY POST")
                    console.log(response.data);  
                
                    Swal.fire({
                    title: 'Success',
                    text: `PDF form had been successfully downloaded. Please check your Downloads folder. `,
                    icon: 'success',
                    timer: 3000,
                    timerProgressBar: true,
                    showConfirmButton: false
                    })
                } catch (error) {
                    if (error) {
                    console.error("errrr", error)

                    Swal.fire({
                        icon: 'warning',
                        title: error,
                        timer: 2000,
                        timerProgressBar: true,
                        showConfirmButton: false
                    })
                    }
                    }
                }
        })
        },
        async DeleteForm(formNo,formName){
            Swal.fire({
        title: `Are you sure to delete  ${formName} ?`,
        text: "You cannot undo the action!",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: '#c7c6c5',
        confirmButtonColor: '#6A79F3',
        confirmButtonText: 'Yes, Delete!',
        cancelButtonText: 'No, Cancel',
        width: 'auto',
    }).then((result) => {
        if (result.isConfirmed) {
          try {
            const response = axios.delete(`${BASE_URL}/api/formtemplate/`+ formNo);
            console.log("SUCCESSFULLY DELETED")
            console.log(response.data);  
          
            Swal.fire({
              title: 'Success',
              text: `${formName} have been successfully deleted`,
              icon: 'success',
              timer: 2000,
              timerProgressBar: true,
              showConfirmButton: false
            }).then(() => {
              window.location.href = "/WorkflowView";
            });

          } catch (error) {
            if (error) {
              console.error("errrr", error)

              Swal.fire({
                icon: 'warning',
                title: error,
                timer: 2000,
                timerProgressBar: true,
                showConfirmButton: false
              })
            }
            }
        }
  })
            },
        isDeadlinePassed(item) {
        const today = new Date();
        const deadlineDate = new Date(item.deadline);
        if(deadlineDate < today && item.stage !== 'Completed'){
            return 'highlighted-red';
        }            
        else if (item.stage == 'Completed') {
            return 'highlighted-green';
        } else {
            return '';
        }
        }
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
        <el-tab-pane label="EDIT FORM TEMPLATE" name="formTable"></el-tab-pane>
    </el-tabs>

    <!-- 1) workflowTable -->
    <div v-if="firstNavOption === 'workflowTable'" >
        <!-- sub nav bar [Active / Inactive] -->
        <el-tabs v-model="secNavOption"  type="border-card" >
            <el-tab-pane label="Active" name="ActiveWorkFlow"  >
                <template #label>In Progress ({{ ActiveWorkflow.length }})</template>
            </el-tab-pane>
            <el-tab-pane label="Inactive" name="InActiveworkflowTable"  @tab-click="secNavOption = 'InActiveworkflowTable'">
                <template #label>Archived ({{ InActiveWorkflow.length }})</template>
            </el-tab-pane>

        <!-- search bar and button (still unable to fit to inline) -->
        <div class="row" >
            <div class="col-sm-2">
                <el-input v-if="firstNavOption === 'workflowTable' && secNavOption !== 'InActiveworkflowTable'" v-model="SearchCompany" @input="searchActivefunction()" placeholder="Search Company Name" style="width:fit-content" size="large">
                <template #suffix>
                <el-icon class="el-input__icon"><Search /></el-icon>
                </template>
                </el-input>
                <el-input v-if="secNavOption === 'InActiveworkflowTable'" v-model="SearchCompanyArchived" @input="searchArchivedfunction()" placeholder="Search Company Name" style="width:fit-content" size="large">
                <template #suffix>
                <el-icon class="el-input__icon"><Search /></el-icon>
                </template>
                </el-input>
            </div>
            <div class="col-sm-2">
                <Button @click="AddWorkflow">+ Add Workflow</Button>
            </div>
        </div>
        
        <!-- 1.1) Active Table content -->        
        <div v-if="firstNavOption === 'workflowTable' && secNavOption !== 'InActiveworkflowTable'" >
            <table class="my-table">
            <thead>
                
                <tr>
                    <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
                    <th>Task</th>
                    <th>Company Name</th>
                    <th>Stage</th>
                    <th>Status</th>
                    <th>Vendor Assigned Date</th>
                    <th>Deadline</th>
                    <th>Reviewed By</th>
                    <th>Approved By</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in FilterActiveWorkflow" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item), [isDeadlinePassed(item)]: true }">
                <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                <td>{{ item.task }}</td>
                <td>{{ item.companyName }}</td>
                <td>{{ item.stage }}</td>
                <td>{{ item.status }}</td>
                <td>{{ item.formEffDate }}</td>
                <td>{{ item.deadline }}</td>
                <td>{{ item.reviewedBy }}</td>
                <td>{{ item.approvedBy }}</td>
                <td >
                    <div  class="btn-group dropup">
                        <Button buttonStyle="none" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            ...
                        </Button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" @click="ViewEachForm(item.id)">View</a></li>
                            <li v-if="item.stage=='Vendor'"><a class="dropdown-item" @click="sendReminder(item.VendorName, item.vendorEmail,item.formNo,item.deadline)">Email</a></li>
                            <li v-if="item.status=='Approved'"><a class="dropdown-item"  @click="SavePDF(item.id, item.entityUEN)">Save PDF</a></li>
                            <li><a class="dropdown-item" @click="deleteWorkflow(item.id, item.vendorID)">Delete</a></li>
                        </ul>
                    </div>

                </td>
                </tr>
                <tr v-if ="FilterActiveWorkflow.length==0" >
                    <td colspan="10">No result found!</td>
                </tr>  
                
            </tbody>
                
            </table>
        </div>
        
        <!-- 1.2) InActive Table content -->
        <div v-if="secNavOption === 'InActiveworkflowTable'">
            <table class="my-table">
            <thead>
                <tr>
                    <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
                    <th>Task</th>
                    <th>Company Name</th>
                    <th>Last Stage</th>
                    <th>Last Status</th>
                    <th>FormEffDate</th>
                    <th>Archived By</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in FilterInactiveWorkflow" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
                <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                <td>{{ item.task }}</td>
                <td>{{ item.companyName }}</td>
                <td>{{ item.stage }}</td>
                <td>{{ item.status }}</td>
                <td>{{ item.formEffDate }}</td>
                <td>{{ item.archivedBy}}</td>
                <td >
                    <el-icon class="el-input__icon" @click="ViewEachForm(item.id)">
                            <View />
                        </el-icon>
                </td>
                </tr>
                <tr v-if ="FilterInactiveWorkflow.length==0" >
                    <td colspan="10">No result found!</td>
                </tr>
            </tbody>
            </table>

        </div>
        </el-tabs>
    </div>

    <!-- 2) taskTable -->
    <div v-if="firstNavOption === 'taskTable'">
        <p>Please review the checklist below to complete any assigned tasks.</p>
        <!-- sub nav bar [ToDo / Completed] -->
        <el-tabs v-model="secNavOption"  type="border-card" >
            <el-tab-pane label="To Do" name="ToDoTable" >
                <template #label>To Do({{ this.Todo.length }})</template>
            </el-tab-pane>
            <el-tab-pane label="Completed" name="CompletedtaskTable"  @tab-click="secNavOption = 'CompletedtaskTable'">
                <template #label>Completed({{ this.Completed.length }})</template>
            </el-tab-pane>
               
            
        <!-- 2.1) To-do Table content -->
        <div v-if="firstNavOption === 'taskTable' && secNavOption !== 'CompletedtaskTable' && Todo.length >0">
            <table class="my-table">
            <thead>
                <tr>
                    <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
                    <th>Task</th>
                    <th>Stage</th>
                    <th>Status</th>
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
                <td>{{ item.deadline }}</td>
                <td >
                    <el-icon class="el-input__icon" @click="EditEachForm(item.id)">
                            <Edit />
                        </el-icon>
                </td>
                </tr>
            </tbody>
            </table>
        </div>
        <div v-if="firstNavOption === 'taskTable' && secNavOption !== 'CompletedtaskTable' && Todo.length ==0">You have no task for now!</div>

        <!-- 2.2) Completed Table content -->
        <div v-if="secNavOption === 'CompletedtaskTable'  && Completed.length >0">
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
                <tr v-for="item in Completed" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
                <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                <td>{{ item.task }}</td>
                <td>{{ item.companyName }}</td>
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
        <div v-if="secNavOption === 'CompletedtaskTable'  && Completed.length ==0">There is no completed task!</div>
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
                <th>View / Edit</th>
                <th>Delete</th>
            </tr>

            </thead>
            <tbody>
                <tr v-for="item in allFormData" :key="item.id" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
                    <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                    <td>{{ item.formName }}</td>
                    <td>{{ item.editedby }}</td>
                    <td>{{ item.lastEdited }}</td>
                    <td >
                        <el-icon class="el-input__icon" @click="EditFormTemplate(item.formNo)">
                            <Edit />
                        </el-icon>
                    </td>
                    <td >
                        <el-icon class="el-input__icon" @click="DeleteForm(item.formNo,item.formName)">
                            <Delete />
                        </el-icon>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<style>
.swal-wide{
    width:850px;
}

  .highlighted-red {
    background-color: #ffdddd;
  }

  .highlighted-green {
    background-color: #a5fba7;
  }

</style>
