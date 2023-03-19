<template>
    <!-- change the method here to try GET or POST -->
    <form @submit.prevent=postData()> 
        <button type="submit" class="btn btn-primary">Try API</button>
        <p>
            open terminal to see if api can be called on web (no CrossOrigin error)
        </p>
            
    </form>
</template>

<script>
  import axios from 'axios';
  export default{
    methods:{
    getData(){ //GET method
        const allData = 'http://localhost:8080/api/formtemplate'
        axios.get(allData).then(response=>{
          var seeAllData = response.data
          console.log(seeAllData)
        })
      },
    async postData() {  //POST method
      console.log("_______________________")
      const data = {
          "toEmail": "kaiwei.chua.2020@scis.smu.edu.sg",
          "body": "TESTING",
          "subject": "TESTING",
          "attachment": ""
          }
        try {
        const response = await axios.post('http://localhost:8080/email/sendEmail', data);
        console.log("SUCCESSFULLY POST")
        console.log(response.data); // do something with the response data
      } catch (error) {
        console.error(error);
      }
    }, 
    }
  }
</script>