<!-- <sec:authorize access="hasRole('ROLE_ADMIN' )">  -->
 <div id="userList"  data-ng-controller="adminController" data-ng-init="initUserList();" data-ng-show="role == 'ROLE_ADMIN'">

<div id="content-wrapper" class="site-full clearfix">
<div id="content" class="site-full">
<div id="content-main" class="site">
<div class="aaron">
<div class="table-responsive">
<div class="clearfix"> </div>
 
 
<div class="btn-header">
  	<div class="search">
  		<label>User Search</label>
		<input type="text" class="search" data-ng-model="quickSearchName" placeholder="Last Name, First Name of User"  />
	 	<a href="#/addUser" class="btn btn-success" role="button">Add New User</a> 
	</div> 
</div>

<div class="inputContainer">
 <input type="checkbox" data-ng-change="changeView()" data-ng-model="showAll"/>  
 <label>View All Users</label>
</div>  		 
 
 <table class="table table-striped table-hover">
 <thead>
     <tr>
  		 <th><div data-ng-click="predicate = 'userName'; sortUname=true" data-ng-hide="sortUname" class="ascending">User Name</div>
    	     <div data-ng-click="predicate = '-userName'; sortUname=false" data-ng-show="sortUname" class="descending">User Name</div> 
		 </th>
		 <th><div data-ng-click="predicate = 'firstName'; sortFname=true" data-ng-hide="sortFname" class="ascending">First Name</div>
    	     <div data-ng-click="predicate = '-firstName'; sortFname=false" data-ng-show="sortFname" class="descending">First Name</div> 
		 </th>
		 
		 <th><div data-ng-click="predicate = 'lastName'; sortLname=true" data-ng-hide="sortLname" class="ascending">Last Name</div>
    	     <div data-ng-click="predicate = '-lastName'; sortLname=false" data-ng-show="sortLname" class="descending">Last Name</div> 
		 </th>
	
		 <th><div data-ng-click="predicate = 'role.roleType'; sortRole=true" data-ng-hide="sortRole" class="ascending">Role</div>
    	     <div data-ng-click="predicate = '-role.roleType'; sortRole=false" data-ng-show="sortRole" class="descending">Role</div> 
		 </th>
		 <th><div data-ng-click="predicate = 'verfiedSystem'; sortVerfiedSystem=true" data-ng-hide="sortVerfiedSystem" class="ascending">System Verified </div>
    	     <div data-ng-click="predicate = '-verfiedSystem'; sortVerfiedSystem=false" data-ng-show="sortVerfiedSystem" class="descending">System Verified </div> 
    	 </th>
		 <th><div data-ng-click="predicate = 'verfiedSystem'; sortVerfiedManual=true" data-ng-hide="sortVerfiedManual" class="ascending">Manual Verified</div>
    	     <div data-ng-click="predicate = '-verfiedSystem'; sortVerfiedManual=false" data-ng-show="sortVerfiedManual" class="descending">Manual Verified </div>  
		</th>
		 <th> <div data-ng-click="predicate = 'active'; sortActive=true" data-ng-hide="sortActive" class="ascending">Active</div>
    	     <div data-ng-click="predicate = '-active'; sortActive=false" data-ng-show="sortActive" class="descending">Active </div>  
		</th>
		 
 </tr>
 </thead>
 <tbody>
 
 <tr data-ng-repeat="u in userList |  orderBy:predicate:reverse" data-ng-click="showUserDetail(u.id);" >
 
	<td> {{u.userName}}</td> 
	<td> {{u.firstName}}</td>
	<td> {{u.lastName}}</td>  
    <td> {{u.role.roleType}}</td> 
    <td> {{u.verfiedSystem}}</td> 
    <td> {{u.verfiedManual}}</td> 
    <td> {{u.active}}</td> 
 </tbody>
 
 
 </tbody>
 </table>
 
<span class="thumbnail" >
    <ul class="pagination" >
    
    <li><a href="#">&laquo;</a></li>
    
	 <li class="disabled">  <button class="btn" data-ng-disabled="disablePrevious" data-ng-click="getPrevious()"> &larr; Show Previous {{recordsPerPage}}  &nbsp;</button>
	 </li>
	  
	 <li class="active"> <button class="btn" data-ng-disabled="disableNext" data-ng-click="getNext()"> Show Next &rarr;  {{recordsPerPage}}  &nbsp; </button>
	 </li>
	 	
	 <li> <b>Total Records Found</b>&nbsp;&nbsp; {{totalRecords}}  &nbsp;&nbsp; &nbsp;&nbsp;
	 </li>
	 	
	 <li> <b>Page</b>&nbsp;&nbsp; {{currentPage}} of  &nbsp;{{totalPages}}  &nbsp;&nbsp;
	 </li>
 	
 	
 	<li><a href="#">&raquo;</a></li>
 	
 	
 	</ul>
 	
</span>
 
 
 
 
 
 
 
 
 
 
 </div><!-- responsive -->
 </div><!-- arron -->
 </div><!-- content-main -->
 </div><!-- content -->
 </div><!-- content-wrapper-->
 
 
 </div><!-- userList -->
 <!-- </sec:authorize> -->