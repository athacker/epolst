package gov.utah.polst.repository;

import gov.utah.polst.model.PolstUser;

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
public class RoleRepositoryTest {

	@Autowired
	RoleRepository roleRepository;


	 
	public void getRolesTest() throws Exception{

		try{
		  roleRepository.findAll();
		}catch(Exception e){
			throw e;
		}
		
	}
}