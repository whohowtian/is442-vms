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
                { label: 'HOME', route: '/VendorAssessmentForm'  },
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
            <div><h1>New Vendor Assessment Form</h1></div>
            <div><span style="color:red">* Required</span></div>
            <!-- Company Details -->
            <div class="col-lg-12 col-md-12">
                <h3>Company Details</h3>
            </div>
            <div class="col-lg-4 col-md-6">
                <h5><label for="inputName" class="form-label">Company's Name<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>

            <div class="col-lg-4 col-md-6">
                <h5><label for="inputName" class="form-label">Company Registration No<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>

            <div class="col-lg-4 col-md-6">
                <h5><label for="inputName" class="form-label">GST Registered<span style="color:red">*</span></label></h5>
                <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                    <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                    <label class="btn btn-outline-primary" for="btnradio1">Yes</label>

                    <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                    <label class="btn btn-outline-primary" for="btnradio2">No</label>
                </div>
            </div>

            <div class="col-lg-4 col-md-6">
                <h5><label for="inputName" class="form-label">Tel<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>

            <div class="col-lg-4 col-md-6">
                <h5><label for="inputName" class="form-label">Fax<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>

            <div class="col-lg-6 col-lg-10">
                <h5><label for="inputScope" class="form-label">Office Address<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>

            <div class="col-lg-6 col-md-6">
                <h5><label for="inputCoordinator" class="form-label">Type of Business License/ Registration<span style="color:red">*</span></label></h5>

                <div class="col">
                    <input class="form-check-input" type="radio" name="BLRtypes" id="Sole proprietorship" value="Sole proprietorship">
                    <label style="font-size:17px;" class="form-check-label" for="exampleRadios2">Sole proprietorship</label>
                </div>
                
                <div class="col">
                    <input class="form-check-input" type="radio" name="BLRtypes" id="Limited Company" value="Limited Company">
                    <label style="font-size:17px;" class="form-check-label" for="exampleRadios2">Limited Company</label>
                </div>

                <div class="col">
                    <input class="form-check-input" type="radio" name="BLRtypes" id="Partnership Agreement" value="Partnership Agreement">
                    <label style="font-size:17px;" class="form-check-label" for="exampleRadios2">Partnership Agreement</label>
                </div>
                
                <div class="col">
                    
                    <input class="form-check-input" type="radio" name="BLRtypes" id="Others" value="-">
                    <label style="font-size:17px;" class="form-check-label" for="exampleRadios2">Others <span><input style="font-size:15px;" type="text" class="form-control"></span></label>
                    
                </div>
                
            </div>
            
            <div class="col-lg-12 col-md-12">
                <h5><label for="inputCoordinator" class="form-label">Contact Person<span style="color:red">*</span></label></h5>
                <div class="row">
                    <div class="col-6 border text-white bg-dark">
                        Name
                    </div>
                    <div class="col-3 border text-white bg-dark">
                        Tel
                    </div>
                    <div class="col-3 border text-white bg-dark">
                        Designation
                    </div>
                </div>
                

                <div class="row">
                    <div class="col-6 border ">
                        <input name="C1name" type="text" class="form-control">
                    </div>
                    <div class="col-3 border ">
                        <input name="C1tel" type="text" class="form-control">
                    </div>
                    <div class="col-3 border ">
                        <input name="C1desig" type="text" class="form-control">
                    </div>
                </div>

                <div class="row">
                    <div class="col-6 border ">
                        <input name="C2name" type="text" class="form-control">
                    </div>
                    <div class="col-3 border ">
                        <input name="C2tel" type="text" class="form-control">
                    </div>
                    <div class="col-3 border ">
                        <input name="C2desig" type="text" class="form-control">
                    </div>
                </div>
                 
            </div>

            <div class="col-lg-8 col-md-8">
                <h5><label for="inputCoordinator" class="form-label">Nature of Business<span style="color:red">*</span></label></h5>

                <div class="col">
                    <input class="form-check-input" type="radio" name="BizNat" id="Manufacturing" value="Manufacturing">
                    <label style="font-size:17px;" class="form-check-label" for="Manufacturing">Manufacturing</label>
                </div>
                
                <div class="col">
                    <input class="form-check-input" type="radio" name="BizNat" id="Agent/dealer" value="Agent/dealer">
                    <label style="font-size:17px;" class="form-check-label" for="Agent/dealer">Agent/dealer</label>
                </div>

                <div class="col">
                    <input class="form-check-input" type="radio" name="BizNat" id="Distributor" value="Distributor">
                    <label style="font-size:17px;" class="form-check-label" for="Distributor">Distributor</label>
                </div>
                
                <div class="col">
                    
                    <input class="form-check-input" type="radio" name="BLRtypes" id="Others" value="-">
                    <label style="font-size:17px;" class="form-check-label" for="Others">Others <span><input style="font-size:15px;" type="text" class="form-control"></span></label>
                    
                </div>
                
            </div>

            <div class="col-lg-8 col-md-8">
                <h5><label for="inputName" class="form-label">Product/Services<span style="color:red">*</span></label></h5>
                <textarea  class="form-control" rows="1"></textarea>
            </div>



            <!-- Evaluation -->
            <div class="col-lg-12 col-md-12">
                <h3>Evaluation</h3>
            </div>
            
            <div class="row">
                    <div class="col border text-white bg-dark">
                        ISO 9001 Certification:
                    </div>
                    <div class = "col border">
                      <div class="col-lg-6 col-md-6">
                      <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                        <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                        <label class="btn btn-outline-primary" for="btnradio1">Yes</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio2">No</label>
                      </div>
                      </div>
                    </div>
            </div>
            <div class="row">
                    <div class="col border text-white bg-dark">
                        ISO 9001 Certification:
                    </div>
                    <div class = "col border">
                        <input name="C1tel" type="text" class="form-control">
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