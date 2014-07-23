'use strict';


var polstApp = angular.module("polstApp", ['ui.bootstrap', 'ngRoute', 'ui.utils']);
 
polstApp.config(['$routeProvider',
    function($routeProvider) {
	    $routeProvider.when('/patientList', {
	        templateUrl: 'resources/js/partials/patientList.jsp' 
	     }).when('/patientDetail/:patientId', {
	        templateUrl: 'resources/js/partials/patientDetail.jsp' 
	     }).when('/addPatient', {
	        templateUrl: 'resources/js/partials/patientDetail.jsp' 
	     }).when('/polstDetail/:polstId/:patientId', {
	        templateUrl: 'resources/js/partials/polstDetail.jsp' 
	    }).when('/polstList', {
	        templateUrl: 'resources/js/partials/polstListMed.jsp' 
	    }).when('/EMTSList', {
	        templateUrl: 'resources/js/partials/polstListEms.jsp' 
	    }).when('/EMTSDetail/:polstId/:patientId', {
	        templateUrl: 'resources/js/partials/polstDetail.jsp' 
	    }) .when('/userList', {
	        templateUrl: 'resources/js/partials/userList.jsp' 
	    }).when('/userDetail/:userId', {
	          templateUrl: 'resources/js/partials/userDetail.jsp' 
	    }).when('/addUser', {
	          templateUrl: 'resources/js/partials/userDetail.jsp' 
	    }).when('/register', {
	          templateUrl: 'resources/js/partials/register.jsp' 
	    }).when('/landingPage', {
	          templateUrl: 'resources/js/partials/landingPage.html' 
	    }).otherwise({
	        redirectTo: '/landingPage' 
	    });
 }]);
 
 