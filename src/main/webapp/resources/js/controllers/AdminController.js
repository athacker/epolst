'use strict';



polstApp.controller("adminController", function($scope,  polstService, $location, $routeParams, $timeout){
	
	var responseHandler = new ResponseHandler($scope);
	
	$scope.userList=[];
	$scope.roles=[]
	
	$scope.quickSearchName;
	$scope.stillTyping = true;
	
	$scope.disablePrevious = true
	$scope.disableNext = true;
	$scope.currentPage=0;
	$scope.totalRecords=0;
	$scope.recordsPerPage=50;
	$scope.totalPages=0;
	$scope.searchBean={ 'currentPage':$scope.currentPage, 'recordsPerPage': $scope.recordsPerPage, 'quickSearchDob':'', 'quickSearchName':'' 	};
		
	
	$scope.$watch('quickSearchName' , function(){
	  if (undefined !== $scope.quickSearchName){
			if (2 < $scope.quickSearchName.length){
				//reset currentPage
				$scope.searchBean['currentPage'] = 1;
				$scope.currentPage=0;
				$scope.startSearch();
			}else if (0 === $scope.quickSearchName.length){
				$scope.userList = [];
				
			}	
		}
	});


	$scope.stopTyping=function(){
		console.log("Timeout has been hit.. check flag");
		$scope.stillTyping=false;
	};
    	
	$scope.startSearch=function(){
	 	console.log("Is user still typing? " + $scope.stillTyping);
	    $timeout( $scope.stopTyping, 500);
		console.log("Is user still typing after timeout? " + $scope.stillTyping); 
		if (!$scope.stillTyping){
		  
	    	$scope.searchBean['quickSearchName'] = $scope.quickSearchName;	
	    	$scope.sendSearchRequest();
			$scope.stillTyping=true;
		}
	 };
	 
	 
	 $scope.changeView=function(){
		 $scope.searchBean['currentPage'] = 1;
		 $scope.currentPage=0;
		 $scope.sendSearchRequest(); 
	 };
	 
 
	 $scope.sendSearchRequest = function(){
		console.log("Send Search Request.");
    	polstService.searchUsers( $scope.searchBean ).then(function(response) {
    		responseHandler.processCommands(response.commands);
    	});
	  };
	 
	 

	  $scope.onSearchComplete=function(searchBean){
		  $scope.setRecordNavButtons(searchBean);
	  };

	
    
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





	

	//USER DETAIL PAGE
	$scope.initUserDetail=function(){
		
		polstService.getRoles().then(function(response) {
			responseHandler.processCommands(response.commands);
		});
		var userId = $routeParams.userId;
	     
		if (typeof userId != "undefined"){
			polstService.getUser(userId).then(function(response) {
				responseHandler.processCommands(response.commands);
			});
		}	
	 
	};
	
 	$scope.showUserDetail= function(userId){
	   $location.url('/userDetail/' + userId);	
	};


	$scope.user = {"stateLicensed":"Utah"};
	$scope.saveUser=function(){
		polstService.saveUser($scope.user).then(function(response) {
			responseHandler.processCommands(response.commands);
		});
	};
	
	$scope.onUserSaved = function(){
		console.log("User was successfully saved.");
		
		polstService.getRoles().then(function(response) {
			responseHandler.processCommands(response.commands);
		});
	};
	$scope.onUserSaveError = function(){
		console.log("Issue was encountered while saving this user.");
	};
		
	$scope.clearPolstUserForm = function(isDirty){
		var cancelAndContinue = true;
		 
		if (isDirty){
			cancelAndContinue = confirm("Warning...you have un-saved changes. Confirm OK if you wish to continue and lose edits, or Cancel and then submit your edits."); 
		}
		if (cancelAndContinue){
			$scope.user = {"stateLicensed":"Utah"};
		}
 	};
 
      $scope.licenseLookup = function(){
		   console.log("Verify License Number:  " + $scope.user.license +" Role: " + $scope.user.roleId);
		   
		   polstService.verifyLicense($scope.user.roleId, $scope.user.license).then(function(response) {
			responseHandler.processCommands(response.commands);
		});
	   };












	
  //REGISTER NEW USERS
 	$scope.registerEmt = false;
	$scope.registerPhysician=false;
 	$scope.registeredUser={};
 	$scope.badgeNumber='';
	$scope.license='';
	$scope.isRegistered=false;
	$scope.registerTitle="Register to access ePOLST"
	 
	
	 $scope.registerUser = function(){
		polstService.register($scope.license).then(function(response) {
    		responseHandler.processCommands(response.commands);
    	});
	  };	  
	  
	 $scope.registerEms = function(){
		polstService.registerEmt($scope.badgeNumber).then(function(response) {
    		responseHandler.processCommands(response.commands);
    	});
	  };		 

	  $scope.onUserRegistered = function(registeredUser){
		  $scope.registerTitle = "User Id: " + $scope.currentUser ;
		  $scope.isRegistered=true;
		  $scope.registeredUser =registeredUser;
	  };
	  $scope.onUserFailureRegister = function(failedUser){
		  $scope.registerTitle = "User Id: " + $scope.currentUser + " could not be verified in the ePOLST system.  Please contact System Administrator.";
		  $scope.isRegistered=false;
		  $scope.registeredUser =failedUser;
		  
	  };
	

	  $scope.toggleView = function(who){
		  $scope.registerTitle="Register to access ePOLST";
		  if(who ==='emt'){
			  $scope.registerEmt = true;
			  $scope.registerPhysician=false;
		  }else{
			  $scope.registerEmt = false;
			  $scope.registerPhysician=true;
		  }
	   };






});