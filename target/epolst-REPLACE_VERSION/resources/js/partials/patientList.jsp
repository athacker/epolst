<sec:authorize access="hasRole('ROLE_MEDSTAFF, ROLE_ADMIN, ROLE_MEDCERT')">
<div id="patientList"  data-ng-controller="patientController"  data-ng-init="initPatientList()" data-ng-show="role != 'ROLE_EMS'" >

<div id="content-wrapper" class="site-full clearfix">
<div id="content" class="site-full">
<div id="content-main" class="site">
<div class="aaron">
<div class="table-responsive">
<div class="clearfix"> </div>
 
 
<div class="btn-header" >
    <div class="search">  
	 	<input type="radio" data-ng-change="changeView()" data-ng-model="searchBean.showAll"  data-ng-value="true"   /> &nbsp;&nbsp; 
 		<font color="white"><label data-tooltip="{{toolTipAllPt}}">View All</label>   </font> &nbsp;&nbsp;
 		<input type="radio" data-ng-change="changeView()" data-ng-model="searchBean.showAll" data-tooltip="{{toolTipAll}}"  data-ng-value="false" /> &nbsp;&nbsp; 
 		<font color="white"><label data-tooltip="{{toolTipMine}}">View Mine</label>   </font>  &nbsp;&nbsp; 
 	 	<input type="text" class="search" name="patientFilter" data-ng-model="quickSearchName" placeholder="Patient Search(Last Name, First Name)"  />
	 </div> 
</div>

 <button class="btn btn-success" data-ng-disabled="disableAddNew" data-ng-click="addNewPatient();" >   Add New Patient</button>
 <div  class="table-responsive">
	 <table class="table table-striped table-hover table-condensed" >
	 <thead>
	      <tr>
	    	<th><div data-ng-click="predicate = 'lastName'; sortLname=true" data-ng-hide="sortLname" class="ascending">Last Name</div>
	            <div data-ng-click="predicate = '-lastName'; sortLname=false" data-ng-show="sortLname" class="descending">Last Name</div> 
	    	</th>
	        <th><div data-ng-click="predicate = 'firstName'; sortFname=true" data-ng-hide="sortFname" class="ascending">First Name</div>
	    	    <div data-ng-click="predicate = '-firstName';sortFname=false" data-ng-show="sortFname" class="descending">First Name</div> 
	    	</th>
	    	<th><div data-ng-click="predicate = 'middleName';  sortMiddle=true" data-ng-hide="sortMiddle" class="ascending">Middle Name</div>
	    	    <div data-ng-click="predicate = '-middleName'; sortMiddle=false" data-ng-show="sortMiddle"  class="descending">Middle Name</div> 
	    	</th>
	    	<th><div data-ng-click="predicate = 'suffix';  sortSuffix=true" data-ng-hide="sortSuffix" class="ascending">Suffix</div>
	    	    <div data-ng-click="predicate = '-suffix'; sortSuffix=false" data-ng-show="sortSuffix"  class="descending">Suffix</div> 
	    	</th>
			 <th><div data-ng-click="predicate = 'gender';  sortGender=true" data-ng-hide="sortGender" class="ascending" >Gender</div>
	    	    <div data-ng-click="predicate = '-gender'; sortGender=false" data-ng-show="sortGender"  class="descending" >Gender</div> 
	    	</th>
	        <th><div data-ng-click="predicate = 'dob'; sortDob=true" data-ng-hide="sortDob" class="ascending">DOB</div>
	    	    <div data-ng-click="predicate = '-dob'; sortDob=false" data-ng-show="sortDob"  class="descending">DOB</div> 
	    	</th>
			 <th><div data-ng-click="predicate = 'physicianUserName'; sortPhy=true" data-ng-hide="sortPhy" class="ascending">Physician</div>
	    	     <div data-ng-click="predicate = '-physicianUserName '; sortPhy=false" data-ng-show="sortPhy"  class="descending">Physician</div> 
	    	</th>
			 <th><div data-ng-click="predicate = 'addedUserId '; sortCreator=true" data-ng-hide="sortCreator" class="ascending">Creator</div>
	    	     <div data-ng-click="predicate = '-addedUserId '; sortCreator=false"data-ng-show="sortCreator"  class="descending">Creator</div> 
			</th>
		
			 
	 </tr>
	     
	 </thead>
	 <tbody> 
	  <tr data-ng-repeat="pt in patientList | orderBy:predicate:reverse" data-ng-click="showPatientDetail(pt.id)"> 
	 		
	 		<td> {{pt.lastName}}</td> 
	 		<td> {{pt.firstName}}</td> 
	 		<td> {{pt.middleName}}</td>
	 		<td> {{pt.suffix }}</td>
	 		<td> {{pt.gender}}</td> 
	 		<td> {{pt.dob | date: date: mediumDate}}</td> 
	 		<td> {{pt.physicianFullName }}</td>
	 		<td> {{pt.creatorName }}</td> 
	 	 
	 </tr>
	 
	 
	 </tbody>
	 </table>
 </div>
 
 
 
 <span class="thumbnail"  >
  
    <button class="btn" data-ng-disabled="disablePrevious" data-ng-click="getPrevious()"> Show Previous {{recordsPerPage}}  &nbsp;</button>
 	<button class="btn" data-ng-disabled="disableNext" data-ng-click="getNext()"> Show Next {{recordsPerPage}}  &nbsp; </button>
 	
 	<b>Total Records Found</b>&nbsp;&nbsp; {{totalRecords}}  &nbsp;&nbsp; &nbsp;&nbsp;
 	<b>Page</b>&nbsp;&nbsp; {{currentPage}} of  &nbsp;{{totalPages}}  &nbsp;&nbsp;
</span>
 
 
 
 
 
 </div><!-- responsive -->
 </div><!-- arron -->
 </div><!-- content-main -->
 </div><!-- content -->
 </div><!-- content-wrapper-->
 
 
 </div><!-- userList -->
 
 </sec:authorize>