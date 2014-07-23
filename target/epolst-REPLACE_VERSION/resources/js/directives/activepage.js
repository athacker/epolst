'use strict';

polstApp.directive('activePage', ['$location', function(location ){
	 return {
	        restrict: 'A',
	        link: function(scope, element, attrs, controller) {
	        	var clazz = attrs.activePage;
	            var path = attrs.href;
	       
	            scope.location = location;
	            scope.$watch('location.path()', function(newPath) {
	                var base = path.substring(1,4);  
		           
	              	if (path === newPath |  newPath.indexOf(base)===0) {
	                    element.addClass(clazz );
	                } else {
	                    element.removeClass(clazz );
	                }
	            });
	        }

	    };
	
}]);

 
 