<script>
import NavBar from '../components/Navbar.vue';

    export default {
        name: "AdminView",
        components: {
            NavBar,
         },
        data() {
            return {
            menuItems: [
                { label: 'HOME', route: '/AdminView'  },
                { label: 'ACCOUNT', route: '/AccountView'  },
                { label: 'WORKFLOW', route: '/WorkflowView'  },
                { label: 'LOGOUT', route: '/'  }
            ],
            username:''
            }
        },
        created() {
            //user session
            const user = JSON.parse(sessionStorage.getItem('user'));
            this.username = user.name
            console.log(user); 
            console.log(user.userEmail); 
    }
}
</script>

<!-- admin home page -->
<template >
    <NavBar :items="menuItems" />
    <div class="row mt-4 mb-5" id = 'welcome'>
        <div class="line">
            <p style = "font-weight:600;">WELCOME,{{ username }}</p>
        </div>
    </div>
    <div class="row "></div>

    <div class="row mt-5 ">
        <div class="col-sm-1"></div>
        <div class="col">
            <div class="card bg-light h-100">
                <!-- change the link to the correct page -->
                <a href="/AccountView">
                    <div class="card-body" >
                        
                        <img src="../assets/icons/adminEditAccount.png" class="mx-auto d-block img-fluid">
                        <h4 class="card-title">Edit Account</h4>
                        <h6 class="card-text">Create/ Read/ Update/ Delete accounts of users</h6>
                    </div>
                </a>
            </div>
        </div>
        <div class="col">
            <div class="card bg-light h-100">
                <!-- change the link to the correct page -->
                <a href="/WorkflowView">
                    <div class="card-body">
                        <img src="../assets/icons/adminEditWorkflow.png" class="mx-auto d-block img-fluid">
                        <h4 class="card-title">Assign & Edit Workflow</h4>
                        <h6 class="card-text">
                            <ul>
                                <li>Assign/ View/ Edit the workflow</li>
                                <li>Check the workflow status</li>
                            </ul>
                        </h6>
                    </div>
                </a>
            </div>
        </div>
        <div class="col-sm-1"></div>

    </div>
</template>
