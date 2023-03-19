<script>
    import NavBar from '../components/Navbar.vue';
    import Header from '../components/Header.vue';
    import Button from '../components/Button.vue';
    import Table from "../components/Table.vue";
    import fakeAccountDate from './fakeAccountDate';
    import { Search } from '@element-plus/icons-vue'

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
            //fake data -- in future change to api endpoint
            data:fakeAccountDate, 
            headers:["Name","Email","Company Name","User Role","Actions"],
            fields:["name","email","company","userRole","Actions-Toggle"],
            }
        }
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

        <Table :data="data" :headers="headers" :fields="fields" :options="dropdownOptions" />


    </div>


</template>