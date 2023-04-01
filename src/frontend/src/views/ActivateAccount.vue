<script>
import NavBar from '../components/Navbar.vue'
import { BASE_URL } from '../api.js';
import axios from 'axios';

export default {
        name: "PasswordView",
        components: {
            NavBar,
         },
        data() {
            
            return {
            password: null,
            password2: null,
            password_length: 0,
            contains_eight_characters: false,
            contains_number: false,
            contains_uppercase: false,
            contains_special_character: false,
            valid_password: false,
            password_match: false,
            userId:'',
            userEmail:'',
            menuItems: [
                { label: 'HOME', route: '/AdminView'  },
                { label: 'ACCOUNT', route: '/AccountView'  },
                { label: 'WORKFLOW', route: '/WorkflowView'  },
                { label: 'LOGOUT', route: '/'  }
            ]
            }
        },
        mounted() {
            const params = new URLSearchParams(window.location.search);
            this.userId = params.get('id');
            this.matchUser();
        },
        methods: {
            matchUser() {
                axios.get(`${BASE_URL}/api/user/id/`+this.userId)
                .then(response => {
                    var selectedUser = response.data.data;
                    this.userEmail = selectedUser.email
                })

            },
            setPassword() {
                console.log(this.userEmail,this.password2 )
                Swal.fire({
                title: 'Reset Password',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, reset it!'
                }).then((result) => {
                if (result.isConfirmed) {
                    var url = `${BASE_URL}/api/user/activate-account`
                    axios.post(url, {
                        email:this.userEmail,password:this.password2
                    }).then(response => {
                        Swal.fire(
                        'Reset!',
                        'success'
                        )
                    })
                }
                })

                // alert('Password set!');
                // window.location.href = '/AccountView';
            },
            checkPassword() {
                this.password_length = this.password.length;
                const format = /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
                        
                if (this.password_length > 8) {
                    this.contains_eight_characters = true;
                } else {
                    this.contains_eight_characters = false;
                        }
                        
                if (this.password === this.password2 && this.password.length != 0 && this.password2.length != 0){
                    this.password_match = true;
                }
                this.contains_number = /\d/.test(this.password);
                this.contains_uppercase = /[A-Z]/.test(this.password);
                this.contains_special_character = format.test(this.password);
                
                if (this.contains_eight_characters === true &&
                                this.contains_special_character === true &&
                                this.contains_uppercase === true &&
                                this.contains_number === true &&this.password_match === true) {
                                    this.valid_password = true;			
                } else {
                    this.valid_password = false;
                }

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
            	

            <div class="input_container">
                <ul class="criteriaList">
                    <li class="criteria" v-bind:class="{ is_valid: contains_eight_characters }">- 8 Characters</li>
                    <li class="criteria" v-bind:class="{ is_valid: contains_number }">- Contains Number</li>
                    <li class="criteria" v-bind:class="{ is_valid: contains_uppercase }">- Contains Uppercase</li>
                    <li class="criteria" v-bind:class="{ is_valid: contains_special_character }">- Contains Special Character</li>
                    <li class="criteria" v-bind:class="{ is_valid: password_match }">- Passwords Match</li>
                </ul>
                    <div class="col-lg-6 col-md-6">
                        <h4><label for="password" class="form-label">Password</label> <span style="color:red">*</span></h4>
                        <input id="password" type="password" @input="checkPassword" v-model="password" autocomplete="off" placeholder="Password" />
                    </div>

                    <div class="col-lg-6 col-md-6">
                        <h4><label for="password" class="form-label">Confirm Password</label> <span style="color:red">*</span></h4>
                        <input id="password2" type="password" @input="checkPassword" v-model="password2" autocomplete="off" placeholder="Confirm Password" />
                    </div>
                </div>
            

            
            <div class="col">
                <button v-if="this.valid_password  == true" type="button" class="btn btn-outline-primary px-4 mt-5 float-end" id="createBtn"
            style="border-radius: 5px;" @click="setPassword()" >Set Password</button>
            
                </div>
            </form>
    </div>
</template>

<style>
body{background-color:#0079B3}

.criteriaList {
	padding-left: 20px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
}

.criteria { 
	margin-bottom: 8px;
	color: #525f7f;
	position: relative;
}

.criteria:before {
  content: "";
	width: 0%; height: 2px;
	background: #2ecc71;
	position: absolute;
	left: 0; top: 50%;
	display: block;
	transition: all .6s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}


/* Password Input --------- */

input[type="password"] {
	line-height: 1.5;
	display: block;
	color: rgba(136, 152, 170, 1);
	font-weight: 300;
	width: 100%;
	height: calc(2.75rem + 2px);
	padding: .625rem .75rem;
	border-radius: .25rem;
	background-color: #fff;
	transition: border-color .4s ease;
	border: 1px solid #cad1d7;
	outline: 0;
}

input[type="password"]:focus {
	border-color: rgba(50, 151, 211, .45);
}


/* Checkmark & Strikethrough --------- */

.is_valid { color: rgba(136, 152, 170, 0.8); }
.is_valid:before { width: 100%; }

.checked { animation: draw 0.5s ease forwards; }

@keyframes draw {
  to { stroke-dashoffset: 0; }
}
</style>