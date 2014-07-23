package gov.utah.epolst.service;

import gov.utah.epolst.model.PolstUserDetails;
import gov.utah.epolst.model.Role;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

	/**
	 * Logs user out of ePOLST system.
	 * @param request
	 */
	void logout(HttpServletRequest request);
	/**
	 * UserDetails used for Spring Login/Security Roles
	 * @return
	 */
	PolstUserDetails getCurrentUser();
	/**
	 * Current Role of User
	 * @return
	 */
	Role getCurrentRole();
	/**
	 * User IP address
	 * @return
	 */
	String getUserIPAddress();
	
}
