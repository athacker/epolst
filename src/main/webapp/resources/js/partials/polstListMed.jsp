<sec:authorize access="hasRole('ROLE_MEDSTAFF, ROLE_ADMIN, ROLE_MEDCERT')">
<div id="polstListMed"  data-ng-controller="ePolstController" data-ng-init="initPhysicianList()"  data-ng-show="role != 'ROLE_EMS'" data-ng-cloak>
 
<div id="content-wrapper" class="site-full clearfix">
<div id="content" class="site-full">
<div id="content-main" class="site">
<div class="aaron">
<div class="table-responsive">
<div class="clearfix"> </div>


 
 
<div class="btn-header">
	<div class="search">
		<input type="radio" data-ng-change="changeView()" data-ng-model="searchBean.showAll" ng-value="true"   /> &nbsp;&nbsp; 
 		<font color="white"><label data-tooltip="{{toolTipAllEpolst}}">View All</label>   </font> &nbsp;&nbsp;
 		<input type="radio" data-ng-change="changeView()" data-ng-model="searchBean.showAll"  ng-value="false" /> &nbsp;&nbsp; 
 		<font color="white"><label data-tooltip="{{toolTipMyEpolst}}">View Mine</label>   </font>  &nbsp;&nbsp; 
 	 	<input type="text" class="search" data-ng-model="quickSearchName" data-tooltip="{{status}}" placeholder="Patient Search(Last Name, First Name)"  />
	</div> 
</div>


</div>

	<div>
	<input type="radio" data-ng-change="changeView()"  data-ng-model="searchBean.polstStatus" value="ALL"/>  
	<span  data-tooltip="{{toolTipAll}}" data-tooltip-placement="bottom">
	<font color="white"><b>All Status</b>&nbsp;&nbsp; &nbsp;</font>
	</span>
	
	<input type="radio" data-ng-change="changeView()"  data-ng-model="searchBean.polstStatus" value="ACTIVE" />
	<span data-tooltip="{{toolTipActive}}" data-tooltip-placement="bottom">
	<font color="white"><b> Active </b>&nbsp;&nbsp;&nbsp;  </font>
	</span>
	
	
	<input type="radio" data-ng-change="changeView()"  data-ng-model="searchBean.polstStatus" value="IN_PROCESS" />
	<span data-tooltip="{{toolTipInProcess}}" data-tooltip-placement="bottom">
	<font color="white"><b> In Process</b> &nbsp;&nbsp;&nbsp; </font>
	</span>
	
	<input type="radio" data-ng-change="changeView()"  data-ng-model="searchBean.polstStatus" value="PENDING_CERTIFICATION"/>
	<span data-tooltip="{{toolTipPending}}" data-tooltip-placement="bottom">
	<font color="white"><b> Pending Certification </b>&nbsp;&nbsp;&nbsp; </font>
	</span> 
	</div>		 
		        
<div class="table-responsive">
	<table class="table table-striped table-hover table-condensed"">
    <thead>
        <tr>
     	 <th><div data-ng-click="predicate = 'patientLastName'; sortLname=true" data-ng-hide="sortLname" class="ascending">Last Name</div>
    	     <div data-ng-click="predicate = '-patientLastName'; sortLname=false" data-ng-show="sortLname" class="descending">Last Name</div> 
		 </th>
	   <th>	<div ng-click="predicate = 'patientFirstName'; sortFname=true" data-ng-hide="sortFname" class="ascending">First Name</div>
    	    <div ng-click="predicate = '-patientFirstName';  sortFname=false" data-ng-show="sortFname" class="descending">First Name</div> 
    	</th>
        <th><div data-ng-click="predicate = 'gender';  sortGender=true" data-ng-hide="sortGender"  class="ascending" >Gender</div>
    	    <div data-ng-click="predicate = '-gender'; sortGender=false" data-ng-show="sortGender" class="descending" >Gender</div> 
    	</th>
        <th><div data-ng-click="predicate = 'dob'; sortDob=true" data-ng-hide="sortDob"  class="ascending">DOB</div>
    	     <div data-ng-click="predicate = '-dob'; sortDob=false" data-ng-show="sortDob" class="descending">DOB</div> 
    	</th>
    	 <th><div data-ng-click="predicate = 'physicianLastName'; sortPhy=true" data-ng-hide="sortPhy"  class="ascending">Physician</div>
    	     <div data-ng-click="predicate = '-physicianLastlName'; sortPhy=false" data-ng-show="sortPhy" class="descending">Physician</div> 
    	</th>
         <th><div data-ng-click="predicate = 'polstStatus'; sortStatus=true" data-ng-hide="sortStatus"  class="ascending"  >Status</div>
    	     <div data-ng-click="predicate = '-polstStatus'; sortStatus=false" data-ng-show="sortStatus" class="descending" >Status</div> 
    	</th> 
        <th><div data-ng-click="predicate = 'createdDate'; sortCreated=true" data-ng-hide="sortCreated" class="ascending">Entered</div>
    	    <div data-ng-click="predicate = '-createdDate'; sortCreated=false" data-ng-show="sortCreated" class="descending">Entered</div> 
    	</th> 
        <th><div data-ng-click="predicate = 'lastUpdatedDate'; sortUpdated=true"  data-ng-hide="sortUpdated"  class="ascending">Updated</div>
    	    <div data-ng-click="predicate = '-lastUpdatedDate'; sortUpdated=false" data-ng-show="sortUpdated" class="descending">Updated</div> 
    	</th> 
        </tr>
    </thead>
                        
    <tbody>
        
      <tr ng-repeat="p in patientListForMed |  orderBy:predicate:reverse " data-ng-click="showPolstDetail(p.polstId, p.patientId);" >
	      <td>{{p.patientLastName}} </td>
	      <td>{{p.patientFirstName}}</td>
	      <td>{{p.gender}}</td>
	      <td>{{p.dob | date: date: mediumDate }}</td>
	      <td>{{p.physicianFirstName }} {{p.physicianLastName }}</td>
	      <td data-ng-class="{ inProcess: p.polstStatus==='IN_PROCESS', pending: p.polstStatus==='PENDING_CERTIFICATION' }">{{p.polstStatus}}</td>
	      <td>{{p.createdDate |  date:'MMM-dd-yyyy'  }}</td>
	      <td>{{p.lastUpdatedDate |  date:'MMM-dd-yyyy' }}</td>
      </tr>
      <tr>
      	<td colspan="8"><div  class="alert-success">{{message}}</div></td>
      </tr>
      
    </tbody>
    </table>
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
 
 
 
	
</div><!-- polstListMed -->	
</sec:authorize>

