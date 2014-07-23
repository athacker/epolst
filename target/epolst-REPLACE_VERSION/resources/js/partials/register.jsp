<div id="register"  data-ng-controller="adminController" > 
 
<div id="content-wrapper" class="site-full clearfix">  
<div id="content" class="site-full">
<div id="content-main" class="site">
 
 
<form name="registerForm" >
<h3>{{registerTitle}}</h3>
 

<div data-ng-hide="isRegistered">	  
		 
	   <div class="radio">
        <input type="radio" data-ng-checked="registerEmt" value="true" ng-click="toggleView('emt')" /><label>Register as Emergency Responder (EMT/EMS)</label>
    	</div>
       <div class="radio">
        <input type="radio"  data-ng-checked="registerPhysician" value="true" ng-click="toggleView('physician')"/><label>Register as Social Worker, Nurse, Physician or Physician Assistant.</label>
       </div>
    	 
	
	 
	
	<div id=emtVerify" data-ng-show='registerEmt'>
	 
	     	<div class="form-group">
			    <label for="firstName" >Badge Number
			    <input class="form-control" name="badgeNumber" id="badgeNumber" data-ng-model="badgeNumber" placeholder="Badge Number"  ></label>
		 	</div>
	  
		 	 <div class="form-group">
			    <button class="btn btn-primary" data-ng-disabled="badgeNumber < 10" data-ng-click="registerEms()" >Verify</button> 
			</div>
	 	
	  </div>
 
	  <div id="physicianVerify" data-ng-show='registerPhysician'>
	 
  		<div class="form-group">
		    <label for="license" >License
		    <input class="form-control"  maxlength="13" name="license" id="license" data-ng-model="license" placeholder="License Number" required></label>
	  	</div>
	 	 <div class="form-group">
		    <button class="btn btn-primary" data-ng-disabled="license.length < 10" data-ng-click="registerUser()">Verify</button> 
	    </div>
	  
		</div>
	 
	 
	</div>
	 


	 
	
	<div data-ng-show="isRegistered">
	 	 {{registeredUser.firstName}} {{registeredUser.lastName}} has successfully registered into the ePOLST system. You will receive an eMAIL when access has been granted.<br/>
		 <p/>
		 <br/>
		 <p/>
		 <center><a class="btn btn-blue"  href="/epolst/logout">Log Off ePOLST</a>  </center>
		 <p/>
		 <br/>
		 <p/>
		 
	 </div>

	 </form> 
 
 





</div>
</div>
</div>




</div> 