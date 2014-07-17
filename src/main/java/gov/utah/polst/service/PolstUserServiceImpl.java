package gov.utah.polst.service;

import gov.utah.polst.model.PolstUser;
import gov.utah.polst.model.Role;
import gov.utah.polst.model.enums.RoleType;
import gov.utah.polst.repository.PolstUserRepository;
import gov.utah.polst.repositoryeden.EdenUserRepository;
import gov.utah.polst.repositorypolaris.PolarisUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("polstUserService")
public class PolstUserServiceImpl implements PolstUserService{
 
	@Autowired
	EdenUserRepository edenUserRepository;
	@Autowired
	PolarisUserRepository polarisUserRepository;
	@Autowired
	PolstUserRepository polstUserRepository;
	
	/**
	 * @ToDo -- see if we can flag a physican in the EDEN db
	 */
	@Override
	public PolstUser findUserByUserName(String userName) {
		
		PolstUser polstUser;
		
		if (null != polarisUserRepository.findPolarisUserByUserName(userName)){
			polstUser = new PolstUser (userName, new Role(RoleType.ROLE_EMT)); 
	  	}else if (null != edenUserRepository.findEdenUserByUserName(userName)){
			polstUser = new PolstUser (userName, new Role(RoleType.ROLE_PHYSICIAN));
		}else{
			polstUser = polstUserRepository.findUserByUserName(userName);
		}
		 
		return polstUser;
	}



 



}