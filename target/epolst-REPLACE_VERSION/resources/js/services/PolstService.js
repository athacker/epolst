'use strict';

var PolstService = function($http, $log, $q) {
	var service = {};
	
 	service.searchPolstListForMed = function(searchBean) {
		return new RequestHandler().postHttpPromise('med/searchPolst/', searchBean, $http, $q, $log);
	};
	
	service.getPolstById = function(polstId){
		return new RequestHandler().getHttpPromise('getPolstById/' + polstId + '.json', $http, $q, $log);
	};
	service.getPatientByPolstId = function(polstId){//polst grid
		return new RequestHandler().getHttpPromise('getPatientByPolstId/' + polstId + '.json', $http, $q, $log);
	};
	service.getPolstByIdByPatient = function(polstId, patientId){//polst detail
		return new RequestHandler().getHttpPromise('getPolstByIdByPatient/' + polstId + '/' + patientId +'.json', $http, $q, $log);
	};
	service.createNewEpolst = function(  patientId){//polst detail
		return new RequestHandler().getHttpPromise('createNewEpolst/' + patientId +'.json', $http, $q, $log);
	};
 	service.getPatient = function(patientId){//pt grid
		return new RequestHandler().getHttpPromise('patient/getPatient/' + patientId + '.json', $http, $q, $log);
	};
	service.getCityState=function(isCurrent, zip){
		return new RequestHandler().getHttpPromise('getCityState/' + isCurrent +"/"+ zip + '.json', $http, $q, $log);
	};
	service.getPhysicians = function(){
		return new RequestHandler().getHttpPromise('getPhysicians.json', $http, $q, $log);
	};
	service.savePolst = function(polst) {
		return new RequestHandler().postHttpPromise('saveEPolst.json', polst, $http, $q, $log);
	};
	service.cancelPolst = function(polst) {
		return new RequestHandler().postHttpPromise('cancelEPolst.json', polst, $http, $q, $log);
	};
 	service.preparePolst = function(polst) {
		return new RequestHandler().postHttpPromise('prepareEPolst.json', polst, $http, $q, $log);
	};
	service.getEmailHistory=function(polstId){
		return new RequestHandler().postHttpPromise('getEmailHistory.json', polstId, $http, $q, $log);
	};
	service.sendReminderEmail=function(polstId){
		 return new RequestHandler().getHttpPromise('sendReminderEmail/'+ polstId + '.json', $http, $q, $log);
	};
	service.certifyPolst = function(polst) {
		return new RequestHandler().postHttpPromise('certifyEPolst.json', polst, $http, $q, $log);
	};
	service.savePolstDiscussion = function(discusson) {
		return new RequestHandler().postHttpPromise('savePolstDiscussion.json', discusson, $http, $q, $log);
	};
	service.deletePolstDiscussion = function(discusson) {//individual discussion records
		return new RequestHandler().postHttpPromise('deletePolstDiscussion.json', discusson, $http, $q, $log);
	};
	service.deleteDiscussionsForType = function(discusson) {//all discussions for a type(PARENT, SURROGATE,OTHER)
		return new RequestHandler().postHttpPromise('deleteDiscussionsForType.json', discusson, $http, $q, $log);
	};
	
	service.savePolstAuthorization = function(authorization) {
		return new RequestHandler().postHttpPromise('savePolstAuthorization.json', authorization, $http, $q, $log);
	};	
	service.deletePolstAuthorization = function(authorization) {//individual authorization records
		return new RequestHandler().postHttpPromise('deletePolstAuthorization.json', authorization, $http, $q, $log);
	};
	service.deleteAuthorizationsForType = function(authorization) {//all authorization for a type(PARENT, SURROGATE)
		return new RequestHandler().postHttpPromise('deleteAuthorizationsForType.json', authorization, $http, $q, $log);
	};
	service.savePatient = function(patient) {
		return new RequestHandler().postHttpPromise('patient/savePatient.json', patient, $http, $q, $log);
	};
 
	service.searchPatientsForRole=function(searchBean){//new
		return new RequestHandler().postHttpPromise('patient/searchPatientsForRole.json',searchBean, $http, $q, $log);
	};	
	
	service.searchAllPatients=function(searchBean){//new
		return new RequestHandler().postHttpPromise('patient/searchAllPatients.json',searchBean, $http, $q, $log);
	};	
	service.nameCheck=function(searchBean){//new
		return new RequestHandler().postHttpPromise('patient/nameCheck.json',searchBean, $http, $q, $log);
	};	

 	service.register=function(license){
		return new RequestHandler().postHttpPromise('register.json', license, $http, $q, $log);
	};
 	service.registerEmt=function(badgeNumber){
		return new RequestHandler().postHttpPromise('registerEmt.json', badgeNumber, $http, $q, $log);
	};
	service.getUsers=function(){
		return new RequestHandler().getHttpPromise('admin/getUsers.json', $http, $q, $log);
	};
	service.searchUsers=function(searchBean){
		return new RequestHandler().postHttpPromise('admin/searchUsers.json', searchBean, $http, $q, $log);
	};
	service.getRoles=function(){
		return new RequestHandler().getHttpPromise('admin/getRoles.json', $http, $q, $log);
	};
	
	service.saveUser = function(user) {
		return new RequestHandler().postHttpPromise('admin/saveUsers.json',user, $http, $q, $log);
	};

	service.getUser = function(userId) {
		 return new RequestHandler().getHttpPromise('admin/getUser/' + userId + '.json', $http, $q, $log);
 	};

	service.quickSearch = function(searchBean) {
	 	return new RequestHandler().postHttpPromise('ems/quickSearch/', searchBean, $http, $q, $log);
	};
	service.getActivePolstByPatientId=function(patientId){
		return new RequestHandler().getHttpPromise('ems/getActivePolstByPatientId/' + patientId +'.json', $http, $q, $log);
	};
	service.verifyLicense=function(role, license){
		return new RequestHandler().getHttpPromise('admin/verifyLicense/' +role+"/"+license +'.json', $http, $q, $log);
	};
	
	return service;
};

polstApp.factory("polstService", PolstService);
