package gov.utah.polst.service;


import gov.utah.polst.model.PolstUser;
import gov.utah.polst.model.PolstUserDetails;
import gov.utah.polst.model.enums.AuditAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service ("polstUserDetailService")
public class PolstUserDetailService  implements UserDetailsService {

	@Autowired
	private PolstUserService polstUserService;
	@Autowired
	private AuditService auditService;

	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	
		UserDetails userDetails = null;
		PolstUser polstUser = polstUserService.findUserByUserName(userName);
		
		GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(polstUser.getRole().getRoleType().toString());
		System.out.println("\n\n\tUser:  " + userName + " Role: " + grantedAuthority.getAuthority());
				
		Collection<GrantedAuthority> userPermissions = new ArrayList<GrantedAuthority>();
		userPermissions.add(grantedAuthority);
		
	    userDetails = new PolstUserDetails( userName,"", true, true, true, true,userPermissions );
	    auditService.createAuditRow(new Date(), (PolstUserDetails)userDetails, "", "", "","", "", AuditAction.LOGIN, "", "");
		return userDetails;
	}

}
