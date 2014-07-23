
package gov.utah.epolst.controller;

import gov.utah.epolst.model.AddressBean;
import gov.utah.epolst.model.PolstUserDetails;
import gov.utah.epolst.model.UserBean;
import gov.utah.epolst.service.AddressService;
import gov.utah.epolst.service.PolstUserService;
import gov.utah.epolst.service.SecurityService;
import gov.utah.epolst.view.ClientCommand;
import gov.utah.epolst.view.ClientCommandType;
import gov.utah.epolst.view.Message;
import gov.utah.epolst.view.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *  
 * Main controller that starts application off and fowards user to index.jsp
 *
 */
@Controller
public class IndexController {
 
	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
   
	@Autowired private AddressService addressService;
	@Autowired private PolstUserService polstUserService;
	@Autowired private SecurityService securityService;
	
	/**
	 * set user and user role attributes and forward to index.jsp
	 * after this -- everything is done via ajax.	
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		PolstUserDetails u = securityService.getCurrentUser() ;
	 	mav.addObject("currentUser", u.getUsername()  );
		mav.addObject("role", securityService.getCurrentRole() );
		mav.addObject("userPhone", polstUserService.findUserByUserName(u.getUsername()).getPhoneNumber());
	 	LOG.debug("Current Role:  " + securityService.getCurrentRole() );
		return mav;
	}
 
	
	/**
	 * New Users who pass UMD login but who do not have accounts in ePOLST
	 * are sent to this page.  User enters their license and it is verifed
	 * against our local DOPL view.  IF we find their license in DOPL then
	 * they are asked to log out and re-LOG.  ajax call from register.jsp
	 * @param license
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public @ResponseBody Response register(@RequestBody String license) {
		Response response = new Response();
		LOG.debug("Register User:  " +securityService.getCurrentUser().getUsername() );
		LOG.debug("License:  " + license);
		try{
			UserBean user = polstUserService.verifyUser(securityService.getCurrentUser().getUsername(), license); 
		 	
				if (user.isVerified()){
					response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onUserRegistered",user ));
				 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
			 				, new Message("Success", "You have been successfully registered into ePOLST. Please re-login to access the ePOLST system. ",  Message.STYLE_SUCCESS)));
			 	}
				else if (!user.isActive()){
					//Admin was "DE-Activated User"
					response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onUserFailureRegister",user ));
				 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
			 				, new Message("Failure To Verify", "Current user has been In-Activated from System.  Please contact System Admin to re-activate user. ",  Message.STYLE_ERROR)));
			
				}else{
					//User was not in DOPL_VW or EPOLST_EMT_VW"
					response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onUserFailureRegister",user ));
				 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
			 				, new Message("Failure to verify user.",  "Please contact System Admin to manually verifiy user and activate User. ",  Message.STYLE_ERROR)));
				}
			}catch (Exception e){
				LOG.error("Exception was caught verifiying User ", e);
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Failure!","An issue was encountered while verifying user. Please contact the ePOLST System Administrator.",  Message.STYLE_ERROR)));
			}
				
			return response;
		
		}
	
	@RequestMapping(value = "registerEmt", method = RequestMethod.POST)
	public @ResponseBody Response registerEmt(@RequestBody String badgeNumber) {
		LOG.debug("Register User:  " + securityService.getCurrentUser().getUsername() );
		Response response = new Response();
			try{
				UserBean user = polstUserService.verifyEmt(securityService.getCurrentUser().getUsername(), badgeNumber); 
			 		if (user.isVerified()){
						response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onUserRegistered",user ));
					 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
				 				, new Message("You have been successfully registered into ePOLST. Please Log-in to access the ePOLST system. ",  Message.STYLE_NONE  )));
					  	return response;
					}else{
						response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onUserFailureRegister",user ));
					 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage", new Message("Failure to verify user. ",  Message.STYLE_NONE)));
					}
					
			}
			catch (Exception e){
					LOG.error("Exception was caught verifiying EMT User ", e);
					response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
								, new Message("Failure!","An issue was encountered while verifying EMT user. Please contact the ePOLST System Administrator.",  Message.STYLE_ERROR)));
			}
				
		return response;
		
		}
	
	/**
	 * type ahead physician list patientDetail
	 * not available for MED_CERT
	 * @return
	 */
	@RequestMapping(value = "/getPhysicians", method = RequestMethod.GET)
	public @ResponseBody Response getPhysicians() {
	 	Response response = new Response();
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"physicians", polstUserService.getActivePhysicians() ));
		return response;
	}
	
	 
	/**
	 * zip-code to pre-populate city state
	 * web service call
	 * @return
	 */
	@RequestMapping(value = "/getCityState/{isCurrent}/{zip}", method = RequestMethod.GET)
	public @ResponseBody Response getCityState(@PathVariable boolean isCurrent, @PathVariable String zip) {
	 	Response response = new Response();
	 	AddressBean bean = addressService.getCityStateByZip(zip);
	 	bean.setCurrent(isCurrent);
 		response.addCommand(new ClientCommand(ClientCommandType.METHOD,"setCityState", bean ));
 		return response;
	}
	
	
 
}
