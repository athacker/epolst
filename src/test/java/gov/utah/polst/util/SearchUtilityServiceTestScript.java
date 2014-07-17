package gov.utah.polst.util;

import gov.utah.polst.model.SearchBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-Test.xml")
@Transactional
public class SearchUtilityServiceTestScript  {
	
	@Autowired SearchUtilityService service;
	
	@Test
	public void testPrepareSearch(){
		String gender = "FEMALE";
		String searchString = "Sm Car";
		SearchBean bean = service.preparSearchString(gender, searchString);
	 	System.out.println(bean.getLastNameLike() );
		System.out.println(bean.getFirstNameLike() );
		System.out.println(bean.getGender() );
	}
	
	@Test
	public void testPrepareSearchAll(){
		String gender = "MALE";
		String searchString = "Sm Car";
		SearchBean bean = service.preparSearchString(gender, searchString);
	 	System.out.println(bean.getLastNameLike() );
		System.out.println(bean.getFirstNameLike() );
		System.out.println(bean.getGender() );
	}
	
	
	
	
	
}
