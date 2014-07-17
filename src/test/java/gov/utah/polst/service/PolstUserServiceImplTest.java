package gov.utah.polst.service;

import gov.utah.polst.model.EdenUser;
import gov.utah.polst.model.PolarisUser;
import gov.utah.polst.model.PolstUser;
import gov.utah.polst.model.Role;
import gov.utah.polst.model.enums.RoleType;
import gov.utah.polst.repository.PolstUserRepository;
import gov.utah.polst.repositoryeden.EdenUserRepository;
import gov.utah.polst.repositorypolaris.PolarisUserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PolstUserServiceImplTest {

	@InjectMocks
	private PolstUserService service = new PolstUserServiceImpl();
	
	@Mock private PolarisUserRepository polarisUserRepository;
	@Mock private PolstUserRepository polstUserRepository;
	@Mock private EdenUserRepository edenUserRepository;
	
	String userName="userName";
	
	@Test
	public void getEmtUserTest(){
		PolarisUser emt = new PolarisUser(userName, new Role(RoleType.ROLE_EMT));
		Mockito.when(polarisUserRepository.findPolarisUserByUserName(userName)).thenReturn(emt);
		service.findUserByUserName(userName);
		Mockito.verify(polarisUserRepository, Mockito.times(1) ).findPolarisUserByUserName(userName);
		Mockito.verify(edenUserRepository, Mockito.times(0) ).findEdenUserByUserName(userName);
		Mockito.verify(polstUserRepository, Mockito.times(0) ).findUserByUserName(userName);
	}
	
	@Test
	public void getPhysicianUserTest(){
		EdenUser physician = new EdenUser(userName, new Role(RoleType.ROLE_PHYSICIAN));
		Mockito.when(polarisUserRepository.findPolarisUserByUserName(userName)).thenReturn(null);
		Mockito.when(edenUserRepository.findEdenUserByUserName(userName)).thenReturn(physician);
		
		service.findUserByUserName(userName);
		
		Mockito.verify(polarisUserRepository, Mockito.times(1) ).findPolarisUserByUserName(userName);
		Mockito.verify(edenUserRepository, Mockito.times(1) ).findEdenUserByUserName(userName);
		Mockito.verify(polstUserRepository, Mockito.times(0) ).findUserByUserName(userName);
	}
		
	


	@Test
	public void getAdminUserTest(){
		PolstUser admin = new PolstUser(userName, new Role(RoleType.ROLE_ADMIN));
		Mockito.when(polarisUserRepository.findPolarisUserByUserName(userName)).thenReturn(null);
		Mockito.when(edenUserRepository.findEdenUserByUserName(userName)).thenReturn(null);
		Mockito.when(polstUserRepository.findUserByUserName(userName)).thenReturn( admin );
	
		service.findUserByUserName(userName);
		
		Mockito.verify(polarisUserRepository, Mockito.times(1) ).findPolarisUserByUserName(userName);
		Mockito.verify(edenUserRepository, Mockito.times(1) ).findEdenUserByUserName(userName);
		Mockito.verify(polstUserRepository, Mockito.times(1) ).findUserByUserName(userName);
		
	}

	
	
}
