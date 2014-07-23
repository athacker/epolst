
package gov.utah.epolst.controller;

import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.model.UserBean;
import gov.utah.epolst.model.enums.RoleType;
import gov.utah.epolst.repository.RoleRepository;
import gov.utah.epolst.service.PolstUserService;
import gov.utah.epolst.service.SecurityService;
import gov.utah.epolst.view.ClientCommand;
import gov.utah.epolst.view.ClientCommandType;
import gov.utah.epolst.view.Message;
import gov.utah.epolst.view.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Ajax methods provided to ADMIN screens(user maintenance)
 */
@Controller
public class AdminController {
 
	private static Logger LOG = LoggerFactory.getLogger(AdminController.class);
 	 
	@Autowired private PolstUserService polstUserService; 
	@Autowired private RoleRepository roleRepository; 
	@Autowired private SecurityService securityService;
	
	/**
	 * userList.jsp(ROLE_ADMIN)
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUsers", method = RequestMethod.POST)
	public @ResponseBody Response searchUsers(@RequestBody  SearchBean searchBean) {
	 	Response response = new Response();
	 
		Page<PolstUser>users;
		if(!RoleType.ROLE_ADMIN.equals(securityService.getCurrentRole().getRoleType() )) {
	 		LOG.warn(securityService.getCurrentUser() + " attempted to access admin screens.");
			users = null;
 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","Only Admins are allowed to this view.",  Message.STYLE_ERROR)));
 			return response;
 		}else {
 			users = polstUserService.searchUsers(searchBean);
	 		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"userList", users.getContent()));
	 		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"currentPage", searchBean.getCurrentPage() ));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalRecords", users.getTotalElements() ));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalPages", users.getTotalPages() ));
			response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onSearchComplete", searchBean));
			return response;
 		}	
 	 }
	
	
	
	
	/**
	 * Save polst_user data.
	 * userDetail(ROLE_ADMIN)
	 * @param userBean
	 * @return
	 */
	@RequestMapping(value = "/admin/saveUsers", method = RequestMethod.POST)
	public @ResponseBody Response saveUsers(@RequestBody UserBean userBean  ){
		Response response = new Response();
		 
		if(!RoleType.ROLE_ADMIN.equals(securityService.getCurrentRole().getRoleType() )) {
	 		LOG.warn(securityService.getCurrentUser() + " attempted to access admin screens.");
		 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","Only Admins are allowed to this view.",  Message.STYLE_ERROR)));
 			return response;
 		}else { 
 			try{ 
				String returnMessage=" ";
				if (polstUserService.isCurrentUser(userBean.getUserName())){
					returnMessage="Existing User " + userBean.getDisplayName() + " was successfully updated.";
					response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage" , new Message("Update Success!",returnMessage,  Message.STYLE_SUCCESS)));
				}else{
					returnMessage="New User " + userBean.getDisplayName() + " was successfully saved.";
					response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage" , new Message("Insert Success!",returnMessage,  Message.STYLE_SUCCESS)));
				 }
			 	userBean = polstUserService.saveUser(userBean);
			   	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onUserSaved", 	userBean));
			
			}catch(Exception e){
				LOG.error("Exception caught saving user.", e);
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onUserSaved", 	userBean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage" , new Message("Failure!","Issue was encountered saving user.",  Message.STYLE_ERROR)));
			}	
 		}
		return response;
	 }	
	
	/**
	 * ROLE select box list on userDetail (ROLE_ADMIN)
	 * @return
	 */
	@RequestMapping(value = "/admin/getRoles", method = RequestMethod.GET)
	public @ResponseBody Response getRoles() {
	 	Response response = new Response();
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"roles", roleRepository.findAll() ));
		return response;
	}
	
	/**
	 * userDetail.jsp (ROLE_ADMIN)
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/admin/getUser/{userId}", method = RequestMethod.GET)
	public @ResponseBody Response getUser(@PathVariable String userId) {
	 	Response response = new Response();
	 	if(!RoleType.ROLE_ADMIN.equals(securityService.getCurrentRole().getRoleType() )) {
	 		LOG.warn(securityService.getCurrentUser() + " attempted to access admin screens.");
		 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","Only Admins are allowed to this view.",  Message.STYLE_ERROR)));
 			return response;
 		}else { 
 			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"user", polstUserService.findUserByUserId(Integer.valueOf(userId))  ));
 			return response;
 		}	
	}
	
	
	/** 
	 * userDetail.jsp (ROLE_ADMIN)
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/admin/verifyLicense/{roleId}/{license}", method = RequestMethod.GET)
	public @ResponseBody Response getUser(@PathVariable String roleId, @PathVariable String license ) {
		Response response = new Response();
	 
	 	if(!RoleType.ROLE_ADMIN.equals(securityService.getCurrentRole().getRoleType() )) {
	 		LOG.warn(securityService.getCurrentUser() + " attempted to access admin screens.");
		 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","Only Admins are allowed to this view.",  Message.STYLE_ERROR)));
	 	}else { 
	 		UserBean userBean = polstUserService.verifyLicense(roleId,license); 
 			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"user", userBean));
	 		
	 		if (StringUtils.isEmpty(userBean.getFirstName())) {
	 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onFailedVerification",	userBean));
	 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage", new Message("Failure!","License/Badge could not be verified for this role.",  Message.STYLE_ERROR)));
	 		}else {
	 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage", new Message("Success", userBean.getDisplayName() + " Lic: " + license + " was verified for role:  " + userBean.getRole().toString(),  Message.STYLE_SUCCESS) ) ) ;
		 	 	
	 		}
	 		
 		}
	 	 
	 	
		return response;
	}
	

	
	
}
