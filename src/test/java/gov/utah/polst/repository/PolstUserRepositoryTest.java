package gov.utah.polst.repository;

import gov.utah.polst.model.PolstUser;
import gov.utah.polst.model.Role;
import gov.utah.polst.model.enums.RoleType;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-Test.xml")
@Transactional
public class PolstUserRepositoryTest {

	@Autowired
	PolstUserRepository polstUserRepository;


	@Test
	public void getAllUsersTest() throws Exception{
		
		try{
			Collection<PolstUser>users = polstUserRepository.findAll();
		}catch(Exception e){
			throw e;
		}
	}

	@Test
	@Transactional
	public void getUserByEmailTest() throws Exception{
		System.out.println("Get User by email test ");
		String email= "testemail.com";
		try{
		  PolstUser user  = new PolstUser("PHYSICIAN", new Role(RoleType.ROLE_PHYSICIAN));
		  user.setEmail(email);
		  polstUserRepository.save(user);
		  PolstUser testUser = polstUserRepository.findUserByUserName(email);
	   //   Assert.notNull( testUser );	
			
		}catch(Exception e){
			throw e;
		}
	}


}