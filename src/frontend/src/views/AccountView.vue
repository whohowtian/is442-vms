<script>
    import NavBar from '../components/Navbar.vue';
    import Header from '../components/Header.vue';
    import Button from '../components/Button.vue';
    import Table from "../components/Table.vue";
    import { Search, More} from '@element-plus/icons-vue'
    import axios from 'axios';
    import { BASE_URL } from '../api.js';

    export default {
        name: "AccountView",
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
            allAccount:[],
            menuItems: [
                { label: 'HOME', route: '/AdminView'  },
                { label: 'ACCOUNT', route: '/AccountView'  },
                { label: 'WORKFLOW', route: '/WorkflowView'  },
                { label: 'LOGOUT', route: '/'  }
            ],
            input:"",
            dropdownOptions: [ //for actions dropdown options
            { label: 'Edit', value: 'edit' },
            { label: 'Delete', value: 'delete' }
            ],
            }
        },
        created() {
            this.getAllAccount() //trigger Account API
        },
        methods: {
            async getAllAccount(){
            axios.get(`${BASE_URL}/api/user/all`)
                .then(response => {
                var allAccount = response.data.data
                // console.log("check-->", allAccount)

                for (const account of allAccount){
                    var userId = account.userId
                    var name = account.name
                    var email = account.email
                    var companyName = account.entityName
                    var userType = account.userType
                    if (companyName == null){
                        companyName = 'Quantum Leap Incorporation'
                    }
                    this.allAccount.push({ userId:userId, name: name, email:email,companyName:companyName,userType:userType})
                }
                })
                .catch(error => {
                console.log(error);
                });
            },
            async DeleteAccount(userId){
                axios.delete(`${BASE_URL}/api/user/id/`+ userId)
                .then(response => {
                    console.log(response.data);
                    alert('Deleted!'); // do verification --> sweet alert
                    location.reload();

                    })
                    .catch(error => {
                    console.log(error);
                    });
            },
            async EditAccount(userId){
                console.log(userId);
                localStorage.setItem('userId', userId)
                window.location.href = "/EditAccount"
            },
        //table styling  function
        selectAllRows() {
            this.selectedRows = this.selectAll ? [...this.allAccount] : [];
        },
        toggleRowSelection(item, event) {
            if (event.target.tagName === 'TD') {
                const index = this.selectedRows.findIndex(selectedRow => selectedRow.userId === item.userId);
                if (index === -1) {
                this.selectedRows.push(item);
                } else {
                this.selectedRows.splice(index, 1);
                }
            }
        },
        isSelected(item) {
            return this.selectedRows.findIndex(selectedRow => selectedRow.userId === item.userId) !== -1;
        },
    },
    computed: {
        allRowsSelected() { //table styling function
            return this.selectedRows.length === this.allAccount.length;
        }
    },
    watch: { //table styling function
        selectAll(val) {
        this.selectedRows = val ? [...this.allAccount] : [];
        },
        selectedRows(val) {
        this.selectAll = val.length === this.allAccount.length;
        }
    },

};
</script>

<template>
    <NavBar :items="menuItems" />
    <Header dept= "ACCOUNT MANAGEMENT" msg= "Create new or edit existing account within your business along with assigning each a specific role"/>
    <div style="margin-top: 21px;" >
        <div class="row" >
            <div class="col-sm-2">
                <el-input v-model="input" placeholder="Search Company Name" style="width:fit-content" size="large">
                <template #suffix>
                <el-icon class="el-input__icon"><Search /></el-icon>
                </template>
                </el-input>
            </div>
            <div class="col-sm-2">
                <a href ='CreateAccount'><Button>+ Add Account</Button></a>
            </div>
        </div>

        <table class="my-table">
            <thead>
            <tr>
                <th class="checkbox-col"><input type="checkbox" v-model="selectAll" @change="selectAllRows"></th>
                <th>Name</th>
                <th>Email</th>
                <th>Company Name</th>
                <th>User Role</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
                <tr v-for="item in allAccount" :key="item.userId" @click="toggleRowSelection(item, $event)" :class="{ 'selected': isSelected(item) }">
                    <td class="checkbox-col"><input type="checkbox" v-model="selectedRows" :value="item" @click.stop></td>
                    <td>{{ item.name }}</td>
                    <td>{{ item.email }}</td>
                    <td>{{ item.companyName }}</td>
                    <td>{{ item.userType }}</td>
                    <td >
                        <div  class="btn-group dropup">
                        <Button buttonStyle="none" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <el-icon><More /></el-icon>
                        </Button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" @click="EditAccount(item.userId)">Edit</a></li>
                            <li><a class="dropdown-item" @click="DeleteAccount(item.userId)">Delete</a></li>
                        </ul>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>



    </div>


</template>