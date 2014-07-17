package gov.utah.polst.repositorypolaris;

import gov.utah.polst.model.PolarisUser;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-Test.xml")
@Transactional
public class PolarisUserRepositoryTest {
	@Autowired
	PolarisUserRepository polarisUserRepository;


	
	@Test
	public void getAllUsersTest() throws Exception{
		System.out.println("Get Polaris User Test... ");
		try{
			Collection<PolarisUser>users = polarisUserRepository.findAll();
			
			System.out.println("\n\nsize:   " + users.size() );
			
		}catch(Exception e){
			throw e;
		}
	}


}
