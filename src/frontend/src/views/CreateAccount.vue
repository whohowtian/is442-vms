<script>
import axios from 'axios';
import { BASE_URL } from '../api.js';
import NavBar from '../components/Navbar.vue';
export default {
        name: "AccountView",
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
            ],

            name: null,
            email: null,
            number: null,
            usertype: null,
            companyName: null,
            companyUEN: null,
            business: [null, null],
            registered: null,
            gstNum: null,
            }
        },
        methods: {
        createAcc() {
            axios.post(`${BASE_URL}/api/user/create`, {
                email: this.email,
                name: this.name,
                number: this.number,
                userType: this.usertype,
                entityUEN: this.companyUEN,
                entityName: this.companyName,
                entityActivities: this.business,
                gstregistered: this.registered,
                gstRegisteredNo: this.gstNum,
            })
            .then(response => {
                    alert(response.data.message);
                    window.location.href = 'AccountView';
                    })
                    .catch(error => {
                    alert(error.data.message);
                    });
            },
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
        <form class="row g-3 p-2" v-on:submit.prevent="createAcc()">
            <div><h1>CREATE USER ACCOUNT</h1></div>
            <div><span style="color:red">* Required</span></div>

            <!-- Name -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputName" class="form-label">Full Name <span style="color:red">*</span></label></h4>
                <textarea  class="form-control" rows="1" v-model="name" required></textarea>
            </div>

            <!-- Email -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputEmail" class="form-label">Email <span style="color:red">*</span></label></h4>
                <textarea   class="form-control" rows="1" v-model="email" required></textarea>
            </div>

            <!-- Number -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputPNum" class="form-label">Phone Number <span style="color:red">*</span></label></h4>
                <textarea   class="form-control" rows="1" v-model="number" required></textarea>
            </div>

            <!-- Role -->
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputRole" class="form-label">Role <span style="color:red">*</span></label></h4>
                <select id="inputRole" class="form-select" v-model="usertype" required>
                    <option disabled selected value>Select Role</option>
                    <option value="ADMIN">Admin</option>
                    <option value="APPROVER">Approver</option>
                    <option value ="VENDOR">Vendor</option>
                </select>
            </div>
            
            <!-- Company name -->
            <div class="col-lg-6 col-md-6" v-if="usertype =='VENDOR'">
                <h4><label for="inputCompanyName" class="form-label">Company Name</label> <span style="color:red">*</span></h4>
                <textarea class="form-control"  rows = '1' v-model="companyName" required></textarea>
            </div>

            <div class="col-lg-6 col-md-6" v-if="usertype =='ADMIN' || usertype =='APPROVER'">
                <h4><label for="inputCompanyName" class="form-label">Company Name</label> </h4>
                <textarea class="form-control"   rows = '1' value="Quantum Leap Incorporation" disabled></textarea>
            </div>

            <!-- Company UEN -->
            <div class="col-lg-6 col-md-6" v-if="usertype =='VENDOR'">
                <h4><label for="inputCompanyUEN" class="form-label">Company UEN <span style="color:red">*</span></label></h4>
                <textarea  class="form-control" rows="1" v-model="companyUEN" required></textarea>
            </div>

            <div class="col-lg-6 col-md-6" v-if="usertype =='ADMIN' || usertype =='APPROVER'">
                <h4><label for="inputCompanyUEN" class="form-label">Company UEN <span style="color:red">*</span></label></h4>
                <textarea  class="form-control" rows="1" value="201616155N" disabled></textarea>
            </div>

            <!-- Nature of business -->
            <div class="col-lg-6 col-md-6" v-if="usertype =='VENDOR'">
                <h4><label for="inputBusiness" class="form-label">Nature of Business (Pri)</label> <span style="color:red">*</span></h4>
                <textarea class="form-control"   rows = '1' v-model="business[0]" required></textarea>
            </div>   

            <div class="col-lg-6 col-md-6" v-if="usertype =='VENDOR'">
                <h4><label for="inputBusiness" class="form-label">Nature of Business (Sec)</label></h4>
                <textarea class="form-control"   rows = '1' v-model="business[1]"></textarea>
            </div> 
            
            <!-- GST Number -->
            <div class="col-lg-6 col-md-6" v-if="usertype =='VENDOR'">
                <h4><label for="inputRole" class="form-label">GST Registered <span style="color:red">*</span></label></h4>
                <select id="inputRole" class="form-select" v-model="registered" required>
                    <option disabled selected value>Select Yes/No</option>
                    <option :value="true">Yes</option>
                    <option :value="false">No</option>
                </select>
            </div>

            <div class="col-lg-6 col-md-6" v-if="registered ==true">
                <h4><label for="inputGSTNum" class="form-label">GST Registered Number</label> <span style="color:red">*</span></h4>
                <textarea class="form-control"   rows = '1' v-model="gstNum" required></textarea>
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