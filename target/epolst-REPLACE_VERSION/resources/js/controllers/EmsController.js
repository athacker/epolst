'use strict';



polstApp.controller("emsController", function($scope, polstService,   $location, $routeParams,  $timeout){
  
	var responseHandler = new ResponseHandler($scope);
	
	$scope.searchPatients = [];
	
	$scope.quickSearchName;
	$scope.quickSearchDob;
	$scope.stillTyping = true;
	
	$scope.disablePrevious = true
	$scope.disableNext = true;
	$scope.currentPage=0;
	$scope.totalRecords=0;
	$scope.recordsPerPage=50;
	$scope.totalPages=0;
	$scope.searchBean={ 'currentPage':$scope.currentPage, 'recordsPerPage': $scope.recordsPerPage, 'quickSearchDob':'', 'quickSearchName':'' 	};
	
	//watches quickSearchName field for user input
	//after they stop typing then an ajax call is sent.
	$scope.$watch('searchBean.quickSearchName' , function(){
	  if (undefined !== $scope.searchBean.quickSearchName){
			if (2 < $scope.searchBean.quickSearchName.length){
				//reset currentPage
			    $scope.currentPage=0;
				$scope.startSearch();
			}else if (0 === $scope.searchBean.quickSearchName.length  && 0 === $scope.searchBean.quickSearchDob.length ){
				$scope.searchPatients = [];
			}else{
				$scope.startSearch();
			}	
		}
	});
	//if user enteres dob then we need to refresh the search
	$scope.$watch('searchBean.quickSearchDob',function(){
		if ( typeof $scope.searchBean.quickSearchDob != 'undefined' ){
		 	if (8 === $scope.searchBean.quickSearchDob.length){
				$scope.sendSearchRequest();
			}else if(0 === $scope.searchBean.quickSearchName.length  && 0 === $scope.searchBean.quickSearchDob.length){
				$scope.searchPatients = [];
			}else if(0 !== $scope.searchBean.quickSearchName.length  && 0 === $scope.searchBean.quickSearchDob.length){
				$scope.sendSearchRequest();
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
		 //	$scope.searchBean['quickSearchDob'] = $scope.quickSearchDob;
	    //	$scope.searchBean['quickSearchName'] = $scope.quickSearchName;	
	    	$scope.sendSearchRequest();
			$scope.stillTyping=true;
		}
	 };
	 
	 //calls service to send ajax call to EmsController.java
	 $scope.sendSearchRequest = function(){
		console.log("Send Search Request.");
		$scope.searchBean['currentPage'] = 1;
    	polstService.quickSearch( $scope.searchBean ).then(function(response) {
    		responseHandler.processCommands(response.commands);
    	});
	  };
	 
	 
	  //call back -- after search is complete
	  $scope.onSearchComplete=function(searchBean){
		  $scope.setRecordNavButtons(searchBean);
	  };

	
      //control navigation buttons
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


	  $scope.showPolst=function(patientId){
			$location.url('/EMTSDetail/ems/'+ patientId ); 
			
	  };


});