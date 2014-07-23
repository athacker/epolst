'use strict';

 
polstApp.directive('dateRange',function( ){
	 return{
			restrict: 'A',
			require: 'ngModel',
			link: function(scope, element, attrs, ctrl) {
	        
			angular.element(element).bind('blur', function() {
			 
				var allowFutureDates =  attrs.future;
				var allowPastDates =  attrs.past;
				var isRequired = attrs.required;
				
				var today = new Date().getTime(); 
	            var dateEntered = this.value.split("-");
	            var year = dateEntered[2] ;
	            var day = dateEntered[1]   ;
	            var month = dateEntered[0]-1 ;
	            dateEntered = new Date( year, month, day  ) ;
	            var compareDate = dateEntered.getTime();
	            
	            var isFuture = (today - compareDate ) < 0 ? true : false;
	            var isPast = ( compareDate - today ) < 0 ? true : false;
	            
	            
	        
	            if ( !isNaN(month) && ( month !== dateEntered.getMonth() | parseInt(day) !== dateEntered.getDate() )  ){
	            	 ctrl.$setValidity('dateRange', false);
	               	 ctrl.$setViewValue(); 
	             }
	             else if ( allowPastDates==='false' && isPast ){
	            	 ctrl.$setValidity('dateRange', false);
	            	 ctrl.$setViewValue(); 
	             }else if (allowFutureDates==='false' && isFuture ){
	            	 ctrl.$setValidity('dateRange', false);
	            	 ctrl.$setViewValue(); 
	             }else{
	                 ctrl.$setValidity('dateRange', true);
	             }
	              
	            
	     	});     
		 	// set "valid" by default on typing
	        angular.element(element).bind('keyup', function () {
	             ctrl.$setValidity('dateRange', true);
	        });


	     }             
		 };
		
	});

	 
	 