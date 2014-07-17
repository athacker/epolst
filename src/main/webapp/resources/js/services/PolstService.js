'use strict';

var PolstService = function($http, $log, $q) {
	var service = {};
 
	service.quickSearch = function(gender, searchString) {
		console.log("Sending over search request now: " + gender + searchString);
		return new RequestHandler().getHttpPromise('quickSearch/'+ gender + "/" + searchString + '.json', $http, $q, $log);
	};
	service.searchPatientsForPhysician = function(physician) {
		return new RequestHandler().getHttpPromise('searchPatient/' + physician + '.json', $http, $q, $log);
	};

	
	
	
	
	
	
	return service;
};

polstApp.factory("polstService", PolstService);
