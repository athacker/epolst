package gov.utah.epolst.controller;

 

import gov.utah.epolst.service.SecurityService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User hits this code when they log out of system.
 */
@Controller
public class LogoutController  {


	@Autowired
	private SecurityService securityService;
	
	/**
	 * Logs user out of UMD and clears session variables.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
 
		securityService.logout(request);
	    return "redirect:https://login2.utah.gov/user/logoff";
		 
 	}
}
