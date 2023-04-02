<template>
    <div class="row" style="background:#0079B3; height:25vh">
        <div class="center">

            <p id="Header">
                {{dept}}
            </p>
            <p>{{msg}}</p>
        </div>
    </div>
</template>

<script>

    export default {
    name: "Header",
    props: {
        msg: {
        type: String,
        },
        dept: {
        type: String,
        }
    }
    };
</script>



<style scoped>

.rwd-break { line-height: 10% }
@media screen and (min-width: 768px){
            .rwd-break { line-height: 200% }
        }

</style>