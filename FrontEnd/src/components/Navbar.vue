<!-- THIS IS A REUSABLE NAVBAR COMPONENT -->
<!-- TO USE THE COMPONENT, pass in the props in menuItems && in template put <NavBar :items="menuItems"/>-->
<!-- CAN REFER TO AdminView.vue -->

<template>
    <nav class="row navbar navbar-expand-lg ">
      <div class="container-fluid">

        <a class="navbar-brand" href="#"><img src="../assets/icons/CompanyLogo.png" alt="Logo" class="logo" ></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul>
            <li v-for="(item, index) in items" :key="index" :class="{ 'bold': $route.path === item.route }">
            <router-link :to="item.route" active-class="active">{{ item.label }}</router-link>
            </li> 
        </ul>
      </div>
      </div>
    </nav>
  </template>
  
<script>
  export default {
    name: 'NavBar',
    props: {
      items: {
        type: Array,
        required: true
      }
    }
}
</script>
  
<style>
  .navbar {
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    height:8vh;
  }
  .logo {
  height: 50px;
  margin: 0px 30px;
  }
  @media (max-width: 767px) {
    .logo {
      height: 40px;
      margin: 0px;
    }
  }
  ul {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0;
    margin: 0;
    list-style: none;
  }
  li {
    padding: 10px;
    font-size: 16px;
    cursor: pointer;
  }
  .bold {
    font-weight: bold;
  }
  .active {
  font-weight: bold;
}
</style>
  