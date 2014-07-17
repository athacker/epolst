package gov.utah.polst.controller;

 

import gov.utah.polst.service.SecurityService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LogoutController  {


	@Autowired
	private SecurityService securityService;
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
	//	clearSessionAttributes();
		securityService.logout(request);
	    return "redirect:https://login2.utah.gov/user/logoff";
		 
 	}
}
