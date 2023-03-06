<!-- JUST A SAMPLE LOGIN FORM TEMPLATE  -->
<template>
    <div class="login-page">
      <h1>Login</h1>
      <p>Right now is hardcoded function, login by enter {usertype} as Username ; password: pw; login the same usertype</p>
      <h6>e.g. username: admin ; password: pw ; login as admin</h6>
      <form @submit.prevent="submitForm">
        <div>
          <label for="username">Username:</label>
          <input type="text" id="username" v-model="username" required>
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="password" required>
        </div>
        <div>
          <label for="usertype">Login as:</label>
          <select id="usertype" v-model="loginAs" required>
            <option value="">Select user type</option>
            <option value="admin">Admin</option>
            <option value="vendor">Vendor</option>
            <option value="approval">Approval</option>
          </select>
        </div>
        <button type="submit">Login</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    name: 'LoginPage',
    data() {
      return {
        username: '',
        password: '',
        loginAs: ''
      }
    },
    methods: {
      submitForm() {
        if (this.username && this.password && this.loginAs) {
          if (this.loginAs === 'admin' && this.username === 'admin' && this.password === 'pw') {
            alert('Login as Admin successful!');
            this.$router.push('/AdminView');
          } else if (this.loginAs === 'vendor' && this.username === 'vendor' && this.password === 'pw') {
            alert('Login as Vendor successful!');
            this.$router.push('/VendorView');
          } else if (this.loginAs === 'approval' && this.username === 'approval' && this.password === 'pw') {
            alert('Login as Approval successful!');
            this.$router.push('/ApprovalView');

          } else {
            alert('Invalid username or password.');
          }
        } else {
          alert('Please fill in all fields.');
        }
      }
    }
  }
  </script>
  
  <style>
  .login-page {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
    padding: 20px;
    border: 1px solid #ccc;
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
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
    outline: none;
  }
  button {
    padding: 10px;
    font-size: 16px;
    border: none;
    border-radius: 5px;
    background-color: #007bff;
    color: #fff;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  button:hover {
    background-color: #0062cc;
  }
  </style>
  