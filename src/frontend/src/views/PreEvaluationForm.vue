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
            date:null,
            toggle:null
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
            <div><h1>Subcontractor's Safety and Health Pre-Evaluation</h1></div>
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
                <h5><label for="inputScope" class="form-label">Scope of Work<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputCoordinator" class="form-label">Evaluation By</label></h5>
                <select id="inputCoordinator" class="form-select">
                    <option disabled selected value>Select a Evaluator</option>
                    <option value="name">BLABLABLA</option>
                </select>  
            </div>
            <div class="col-lg-6 col-md-6">
                <h5><label for="inputDate" class="form-label">Date<span style="color:red">*</span></label></h5>
                <VueDatePicker v-model="date"></VueDatePicker>
            </div>



            <div class="col-lg-12 col-md-12">
                <div class="row">
                    <div class="col border text-white bg-dark">
                        <h6>Safety & Health Policy and Organisation</h6>
                    </div>
                </div>
                <div class="row">
                    <div class="col border ">
                        Is there a Written Safety & Health Policy
                    </div>
                    <div class = "col border">
                      <div class="col-lg-6 col-md-6">
                      <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                        <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                        <label class="btn btn-outline-primary" for="btnradio1">Yes</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio2">No</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio3">N.A.</label>
                      </div>
                      </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col border ">
                      Is there a Safety Organisation with proper delegation of responsibility and accountability for safety and health?
                    </div>
                    <div class = "col border">
                      <div class="col-lg-6 col-md-6">
                      <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                        <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                        <label class="btn btn-outline-primary" for="btnradio1">Yes</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio2">No</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio3">N.A.</label>
                      </div>
                      </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col border ">
                      Is there a written safety commitment and is it submitted?
                    </div>
                    <div class = "col border">
                      <div class="col-lg-6 col-md-6">
                      <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                        <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                        <label class="btn btn-outline-primary" for="btnradio1">Yes</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio2">No</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio3">N.A.</label>
                      </div>
                      </div>
                    </div>
                </div>
                
            </div>
            
            <!-- Acknowledgement-->
            <div class="col-lg-12 col-md-12">
                <h4><label class="form-label">Acknowledgement</label></h4>
                I, representative of the above-named sub-contractor, have understand the various Safety Criteria listed above and hereby acknowledged that the information given above are valid and supporting items/documents are available upon request by the main contractor.
            </div>

            <div class="col-lg-6 col-md-6">
                <h5><label for="inputUser" class="form-label">Acknowledged by</label></h5>
                <textarea  class="form-control" rows="1"></textarea>                   
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