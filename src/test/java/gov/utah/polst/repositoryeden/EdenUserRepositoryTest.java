package gov.utah.polst.repositoryeden;

import gov.utah.polst.model.EdenUser;

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
public class EdenUserRepositoryTest {

	@Autowired
	EdenUserRepository edenUserRepository;


	@Test
	public void getAllUsersTest() throws Exception{
		System.out.println("Get Eden User Test... ");
		try{
			Collection<EdenUser>users = edenUserRepository.findAll();
			
			System.out.println("\n\nsize:   " + users.size() );
			
		}catch(Exception e){
			throw e;
		}
	}

 

}
