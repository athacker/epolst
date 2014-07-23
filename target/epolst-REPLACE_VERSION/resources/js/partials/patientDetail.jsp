<sec:authorize access="hasRole('ROLE_ADMIN, ROLE_MEDCERT, ROLE_MEDSTAFF')">

<div id="patient" data-ng-controller="patientController" data-ng-init="initPatientDetail();"  data-ng-show="role != 'ROLE_EMS'" >
 
	<div id="content-wrapper" class="site-full clearfix">

	<div id="content" class="site-full">
		
	<div id="content-main" class="site">
	
	<row> <!--  main container row -->
	<div id="patientForm" class="col-md-11">		     
    
     <div class="page-header">
  	 	<h3 id="patientInfo">Patient Information -- {{fullName}}  </h3>
     </div>  
   
	
		<form name="patientForm" >
		<row>
		<div class=" col-md-3 pull-right clearfix">
		
		<div class="legend">
		<label> Patient ePOLST History </label>
		 
		<div class="history"  style="overflow:scroll; height:150px; overflow-x:hidden;">
		    <div ng-repeat="polst in patient.polstHistory" >
		     <label data-ng-class="{active: polst.polstStatus==='ACTIVE', pending: polst.polstStatus==='PENDING_CERTIFICATION', process: polst.polstStatus==='IN_PROCESS', inactive: polst.polstStatus==='IN_ACTIVE' }"><a href="#/polstDetail/{{polst.ePolstId}}/{{patient.patientId}}" role="button"> {{polst.label}}</a></label>
		    </div>
	    </div>
	 	<br/>
	    <span class="help-block"><span class="glyphicon glyphicon-exclamation-sign"></span> Active and Inactive ePOLST forms are <strong>view only</strong>.  Create a new ePOLST when revisions are required to existing forms.</span>
	 	<button class="btn btn-primary" data-ng-disabled="patientForm.$invalid"  data-ng-click="createNewEpolst( patientForm.$dirty );" data-ng-show="showNewPolstButton">Start New Polst</button>
	  	</div>
		</div>
		</row>



<row>
<div class="col-md-9">

  	<div class="col-md-4">
		<div class="form-group" data-ng-class="{'has-error': patientForm.firstName.$error.required}">
		    <label for="firstName">First Name</label>
		    <input capitalize-first class="form-control" name="firstName" id="firstName" data-ng-model="patient.firstName " placeholder="First Name" maxlength="30" required>
		  	<span  class="help-block" data-ng-show="patientForm.firstName.$error.required">Required</span> 
		 </div>
    </div>
    
    <div class="col-md-2">    
	    <div class="form-group">
		    <label for="middleName">Middle</label>
		    <input capitalize-first class="form-control" data-ng-model="patient.middleName"  id="middleName" placeholder="Middle"  maxlength="20">
		</div>
    </div>
    
     <div class="col-md-4">
	    <div class="form-group" data-ng-class="{'has-error': patientForm.lastName.$error.required}">
	    	<label for="lastName" >Last Name</label>
	    	<input capitalize-first data-on-blur="checkDuplicates();" class="form-control" data-ng-model="patient.lastName" name="lastName" id="lastName" placeholder="Last Name"  maxlength="30" required>
	  		<span class="help-block" data-ng-show="patientForm.lastName.$error.required">Required </span> 
	    </div>
    </div>
    
    <div class="col-md-2">    
	    <div class="form-group">
		    <label for="suffix" >Suffix</label>
		    <input capitalize-first class="form-control" data-ng-model="patient.suffix" name="suffix" id="suffix" placeholder="suffix"  maxlength="10" >
	 	</div>
    </div>
    
       
    
  
</div> 
</row> 




<row>
<div class="col-md-9">

	<div class="col-md-3">
	<label>Gender</label> 
	<div class="form-group" data-ng-class="{'has-error': patientForm.gender.$error.required}">
    	
    	<div class="radio-inline">
      		<label>
        	<input type="radio" name="gender" data-ng-model="patient.gender" required  value="MALE">Male</label>
    	</div>
    	
    	<div class="radio-inline">
      		<label>
        	<input type="radio" name="gender" data-ng-model="patient.gender" required value="FEMALE">Female</label>
    	</div>
     	<span class="help-block" data-ng-show="patientForm.gender.$error.required">Required </span>
  	</div> 
    </div>
    
    <div class="col-md-3">
    	<div class="form-group left-inner-addon" data-ng-class="{'has-error': patientForm.dob.$error.required}">
    	<label for="dob" > Date of Birth</label>   
        <input type="text" date-range future='false' data-ui-mask="99-99-9999"  class="form-control" data-ng-model="patient.dob " name="dob" id="dob" required>
       	<span class="help-block" data-ng-show="patientForm.dob.$error.required">Required - </span><br/>
       	<span class="help-block" data-ng-show="patientForm.dob.$error.dateRange">Valid DOB, cannot be in Future</span>
    </div>
    </div>
    
      
    
    <div class="col-md-3">
	    <div class="form-group left-inner-addon" data-ng-class="{'has-error': patientForm.dateOfDeath.$error.dateRange }">
		    <label for="dateOfDeath"  >Date of Death</label>
		    <input class="form-control" date-range future='false' name="dateOfDeath" id="dateOfDeath" data-ui-mask="99-99-9999"  data-ng-model="patient.dateOfDeath" id="dateOfDeath">
		    <span class="help-block" data-ng-show="patientForm.dateOfDeath.$error.dateRange">Valid Date Of Death cannot be in Future</span>
	 	</div>
    </div>
    
     <div class="col-md-3">
	    <div class="form-group left-inner-addon">
		    <label for="patientPhone"  >Phone:  </label>
		    <input class="form-control"  data-ui-mask="(999)999-9999"  data-ng-model="patient.patientPhoneNumber" id="patientPhone"   >
	 	</div>
    </div>
</div>
</row><!-- end of row -->







<row>    
<div class="col-md-9">
    <h4>Current Address</h4>
	    <div class="col-md-12">
		    <div class="form-group" data-ng-class="{'has-error': patientForm.addressCurrent.$error.required}">
		    <label for="addressCurrent">Street Address</label>
		    <input class="form-control" data-ng-model="patient.addressCurrent"  name="addressCurrent" id="addressCurrent" placeholder="Example: 100 South Main St." maxlength="50" required>
			<span class="help-block" data-ng-show="patientForm.addressCurrent.$error.required">Required</span>  
		 </div> 
    	</div>
    
     	<div class="col-md-4">
		    <div class="form-group" data-ng-class="{'has-error': patientForm.zipCurrent.$error.required}">
		    <label for=" zipCurrent"> </label>  
		    <input class="form-control" data-ui-mask="99999" data-ng-model="patient.zipCurrent"  name="zipCurrent"  id="zipCurrent" maxlength="5"  data-on-blur="getCityState(true);"  required>
		   	<span class="help-block" data-ng-show="patientForm.zipCurrent.$error.required">Required {{badZipCurrent}} </span>
	 	 </div>
	    </div>
          
	    <div class="col-md-4">
		    <div class="form-group" data-ng-class="{'has-error': patientForm.cityCurrent.$error.required}">
		    <label for=" cityCurrent">City</label> 
		    <input class="form-control" data-ng-model="patient.cityCurrent" data-ng-bind="patient.cityCurrent" name="cityCurrent" id="cityCurrent" placeholder="City" maxlength="20" required> 
 		 	<span class="help-block" data-ng-show="patientForm.cityCurrent.$error.required">Required</span>
 		 	</div> 
	    </div>
    
	    <div class="col-md-4">    
		    <div class="form-group" data-ng-class="{'has-error': patientForm.stateCurrent.$error.required}">
		    <label for="stateCurrent">State</label>	
         	<input class="form-control" type="text" name="stateCurrent" id="stateCurrent" ng-model="patient.stateCurrent" placeholder="Current State" required/>
 			<span class="help-block" data-ng-show="patientForm.stateCurrent.$error.required">Required </span>
 			</div>
	    </div>
     
	   
</div>
</row><!-- end of row -->

<row>    
  	<div class="col-md-9">
   	<h4>Permanent Address</h4>
   	<div class="col-md-9">
  	<div class="checkbox">
    <label>
      <input data-ng-model="permAddressSame" type="checkbox" data-ng-click="togglePermAddress()"> Select if permanent address is same as current address.
    </label>
 	 </div>
  	</div>
    
    <div class="col-md-12">
    <div class="form-group">
    <label for="addressPerm">Street Address</label>
    <input class="form-control" data-ng-model="patient.addressPerm" id="addressPerm" placeholder="Example: 100 South Main St.">
  	</div> 
    </div>
    
    <div class="col-md-4">
    <div class="form-group">
    <label for="zipPerm">Zip</label>
    <input class="form-control" data-ui-mask="99999" data-ng-model="patient.zipPerm" id="zipPerm" maxlength="5" data-on-blur="getCityState(false);" >
  	<span class="required" >{{badZipPermanent}} </span>
  	</div>
    </div>
    
    
    <div class="col-md-4">   
    <div class="form-group">
    <label for="cityPerm">City</label>
    <input class="form-control" data-ng-model="patient.cityPerm" data-ng-bind="patient.cityPerm" id="cityPerm" placeholder="Perm City">
  	</div>
    </div>
    
    <div class="col-md-4"> 
    <div class="form-group">
    <label for="statePerm">State</label>
    <input class="form-control" type="text" ng-model="patient.statePerm" typeahead="state for state in states| filter:$viewValue | limitTo:8"  placeholder="Perm State"/>
  	</div>
    </div>
    
   
</div><!-- end of row -->
</row>


<row>   
<div class="col-md-9">
	<h4>Physician Information</h4> 

    
    <div class="col-md-6">
	     <div class="form-group" data-ng-class="{'has-error': patientForm.physicianUserName.$error.required}">
	   		<label >Certified Physician</label>
	    	<div data-ng-show="'ROLE_MEDCERT' === role"> 
	    	<input type="text" class="form-control" name="physicianUserName" id="physicianUserName" data-ng-model="patient.physicianUserName" disabled />
	     	</div>
	  		<div data-ng-hide="'ROLE_MEDCERT' === role">
	  			<select data-ng-change="setPhone()" name="physicianUserName" id="physicianUserName" class="form-control" data-ng-model="patient.physicianUserName" data-ng-options="phy.userName as phy.displayName for phy in physicians" required></select>
	  	    </div>
	        <span class="help-block" data-ng-show="patientForm.physicianUserName.$error.required">Required</span>
  	 	</div>
    </div>
    
    
    <div class="col-md-6">
	    <div class="form-group" data-ng-class="{'has-error': patientForm.physicianPhoneNumber.$error.required}">
		    <label for="physicianPhoneNumber">Certified Physician Phone</label>
		    <input type="text" ui-mask="(999) 999-9999" class="form-control" data-ng-model="patient.physicianPhoneNumber" name="physicianPhoneNumber" id="physicianPhoneNumber" maxlength="15" required>
		 	<span class="help-block" style="display: none;">Please enter a valid phone number</span> 
		 	<span class="help-block" data-ng-show="patientForm.physicianPhoneNumber.$error.required">Required </label>
	  	</div>
    </div>
    
</div>    
    

 <div class="col-md-9">
  	<div class="checkbox">
    <label>
      <input data-ng-model="physicianSame" type="checkbox" data-ng-click="togglePhysicians()"> Select if Primary Care Provider is the same as Certified Physician .
    </label>
 	 </div>
  </div>





<div class="col-md-9">
    
    <div class="col-md-6">
	    <div class="form-group" data-ng-class="{'has-error': patientForm.primaryCareProvider.$error.required}">
	    <label for="primaryCareProvider">Primary Care Provider</label>
	    <input capitalize-first type="text" class="form-control" data-ng-model="patient.primaryCareProvider" name="primaryCareProvider" id="primaryCareProvider" placeholder="Name of Provider/Physician" required>
	  	<span class="help-block" data-ng-show="patientForm.primaryCareProvider.$error.required">Required</span>
	  	</div>
    </div>
    
    
    <div class="col-md-6">
	    <div class="form-group " data-ng-class="{'has-error': patientForm.primaryCarePhysicianPhoneNumber.$error.required}">
		    <label for="primaryCarePhysicianPhoneNumber">Primary Care Phone</label>	
		    <input type="text" ui-mask="(999) 999-9999" class="form-control" name="primaryCarePhysicianPhoneNumber" id="primaryCarePhysicianPhoneNumber" data-ng-model="patient.primaryCarePhysicianPhoneNumber"   required >
		 	<span class="help-block" style="display: none;">Please enter a valid phone number</span> 
		 	<span class="help-block" data-ng-show="patientForm.primaryCarePhysicianPhoneNumber.$error.required">Required</span>
	 	</div>
	 </div>	
	 
  </div>
    

</row><!-- end of row -->


<div class="clear"></div>
<row>
	<div class="col-md-6">
 	<button type="submit" data-ng-disabled="patientForm.$invalid" data-ng-click="savePatient()" class="btn btn-primary" data-ng-show="role != 'ROLE_EMS'">{{mode}}</button>
 	<button type="submit" data-ng-click="resetPatientForm( patientForm.$dirty )" class="btn btn-warning" data-ng-show="role != 'ROLE_EMS'">Revert Changes</button>
  	<button type="submit" data-ng-click="clearPatientForm( patientForm.$dirty )" class="btn btn-success" data-ng-show="role != 'ROLE_EMS'">Clear Form</button>
 	</div>
</row>

<div class="clear"></div>


</form>
</div>  
<div class="clear"/>       
   
</row><!-- end main container row -->            
			
			
			
			
</div> <!-- end #content-main -->
</div> <!-- end #content -->

</div> <!-- end #content-wrapper -->

<div class="border site-full"></div>

	
 
  
  
  
</div><!-- pt -->
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
 
</sec:authorize>