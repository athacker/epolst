 <sec:authorize access="hasRole('ROLE_ADMIN' )"> 
 <div id="user" data-ng-controller="adminController" data-ng-init="initUserDetail();" data-ng-show="role === 'ROLE_ADMIN'"> 
 
 <div id="content-wrapper" class="site-full clearfix">  
 <div id="content" class="site-full">
 <div >
 
<form name="userForm" class="col-md-12">
 <h3 class="page-header">User Management</h3>


<row>
<div class="col-md-12">

<div class="col-md-3"> 
		<div class="form-group" data-ng-class="{'has-error': userForm.roleId.$error.required}">
		 	<label for="roleId">User Role</label> 
 		 	<select id="roleId" name="roleId" class="form-control" data-ng-model="user.roleId" data-ng-options="role.id as role.roleType for role in roles" required></select>
			<span class="help-block" data-ng-show="userForm.roleId.$error.required">Required</span> 
		</div>
	</div>

	<div class="col-md-3">
		<div class="form-group" data-ng-class="{'has-error': userForm.license.$error.required}">
			<label for="license">License/Badge </label>
			<input type="text" id="license" name="license" data-ng-model="user.license" class="form-control" id="registrationId" placeholder="License" data-on-blur="licenseLookup();" data-ng-disabled="user.roleId==1" required />
			<span  class="help-block" data-ng-show="userForm.license.$error.required">Required</span>
		</div>
	</div>
	<div class="col-md-3">
		<div class="form-group" data-ng-class="{'has-error': userForm.stateLicensed.$error.required}">
			<label for="stateLicensed" >State Licensed</label> 
			<input type="text" id="stateLicensed" name="stateLicensed" class="form-control" data-ng-model="user.stateLicensed" typeahead="state for state in states| filter:$viewValue | limitTo:8"  placeholder="State Licensed" required />
			<span  class="help-block" data-ng-show="userForm.stateLicensed.$error.required">Required</span>
		</div>
	</div>
	
	
	 
	
</div>


<div class="col-md-12">
	<div class="col-md-3">
	 	<div class="form-group" data-ng-class="{'has-error': userForm.userName.$error.required}">
	
			<label for="userName">UMD Login - User Name</label>
			<input type="text" id="userName" name="userName" class="form-control" data-ng-model="user.userName" placeholder="UMD User Name" required/>
		  	<span  class="help-block" data-ng-show="userForm.userName.$error.required">Required</span> 
	
		</div>
	
	</div>
	 
	
	<div class="col-md-3">
		<div class="form-group" data-ng-class="{'has-error': userForm.firstName.$error.required}">
			<label>First Name</label>
			<input capitalize-first type="text" id="firstName" name="firstName" class="form-control" data-ng-model="user.firstName"  placeholder="First Name" required/>
			<span  class="help-block" data-ng-show="userForm.firstName.$error.required">Required</span> 
		</div>
	</div>
	
	<div class="col-md-3">
		<div class="form-group" data-ng-class="{'has-error': userForm.lastName.$error.required}">
			<label for="lastName">Last Name</label>
			<input capitalize-first type="text" id="lastName" name="lastName" class="form-control" data-ng-model="user.lastName" placeholder="Last Name" required/>
			<span  class="help-block" data-ng-show="userForm.lastName.$error.required">Required</span> 
		</div>
	</div>
	
</div>




 



<div class="col-md-12">
	<div class="col-md-3">
		<div class="form-group" data-ng-class="{'has-error': userForm.phoneNumber.$error.required}">
			<label for="phoneNumber">Phone Number</label>
			<input type="text" id="phoneNumber"class="form-control" data-ng-model="user.phoneNumber" ui-mask="(999) 999-9999" name="phoneNumber" required/>
			<span  class="help-block" data-ng-show="userForm.phoneNumber.$error.required">Required</span> 
	 	</div>
	</div>
	<div class="col-md-3">
		<div class="form-group" data-ng-class="{'has-error': userForm.email.$error.required}">
			<label for="email">User eMail</label>
			<input type="text" id="email" name="email" class="form-control" data-ng-model="user.email" placeholder="User Email"  />
			<span  class="help-block" data-ng-show="userForm.email.$error.required">Required</span>
		</div>
	</div>
	

	
</div>

 

<div class="col-md-12"> 


	<div class="col-md-2"   >
		<label>System Verified&nbsp;&nbsp;  
		<input type="checkbox" data-ng-model="user.verified"   data-ng-checked="user.verified"  />   
		</label>
	</div>	 


	<div class="col-md-2">
		<label>Manual Verified&nbsp;&nbsp;  
		<input type="checkbox" data-ng-model="user.verifiedManual"   data-ng-checked="user.verifiedManual"  />   
		</label>
	</div>	 
	
	<div class="col-md-2">
		<label>Active&nbsp;&nbsp;
		<input type="checkbox" data-ng-model="user.active"   ng-checked="user.active" />  </label> 
	</div> 
 	<div class="col-md-4"  > 
 		<button type="submit" data-ng-disabled="userForm.$invalid"  data-ng-click="saveUser()" class="btn btn-primary input-sm">Save Changes</button>
  	 &nbsp;&nbsp; 
   		<button type="submit" data-ng-disabled="userForm.$invalid"  data-ng-click="clearPolstUserForm(userForm.$dirty)" class="btn btn-success input-sm">Add New User</button>
 	</div>
</div>


</div>



</row>
</form> 
<div class="clearfix"></div>

 </div> <!-- content -->
 </div> <!-- content-wrapper -->
 </div> <!--  user -->
 </sec:authorize> 