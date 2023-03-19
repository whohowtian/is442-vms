<script>
import NavBar from '../components/Navbar.vue';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import Rating from "../components/Rating.vue";

export default {
        name: "AdminView",
        components: {
            NavBar,
            VueDatePicker,
            Rating
         },
         props: {
    star: {
      type: Number,
      default: 0
    },
    ratingdescription: {
      type: Array,
      default() {
        return [
          {
            text: "Poor",
            class: "star-poor"
          },
          {
            text: "Below Average",
            class: "star-belowAverage"
          },
          {
            text: "Average",
            class: "star-average"
          },
          {
            text: "Above Average",
            class: "star-aboveAverage"
          },
          {
            text: "Good",
            class: "star-good"
          }
        ];
      }
    },
    hasresults: {
      type: Boolean,
      default: true
    },
    hasdescription: {
      type: Boolean,
      default: true
    },
    starsize: {
      type: String,
      default: "lg" //xs/6x
    },
    maxstars: {
      type: Number,
      default: 5
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
        data() {
            return {
            menuItems: [
                { label: 'HOME', route: '/VendorView'  },
                { label: 'LOGOUT', route: '/'  }
            ],
            date:null
            }
        },
        methods: {
            Submit() {alert('Submitted!')}
        }
    }
</script>
<template >
    <NavBar :items="menuItems" />
    

    <div class="shadow p-3 mt-5 mb-5 bg-white rounded " style="margin: auto; width: 90%;">
            <!-- button -->
        <div class="row mb-4" >
            <div class="col">
                <button type="button" class="btn btn-outline-primary px-4" id="backBtn"
                style="border-radius: 5px;" onclick="history.back()">Back</button>
            </div>
        </div>
        <form class="row g-3 p-2">
            <div><h1>Subcontractor's Safety and Health Performance Evaluation</h1></div>
            <div><span style="color:red">* Required</span></div>
            <!-- Evaluation Details -->
            <div class="col-lg-12 col-md-12">
                <h3>Evaluation Details</h3>
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputName" class="form-label">Name of Contractor<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputTrade" class="form-label">Trade<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputProj" class="form-label">Project Worksite<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputDate" class="form-label">Date<span style="color:red">*</span></label></h5>
                <VueDatePicker v-model="date"></VueDatePicker>
            </div>

            <!-- Part 1: Participation in Safety **need to change in future--> 
            <div class="col-lg-12 col-md-12">
                <h3>Part 1: Participation in Safety</h3>
            </div>
            <div class="col-lg-12 col-md-12">
                <div class="row">
                    <div class="col border d-flex align-items-center">Attendance in Safety Meeting</div>
                    <div class = "col border">
                    <span><Rating :star="this.star" :disabled="disabled" :maxstars="this.maxstars" :starsize="this.starsize" :hasresults="this.hasresults" :hasdescription="this.hasdescription" :ratingdescription="this.ratingdescription" /></span>
                    </div>
                </div>
            </div>
            <div class="col-lg-12 col-md-12">
                <div class="row">
                    <div class="col border d-flex align-items-center">Tool Box Meeting
                    </div>
                    <div class = "col border">
                    <span><Rating :star="this.star" :disabled="disabled" :maxstars="this.maxstars" :starsize="this.starsize" :hasresults="this.hasresults" :hasdescription="this.hasdescription" :ratingdescription="this.ratingdescription" /></span>
                    </div>
                </div>
            </div>
            <div class="col-lg-12 col-md-12">
                <div class="row">
                    <div class="col border d-flex align-items-center">Compliance to Rules and Regulations
                    </div>
                    <div class = "col border">
                    <span><Rating :star="this.star" :disabled="disabled" :maxstars="this.maxstars" :starsize="this.starsize" :hasresults="this.hasresults" :hasdescription="this.hasdescription" :ratingdescription="this.ratingdescription" /></span>
                    </div>
                </div>
            </div>
            <div class="col-lg-12 col-md-12">
                <div class="row">
                    <div class="col border d-flex align-items-center">Document Submission
                    </div>
                    <div class = "col border">
                    <span><Rating :star="this.star" :disabled="disabled" :maxstars="this.maxstars" :starsize="this.starsize" :hasresults="this.hasresults" :hasdescription="this.hasdescription" :ratingdescription="this.ratingdescription" /></span>
                    </div>
                </div>
            </div>
            <div class="col-lg-12 col-md-12">
                <div class="row">
                    <div class="col">
                    </div>
                    <div class = "col border">
                        SCORE (1): 
                    </div>
                </div>
            </div>

            <div class="col-lg-12 col-md-12">
                <div class="row">
                    <div class="col border ">
                        TOTAL SCORE
                    </div>
                    <div class = "col border">
                        OVERALL PERFORMANCE STANDARD
                    </div>
                </div>
                <div class="row">
                    <div class="col border ">
                        = (I + II + III + IV + V)/25 x 5 x 100%
                        = XX %
                    </div>
                    <div class = "col border">
                        Good - 85% and above
                    </div>
                </div>
            </div>
            
            <!-- Additional Comments -->
            <div class="col-lg-12 col-md-12">
                <h4><label class="form-label">Additional Comments</label></h4>
                <textarea class="form-control"  placeholder="Type in additional comments (if any)" rows = '3'></textarea>
            </div>

            <!-- Evaluation Done By -->
            <div class="col-lg-12 col-md-12">
                <h3>Evaluation Done By</h3>
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputCoordinator" class="form-label">Name of Safety Coordinator</label></h5>
                <select id="inputCoordinator" class="form-select">
                    <option disabled selected value>Select a Safety Coordinator</option>
                    <option value="name">BLABLABLA</option>
                </select>                    
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputDate" class="form-label">Date<span style="color:red">*</span></label></h5>
                <VueDatePicker v-model="date"></VueDatePicker>
            </div>

            <!-- Approved by -->
            <div class="col-lg-12 col-md-12">
                <h3>Approved By</h3>
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputDirector" class="form-label">Name of Director</label></h5>
                <select id="inputDirector" class="form-select">
                    <option disabled selected value>Select a Director</option>
                    <option value="name">BLABLABLA</option>
                </select>                    
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputDate" class="form-label">Date<span style="color:red">*</span></label></h5>
                <VueDatePicker v-model="date"></VueDatePicker>
            </div>
            
            
            
            <div class="col">
            <button type="button" class="btn btn-outline-primary px-4 mt-5 float-end" id="createBtn"
            style="border-radius: 5px;" @click="Submit()" >Submit</button>
            </div>
            </form>
    </div>
</template>

<style>
body{background-color:#0079B3}
</style>