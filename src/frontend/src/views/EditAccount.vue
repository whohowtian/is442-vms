<script>
import NavBar from '../components/Navbar.vue';
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
            ]
            }
        },
        methods: {
        createAcc() {alert('Created!');
            window.location.href = '/AccountView';
        },
        async EditAccount(userId){
            axios.put('http://localhost:8080/api/user/'+ userId)
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
        <form class="row g-3 p-2">
            <div><h1>EDIT USER ACCOUNT</h1></div>
            <div><span style="color:red">* Required</span></div>
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputName" class="form-label">Full Name <span style="color:red">*</span></label></h4>
                <textarea  class="form-control" rows="1"></textarea>
            </div>
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputRole" class="form-label">Role <span style="color:red">*</span></label></h4>
                <select id="inputRole" class="form-select">
                    <option disabled selected value>Select Role</option>
                    <option value ='Inactive'>Vendor</option>
                    <option value="Inactive">Admin</option>
                    <option value="Inactive">Approver</option>
                </select>
            </div>
            
            <div class="col-lg-12 col-md-12">
                <h4><label for="inputCompany" class="form-label">Company Name</label> <span style="color:red">*</span></h4>
                <textarea class="form-control"   rows = '1'></textarea>
            </div>
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputEmail" class="form-label">Email <span style="color:red">*</span></label></h4>
                <textarea   class="form-control"  rows="1"></textarea>
            </div>
            <div class="col-lg-6 col-md-6">
                <h4><label for="inputCountry" class="form-label">Country <span style="color:red">*</span></label></h4>
                <select id="inputCountry" class="form-select" >
                    <option disabled selected value>Select Country</option>
                    <option value="Singapore">Singapore</option>
                    <option value="China">China</option>
                    <option value="Japan">Japan</option>
                </select>
                
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