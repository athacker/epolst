<sec:authorize access="hasRole('ROLE_MEDSTAFF, ROLE_ADMIN, ROLE_MEDCERT, ROLE_EMS')">

<script>
var $j = jQuery.noConflict();
var topPosition;
var offset = 40;


function scrollWindow2(){
	$j(window).scroll(function(){
		stickyNav();
	});
		stickyNav();
	}
	
	
function stickyNav (){
	var scrollPositionY = $j(window).scrollTop();
		if(topPosition < scrollPositionY){
			$j("#content-secondary").addClass("fixedScroller");
		 	posContent2();
		}
		else{
			$j("#content-secondary").removeClass("fixedScroller");
		}	
}

function posContent2(){
	if ( $j("#content-secondary").hasClass("fixedScroller")){
	     $j("#content-secondary .nav").css({"width":$j("#content-secondary").width()+"px"});
	}
}	

function resizeWindow2(){
 	$j(window).resize(function(){
	 	posContent2();
  	});
	posContent2();
}

function setupScrollToAnchor(that) {
	//that = the selector that contains the links that you wish to scroll to
	 $j(that).click(function(event){
		event.preventDefault();
	 	var jloc = $j('#'+$j(this).attr('name')  );
	  	$j('#content-secondary').find('.active').removeClass('active');
	  	$j(that).addClass('active') ;
	    scrollAnimateToView(jloc, 1500);
	});
}

function scrollAnimateToView (that,speed) {
	//that = the jquery object or selector that we would like to scroll into view
	//speed = how fast to scroll it.
 	var scrollTo = $j(that).offset().top-5;
	 
	var scrollMax = $j(document).height()-$j(window).height();
	if (scrollTo > scrollMax) {
		scrollTo = scrollMax-5;
	}
	$j("html:not(:animated),body:not(:animated)").animate({"scrollTop":scrollTo+"px"},speed,'easeInOutCirc');
}


$j( document ).ready(function() {
 	topPosition= $j("#content-secondary").offset().top;
	scrollWindow2();
	resizeWindow2();
	
	$j("#content-secondary .nav li a").each(function(){
		setupScrollToAnchor($j(this));
    });
	
	
});
 
</script>

<div id="ePolst" data-ng-controller="ePolstController" data-ng-init="initPolstForm()" >


<div id="content-wrapper" class="site-full clearfix">
	<div id="content" class="site-full">
	<div id="content-main" class="site">
   
   
 
   
    <div class="row">
        
          
			<div id="content-secondary" class="col-md-3">
			
			<ul class="nav">
			<li><a name="formStatus" data-ng-class="{completed: true==polstBean.status == 'ACTIVE'}" href=""> Status:  {{polstBean.statusDescription}}</a></li>
		 	<li><a name="patientInfo" class="completed"  href="" >Patient info: {{patient.fullName}}</a></li></li>
			<li><a name="codeStatus" data-ng-class="{completed: true==polstBean.sectionStatus.codeStatusComplete}" href=""  >Code Status </a></li>
			<li><a name="medicalCare" data-ng-class="{completed: true==polstBean.sectionStatus.medicalCareComplete}" href=""  >Medical Care</a></li>
			
			<li><a name="antibiotics" data-ng-class="{completed: true==polstBean.sectionStatus.antibioticsComplete}" href=""  >Antibiotics</a></li>
			<li><a name="nutrition" data-ng-class="{completed: true==polstBean.sectionStatus.nutritionComplete}" href=""  >Nutrition</a></li>
			<li><a name="discussion" data-ng-class="{completed: true==polstBean.sectionStatus.discussionComplete}" href=""  >Discussion</a></li>
			<li><a name="advanceDirective" data-ng-class="{completed: true==polstBean.sectionStatus.advDirectiveComplete}" href="" >Advanced Directive</a></li>
			<li><a name="medicalCondition" data-ng-class="{completed: true==polstBean.sectionStatus.medicalConditionComplete}" href="" >Medical Condition</a></li>
			<li><a name="authorizedBy" data-ng-class="{completed: true==polstBean.sectionStatus.authorizedComplete}" href=""  >Authorized By </a></li>
			 
			<li data-ng-show="polstBean.sectionStatus.readyToPrepare"><a name="preparer" data-ng-class="{completed: true==polstBean.sectionStatus.preparerComplete}" href=""  >Preparer </a></li>
		 	<li data-ng-show="(role === 'ROLE_MEDCERT' && polstBean.sectionStatus.preparerComplete === true  ) || polstBean.sectionStatus.certifiedComplete === true"><a name="certification" data-ng-class="{completed: true==polstBean.sectionStatus.certifiedComplete}"  href="" >Certification </a></li>
		 
		    <li><button class="btn btn-primary btn-large"  data-ng-click="printEpolst();" >Print ePOLST Form &nbsp;</button></li>  
			<li><button class="btn btn-primary btn-large"  data-ng-hide="disableEpolstForm"  data-ng-click="checkEmailHistory();">Send Reminder Email </button></li>   
			<li><button class="btn btn-primary btn-large"  data-ng-hide="polstBean.status=='IN_ACTIVE'"  data-ng-disabled="disableInActivateButton"  data-ng-click="cancelEpolst();">&nbsp;In-Activate Polst &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button></li> 
		 	 
			</ul>
			</div>
			
<div id="polstForm" name="polstForm" class="col-md-9">
 <h2>POLST Form Registration </h2>

 
 
 <div class="patientId">
       <ul> 
        <li data-ng-class="{csresuscitate: true==polstBean.codeStatus !== 'DNR',  csdnr: true==polstBean.codeStatus == 'DNR'}">Code Status A: <span>{{polstBean.codeStatusTitle}}&nbsp;  </span></li>
        <li data-ng-class="{ mcresuscitate: true==polstBean.medicalCare !== 'COMFORT', mcdnr: true==polstBean.medicalCare == 'COMFORT'}">Medical Care B: <span>{{polstBean.medicalCareTitle}}&nbsp; </span></li>
        </ul>
        
   </div>





<fieldset data-ng-disabled="true">
<h3 id="patientInfo">Patient Information(read only)</h3>
<row>  
<div class="col-md-9">
	<div class="col-md-4">
	<div class="form-group">
    	<label for="firstName">First Name</label>    
       <input type="text" class="form-control" id="firstName" value="{{patient.firstName}}"    />
  	</div>
    </div>
    
    <div class="col-md-4">    
    <div class="form-group">
    	<label for="middleName">Middle Name</label>
    	<input class="form-control" id="middleName"  value="{{patient.middleName}}"    />
  	</div>
    </div>
    
    <div class="col-md-4">
    <div class="form-group">
   		<label for="lastName">Last Name</label>
    	<input type="text" class="form-control" id="lastName" value="{{patient.lastName}}"  />
  	</div>
    </div>
</div> 
</row><!-- end of row -->

<row>
<div class="col-md-9">
    <div class="col-md-4">
	<div class="form-group">
    	
    	<div class="radio-inline">
      		<label>
        	<input type="radio" name="gender" data-ng-model="patient.gender"  value="MALE" disabled>Male</label>
    	</div>
    	
    	<div class="radio-inline">
      		<label>
        	<input type="radio" name="gender" data-ng-model="patient.gender"  value="FEMALE" disabled>Female</label>
    	</div>
  	</div> 
    </div>
    
    
    <div class="col-md-4">
    <div class="form-group left-inner-addon">
    	<label for="dob" >Date of Birth</label>
       <input type="text" class="form-control" id="dob" value="{{patient.dob | date}}"  />
  	</div>
    </div>
    
    <div class="col-md-4">
    <div class="form-group left-inner-addon">
    	<label for="dateOfDeath">Date of Death</label>
    	<input type="text" class="form-control" id="dateOfDeath" value="{{patient.dateOfDeath | date}}"   />
  	</div>
    </div>
</div>
</row>


<row>    
<div class="col-md-9">
    <h4>Current Address</h4>
    <div class="col-md-12">
    <div class="form-group">
    	<label for="addressCurrent">Street Address</label>
   		 <input type="text" class="form-control" id="addressCurrent"  value="{{patient.addressCurrent }}"    />
  	</div> 
    </div>
    
    <div class="col-md-4">
    <div class="form-group">
    	<label for="cityCurrent">City</label>
    	<input type="text" class="form-control" id="cityCurrent" value="{{patient.cityCurrent }}"   />
  	</div> 
    </div>
    
    <div class="col-md-4">    
    <div class="form-group">
    	<label for="stateCurrent">State</label>
    	<input type="text" class="form-control" id="stateCurrent" value="{{patient.stateCurrent }}"   />
  	</div>
    </div>
     
    <div class="col-md-4">
    <div class="form-group">
    	<label for="zipCurrent">Zip</label>
    	<input type="text" class="form-control" id="zipCurrent"  value="{{patient.zipCurrent }}"   />
  	</div>
    </div>
</div>
</row><!-- end of row -->

<row>    
  	<div class="col-md-9">
   	<h4>Permanent Address</h4>
   	
    <div class="col-md-12">
    <div class="form-group">
    	<label for="addressPerm">Street Address</label>
    	<input type="text" class="form-control" id="addressPerm" value="{{patient.addressPerm }}"   />
  	</div> 
    </div>
    
    <div class="col-md-4">   
    <div class="form-group">
    	<label for="cityPerm">City</label>
    	<input type="text" class="form-control" id="cityPerm"  value="{{patient.cityPerm }}"   />
  	</div>
    </div>
    
    <div class="col-md-4"> 
    <div class="form-group">
    	<label for="statePerm">State</label>
    	<input type="text" class="form-control" id="statePerm" value="{{patient.statePerm }}"    />
  	</div>
    </div>
    
    <div class="col-md-4">
    <div class="form-group">
    	<label for="zipPerm">Zip</label>
    	<input type="text" class="form-control" id="zipPerm" value="{{patient.zipPerm }}"   />
  	</div>
    </div>
</div><!-- end of row -->
</row>


<row>   
<div class="col-md-9">
  	<h4>Physician Info</h4>
	<div class="col-md-6">
    <div class="form-group">
    	<label  >Primary Care Provider</label>
    	<input type="text" class="form-control"   value="{{patient.primaryCareProvider }}"  />
  	</div>
    </div>
    
    <div class="col-md-6">
     <div class="form-group">
    	<label >Certified Physician</label>
    	<input type="text" class="form-control" value="{{patient.physicianFullName }}"    />
  	</div>
    </div>
    
    
    <div class="col-md-6">
    <div class="form-group">
	    <label  >Primary Care Phone</label>
	    <input type="text" class="form-control"    value="{{patient.primaryCarePhysicianPhoneNumber }}"    />
  	</div>
    </div>
    
    <div class="col-md-6">
    <div class="form-group">
	    <label >Certified Physician</label>
	    <input type="text" class="form-control"   value="{{patient.physicianPhoneNumber}}"   />
  	</div>
    </div>
    
</div>
</row><!-- end of row -->
</fieldset>


<div class="clear"></div>
 




<fieldset data-ng-disabled="disableEpolstForm">
<h3 id="formStatus"> </h3>

<h3 id="codeStatus">Code Status</h3>
<form role="form">
<row>
    <div class="col-md-12">
	    <p>Instructions: Explain odds of success of resuscitation for relevant patient populations. Explain that the DNR order applies only if the patient has no pulse and is not breathing, and that the level of care desired by the patient will be provided if the patient has a pulse or is breathing.</p>
	    <p><b>Treatment options when the patient has no pulse and is not breathing:</b></p>
	    	<div >
	    		 <input type="hidden"  data-ng-model="polstBean.patientId" /> 
	    		 <input type="hidden"  data-ng-model="polstBean.polstId" />  
	    	</div>
	    	
	    	<div class="radioLeft">
	    		 <input type="radio" name="codeStatus" value="ATTEMPT" data-ng-model="polstBean.codeStatus" data-ng-change="savePolst()"   /> 
	    		 <label>Attempt to resuscitate</label>
	    	</div>
	    	<div class="radioLeft">
	   			<input type="radio" name="codeStatus" value="DNR" data-ng-model="polstBean.codeStatus" data-ng-change="savePolst()"  />
	      		<label>Do not attempt to resuscitate or continue any resuscitation (DNR) </label>
	    	</div>
	    	<div class="radioLeft">
	    		<input type="radio" name="codeStatus" value="NO_CHOICE" data-ng-model="polstBean.codeStatus" data-ng-change="savePolst()" />
	   			<label> No choice indicated on paper form</label>
		    </div>
	    <p/>	
	    <p>Other instructions or clarification:</p> 
	    <textarea capitalize-first class="form-control" rows="3" data-ng-model="polstBean.codeStatusInstructions"  data-on-blur="savePolst();" ></textarea>
    </div>
</row> 
<div class="clearfix"></div>

</form>
<!--end of code status-->






<h3 id="medicalCare">Medical Care</h3>
<form role="form">
<row>
	<div class="col-md-12">
	    <p>Instructions: Prior to completing this form section, elicit the patient's goals of care and explain how each option will or will not help the patient to receive treatment that will promote the patient's goals of care. Emphasize that comfort care will always be provided. </p>
	    <p><b>Treatment options when the patient has a pulse and is breathing:</b></p>
	    
	    <div class="radio">
	     	<input type="radio" value="COMFORT" name="medicalCare" data-ng-model="polstBean.medicalCare" data-ng-change="savePolst()" >
	       	<b>Comfort measures - Allow Natural Death: </b>Oral and body hygiene; reasonable effort to offer food and fluids orally; Heimlich maneuver for choking or other minimally invasive emergency procedures; medication, oxygen, positioning, warmth, and other measures to relieve pain and suffering. Provide privacy and respect for the dignity and humanity of the patient. Transfer to hospital only if comfort measures can no longer be effectively managed at current setting.
	    </div>
	    
	    <div class="radio ">
	      	<input type="radio" value="LIMITED" name="medicalCare" data-ng-model="polstBean.medicalCare" data-ng-change="savePolst()" >
	       	<b>Limited additional interventions: </b>Includes care above. May also include suction, treatment of airway obstruction, bag/valve/mask ventilation, monitoring of cardiac rhythm, medications, IV fluids. Transfer to hospital if indicated, but no endotracheal intubation or long-term life support measures.  
	    </div>
	   
		<div class="radio"> 
	    	 <input type="radio"  value="FULL_TREATMENT" name="medicalCare" data-ng-model="polstBean.medicalCare" data-ng-change="savePolst()">
	         <b>Full treatment: </b>Includes all care above plus endotracheal intubation, defibrillation/cardioversion, and any other life sustaining care required.</label>
	    </div>
	    
	     <div class="radio">
	      	<input type="radio" value="NO_CHOICE" name="medicalCare" data-ng-model="polstBean.medicalCare" data-ng-change="savePolst()">
	       	<b>No choice indicated on paper form  </b>
	     </div>
	     <p/>	
	     <p>Other instructions or clarification:</p>
	     <textarea class="form-control" capitalize-first  rows="3" data-ng-model="polstBean.medicalCareInstructions" data-on-blur="savePolst();"></textarea>
    </div>
</row> 
<div class="clearfix"></div>
</form>
<!--end of medical care-->






<h3 id="antibiotics">Antibiotics</h3>
<form role="form">
<row>
    <div class="col-md-12">
	     <p>Instructions: Prior to completing this form section, elicit the patient's goals of care and explain how each option will or will not help the patient to receive treatment that will promote the patient's goals of care. </p>
	     
	     <p>Antibiotics: Comfort measures are always provided 
		     <div class="radioLeft">
		        <input type="radio" name="antibiotics" value="NO" data-ng-model="polstBean.antibiotics" data-ng-change="savePolst()">
		      	<label>No antibiotics </label>
		     </div>
	    	<div class="radioLeft">
	      		<input type="radio" name="antibiotics"  value="YES"  data-ng-model="polstBean.antibiotics" data-ng-change="savePolst()">
	        	<label>Antibiotics may be administered</label>
	    	</div>
	    	<div class="radioLeft">
	      		<input type="radio" name="antibiotics"  value="NO_CHOICE"  data-ng-model="polstBean.antibiotics" data-ng-change="savePolst()">
	      		<label>No choice indicated on paper form </label>
	    	</div>
	      <p/>	
	      <p>Other instructions or clarification:</p>
	      <textarea class="form-control" capitalize-first  rows="3" data-ng-model="polstBean.antibioticInstructions" data-on-blur="savePolst();"></textarea>
     </div> 
</row>
<div class="clearfix"></div>
</form>
<!--end of antibiotics-->









<h3 id="nutrition">Nutrition</h3>
<form role="form">
<row>
<div class="col-md-12">
    <p>Instructions: Explain the benefits and burdens of food and fluids by tube. Explain that food and fluids by mouth will always be offered when feasible. </p>
    <p>Artificially administered fluid and nutrition.   Comfort measures are always provided.</p>
    <div class="row">
    <div class="col-md-6">
    	<p class="bold">Feeding Tube</p>
     		<div class="radioLeft"> 
      			<input type="radio" name="feedingTube" value="NO_FEEDING_TUBE" data-ng-model="polstBean.feedingTube" data-ng-change="savePolst()">
     			<label>No feeding tube </label>
    		</div>
    		<div class="radioLeft">
      			<input type="radio" name="feedingTube" value="DEFINED_TRIAL_PERIOD" data-ng-model="polstBean.feedingTube" data-ng-change="savePolst()">
       			<label>Defined trial period of feeding tube </label>
    		</div>
    		<div class="radioLeft">
    			<input type="radio" name="feedingTube" value="LONG_TERM" data-ng-model="polstBean.feedingTube" data-ng-change="savePolst()">
       			<label>Long-term feeding tube </label>
    		</div>
    		<div class="radioLeft">
    			<input type="radio" name="feedingTube" value="NO_CHOICE" data-ng-model="polstBean.feedingTube" data-ng-change="savePolst()">
       			<label>No choice indicated on paper form  </label>
    		</div>
    </div><!--end of column 1-->
    
    <div class="col-md-6">
	    <p class="bold">IV Fluids: </p>
	     	<div class="radioLeft">
	      		<input type="radio" name="ivFluids" value="NO_IV_FLUIDS" data-ng-model="polstBean.ivFluids" data-ng-change="savePolst()">
				<label>No IV Fluids </label>
	    	</div>
	   		<div class="radioLeft">
		        <input type="radio" name="ivFluids" value="DEFINED_TRIAL_PERIOD" data-ng-model="polstBean.ivFluids" data-ng-change="savePolst()">
		        <label>Defined trial period of IV Fluids</label>
	    	</div>
	    	<div class="radioLeft">
	      		<input type="radio" name="ivFluids" value="LONG_TERM_IV_FLUIDS" data-ng-model="polstBean.ivFluids" data-ng-change="savePolst()">
	     		<label>IV Fluids</label>
	    	</div>
	    	<div class="radioLeft">
	        	<input type="radio" name="ivFluids" value="NO_CHOICE" data-ng-model="polstBean.ivFluids" data-ng-change="savePolst()">
	     		<label> No choice indicated on paper form </label>
	    	</div>
	    </div><!--end of column 2-->
	    
    </div><!--end of row-->
      <p/>	
      <p>Other instructions or clarification:</p>
      <textarea class="form-control" capitalize-first  rows="3" data-ng-model="polstBean.nutritionInstuctions" data-on-blur="savePolst();"></textarea>
    </div>
</row> 
<div class="clearfix"></div>
</form>


<!--end of nutrition-->







<h3 id="discussion">Discussion</h3>
											
<form role="form">

    <p>List individuals who were involved with the creation of the form who did not authorize the POLST. DO NOT indicate the person who authorized the form in this section. Even patients who lack capacity should be included in goals of care discussions.</p> 
    
    <p>Participants in the discussion about the form:</p> 
    <row>
    <div class="col-md-12">

 
	   	<div class="checkboxLeft"> 
	   		<input type="checkbox" data-ng-model="polstBean.patientDiscussed" data-ng-click="udpdatePatientDiscussion()">
	       	<label>Patient</label>
	     </div>  
     	
 
     	<div class="checkboxLeft">
        	<input type="checkbox" data-ng-checked="showParentDisc"  data-ng-click="toggleParentDiscussions()"> 
        	<label>Parent of a minor child</label>
       	</div>   
     	
     	<div id="parentDiscussionEntry" data-ng-show="showParentDisc">
     	    <div class="col-md-12">
     	 		<div class="col-md-4">Parent Name</div>
     	   	 	<div class="col-md-4"> Relationship</div>
           		<div class="col-md-3">Phone Number </div>
           		<div class="col-md-1"></div>
           	</div>
           	
     	
     	 	<div class="col-md-12">
     	 		<div class="col-md-4"><input type="text" capitalize-first  class="form-control" data-ng-model="newParentDisc.name" placeholder="First Name Last Name"></div>
     	   	 	<div class="col-md-4"><input type="text" capitalize-first class="form-control"  data-ng-model="newParentDisc.discussorsRelationship" placeholder="Relationship to Patient"></div>
           		<div class="col-md-3"><input type="text" class="form-control" name="newParentDisc.phone" data-ng-model="newParentDisc.phone" ui-mask="(999) 999-9999" ></div>
           		<div class="col-md-1"><button data-ng-disabled="disableParentDiscSave"  class="btn btn-success input-sm" data-ng-click="saveDiscussion(newParentDisc);">Save</button></div>
           	</div>
           	
           	
           	<div data-ng-repeat="pd in polstBean.parentDiscussions" class="col-md-12">
           		<div class="col-md-4"><input type="text"  class="form-control" data-ng-model="pd.name" disabled></div>
	     		<div class="col-md-4"><input type="text"  class="form-control" data-ng-model="pd.discussorsRelationship" disabled></div>
	     		<div class="col-md-3"><input type="text"  class="form-control" data-ng-model="pd.phone" ui-mask="(999) 999-9999"  disabled></div>
	     	  	<div class="col-md-1"><button class="btn btn-warning  input-sm" data-ng-click="deleteDiscussion(pd);">Remove</button></div>
     	 	</div>
           	
     	</div>
      	
    	<div class="checkboxLeft">
        	 <input type="checkbox" data-ng-checked="showSurrogateDisc"  data-ng-click="toggleSurrogateDiscussions()">
        	<label> Surrogate (Legal Authority Name, Source and Phone Number)</label>
        </div>
      	<div id="surrogateDiscussionEntry" data-ng-show="showSurrogateDisc">
      		<div class="col-md-12">
	    	   	<div class="col-md-4">Surrogate Name</div>
	    	   	<div class="col-md-4">Legal Authority Source</div>
	           	<div class="col-md-3">Phone Number</div>
	           	<div class="col-md-1"></div>
	        </div>  
      	
      		<div class="col-md-12">
	    	   	<div class="col-md-4"><input type="text" capitalize-first class="form-control" data-ng-model="newSurrogateDisc.name" placeholder="First Name Last Name"></div>
	    	   	<div class="col-md-4"><input type="text" capitalize-first class="form-control" data-ng-model="newSurrogateDisc.discussorsRelationship" placeholder="Legal Authority Source"></div>
	           	<div class="col-md-3"><input type="text" id="newSurrogateDisc.phone"  name="newSurrogateDisc.phone" class="form-control" data-ng-model="newSurrogateDisc.phone" ui-mask="(999) 999-9999" ></div>
	           	<div class="col-md-1"><button data-ng-disabled="disableSurrogateDiscSave" class="btn btn-success input-sm"  data-ng-click="saveDiscussion(newSurrogateDisc);">Save</button></div>
	        </div>   	
           	 <div data-ng-repeat="s in polstBean.surrogateDiscussions" class="col-md-12">
           	  <div class="col-md-4"> <input type="text" class="form-control" data-ng-model="s.name" disabled></div>
     	  	  <div class="col-md-4"><input type="text" class="form-control" data-ng-model="s.discussorsRelationship" disabled></div>
     		  <div class="col-md-3"> <input type="text" class="form-control" data-ng-model="s.phone" ui-mask="(999) 999-9999"  disabled></div>
     		  <div class="col-md-1"> <button class="btn btn-warning input-sm" data-ng-click="deleteDiscussion(s);">Remove</button></div>
	     	 </div>
           	
     	</div>
    
       	<div class="checkboxLeft">
	         <input type="checkbox" data-ng-checked="showOtherDisc" data-ng-click="toggleOtherDiscussions()">
			 <label>Other</label>
        </div>
        <div id="otherDiscussionEntry" data-ng-show="showOtherDisc" >
        	<div class="col-md-12">
        	  	<div class="col-md-4">Name</div>
	       		<div class="col-md-4">Association to Patient</div>
	           	<div class="col-md-3">Phone Number</div>
	           	<div class="col-md-1"></div>
		     </div>
        
        	<div class="col-md-12">
        	  	<div class="col-md-4"><input type="text" capitalize-first class="form-control" data-ng-model="newOtherDisc.name" placeholder="First Name Last Name"></div>
	       		<div class="col-md-4"><input type="text" capitalize-first class="form-control" data-ng-model="newOtherDisc.discussorsRelationship" placeholder="Association To Patient"></div>
	           	<div class="col-md-3"><input type="text" id="newOtherDisc.phone" name="newOtherDisc.phone" class="form-control" data-ng-model="newOtherDisc.phone" ui-mask="(999) 999-9999" ></div>
	           	<div class="col-md-1"><button data-ng-disabled="disableOtherDiscSave" class="btn btn-success  input-sm"  data-ng-click="saveDiscussion(newOtherDisc);">Save</button></div>
		     </div>   
	        <div data-ng-repeat="o in polstBean.otherDiscussions" class="col-md-12">
	        	<div class="col-md-4"><input type="text" class="form-control" data-ng-model="o.name" disabled></div>
	     		<div class="col-md-4"><input type="text" class="form-control" data-ng-model="o.discussorsRelationship" disabled></div>
	     	 	<div class="col-md-3"><input type="text" class="form-control" data-ng-model="o.phone" ui-mask="(999) 999-9999"  disabled></div>
	     		<div class="col-md-1"><button class="btn btn-warning  input-sm"  data-ng-click="deleteDiscussion(o);">Remove</button></div>
	     	</div>
	       	
     	</div>
   		  
    
    
    
    
    
</div>   
</row>
      
<div class="clearfix"></div>  
</form>


<!--end of discussion -->






<h3 id="advanceDirective">Advanced Directive</h3>
											
<form name="advDirectiveForm" id="advDirectiveForm" role="form">
<row>
<div class="col-md-12">
 
	<div class="radioLeft">
		<input type="radio" name="advancedDirective" data-ng-model="polstBean.advancedDirective" value="YES"  data-ng-change="savePolst()">
  		<label>The patient has completed an advance directive.</label>
 	</div>
	<div class="radioLeft">
   		<input type="radio" name="advancedDirective"  data-ng-model="polstBean.advancedDirective" value="NO" data-ng-change="savePolst()">
   		<label>The patient has not completed an advance directive </label>
	</div>
 
	<div class="radioLeft">
  		<input type="radio" name="advancedDirective" data-ng-model="polstBean.advancedDirective" value="NO_CHOICE" data-ng-change="savePolst()">
  		<label>No choice indicated on paper form </label>
	</div>
  <p/>	
  
   <div class="form-group left-inner-addon  "> 
    	<label>Date Completed:   </label>  
   	 	<input type="text" date-range future='false' name="advancedDirectiveCompleteDate" id="advancedDirectiveCompleteDate" data-on-blur="savePolst(advDirectiveForm.$valid)" class="form-control input-sm" data-ng-model="polstBean.advancedDirectiveCompleteDate"   data-ui-mask="99-99-9999"     >
       	<div class="help-block" data-ng-show="advDirectiveForm.advancedDirectiveCompleteDate.$error.dateRange">Advanced Directive Completed Dates Cannot be in Future</div><br/>
 
   </div>
  
  <p>Other instructions or clarification:</p>
  <textarea class="form-control" capitalize-first  rows="3" data-ng-model="polstBean.advancedDirectiveInstructions" data-on-blur="savePolst();"></textarea>
  </div> 
</row>
<div class="clearfix"></div>
</form>

<!-- Advanced Directive -->





<h3 id="medicalCondition">Medical Condition</h3>
											
<form role="form">
<row>
<div class="col-md-12">
 <p>The following is a brief description of the patient's health conditions and health status that have been considered in completing this form.</p>
  <textarea class="form-control" capitalize-first  rows="3" data-ng-model="polstBean.medicalCondition" data-on-blur="savePolst();"></textarea>
  </div><!--end of medical condition-->
  <div class="clearfix"></div>
</row>
 
</form>


<!-- end Medical Condition -->







<h3 id="authorizedBy">Authorized By</h3>
											
<form role="form">
<p class="bold">POLST form authorized by:</p> 
 

 
    	<div class="checkboxLeft">
         	<input type="checkbox" data-ng-model="polstBean.patientAuthorized" data-ng-click="udpdatePatientAuthorization()" >
        	<label> Patient </label>
        </div>  

     	
     	<div class="checkboxLeft">
         	<input type="checkbox" data-ng-checked="showParentAuth" data-ng-click="toggleParentAuthorizations()">
        	<label> Parent of a minor child (Name, Relationship, Phone and Date) </label> 
         </div>  
     	
		<div id="parentAuthorizationEntry" data-ng-show="showParentAuth">
		
			<div class="col-md-12">
			 	<div class="col-md-3">Authorizer Name</div>
	           	<div class="col-md-2">Relationship</div>
	           	<div class="col-md-2">Phone</div>
	           	<div class="col-md-2">Authorized Date</div>
	           	<div class="col-md-2"> </div>
			</div>
			<div class="col-md-12">
			 	<div class="col-md-3"><input type="text" capitalize-first class="form-control input-sm" data-ng-model="newParentAuth.authorizerName" placeholder="Authorizer Name"></div>
	           	<div class="col-md-2"><input type="text" capitalize-first class="form-control input-sm" data-ng-model="newParentAuth.authorizerRelationship" placeholder="Relations to Pt"></div>
	           	<div class="col-md-2"><input type="text" class="form-control input-sm" data-ng-model="newParentAuth.authorizerPhone"  ui-mask="(999) 999-9999" ></div>
	           	<div class="col-md-2"><input type="text" date-range future='false' class="form-control input-sm" name="authorizedDate"  data-ng-model="newParentAuth.authorizedDate"  data-ui-mask="99-99-9999" ></div>
	           	<div class="col-md-2"><button class="btn btn-success input-sm" data-ng-disabled="disableParentAuthSave" data-ng-click="saveAuthorization(newParentAuth);">Save</button></div>
			</div>
		
			<div data-ng-repeat="pa in polstBean.parentAuthorizations" class="col-md-12" >
	     		<div class="col-md-3"><input type="text" class="form-control input-sm" data-ng-model="pa.authorizerName" disabled></div>
	     		<div class="col-md-2"><input type="text" class="form-control input-sm" data-ng-model="pa.authorizerRelationship" disabled></div>
	     		<div class="col-md-2"><input type="text" class="form-control input-sm" data-ng-model="pa.authorizerPhone" ui-mask="(999) 999-9999"   ></div>
	     		<div class="col-md-2"><input type="text" class="form-control input-sm" data-ng-model="pa.authorizedDate"    disabled></div>
	     		<div class="col-md-2"><button class="btn btn-warning input-sm"  data-ng-click="deleteAuthorization(pa);">Remove</button></div>
	     	</div>
     	
     	   
     	</div>
   
    
    	<div class="checkboxLeft">
        	<input type="checkbox" data-ng-checked="showSurrogateAuth" data-ng-click="toggleSurrogateAuthorizations()"> 
        	<label>Surrogate</label>
        </div>  
        
    	<div id="surrogateAuthorizationEntry" data-ng-show="showSurrogateAuth">
    	
    		<div class="col-md-12">
			 	<div class="col-md-3">Authorizer Name</div>
	           	<div class="col-md-3">Appointed Agent</div>
	           	<div class="col-md-2">Phone</div>
	           	<div class="col-md-2">Authorized Date</div>
	           	<div class="col-md-2"> </div>
			</div>
    	
			<div class="col-md-12">
			 	<div class="col-md-3"><input type="text"  capitalize-first class="form-control input-sm" data-ng-model="newSurrogateAuth.authorizerName" placeholder="Authorizer Name"></div>
	   	 	 	<div class="col-md-3"><select data-ng-model="newSurrogateAuth.appointedAgent"  class="form-control input-sm"  >
						   	 	    	<option value="">--Select Appt Agent--</option> 
						    			<option value="APPOINTED_AGENT">Appointed Agent</option>
						    			<option value="APPOINTED_GUARDIAN">Court Appointed Guardian</option>
						    			<option value="DEFAULT_SURROGATE">Default Surrogate</option>
	  		 						</select></div>
	           	<div class="col-md-2"><input type="text" class="form-control input-sm"  data-ng-model="newSurrogateAuth.authorizerPhone" ui-mask="(999) 999-9999" ></div>
	           	<div class="col-md-2"><input type="text" date-range future='false' class="form-control input-sm"  data-ng-model="newSurrogateAuth.authorizedDate"  data-ui-mask="99-99-9999"  ></div>
	           	<div class="col-md-2"><button data-ng-disabled="disableSurrogateAuthSave" class="btn btn-success input-sm" class="form-control input-sm"  data-ng-click="saveAuthorization(newSurrogateAuth);">Save</button></div>
	        </div>
           	
           	<div data-ng-repeat="s in polstBean.surrogateAuthorizations" class="col-md-12" >
	     		<div class="col-md-3"><input type="text"  class="form-control input-sm"  data-ng-model="s.authorizerName" disabled></div>
	     		<div class="col-md-3"><input type="text"  class="form-control input-sm"  size="26" data-ng-model="s.appointedAgent" disabled></div>
	     	 	<div class="col-md-2"><input type="text"  class="form-control input-sm" data-ng-model="s.authorizerPhone" ui-mask="(999) 999-9999"  disabled></div>
	     		<div class="col-md-2"><input type="text"  class="form-control input-sm" data-ng-model="s.authorizedDate"   disabled></div>
	     		<div class="col-md-2"><button class="btn btn-warning input-sm" data-ng-click="deleteAuthorization(s);">Remove</button></div>
	     	</div>
           	
     	</div>
     	
 
 <div class="clearfix"></div>
</form>








<div id="readyToProcess" data-ng-show="polstBean.sectionStatus.readyToPrepare">

<h3 id="preparer">Preparer </h3>
<form name="prepareForm" id="prepareForm" role="form">
<p>Please enter the name of the person who completed the form with the patient</p>
	<row>
  		<div class="form-group col-md-6" data-ng-class="{'has-error': prepareForm.preparedBy.$error.required}">
	    	<label for="preparedBy">Form Preparer</label>
	    	<input name="preparedBy" id="preparedBy" capitalize-first type="text" class="form-control" data-ng-disabled="'PENDING_CERTIFICATION' === polstBean.status" placeholder="Form Preparer" data-ng-model="polstBean.preparedBy"  required >
	    	<span  class="help-block" data-ng-show="prepareForm.preparedBy.$error.required">Required</span>
	  	</div>
	  	<div class="form-group col-md-6" data-ng-class="{'has-error': prepareForm.preparedDate.$error.required}">
	    	<label for="preparedDate">Date Prepared </label>
	    	<input type="text" id="preparedDate" name="preparedDate" date-range future='false'  class="form-control"  data-ng-disabled="'PENDING_CERTIFICATION' === polstBean.status"  data-ng-model="polstBean.preparedDate" data-ui-mask="99-99-9999"  required>
	 	 	<span  class="help-block" data-ng-show="prepareForm.preparedDate.$error.dateRange">Prepared Date cannot be in future</span> 
	 		<span  class="help-block" data-ng-show="prepareForm.preparedDate.$error.required">Required</span> 
	 		<br/><button class="btn btn-success" data-ng-hide="'PENDING_CERTIFICATION' === polstBean.status" data-ng-disabled="prepareForm.$invalid"  data-ng-click="prepareEpolst()">Submit for Certification</button> 
	 </div>
	</row>
  <p>The objective of ePOLST is to align exactly with patient's wishes as documented on the paper POLST form that is signed by the patient or surrogate. Changes may not be made to ePOLST that are not consistent with the signed paper POLST form. </p>
  <p>A copy of the signed paper POLST form should be kept in the patient's medical records.</p>
 
</form>






<div data-ng-show="(role === 'ROLE_MEDCERT' && polstBean.sectionStatus.preparerComplete === true  ) || polstBean.sectionStatus.certifiedComplete === true">
<h3 id="certification">Certification</h3>

<form role="form">
<p>Enter the name of the licensed provider who is authorized to certify the form. </p>
	<row>
  		<div class="form-group col-md-6">
	    	<label for="licensedProvider">Licensed Provider</label>
	    	<input type="text" class="form-control" id="licensedProvider" placeholder="Enter licensed provider" data-ng-model="polstBean.licensedProvider"  data-ng-disabled="true" >
	  	</div>
	  	<div class="form-group col-md-6" >
	    	<label data-ng-show="'ACTIVE'===polstBean.status"  for="certifiedDate">Date Certified</label>
	    	<input type="text" data-ng-show="'ACTIVE'===polstBean.status"  class="form-control" id="certifiedDate" data-ng-model="polstBean.certifiedDate" data-ui-mask="99-99-9999"  >
	 		<br/><button class="btn btn-success" data-ng-click="certifyEpolst()">Submit to Activiate</button> 
	  </div>
	</row>
  <p>The objective of ePOLST is to align exactly with patient's wishes as documented on the paper POLST form that is signed by the patient or surrogate. Changes may not be made to ePOLST that are not consistent with the signed paper POLST form. </p>
  <p>A copy of the signed paper POLST form should be kept in the patient's medical records.</p>
<div class="clearfix"></div>
</form>
</div><!-- certify --> 


</div><!-- ready to process -->
  

</fieldset>






</div><!--end of bootstrap row-->

                       
<div class="clearfix"></div>
<div class="clearfix"></div>


</div>	<!-- div polstForm -->		
			
</div> <!-- row -->
</div> <!-- content-main -->		
</div> <!-- content -->
</div> <!-- content-wrapper -->







</div> <!-- ePolst -->

</sec:authorize>
 