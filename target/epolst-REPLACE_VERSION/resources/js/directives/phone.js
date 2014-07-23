'use strict';

var PHONE_REGEXP = /^[(]{0,1}[0-9]{3}[)\.\- ]{0,1}[0-9]{3}[\.\- ]{0,1}[0-9]{4}$/;

polstApp.directive('phone',function( ){
	 return{
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, element, attrs, ctrl) {
        
		angular.element(element).bind('blur', function() {
             var value = this.value;
             if(PHONE_REGEXP.test(value)) {
                 // Valid input
                 console.log("valid phone number");
                 angular.element(this).next().css('display','none');
             } else {
                 // Invalid input  
                 console.log("invalid phone number");
                 angular.element(this).focus();
                 angular.element(this).next().css('display','block');
                                    
             }
         });              
     }             
	 };
	
});

 
 