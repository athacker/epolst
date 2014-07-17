package gov.utah.polst.service;

import gov.utah.polst.model.PolstUserDetails;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

	
	void logout(HttpServletRequest request);
	PolstUserDetails getCurrentUser();
}
