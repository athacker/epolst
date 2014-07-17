'use strict';


var polstApp = angular.module("polstApp",    [ 'ngRoute' ]);
 
polstApp.config(['$routeProvider',
    function($routeProvider) {
	    $routeProvider.when('/patient', {
	        templateUrl: 'resources/js/partials/patient.html' 
	     }).when('/polstDetail/:patientId', {
	        templateUrl: 'resources/js/partials/polstDetail.html' 
	    }).when('/queue', {
	        templateUrl: 'resources/js/partials/queue.html' 
	    }).when('/search', {
	          templateUrl: 'resources/js/partials/search.html' 
	    }).when('/reports', {
	          templateUrl: 'resources/js/partials/reports.html' 
	    }) .otherwise({
	        redirectTo: '/polst'
	    });
 }]);
 
 