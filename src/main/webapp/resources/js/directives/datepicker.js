'use strict';

polstApp.directive('datePicker',function( ){
	 return{
			restrict: 'A',
			require: 'ngModel',
			
			link: function(scope, element, attrs, ngModelCtrl ) {
				
				var myDate = new Date();
				var startYear = scope.$eval(attrs.startYear);
				var dateValue = attrs.ngModel;
	        	
				if (typeof startYear != "undefined"){
					myDate = new Date(startYear,10,30);
				}
				
	        	$j(element).datepicker({
	        		  dateFormat: 'M-dd-yy',
	        		  changeMonth: true,
	        		  changeYear: true,
	        		  defaultDate: myDate,
	        	
	        	 onSelect: function(dateValue ) {
                    var ngModelName = this.attributes['data-ng-model'].nodeValue;
                    scope[ngModelName] = dateValue;
                    scope.$apply(function () {
                        ngModelCtrl.$setViewValue(dateValue );
                    });
                 }
	        	
	        	
	        	
	        	
	        	});
	        	
	        	
			}

	 
	 
	 
	 
	 };



});

 
 