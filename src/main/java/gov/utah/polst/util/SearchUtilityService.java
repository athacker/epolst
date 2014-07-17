package gov.utah.polst.util;

import gov.utah.polst.model.SearchBean;
import gov.utah.polst.model.enums.Gender;

public interface SearchUtilityService {

	SearchBean preparSearchString(String gender, String searchString);
	
}
