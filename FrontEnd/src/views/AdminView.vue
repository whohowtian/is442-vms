<template>
    <div class="container-fluid">

    <h1 style="text-align: center; margin-top: 20px;">Login as..</h1>
    <div class="row mt-4" id="login">
        <div class="col">
            <div class="card">
                <a href="LJhome">
                    <div class="card-body">
                        <h2 class="card-title" style="font-size:4vw;">Learner</h2>
                        <img src="../Icons/learner.png" class="img-fluid" alt="learner">
                    </div>
                </a>
            </div>
        </div>
        <div class="col">
            <div class="card">
                <a href="hrHome">
                    <div class="card-body">
                        <h2 class="card-title" style="font-size:3vw;" >Human Resource</h2>
                        <img src="../Icons/hr.png" class="img-fluid" alt="HR">
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>


</template>

<style>
#login {
  display: flex;
  align-items: center;
}
.card{
    width: 65%; 
    margin: auto;
}

</style>