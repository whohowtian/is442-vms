<script>
    import NavBar from '../components/Navbar.vue';
    import Header from '../components/Header.vue';

    export default {
        name: "AccountView",
        components: {
            NavBar,
            Header,
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
        }
    };
</script>

<template>
    <NavBar :items="menuItems" />
    <Header dept= "ACCOUNT MANAGEMENT" msg= "Create new or edit existing account within your business along with assigning each a specific role"/>



</template>