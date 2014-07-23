'use strict';
polstApp.controller("patientController", function($scope, polstService, $modal,  $location, $routeParams,  $timeout){
 var responseHandler = new ResponseHandler($scope);

//PATIENT LIST JAVASCRIPT
$scope.patientList=[];
$scope.quickSearchName;
$scope.stillTyping = true;
$scope.disablePrevious = true;
$scope.disableNext = true;
$scope.currentPage=0;
$scope.totalRecords=0;
$scope.recordsPerPage=50;
$scope.totalPages=0;
$scope.searchBean={ 'currentPage':$scope.currentPage, 'recordsPerPage': $scope.recordsPerPage, 'quickSearchDob':'', 'quickSearchName':'' 	};
 

 //SET UP TOOL TIP FOR CURRENT USER 
 $scope.setUpToolTip=function(){
	//SEARCH GRID TOOL TIPS
	//radio button help
	$scope.toolTipAllPt="View All Patients";
	$scope.toolTipMine = "View Patients who are assigned to me.";
 	 
 	if('ROLE_MEDCERT' === $scope.role && $scope.searchBean['showAll'] == false  ){
 		$scope.toolTipMine = "View Patients who are assigned to me.";
 	} else if('ROLE_MEDSTAFF' === $scope.role && $scope.searchBean['showAll'] == false   ){
 		$scope.toolTipMine= "View Patients who were added to ePOLST by me.";
 	}else if('ROLE_ADMIN' === $scope.role ){
 		$scope.toolTipMine= "View All Patients in ePOLST. ";
 	} 
 	 
 	 
 };
 
 //DEFAULT VIEW
 $scope.initPatientList=function(){
 	 $scope.setUpToolTip();
 	 $scope.searchBean.showAll=false;
 	 $scope.changeView();
 };

$scope.changeView=function(){
	$scope.searchBean['currentPage'] = 1;
	$scope.currentPage=0;
	$scope.setUpToolTip();
	$scope.sendSearchRequest();
 };
 

$scope.addNewPatient=function(){
	  $location.url('/addPatient' ); 
};

 
$scope.$watch('quickSearchName' , function(){
 	if (undefined !== $scope.quickSearchName){
		if (2 < $scope.quickSearchName.length){
	 		//reset currentPage
			$scope.searchBean['currentPage'] = 1;
			$scope.currentPage=0;
		    $scope.startSearch();
		}else if (0 === $scope.quickSearchName.length){
			$scope.patientList = [];
			$scope.searchBean['quickSearchName']='';
			$scope.changeView(); 
		}	
	}
});
	
$scope.stopTyping=function(){
	console.log("Timeout has been hit.. check flag");
	$scope.stillTyping=false;
	$scope.searchBean['quickSearchName'] = $scope.quickSearchName;	
	$scope.sendSearchRequest();
	$scope.stillTyping=true;
};
    	
$scope.startSearch=function(){
  $timeout( $scope.stopTyping, 600);
 };

 $scope.sendSearchRequest = function(){
 	$scope.disableAddNew = false;
 
	if ($scope.searchBean.showAll){
		polstService.searchAllPatients($scope.searchBean).then(function(response) {
    		responseHandler.processCommands(response.commands);
     	});
	}else{
		polstService.searchPatientsForRole($scope.searchBean).then(function(response) {
    		responseHandler.processCommands(response.commands);
     	});
	}
};
 

$scope.onSearchComplete=function(searchBean){
	$scope.setRecordNavButtons(searchBean);
};

$scope.onSearchError=function(searchBean){
	console.log("An exception was encountered when requesting patient data.");
};


//NAVIGATION BELOW
$scope.setRecordNavButtons=function(searchBean){
    	
	if (1 < searchBean.currentPage){
		$scope.disablePrevious=false;
	}else{
		$scope.disablePrevious=true;
	}
	
	if ($scope.totalPages > $scope.currentPage ){
		$scope.disableNext=false;
	}else{
		$scope.disableNext=true;
	}
 };
 
    
$scope.getPrevious=function(){
   	$scope.searchBean['currentPage']=$scope.currentPage -=1;
   	$scope.sendSearchRequest();
};

$scope.getNext=function(){
	$scope.searchBean['currentPage']=$scope.currentPage +=1;
	$scope.sendSearchRequest();
};




//PATIENT DETAIL Javascript

$scope.showNewPolstButton=false;
$scope.disableAddNew=true;
$scope.mode="Save";
$scope.disableAdd=false;
$scope.physicians=[];
$scope.polstHistoryId;
$scope.patientsWithSameNameDob=[];	 
$scope.fullName;

//initialize patient form
$scope.initPatientDetail=function(){
	var patientId = $routeParams.patientId;
	console.log("Initialize Patient Detail patientId:  " + patientId); 
	$scope.resetEmptyPatient();
	if (typeof patientId != "undefined" && $scope.role != 'ROLE_EMS'){
		$scope.mode="Save Edits";
		$scope.showNewPolstButton=true;
		polstService.getPatient(patientId).then(function(response) {
			responseHandler.processCommands(response.commands);
		});
	}
	//physician field has different behavior depending on who is logged in.
	if ('ROLE_MEDCERT' === $scope.role){ 
		$scope.patient['physicianUserName']=$scope.currentUser ;
		$scope.patient['physicianPhoneNumber']=$scope.userPhone ;
 	} 
	//initialize physician list for nurse drop down and for look ups for med_cert
	polstService.getPhysicians().then(function(response) {
		responseHandler.processCommands(response.commands);
	});
	 

};




$scope.$watch('patient.lastName', function(){
	if (typeof $scope.patient != 'undefined'){
	 	$scope.fullName = $scope.formatFullName ($scope.patient.firstName , $scope.patient.lastName);
	}	
});

//formats patient name on the top of the form
$scope.formatFullName = function(firstName, lastName){
	return firstName + " " + lastName;
}; 

//after user changes the last name we need to look for duplicates
$scope.checkDuplicates = function(){
    //only launch the modal when we are entering new patients
	if ( typeof $scope.patient.patientId === 'undefined'){
		$scope.searchBean['currentPage'] = 1;
		$scope.searchBean['quickSearchName'] =  $scope.patient.lastName + " " + $scope.patient.firstName;
		$scope.searchBean['quickSearchDob'] = $scope.patient.dob;
		polstService.nameCheck($scope.searchBean).then(function(response) {
			responseHandler.processCommands(response.commands);
		});
	}	
};

//call back for duplicate checks.this is where the modal is launched
$scope.onNameCheck=function( duplicateData ){
 
  //if we have duplicates then we need to launch a modal	
  if (duplicateData.length>0){
	    	 var modalInstance = $modal.open({
		      templateUrl: 'resources/js/partials/duplicateModal.html',
		      controller: ModalInstanceCtrl,
		      windowClass:  'polst-modal-window',
		      resolve: {
		        items: function () {
		          return duplicateData;
		        }
		      }
		    });
	  	 
          //the modal returns a promise when closed
	  	  modalInstance.result.then(function (selectedItem) {
	  		    $scope.patientForm.$setPristine();
	  		  	polstService.getPatient(selectedItem.id).then(function(response) {
	  		 		responseHandler.processCommands(response.commands);
	  		 	});
	  		  	  //console.log("close modal")
	  		  }, function () { 
	  			 //console.log("dismiss modal")
	  			});
  	 
  }
};

//JAVASCRIPT that is required on the MODAL->Search Pop-Up Grid
var ModalInstanceCtrl = function ($scope, $modalInstance, items) {
	 $scope.items = items;
	 $scope.ok = function ( item) {
		 $modalInstance.close(item);
	 };

	 $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	 };
 };
 
//MODAL->when user clicks a row from grid 
$scope.showPatientDetail= function(patientId){
   $location.url('/patientDetail/' + patientId);	
};
	


//web services call to get city/state for an entered zip code
$scope.getCityState=function(isCurrent){ 
	 if (isCurrent && 5===$scope.patient.zipCurrent.length){
 		polstService.getCityState(isCurrent, $scope.patient.zipCurrent).then(function(response) {
			responseHandler.processCommands(response.commands);
		});
 	 }else if (!isCurrent && 5===$scope.patient.zipPerm.length){
 		polstService.getCityState(isCurrent, $scope.patient.zipPerm).then(function(response) {
			responseHandler.processCommands(response.commands);
		});
 	 }
};
//call back from webservices call to populate fields.
$scope.setCityState=function(address){
	if (address.current){
		$scope.patient.cityCurrent=address.city;
		$scope.patient.stateCurrent=address.state;
		if (!address.validZip){
			$scope.patient.zipCurrent='';
			$scope.badZipCurrent=address.zipCode + " is not a valid zip code.";
		}	
	}else {
		$scope.patient.cityPerm=address.city;
		$scope.patient.statePerm=address.state;
		if (!address.validZip){
			$scope.patient.zipPerm='';
			$scope.badZipPermanent=address.zipCode + " is not a valid zip code.";
		}	
	}	
};


//copy address from current to permanent
$scope.permAddressSame = false;
$scope.togglePermAddress = function(){
	if (!$scope.permAddressSame){
		$scope.permAddressSame=true;
	}else{
		$scope.permAddressSame=false;
	}
	
	if($scope.permAddressSame){
		$scope.patient.addressPerm = $scope.patient.addressCurrent;
		$scope.patient.cityPerm = $scope.patient.cityCurrent;
		$scope.patient.statePerm = $scope.patient.stateCurrent;
		$scope.patient.zipPerm = $scope.patient.zipCurrent;
	 
	}else{
		$scope.patient.addressPerm="";
		$scope.patient.cityPerm = "";
		$scope.patient.statePerm ="";
		$scope.patient.zipPerm ="";
	}

};


//copy certified physician to primary care physician
$scope.physicianSame=false;
$scope.togglePhysicians=function(){
	if (!$scope.physicianSame){
		$scope.physicianSame=true;
	}else{
		$scope.physicianSame=false;
	}
	 
	if ($scope.physicianSame){
		var physicianRecord = $scope.getPhysicianData($scope.patient.physicianUserName);
		$scope.patient.primaryCareProvider = physicianRecord.displayName;
	 	$scope.patient.primaryCarePhysicianPhoneNumber = $scope.patient.physicianPhoneNumber;
	}else{
		$scope.patient.primaryCareProvider = "";
		$scope.patient.primaryCarePhysicianPhoneNumber ="";
	}
};


//pre-set physician phone number	
$scope.setPhone = function(){
	var physicianRecord = $scope.getPhysicianData($scope.patient.physicianUserName);
	$scope.patient.physicianPhoneNumber = physicianRecord.phoneNumber;
  };

$scope.getPhysicianData = function(physicianUserName){
	for (var i=0; i<$scope.physicians.length; i++ ){
		if (physicianUserName === $scope.physicians[i].userName){
			return  $scope.physicians[i];
		}
	}
	
};


//save patient	
$scope.savePatient = function(){
	polstService.savePatient($scope.patient).then(function(response) {
		responseHandler.processCommands(response.commands);
	});
};

//javascript logic to enable/disable form when a patient bean is loaded.
$scope.onPatientLoaded = function(patientBean){

	//if patient has died, don't allow a new ePOLST to be created.
	if ( typeof $scope.patient.dateOfDeath === 'undefined' ){
		$scope.showNewPolstButton = true;
	}else if(null ===  $scope.patient.dateOfDeath){
		$scope.showNewPolstButton = true;
	}else if("" ===  $scope.patient.dateOfDeath){
		$scope.showNewPolstButton = true;
	}else{
		$scope.showNewPolstButton = false;
	}
	
	$scope.patient.patientId=patientBean.patientId ;
	$scope.patientForm.$setPristine();
	
};

$scope.onPatientSaveError = function(){
	console.log("onPatientSaveError");
};
//user clicks Start New Form button
$scope.createNewEpolst = function(isDirty){
	var cancelAndContinue = true;
	 
	if (isDirty){
		cancelAndContinue = confirm("Warning...you have un-saved changes. Confirm OK if you wish to continue and lose edits, or Cancel and then submit your edits."); 
	}
	
	if (false != cancelAndContinue && null !== $scope.patient.polstHistory ){
		 for (var i=0; i<$scope.patient.polstHistory.length; i++ ){
			if ( 'IN_PROCESS' === $scope.patient.polstHistory[i].polstStatus  || 'PENDING_CERTIFICATION' === $scope.patient.polstHistory[i].polstStatus ){
				  cancelAndContinue=confirm("Warning...Patient currently has IN-PROCESS or PENDING ePOLST that will be IN-ACTIVATED.  Please confirm OK or cancel.");
				  break;
			}
		 } 
	 }
	
	if (cancelAndContinue){
		  $location.url('/polstDetail/new/' + $scope.patient.patientId); 
	 }
}

$scope.resetPatientForm=function(isDirty){
	var cancelAndContinue = true;
	if (isDirty){
		cancelAndContinue = confirm("Warning...you have un-saved changes. Confirm OK if you wish to continue and lose edits, or Cancel and then submit your edits."); 
	}
	if (cancelAndContinue){
		$scope.permAddressSame = false;
		if (typeof $scope.patient.patientId === 'undefined'){
			$scope.resetEmptyPatient();
		}else{
		 	polstService.getPatient($scope.patient.patientId).then(function(response) {
				responseHandler.processCommands(response.commands);
			});
		}	
	}
 };

$scope.resetEmptyPatient=function(){
 	$scope.patient = {'lastName':'', 'firstName':'' };
	$scope.permAddressSame = false;
	if ('ROLE_MEDCERT' === $scope.role){ 
		$scope.patient['physicianUserName']=$scope.currentUser ;
		$scope.patient['physicianPhoneNumber']=$scope.userPhone ;
 	} 
}; 
 
$scope.clearPatientForm=function(isDirty){
	var cancelAndContinue = true;
	if (isDirty){
		cancelAndContinue = confirm("Warning...you have un-saved changes. Confirm OK if you wish to continue and lose edits, or Cancel and then submit your edits."); 
	}
	
	if (cancelAndContinue){
		$scope.resetEmptyPatient();
	 }

};

	
	


});



