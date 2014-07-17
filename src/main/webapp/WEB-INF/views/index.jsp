<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html ng-app="polstApp" ng-controller="polstController" >
<head>
	<link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="resources/css/main.css">
	<title>ePolst</title>
</head>
<body>

<div ng-init="currentUser='${currentUser}';">
<div ng-init="role='${role}';">

 


<sec:authorize access="hasRole('ROLE_PHYSICIAN')">
<div physician-nav> </div>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_EMT')">
<div emt-nav> </div>
</sec:authorize>


<ng-view></ng-view>
 


<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.8/angular.min.js"></script>
<script src="http://code.angularjs.org/1.2.8/angular-route.min.js"></script>
<script type="text/javascript" src="resources/js/lib/underscore.js"></script>
<script type="text/javascript" src="resources/js/lib/jquery-class.js"></script>
<script type="text/javascript" src="resources/js/lib/RequestHandler.js"></script>
<script type="text/javascript" src="resources/js/lib/ResponseHandler.js"></script>

 
<script type="text/javascript" src="resources/js/app.js"></script>
<script type="text/javascript" src="resources/js/controllers/PolstController.js"></script>
<script type="text/javascript" src="resources/js/services/PolstService.js"></script>
 
<script type="text/javascript" src="resources/js/directives/physiciannav.js"></script>
<script type="text/javascript" src="resources/js/directives/emtnav.js"></script>




</body>
</html>