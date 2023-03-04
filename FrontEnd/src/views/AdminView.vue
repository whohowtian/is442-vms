<script>
    import Header from "../components/AdminHeader.vue";

    export default {
        name: "AdminView",
        components: {
            Header,
            },
        data() {
        return {
        };
        }
    };

</script>

<!-- admin home page -->
<template >
    <Header />
    <div class="row mt-4 mb-5" id = 'welcome'>
        <div class="line">
            <p style = "font-weight:600;">WELCOME, JOHN</p>
        </div>
    </div>
    <div class="row "></div>

    <div class="row mt-5 ">
        <div class="col-sm-1"></div>
        <div class="col">
            <div class="card bg-light h-100">
                <!-- change the link to the correct page -->
                <a href="">
                    <div class="card-body" >
                        
                        <img src="../components/icons/adminEditAccount.png" class="mx-auto d-block img-fluid">
                        <h4 class="card-title">Edit Account</h4>
                        <h6 class="card-text">Create/ Read/ Update/ Delete accounts of users</h6>
                    </div>
                </a>
            </div>
        </div>
        <div class="col">
            <div class="card bg-light h-100">
                <!-- change the link to the correct page -->
                <a href="">
                    <div class="card-body">
                        <img src="../components/icons/adminEditWorkflow.png" class="mx-auto d-block img-fluid">
                        <h4 class="card-title">Assign & Edit Workflow</h4>
                        <h6 class="card-text">
                            <ul>
                                <li>Assign/ View/ Edit the workflow</li>
                                <li>Check the workflow status</li>
                            </ul>
                        </h6>
                    </div>
                </a>
            </div>
        </div>
        <div class="col-sm-1"></div>

    </div>
</template>

