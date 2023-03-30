
<script>
import NavBar from '../components/Navbar.vue';
export default {
        name: "PasswordView",
        components: {
            NavBar,
         },
        data() {
            
            return {
            selectedRole: null,
            menuItems: [
                { label: 'HOME', route: '/AdminView'  },
                { label: 'ACCOUNT', route: '/AccountView'  },
                { label: 'WORKFLOW', route: '/WorkflowView'  },
                { label: 'LOGOUT', route: '/'  }
            ]
            }
        },
        methods: {
        setPassword() {alert('Password set!');
            window.location.href = '/AccountView';
            }
        },
        computed: {
            selectedR() {
                return this.selectedRole;
            }
        }
    }
</script>
<template >
    <NavBar :items="menuItems" />

    <div class="shadow p-3 mt-5 mb-5 bg-white rounded " style="margin: auto; width: 90%;">
            <!-- button -->
        <div class="row mb-4" >
            <div class="col">
                <button type="button" class="btn btn-outline-primary px-4" id="backBtn"
                style="border-radius: 5px;" onclick="history.back()">Back</button>
            </div>
        </div>
        <form class="row g-3 p-2">
            <div><h1>SET USER PASSWORD</h1></div>
            <div><span style="color:red">* Required</span></div>
            <div class="col-lg-6 col-md-6">
                <div class="col-lg-6 col-md-6">
                    <h4><label for="pw" class="form-label">Password</label> <span style="color:red">*</span></h4>
                    <input class="form-control" >
                </div>

                <div class="col-lg-6 col-md-6">
                    <h4><label for="pw2" class="form-label">Confirm Password</label> <span style="color:red">*</span></h4>
                    <input class="form-control" >
                </div>
            </div>
            

                
            <div class="col">
            <button type="button" class="btn btn-outline-primary px-4 mt-5 float-end" id="createBtn"
            style="border-radius: 5px;" @click="setPassword()" >Set Password</button>
            </div>
            </form>
    </div>
</template>

<style>
body{background-color:#0079B3}
</style>