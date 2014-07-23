<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML >
<html id="ng-app" data-ng-app="polstApp" data-ng-controller="mainController" data-ng-cloak >
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<link rel='stylesheet' id='normalize-css'  href="resources/js/partials/wpboilerplate/css/normalize.css" type='text/css' media='all' />
 	<link rel='stylesheet' id='wpboilerplate-style-css'  href="resources/js/partials/wpboilerplate/style.css" type='text/css' media='all' />
	<link rel='stylesheet' id='bootstrap-style-css'  href="resources/js/partials/wpboilerplate/bootstrap.css" type='text/css' media='all' />
	<link href='http://fonts.googleapis.com/css?family=Fjalla+One|Alegreya+Sans+SC|Alegreya+Sans' rel='stylesheet' type='text/css'>
	
	<title>ePolst</title>
    <!-- ie 8 -->
    <!--[if lte IE 8]>
	<script>
		document.createElement('ng-pluralize');
		document.createElement('ng-include');
		document.createElement('ng-view');
	</script>
	<![endif]-->

	<!--[if lt IE 9]>
	<script src="http://168.177.20.195/wp-content/themes/wpboilerplate/js/html5.js" type="text/javascript"></script>
	<![endif]-->
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
  	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  	<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->


</head>






<body data-spy="scroll" data-target="#content-secondary">

<div ng-init="currentUser='${currentUser}' "/> 
<div ng-init="role='${role}' "/><br> 
<div ng-init="userPhone='${userPhone}' "/><br> 
 


<sec:authorize access="hasRole('ROLE_NOT_VERIFIED')">
 <div nav-not-verified>  </div>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_MEDCERT' )">
<div nav-med>  </div>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_MEDSTAFF' )">
<div nav-med>  </div>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_EMS')">
<div nav-emt> </div>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">
<div nav-admin> </div>
</sec:authorize>

 


<div ng-view></div ng-view>
<div class="messageBackground">
 

<div data-ng-repeat="message in messages" data-ng-animate="'fade'"  class="messageCenter {{messageStyle}}" >
	<h3>{{ message.title }}</h3>
	<p>{{ message.text }}</p>
</div>
 



 </div>
 
<div footer></div>
 
 




 
 
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>

<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.11/angular.min.js"></script>
<script src="http://code.angularjs.org/1.2.11/angular-route.min.js"></script>
<script type="text/javascript" src="resources/js/lib/ui-utils-0.1.1/ui-utils.min.js"></script>
<script type="text/javascript" src="resources/js/lib/underscore.js"></script>
<script type="text/javascript" src="resources/js/lib/ui-bootstrap-tpls-0.10.0.min.js"></script>
<script type="text/javascript" src="resources/js/lib/jquery-class.js"></script>
<script type="text/javascript" src="resources/js/lib/RequestHandler.js"></script>
<script type="text/javascript" src="resources/js/lib/ResponseHandler.js"></script>

 
<script type="text/javascript" src="resources/js/app.js"></script>
<script type="text/javascript" src="resources/js/controllers/AdminController.js"></script>
<script type="text/javascript" src="resources/js/controllers/EPolstController.js"></script>
<script type="text/javascript" src="resources/js/controllers/MainController.js"></script>
<script type="text/javascript" src="resources/js/controllers/PatientController.js"></script>
<script type="text/javascript" src="resources/js/controllers/EmsController.js"></script>
 

<script type="text/javascript" src="resources/js/services/PolstService.js"></script>
 
<script type="text/javascript" src="resources/js/directives/navadmin.js"></script>
<script type="text/javascript" src="resources/js/directives/navemt.js"></script>
<script type="text/javascript" src="resources/js/directives/navmed.js"></script>
<script type="text/javascript" src="resources/js/directives/navnotverified.js"></script>
<script type="text/javascript" src="resources/js/directives/activepage.js"></script>
<script type="text/javascript" src="resources/js/directives/footer.js"></script>
<script type="text/javascript" src="resources/js/directives/dateRange.js"></script>
<script type="text/javascript" src="resources/js/directives/onBlur.js"></script>
<script type="text/javascript" src="resources/js/directives/datepicker.js"></script>
<script type="text/javascript" src="resources/js/directives/capitalizefirst.js"></script>
<script type="text/javascript" src="resources/js/directives/number.js"></script>
 


<script type='text/javascript' src="resources/js/partials/wpboilerplate/js/vendor/modernizr-2.6.2.min.js"></script>
<script type='text/javascript' src="resources/js/partials/wpboilerplate/js/vendor/jquery.easing.min.js"></script>
<script type='text/javascript' src="resources/js/partials/wpboilerplate/js/plugins.js"></script>
<script type='text/javascript' src="resources/js/partials/wpboilerplate/js/vendor/jquery.cookie.js"></script>
<script type='text/javascript' src="resources/bootstrap/js/bootstrap.js"></script>
 
 

</body>

<script>
var $j = jQuery.noConflict();

$j( document ).ready(function() {
  window.XMLHttpRequest = window.XMLHttpRequest || (function() {
        return new window.ActiveXObject("Microsoft.XMLHTTP");
    });
   
});
 
</script>
 
<div ng-init="release='<%@include file="version.jsp" %>' "/><br>
</html>