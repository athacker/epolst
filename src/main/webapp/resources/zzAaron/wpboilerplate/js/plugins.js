// Avoid `console` errors in browsers that lack a console.
(function() {
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }
}());

// Place any jQuery/helper plugins in here.

jQuery(function( $ ){
  $.fn.actualHeight = function(options){
        // find the closest visible parent and get it's hidden children
	var visibleParent = this.closest(':visible').children(),
        thisHeight;
    
    // set a temporary class on the hidden parent of the element
    visibleParent.addClass('temp-show');
	
    if (options) {
		// get the height with margin
		thisHeight = this.outerHeight(true);
	} else {
		// get the height
		thisHeight = this.height();
	}
	
    // remove the temporary class
    visibleParent.removeClass('temp-show');
    
    return thisHeight;
  };

});