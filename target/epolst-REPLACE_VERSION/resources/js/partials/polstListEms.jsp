<sec:authorize access="hasRole('ROLE_EMS')" >
<div id="polstListEms"  data-ng-controller="emsController"   >
 
<div id="content-wrapper" class="site-full clearfix">
<div id="content" class="site-full">
<div id="content-main" class="site">
<div class="aaron">
<div class="table-responsive">
<div class="clearfix"> </div>
 
 
<div class="btn-header">
  	<div class="search">
  		 <label>Patient Name</label>  
  		 <input type="text" class="search" data-ng-model="searchBean.quickSearchName" placeholder="Patient Search(Last Name, First Name)"  />
  		 <label>DOB</label>  
  		 <input class="searchDate"  data-ng-model="searchBean.quickSearchDob"  data-ui-mask="99-99-9999"  />
  	</div> 
</div>
        

<div class="table-responsive">
	<table class="table table-striped table-hover">

    <thead>
     
     <tr>
        <th><div data-ng-click="predicate = 'lastName'; sortLname=true" data-ng-hide="sortLname" class="ascending">Last Name</div>
            <div data-ng-click="predicate = '-lastName'; sortLname=false" data-ng-show="sortLname" class="descending">Last Name</div> 
    	</th>
        <th><div data-ng-click="predicate = 'firstName'; sortFname=true" data-ng-hide="sortFname" class="ascending">First Name</div>
    	    <div data-ng-click="predicate = '-firstName';sortFname=false" data-ng-show="sortFname" class="descending">First Name</div> 
    	</th>
    	<th><div data-ng-click="predicate = 'middleName';  sortMiddle=true" data-ng-hide="sortMiddle"  class="ascending">Middle Name</div>
    	    <div data-ng-click="predicate = '-middleName'; sortMiddle=false" data-ng-show="sortMiddle"  class="descending">Middle Name</div> 
    	</th>
    	<th><div data-ng-click="predicate = 'suffix';  sortSuffix=true" data-ng-hide="sortSuffix"  class="ascending">Suffix</div>
    	    <div data-ng-click="predicate = '-suffix'; sortSuffix=false" data-ng-show="sortSuffix"  class="descending">Suffix</div> 
    	</th>
    	<th><div data-ng-click="predicate = 'dob';  sortDob=true" data-ng-hide="sortDob"  class="ascending">DOB</div>
    	    <div data-ng-click="predicate = '-dob'; sortDob=false" data-ng-show="sortDob"  class="descending">DOB</div> 
    	</th>
   	
   	  </tr>	
    </thead>
                        
    <tbody>
    <tr data-ng-repeat="pt in searchPatients | orderBy:predicate:reverse " data-ng-click="showPolst(pt.id)";> 
     	<td> {{pt.lastName}} </td>
    	<td> {{pt.firstName}} </td>
    	<td> {{pt.middleName}} </td>
    	<td> {{pt.suffix}} </td>
    	<td> {{pt.dob | date: date: mediumDate}} </td>
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

