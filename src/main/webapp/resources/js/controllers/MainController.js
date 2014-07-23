'use strict';



polstApp.controller("mainController", function($scope, polstService,  $anchorScroll, $location, $routeParams,  $timeout){
	
	$scope.currentUser;
	$scope.role;
	$scope.userPhone;
	$scope.license;
	 
	var responseHandler = new ResponseHandler($scope);

	$scope.messages = [];
    $scope.messageStyle="alert-success";
	
	$scope.addMessage = function(message) {
		 
		if (typeof message.style != 'undefined'){
		   $scope.messageStyle = message.style;
		 }
			
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
	
	$scope.scrollTo = function(id) {
		$location.hash(id);
	   	$anchorScroll();
	 };
	
	 
	 $scope.setUserStartPage=function(){
		 if ('ROLE_MEDCERT' === $scope.role){
			 $location.url('/polstList');
		 }else if( 'ROLE_MEDSTAFF' === $scope.role){
			 $location.url('/patientList');
		 }else if ('ROLE_ADMIN' === $scope.role){
			 $location.url('/userList');
		 }else if ('ROLE_EMS' === $scope.role){
			 $location.url('/EMTSList');
		 }else if ('ROLE_NOT_VERIFIED' === $scope.role){
			 $location.url('/register');
		 }
		 
	 };
	 
	

 







	
	
	
















});