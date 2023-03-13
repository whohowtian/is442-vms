<script>
    import NavBar from '../components/Navbar.vue';

    export default {
        name: "VendorView",
        components: {
            NavBar
         },
        data() {
            return {
            menuItems: [
                { label: 'HOME', route: '/VendorView'  },
                { label: 'LOGOUT', route: '/'  }
            ]
            }
        }
    };
</script>

<template>
    <NavBar :items="menuItems" />




</template>