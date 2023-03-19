<script>
    import NavBar from '../components/Navbar.vue';
    import fakeTaskData from './fakeTaskData';
    import Table from "../components/Table.vue";


    export default {
        name: "ApprovalView",
        components: {
            NavBar,
            Table,
         },
        data() {
            return {
            menuItems: [
                { label: 'HOME', route: '/ApprovalView'  },
                { label: 'LOGOUT', route: '/'  }
            ],
            activeOption:'taskTable', //default table displaying

            userName:fakeTaskData.todo[0].task, // to fetch from BE
            data1:fakeTaskData.todo, 
            headers1:["Task","Form No.","Stage","Status","Date Assigned","Actions"],
            fields1:["task","formNo","company","id","dateAssign","Actions"],

            data2:fakeTaskData.completed, 
            headers2:["Task","Form No.","Stage","Status","Date Assigned","Actions"],
            fields2:["task","formNo","company","id","dateAssign","Actions"],
            }
        },
        methods: {
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
    <div class="row mt-4 mb-5" id = 'welcome'>
        <div class="line">
            <p style = "font-weight:600;">WELCOME, {{ userName.toUpperCase() }}</p>
        </div>
    </div>
    <div v-if="activeOption === 'taskTable'|| activeOption === 'CompletedtaskTable'">
        <h2>My Task</h2>
        <p>Please review the checklist below to complete any assigned tasks.</p>
        <!-- sub nav bar [ToDo / Completed] -->
        <div class="row">
            <div class="col-1" @click="activeOption = 'taskTable'">
                <a href="#">To Do</a><span>({{ data1.length }})</span>
            </div>
            <div class="col-1" @click="activeOption = 'CompletedtaskTable'"> 
                <a href="#" >Completed</a><span>({{ data2.length }})</span>
            </div>
            <div class="col-lg-2 col-sm-4">
                <input type="text" placeholder="Search Company Name">
            </div>
        </div>

        <!-- 2.1) To-do Table content -->
        <div v-if="activeOption === 'taskTable'">
            <Table :data="data1" :headers="headers1" :fields="fields1" icon-class="pen-square" @action-click="TaskToDoAction" />
        </div>

        <!-- 2.2) Completed Table content -->
        <div v-if="activeOption === 'CompletedtaskTable'">
            <Table :data="data2" :headers="headers2" :fields="fields2" icon-class="eye" @action-click="TaskCompleted" />
        </div>
    </div>



</template>