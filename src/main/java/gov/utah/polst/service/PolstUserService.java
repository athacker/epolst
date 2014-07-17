package gov.utah.polst.service;

import gov.utah.polst.model.PolstUser;

public interface PolstUserService {
	
	PolstUser findUserByUserName(String email);

}
