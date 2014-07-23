'use strict';



polstApp.controller("polstController", function($scope, polstService,  $location, $timeout){
	
	$scope.currentUser ="";
	$scope.role ="";
	
	var responseHandler = new ResponseHandler($scope);

	$scope.messages = [];
	$scope.addMessage = function(message) {
		$scope.messages.push(message);
		
		if(message.displaySeconds) {
			var displayLength = message.displaySeconds ? message.displaySeconds * 1000 : 5000; 
			$timeout(function() {
				$scope.removeMessage(message);
			}, displayLength);			
		}
	};
	
	$scope.removeMessage = function(message) {
		$scope.messages = _.without($scope.messages, message);
	};
	
	
	//QUICK SEARCH
	$scope.searchPatients = [];
	$scope.quickSearch;
	$scope.stillTyping = true;
	$scope.searchGender='MALE';
	
	$scope.$watch('quickSearch' , function(){
		if (undefined !== $scope.quickSearch ){
			if (4 < $scope.quickSearch.length){
				$scope.startSearch();
			}	
		}
	});
	
	$scope.resetGender=function(gender){
		$scope.searchGender=gender;
		if (undefined !== $scope.quickSearch ){
			if (4 < $scope.quickSearch.length){
				console.log($scope.searchGender);
				$scope.startSearch();
			}	
		}
    };
	
	
	$scope.startSearch=function(){
	 	console.log("Is user still typing? " + $scope.stillTyping);
	    $timeout( $scope.stopTyping, 500);
		console.log("Is user still typing after timeout? " + $scope.stillTyping); 
		if (!$scope.stillTyping){
			 $scope.sendSearchRequest();
			 $scope.stillTyping=true;
		}
	 };
 
	$scope.stopTyping=function(){
		console.log("Timeout has been hit.. check flag");
		$scope.stillTyping=false;
	};
    $scope.sendSearchRequest = function(){
    	polstService.quickSearch($scope.searchGender, $scope.quickSearch ).then(function(response) {
    		responseHandler.processCommands(response.commands);
    	});
    };
    
    $scope.clearSearch=function(){
    	$scope.searchPatients = [];
    	$scope.quickSearch="";
    	$scope.stillTyping = true;
    	$scope.searchGender='MALE';
    };
    
    
	
	//PHYSICIAN
	$scope.physician='Dr Joe';
	$scope.patientsForPhysician = [];
	$scope.patientsForPhysicianPromise = polstService.searchPatientsForPhysician( $scope.physician ).then(function(response) {
		responseHandler.processCommands(response.commands);
	});
	
	
	
	//POLST
	$scope.showPolstDetail = function(patientId){
	  $location.url('/polstDetail/' + patientId);
	};
	

});