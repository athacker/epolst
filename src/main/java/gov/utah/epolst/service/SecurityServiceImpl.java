package gov.utah.epolst.service;

import gov.utah.epolst.model.PolstUserDetails;
import gov.utah.epolst.model.Role;
import gov.utah.epolst.model.enums.AuditAction;
import gov.utah.epolst.model.enums.Login;
import gov.utah.epolst.model.enums.RoleType;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Service("securityService")
public class SecurityServiceImpl implements SecurityService{
	
	@Autowired AuditService auditService;
	
	public void logout(HttpServletRequest request){
	 	PolstUserDetails user = getCurrentUser();
	  	auditService.createLoginAuditRow( user,  AuditAction.LOGOUT, "", Login.SUCCESS, "", "", "", "");
		SecurityContextHolder.getContext().setAuthentication(null);
		request.getSession().invalidate();
		return;
 }

	public PolstUserDetails getCurrentUser(){
		 return (PolstUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	
	public Role getCurrentRole(){
		//set default role
	 	Role role = new Role(RoleType.ROLE_NOT_VERIFIED); 
		
		for (GrantedAuthority ga:  SecurityContextHolder.getContext().getAuthentication().getAuthorities() ){
			//user should only have 1 role
			role = new Role(RoleType.valueOf( ga.getAuthority() )); 
		}
		return role;
	}
	
	public String getUserIPAddress(){
		String remoteAddress="";
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		if (null != request){	
			remoteAddress = request.getRemoteAddr();
		}
		return remoteAddress;
	}
	


}
