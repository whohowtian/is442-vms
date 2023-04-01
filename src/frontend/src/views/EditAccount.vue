<script>
import NavBar from '../components/Navbar.vue';
import { BASE_URL } from '../api.js';
import axios from 'axios';

export default {
        name: "ACCOUNT",
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

            userId: null,
            user: [],

            
            }
        },

        created() {
            this.userId = localStorage.getItem('userId');
            this.getUser(this.userId);
        },
        methods: {
        async getUser(userId) {
            axios.get(`${BASE_URL}/api/user/id/` + userId)
                .then(response => {
                console.log(response.data.data)
                this.user = response.data.data
                })
        },
        createAcc() {alert('Created!');
            window.location.href = '/AccountView';
        },
        async editAcc(){
            axios.put(`${BASE_URL}/api/user/`+ userId)
            .then(response => {
                this.allAccount = response.data.data;
                console.log(this.allAccount)
                })
                .catch(error => {
                console.log(error);
                });
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
        <form class="row g-3 p-2" v-on:submit="editAcc()">
            <div><h1>EDIT USER ACCOUNT</h1></div>
            <div><span style="color:red">* Required</span></div>

            <!-- Name -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputName" class="form-label">Full Name <span style="color:red">*</span></label></h4>
                <textarea  class="form-control" rows="1" v-model="user.name" required></textarea>
            </div>

            <!-- Email -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputEmail" class="form-label">Email <span style="color:red">*</span></label></h4>
                <textarea   class="form-control" rows="1" v-model="user.email" required></textarea>
            </div>

            <!-- Number -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputPNum" class="form-label">Phone Number <span style="color:red">*</span></label></h4>
                <textarea   class="form-control" rows="1" v-model="user.number" required></textarea>
            </div>

            <!-- Role -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputRole" class="form-label">Role <span style="color:red">*</span></label></h4>
                <select id="inputRole" class="form-select" v-model="user.userType" required>
                    <option disabled selected value>Select Role</option>
                    <option value="ADMIN">Admin</option>
                    <option value="APPROVER">Approver</option>
                    <option value ="VENDOR">Vendor</option>
                </select>
            </div>
            
            <!-- Company name -->
            <div class="col-lg-6 col-md-6" v-if="user.userType =='VENDOR'">
                <h4><label for="inputCompanyName" class="form-label">Company Name</label> <span style="color:red">*</span></h4>
                <textarea class="form-control"  rows = '1' v-model="user.entityName" required></textarea>
            </div>

            <div class="col-lg-6 col-md-6" v-if="user.userType =='ADMIN' || user.userType =='APPROVER'">
                <h4><label for="inputCompanyName" class="form-label">Company Name</label> </h4>
                <textarea class="form-control"   rows = '1' v-model="user.entityUEN" disabled></textarea>
            </div>

            <!-- Company UEN -->
            <div class="col-lg-6 col-md-6" v-if="user.userType =='VENDOR'">
                <h4><label for="inputCompanyUEN" class="form-label">Company UEN <span style="color:red">*</span></label></h4>
                <textarea  class="form-control" rows="1" v-model="user.entityUEN" required></textarea>
            </div>

            <div class="col-lg-6 col-md-6" v-if="user.userType =='ADMIN' || user.userType =='APPROVER'">
                <h4><label for="inputCompanyUEN" class="form-label">Company UEN <span style="color:red">*</span></label></h4>
                <textarea  class="form-control" rows="1" v-model="user.entityUEN" disabled></textarea>
            </div>

            <!-- Nature of business -->
            <div class="col-lg-6 col-md-6" v-if="user.userType =='VENDOR'">
                <h4><label for="inputBusiness" class="form-label">Nature of Business (Pri)</label> <span style="color:red">*</span></h4>
                <textarea class="form-control"   rows = '1' v-model="user.entityActivities[0]" required></textarea>
            </div>   

            <div class="col-lg-6 col-md-6" v-if="user.userType =='VENDOR'">
                <h4><label for="inputBusiness" class="form-label">Nature of Business (Sec)</label></h4>
                <textarea class="form-control"   rows = '1' v-model="user.entityActivities[1]"></textarea>
            </div> 
            
            <!-- GST Number -->
            <div class="col-lg-6 col-md-6" v-if="user.userType =='VENDOR'">
                <h4><label for="inputRole" class="form-label">GST Registered <span style="color:red">*</span></label></h4>
                <select id="inputRole" class="form-select" v-model="user.gstregistered" required>
                    <option disabled selected value>Select Yes/No</option>
                    <option :value="true">Yes</option>
                    <option :value="false">No</option>
                </select>
            </div>

            <div class="col-lg-6 col-md-6" v-if="user.gstregistered ==true">
                <h4><label for="inputGSTNum" class="form-label">GST Registered Number</label> <span style="color:red">*</span></h4>
                <textarea class="form-control"   rows = '1' v-model="user.gstRegisteredNo" required></textarea>
            </div>   
            
            <div class="col">
                <input type="submit" class="btn btn-outline-primary px-4 mt-5 float-end" id="createBtn" value="submit"
                style="border-radius: 5px;">
            </div>
            
        </form>
    </div>
</template>

<style>
body{background-color:#0079B3}
</style>