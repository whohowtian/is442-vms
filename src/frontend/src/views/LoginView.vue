<!-- JUST A SAMPLE LOGIN FORM TEMPLATE  -->
<template >
    <NavBar items="" />
    
    <div class="login-page row justify-content-center align-items-center">
      <div class="col-lg-6 ">
        <h1 class="text-center" style="color: white;white-space: nowrap;margin-bottom: 5vw;"> Quantum Vendor Management System (VMS) </h1>
        <h3 class="text-center mb-5"> Login or Sign Up</h3>
        <form>
          <div class="form-group">
            <label for="email">Email address</label>
            <input type="email" class="form-control" id="email" v-model="email" required>
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" v-model="password" required>
          </div>
          <div class="row justify-content-center">
            <div class="col-sm-6">
              <button type="submit" class="btn btn-primary btn-block" @click.prevent="login">Login</button>
            </div>
          </div>
        </form>
      </div>
    </div>
</template>
  
  <script>
  import NavBar from '../components/Navbar.vue';
  import axios from 'axios';
    import { BASE_URL } from '../api.js';
    
  export default {
    name: 'LoginView',
    components: {
      NavBar
    },
    data() {
      return {
        email: '',
        password: '',
      }
    },
    methods: {
      async login() {
        if (!this.email || !this.password) {
          alert('Please fill in all fields.');
          return;
        }

        try {
          axios.get(`${BASE_URL}/api/user/`+ this.email)
            .then(response => {
              var userData = response.data.data;
              console.log(userData)
          
          if (userData.password === this.password) {
            alert(`Login as ${userData.userType} successful!`);
            sessionStorage.setItem('user', JSON.stringify({userId: userData.userId, userEmail: userData.email, name:userData.name, userType:userData.userType, entityName:userData.entityName}));
            if(userData.userType =="VENDOR"){
              this.$router.push('/VendorView');
            }
            if(userData.userType =="ADMIN"){
              this.$router.push('/AdminView');
            }
            if(userData.userType =="APPROVER"){
              this.$router.push('/ApprovalView');
            }
          }
          else{
              Swal.fire({
                icon: 'warning',
                title: "Wrong password",
                timer: 2000,
                timerProgressBar: true,
                showConfirmButton: false
              })
          }
        })
          .catch(error => {
          console.log(error);
          alert('invalid email address & password')
          });
        } catch (error) {
          console.error(error);
          alert('An error occurred while logging in.');
        }
      }
    }
  }
  </script>
  
<style scoped>
  .login-page {
    background-color:rgb(0, 121, 179);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh;
    color: #fff;
  }
  form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 60px;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  }
  label {
    font-size: 18px;
    font-weight: bold;
  }
  input, select {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #fff;
    border-radius: 5px;
    box-sizing: border-box;
    outline: none;
  }
  </style>
  