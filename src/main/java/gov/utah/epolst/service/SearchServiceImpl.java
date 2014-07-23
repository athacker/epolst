package gov.utah.epolst.service;

import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.model.SearchParameters;
import gov.utah.epolst.model.enums.PolstStatus;
import gov.utah.epolst.model.enums.RoleType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class SearchServiceImpl implements SearchService{
 
	private static final Logger LOG = LoggerFactory.getLogger(SearchServiceImpl.class);
	@Autowired UtilityService utilityService;
	
	
	/**
	 * Method is shared between all roles.
	 */
	public SearchParameters prepareQueryParameters(SearchBean searchBean, String userName, RoleType roleType)  {
	 
		SearchParameters params = new SearchParameters();
	
		//show all records regardless of who they are assigned to or created by
		if (searchBean.isShowAll() || RoleType.ROLE_ADMIN.equals(roleType)  || RoleType.ROLE_EMS.equals(roleType)){
			params.setAddedUserId("%");
			params.setPhysicianUserName("%");
		//show only the records assigned to a physician	patient.physician
		}else if (RoleType.ROLE_MEDCERT.equals(roleType)){
			params.setAddedUserId("%" );
		  	params.setPhysicianUserName(userName );
		}else{//show only the records a nurse or social worker created patient.added_user_id
			params.setAddedUserId(userName );
		 	params.setPhysicianUserName("%" );
		}
		
		
		//filter by polst.polst_status
		if (PolstStatus.ALL.toString().equals( searchBean.getPolstStatus() )){
			params.setPolstStatus("%");
		}else{
			params.setPolstStatus(searchBean.getPolstStatus());
		}
		
		String searchString = searchBean.getQuickSearchName().trim().toLowerCase();
		
		int startSpace = searchString.indexOf(" ");
	    int endSpace = searchString.length();
	    
	    String firstName = "";
	    String lastName = "";

	    if (startSpace >= 0)   {
	       lastName = searchString.substring(0, startSpace);
	       firstName = searchString.substring(startSpace + 1, endSpace);
	     }else{
	    	//if no space, consider it all the last name.
	    	 lastName = searchString; 
	     }
	    
	    //if user entered middle name -- remove it, currently is not part of the search
	    char blank=' ';
	    if (firstName.indexOf(blank)> 0){
	    	firstName = firstName.substring(0, firstName.indexOf(" "));
	    }
	    params.setLastNameLike(lastName+"%");
	    params.setFirstNameLike(firstName+"%");
	    
	    if (null != searchBean.getQuickSearchDob() && ! StringUtils.isEmpty(searchBean.getQuickSearchDob()) ){
	    	Date dob = utilityService.formatDate(searchBean.getQuickSearchDob());
    		params.setDateOfBirth(dob);
	    }	 
	   
	    return params;
	}

	
 
	
	/**
	 * @Todo fix this so it can be shared a bit better.
	 */
	public Pageable constructPageSpecification(SearchBean searchBean){
		if(null == searchBean.getSortField()){
			return new PageRequest(searchBean.getCurrentPage()-1,searchBean.getRecordsPerPage(), new Sort(Sort.Direction.ASC, "lastName" ));
		}else{
			return new PageRequest(searchBean.getCurrentPage()-1,searchBean.getRecordsPerPage(), new Sort(Sort.Direction.ASC, searchBean.getSortField() ));
		}
  
	}

	 
 
}
