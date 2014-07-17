package gov.utah.polst.service;

import gov.utah.polst.model.PolstUserDetails;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service("securityService")
public class SecurityServiceImpl implements SecurityService{
	public void logout(HttpServletRequest request){
	 	PolstUserDetails user = getCurrentUser();
	  //  auditService.createAuditRow(new Date(),user,"","MEMBER",user.getUsername(),"",auditService.getTransaction(),Audit.ACTION_LOGOUT,null,user.getId().toString());
		SecurityContextHolder.getContext().setAuthentication(null);
		request.getSession().invalidate();
		return;
 }

	public PolstUserDetails getCurrentUser(){
		 return (PolstUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	


}
