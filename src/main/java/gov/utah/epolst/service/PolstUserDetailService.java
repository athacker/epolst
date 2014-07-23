package gov.utah.epolst.service;


import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.model.PolstUserDetails;
import gov.utah.epolst.model.enums.AuditAction;
import gov.utah.epolst.model.enums.Login;
import gov.utah.epolst.model.enums.RoleType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service ("polstUserDetailService")
public class PolstUserDetailService  implements UserDetailsService {
	
	@Autowired private AuditService auditService;
	@Autowired private PolstUserService polstUserService;
	
	private static final Logger LOG = LoggerFactory.getLogger(PolstUserDetailService.class);
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	
		UserDetails userDetails = null;
		PolstUser polstUser = polstUserService.determineLoggedInUserPermissions(userName);
		String note="";
		
		GrantedAuthority grantedAuthority;
		//user might not have a role
		if (null != polstUser && null != polstUser.getRole() ){
			grantedAuthority = new GrantedAuthorityImpl(polstUser.getRole().getRoleType().toString());
			LOG.info("User Id: " + userName + " is currently verified: " + polstUser.getRole().getRoleType().toString());
		} else{
			grantedAuthority = new GrantedAuthorityImpl( RoleType.ROLE_NOT_VERIFIED.toString());
		    LOG.warn("User Id: " + userName + " has not yet verified.");
		}
				
		Collection<GrantedAuthority> userPermissions = new ArrayList<GrantedAuthority>();
		userPermissions.add(grantedAuthority);
		
		userDetails= new PolstUserDetails( userName,"", true, true, true, true,userPermissions );
      	auditService.createLoginAuditRow((PolstUserDetails)userDetails, AuditAction.LOGIN, note,  Login.SUCCESS, "", "", "","");
        return userDetails;
	}

}
