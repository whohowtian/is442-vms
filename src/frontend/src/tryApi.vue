<template>
    <form @submit.prevent=submitForm()>
        <button type="submit" class="btn btn-primary">Try API</button>
        <p>
            console.log to see if api can be called on web (no CrossOrigin error)
        </p>
            
    </form>
</template>

<script>
  import axios from 'axios';
  export default{
    methods:{
    getData(){
        const allData = '/email/sendEmail'
        axios.get(allData).then(response=>{
          var seeAllData = response.data
          console.log(seeAllData)
        })
      },
      async submitForm() {
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