<script>
    import NavBar from '../components/Navbar.vue';
    import fakeTaskData from './fakeTaskData';
    import Table from "../components/Table.vue";


    export default {
        name: "VendorView",
        components: {
            NavBar,
            Table,
         },
        data() {
            return {
            menuItems: [
                { label: 'HOME', route: '/VendorView'  },
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
        },
        EditEachForm(formNo){
            localStorage.setItem('formNo', formNo)
            window.location.href = "VendorForm";
        },
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
    
    <div>
    <h2>My Task</h2>
    <p>Please review the checklist below to complete any assigned tasks.</p>
    <el-tabs v-model="activeOption"  type="border-card">
        <el-tab-pane label="To Do" name="taskTable"  @tab-click="activeOption = 'taskTable'">
            <template #label>To Do({{ data1.length }})</template>
        </el-tab-pane>
        <el-tab-pane label="Completed" name="CompletedtaskTable"  @tab-click="activeOption = 'CompletedtaskTable'">
            <template #label>Completed({{ data2.length }})</template>
        </el-tab-pane>

        <!-- 2.1) To-do Table content -->
        <div v-if="activeOption === 'taskTable'">
            <table class="my-table">
            <thead>
            <tr>
                <th class="checkbox-col"><input type="checkbox"></th>
                <th>Task</th>
                <th>Form No.</th>
                <th>Stage</th>
                <th>Status</th>
                <th>Date Assigned</th>
                <th>Actions</th>
            </tr>

            </thead>
            <tbody>
                <tr v-for="item in data1" :key="item.id">
                    <td class="checkbox-col"><input type="checkbox" ></td>
                    <td>{{ item.task }}</td>
                    <td>{{ item.formNo }}</td>
                    <td>{{ item.company }}</td>
                    <td>{{ item.id }}</td>
                    <td>{{ item.dateAssign }}</td>
                    <td >
                        <el-icon class="el-input__icon" @click="EditEachForm(item.formNo)">
                            <Edit />
                        </el-icon>
                    </td>
                </tr>
            </tbody>
        </table>
        </div>

        <!-- 2.2) Completed Table content -->
        <div v-if="activeOption === 'CompletedtaskTable'">
            <Table :data="data2" :headers="headers2" :fields="fields2" icon-class="eye" @action-click="TaskCompleted" />
        </div>
    </el-tabs>
    </div>



</template>