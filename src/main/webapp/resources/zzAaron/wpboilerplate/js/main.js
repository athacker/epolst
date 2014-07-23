var mainmenuover = false;
var mainmenuopen = false;
var mainmenutimer = null;
var screenSize = '';

function scrollAnimateToView (that,speed) {
	//that = the jquery object or selector that we would like to scroll into view
	//speed = how fast to scroll it.
	var scrollTo = $(that).offset().top-5;
	var scrollMax = $(document).height()-$(window).height();
	if (scrollTo > scrollMax) {
		scrollTo = scrollMax-5;
	}
	console.log(scrollTo)
	$("html:not(:animated),body:not(:animated)").animate({"scrollTop":scrollTo+"px"},speed,'easeInOutCirc');
}

function setupScrollToAnchor(that) {
	//that = the selector that contains the links that you wish to scroll to
	$(that).click(function(event){
		event.preventDefault();console.log($(that));
		var jloc = $('#'+$(this).attr('href').split('#')[1]);
		scrollAnimateToView(jloc, 1500);
	});
}

function setupCollapseableArea (button,targetarea,speed,easing,scrolltoview,callback) { 
	/* button = (Selector or Jquery Object) that represent the button that will open and close the "targetarea"
	 * targetarea = (Selector or Jquery Object) of the area that will be opening and closing when the button is clicked.
	 * speed = (Number) The speed at which the targetarea will open and close.
	 * easing = (String) The easing function name that will be used during the animation
	 * scrolltoview = (Boolean) if True the area will be scrolled into view after it opens
	 * callback = Callback function that is called after the click event fires
	 */
	speed = speed || 500;
	easing = easing || 'easeInOutCirc';
	scrolltoview = scrolltoview || false;
	callback = callback || false;
	//setup the secondary content area button that appears when the screen is too small.
	$(button).click(function(event){
		event.preventDefault();
		event.stopPropagation();
		var $targetarea = $(targetarea);
		var ht = $targetarea.actualHeight(true);
		var pdtop = $targetarea.css('padding-top');
		var pdbottom = $targetarea.css('padding-bottom');
		if ($targetarea.css('display') == 'block') {
			// HIDE The targetarea, it is visible.
			$targetarea.animate({'height':'0',
				'padding-top':0,
				'padding-bottom':0,
				'opacity':0}, speed, easing, function(){
				$targetarea.removeAttr('style');
			});
		} else {
			// SHOW the targetarea, it is not visible.
			$targetarea.css({
				'height':0,
				'overflow':'none',
				'display':'block',
				'padding-top':'0',
				'padding-bottom':'0',
				'opacity':0
			});
			$targetarea.animate({'height':ht+'px',
				'padding-top':pdtop,
				'padding-bottom':pdbottom,
				'opacity':1}, speed, easing, function(){
				//callback
				if (scrolltoview) {
					$targetarea.css({'height':''});
					scrollAnimateToView($(targetarea), 200)
				}
			});
			if (callback) {
				callback();
			}
		}
	});
}

// ------------------------------ Accessibility Functions ------------------------------ //
function accessibleViewName() { return 'AccessibleView' }
function setupAccessibilityView() {
	//The user has specified they want accessibility view.
	//We set everything up here for it.
	//var htm = $('<link rel="stylesheet" href="'+themeurl+'/css/accessible.css" id="accessibility_css">');
	//$('head').append(htm);
	$('body').addClass('accessibilityview');
	writeSiteSetting(accessibleViewName(), true);
}
function removeAccessibilityView() {
	$('#accessibility_css').remove();
	$('body').removeClass('accessibilityview');
	writeSiteSetting(accessibleViewName(), false);
}
function fontSizeName() { return 'FontSizeAdjustment'}
function adjustFontSize (adjustment) {
	var size = readSiteSetting(fontSizeName());
	if (size) {
		size += adjustment;
	} else {
		size = 1.0 + adjustment;
	}
	$('body').css({'font-size':size+'em'});
	writeSiteSetting(fontSizeName(), size);
}
function resetFontSize () {
	$('body').css({'font-size':''});
	writeSiteSetting(fontSizeName(), 1.0);
}

// ------------------------------ Accessibility Adjustment Panel ------------------------------ //
function openSiteSettingsPanel() {
	var htm = $(
		'<div id="sitesettings-panel">\n\
			<h4>Contrast</h4>\n\
			<a href="#" id="highContrastView" class="button gray-v">High Contrast Toggle</a>\n\
			<h4>Font Size</h4>\n\
			<a href="#" id="smallerFontSize" class="button gray-v small first">-</a>\n\
			<a href="#" id="resetFontSize" class="button gray-v medium">Reset</a>\n\
			<a href="#" id="largerFontSize" class="button gray-v small">+</a>\n\
		</div>'
		);
	
	htm.find('#highContrastView').click(function(event){
		event.preventDefault();
		if (readSiteSetting(accessibleViewName())) {
			removeAccessibilityView();
		} else {
			setupAccessibilityView();
		}
	});
	htm.find('#smallerFontSize').click(function(event){
		event.preventDefault();
		if (readSiteSetting(fontSizeName()) > 1) {
			adjustFontSize(-0.1);
		}
	});
	htm.find('#resetFontSize').click(function(event){
		event.preventDefault();
		resetFontSize();
	});
	htm.find('#largerFontSize').click(function(event){
		event.preventDefault();
		adjustFontSize(0.1);
	});
	$('body').append(htm);
}
function setupSiteSettingsButton () {
	$('#settings-link').click(function(event){
		event.preventDefault();
		var settingspanel = $('#sitesettings-panel');
		if (settingspanel.length == 0) {
			openSiteSettingsPanel();
		} else {
			settingspanel.remove();
		}
	});

	if (readSiteSetting(accessibleViewName())) {
		setupAccessibilityView();
	}
	if (readSiteSetting(fontSizeName()) > 1) {
		adjustFontSize(0);
	}
}

// ------------------------------ Site Settings & Cookies ------------------------------ //
function getCookieName(){ return 'My_Site_Settings'} /* Change this cookie name to match your site */
function writeSiteSetting(key,val){
	//set a site setting value by key
	var c = readSiteCookie();
	var sitesettings = {};
	//console.log('key: '+ key + ' value: ' + val);
	//console.log('cookie: ' + c);
	if (c) {
		sitesettings = $.parseJSON(c);
	}
	sitesettings[key] = val;
	//console.log('sitesettings: '+ JSON.stringify(sitesettings));
	$.cookie.raw = true;
	$.cookie.json = true;
	$.cookie(getCookieName(), JSON.stringify(sitesettings), {expires:7000,path:'/'});
}
function readSiteSetting(key) {
	//read a site setting value by key
	var retval = null;
	var c = $.parseJSON(readSiteCookie());
	if (c) {
		retval = c[key];
	}
	return retval
}

function readSiteCookie (){
	var c = $.cookie(getCookieName());
	//console.log('read cookie: '+c);
	return c;
}

// ------------------------------ Automatic Document Icons ------------------------------ //
function setupDocumentIcons () {
	$('a').attr('href', function(){
		var link = $(this).attr('href')
		var that = $(this).children('span.title');
		if (!that || that.length <= 0) {
			that = $(this);
		}
		if (link){
			link = link.toLowerCase();
			if (link.indexOf('.pdf') >= 0){
				that.addClass('documentdl');
				that.addClass('pdf');
			} else if (link.indexOf('.xls') >= 0 || link.indexOf('.xlsx') >= 0){
				that.addClass('documentdl');
				that.addClass('excel');
			} else if (link.indexOf('.doc') >= 0 || link.indexOf('.docx') >= 0){
				that.addClass('documentdl');
				that.addClass('word');
			} else if (link.indexOf('.zip') >= 0) {
				that.addClass('documentdl');
				that.addClass('zip');
			} else if (link.indexOf('.mp4') >= 0 || link.indexOf('.mov') >= 0 || link.indexOf('http://youtu.be') >= 0 || link.indexOf('http://www.youtube.com') >= 0) {
				that.addClass('documentdl');
				that.addClass('video');
			}
		}
	});
}

// ------------------------------ Main Menu (Large Screen) Functions ------------------------------ //
function setupMainMenuLarge() {
	$('#mainmenu-wrapper, #mainmenu-wrapper ul, #mainmenu-wrapper ul li').unbind().removeAttr('style');
	$('#mainmenu-wrapper ul > li').hover(
		function(){
			startMenuTimer($(this))
		}, function(){
			closeMenu($(this));
		});
		
	$('#mainmenu-wrapper ul').hover(
		false, 
		function(){
			mainmenuopen = false;
		});
}
function startMenuTimer(that){
	if (mainmenuopen) {
		// menu is already open we don't need to have a timer delay
		openMenu(that);
	} else if (!mainmenutimer) {
		mainmenutimer = setTimeout(function(){
			openMenu(that);
		}, 200);
	}
}
function openMenu(that){
	//passing in the <li> of the menu item to be opened if it has children.
	$(that).addClass('menuover');
	var $ul = $(that).children('ul.sub-menu');
	if ($(that).parent('ul').hasClass('nav-menu')) {
		//this is the first level of the menu position it below the parent li
		var ht = $(that).actualHeight();
		$ul.css({'top':ht+'px'});
	} else {
		//this is a grandchild+ menu item position it to the left or right of the parent li
		var posx = $(that).position().top;
		$ul.css({'top':posx+'px'});
		if ($(that).offset().left + $(that).outerWidth() + $ul.outerWidth() > $(window).width()){
			$ul.css({'left':'-100%'});
		} else {
			$ul.css({'left':'100%'});
		}
	}
	mainmenuopen = true;
}
function closeMenu(that) {
	clearTimeout(mainmenutimer);
	mainmenutimer = null;
	$(that).removeClass('menuover');
}
// ------------------------------ Main Menu (Small / Mobile Screen) Functions ------------------------------ //
function setupMainMenuMobile(){
	$('#mainmenu-wrapper, #mainmenu-wrapper ul, #mainmenu-wrapper ul li').unbind().removeAttr('style');
	$('#mainmenu-wrapper ul li').each(function(){
		var childul = $(this).children('ul')
		if (childul.length > 0) {
			setupCollapseableArea($(this), childul, 500, 'easeInOutCirc', true, false)
		}
	});
}


// ------------------------------ Scrolling and Resize Window Functions ------------------------------ //
function posContent(){
	var wWidth;
	wWidth = Math.max( $(window).innerWidth(), window.innerWidth || 0);
	if (wWidth <= 600 && screenSize != 'mobile') {
		screenSize = 'mobile';
		setupMainMenuMobile();
	} else if (wWidth > 600 && wWidth <= 1200 && screenSize != 'small') {
		screenSize = 'small';
		setupMainMenuLarge();
	} else if (wWidth > 1200 && screenSize != 'large') {
		screenSize = 'large';
		setupMainMenuLarge();

	}
}

function scrollWindow(){
	var headerTop = parseInt($('#header-container').css('margin-top').split('px')[0]);
	$(window).scroll(function(){
		//place functions here for scrolling
		//stickyHeader(headerTop); //Comment this line out to turn off Sticky Header
	});
}

function resizeWindow(){
	$(window).resize(function(){
		//place functions here for window resizing
		posContent();
	});
	posContent();
}

// ------------------------------ Sticky Header Functions ------------------------------ //
function stickyHeader(ht) {
	//ht = "Header Top" The margin above the header
	if (screenSize != 'mobile' && !Modernizr.touch){
		//We dont use the sticky header on mobile devices or touch devices (ipad)
		var replaceHeight = $('#header-container').outerHeight(true);
		var scrollPosx = $(window).scrollTop();
		if (scrollPosx > ht) {
			//Stick the header to the top of the screen.
			if ($('#placeholder-bar').length == 0){
				var placeholderBar = $('<div id="placeholder-bar"></div>');
				placeholderBar.css({'height':replaceHeight+'px','width':'100%'})
				$('#header-container').before(placeholderBar);
			}
			$('#header-container').addClass('sticky');
		} else {
			$('#placeholder-bar').remove();
			$('#header-container').removeClass('sticky');
		}
	} else {
		if ($('#placeholder-bar').length > 0) {
			$('#placeholder-bar').remove();
		}
		if ($('#header-container').hasClass('sticky')) {
			$('#header-container').removeClass('sticky');
		}
	}
}


/* ************************************************************************ *
 *               Main Functions (On jQuery Load / On Load)
 * ************************************************************************ */

$(function(){
	//event fires after the DOM is loaded and jQuery is fully loaded.
	resizeWindow();
	scrollWindow();
	setupCollapseableArea('#secondary-menu-button', '#content-secondary', 500, 'easeInOutCirc', true);
	setupCollapseableArea('#mobile-mainmenu-button', '#mainmenu-wrapper', 500, 'easeInOutCirc', true);
	setupCollapseableArea('#mobile-searchbutton', '#searchform_main', 500, 'easeInOutCirc', true, 
		function(){
			$('#cse-search-box input[type="text"]').focus();
		});
	//setupSiteSettingsButton();
});


$(window).load(function () {
	//event fires after images have loaded or DOM is fully rendered in the browser.
});