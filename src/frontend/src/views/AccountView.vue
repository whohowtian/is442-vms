<script>
    import NavBar from '../components/Navbar.vue';
    import Header from '../components/Header.vue';
    import Button from '../components/Button.vue';
    import Table from "../components/Table.vue";
    import fakeAccountDate from './fakeAccountDate';

    export default {
        name: "AccountView",
        components: {
            NavBar,
            Header,
            Button,
            Table
         },
        data() {
            return {
            menuItems: [
                { label: 'HOME', route: '/AdminView'  },
                { label: 'ACCOUNT', route: '/AccountView'  },
                { label: 'WORKFLOW', route: '/WorkflowView'  },
                { label: 'LOGOUT', route: '/'  }
            ],
            //fake data -- in future change to api endpoint
            data:fakeAccountDate, 
            headers:["Name","Email","Company Name","User Role","Actions"],
            fields:["name","email","company","userRole","Actions"],
            }
        },
        methods: {
        handleButton() {alert('DIE ALR DIE ALR');
    },
}
};
</script>

<template>
    <NavBar :items="menuItems" />
    <Header dept= "ACCOUNT MANAGEMENT" msg= "Create new or edit existing account within your business along with assigning each a specific role"/>

    <div class="row" style="margin-top: 21px;" >
        <div class="col-lg-2 col-sm-4">
            <input type="text" placeholder="Search Company Name">
        </div>
        <div class="col-lg-2 col-sm-4">
            <a href ='CreateAccount'><Button>+ Add Account</Button></a>
            
        </div>

        <Table :data="data" :headers="headers" :fields="fields" icon-class="pen-square" @action-click="handleButton" />


    </div>


</template>