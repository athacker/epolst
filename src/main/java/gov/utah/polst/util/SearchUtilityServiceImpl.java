package gov.utah.polst.util;

import gov.utah.polst.model.SearchBean;
import gov.utah.polst.model.enums.Gender;

import org.springframework.stereotype.Service;




@Service("searchUtilityService")
public class SearchUtilityServiceImpl implements SearchUtilityService{

	
	
	@Override
	public SearchBean preparSearchString(String gender, String searchString) {
		SearchBean searchBean = new SearchBean();
		
		searchBean.setGender(Gender.valueOf(gender));
	 				
		searchString = searchString.trim();
		
		int startSpace = searchString.indexOf(" ");
	    int endSpace = searchString.length();
	    
	    String firstName = "";
	   
	    String lastName = "";

	    if (startSpace >= 0)   {
	       lastName = searchString.substring(0, startSpace);
	       firstName = searchString.substring(startSpace + 1, endSpace);
	     }
		
	    searchBean.setLastNameLike("%"+lastName+"%");
		searchBean.setFirstNameLike("%"+firstName+"%");
		
		return searchBean;
		
	}

	
	
	
}
