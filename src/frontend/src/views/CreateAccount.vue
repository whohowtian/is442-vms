<script>
import NavBar from '../components/Navbar.vue';
export default {
        name: "AccountView",
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

            usertype: "null"
            }
        },
        methods: {
        createAcc() {alert('Created!');
            window.location.href = '/AccountView';
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
            <div><h1>CREATE USER ACCOUNT</h1></div>
            <div><span style="color:red">* Required</span></div>

            <!-- Name -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputName" class="form-label">Full Name <span style="color:red">*</span></label></h4>
                <textarea  class="form-control" rows="1"></textarea>
            </div>

            <!-- Email -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputEmail" class="form-label">Email <span style="color:red">*</span></label></h4>
                <textarea   class="form-control"  rows="1" required></textarea>
            </div>

            <!-- Password -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputPassword" class="form-label">Password <span style="color:red">*</span></label></h4>
                <input type="password" class="form-control" rows="1" required>
            </div>

            <!-- Role -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputRole" class="form-label">Role <span style="color:red">*</span></label></h4>
                <select id="inputRole" class="form-select" v-model="usertype">
                    <option disabled selected value>Select Role</option>
                    <option value ="vendor">Vendor</option>
                    <option value="admin">Admin</option>
                    <option value="approver">Approver</option>
                </select>
            </div>
            
            <!-- Company name -->
            <div class="col-lg-6 col-md-6" v-if="usertype =='vendor'">
                <h4><label for="inputCompanyName" class="form-label">Company Name</label> <span style="color:red">*</span></h4>
                <textarea class="form-control"   rows = '1'></textarea>
            </div>

            <div class="col-lg-6 col-md-6" v-if="usertype =='vendor'">
                <h4><label for="inputCompanyUEN" class="form-label">Company UEN <span style="color:red">*</span></label></h4>
                <textarea  class="form-control" rows="1"></textarea>
            </div>
            
            <div class="col">
                <button type="button" class="btn btn-outline-primary px-4 mt-5 float-end" id="createBtn"
                style="border-radius: 5px;" @click="createAcc()" >Create</button>
            </div>
            </form>
    </div>
</template>

<style>
body{background-color:#0079B3}
</style>