package gov.utah.epolst.service;

import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.model.SearchParameters;
import gov.utah.epolst.model.enums.RoleType;

import org.springframework.data.domain.Pageable;


/**
 *  
 * Services to construct pageable searches
 *
 */
public interface SearchService {
 
	/**
	 * Construct Pageable
	 * @param searchBean
	 * @return
	 */
	Pageable constructPageSpecification(SearchBean searchBean);
	/**
	 * Prepares query for a like % 
	 * @param searchBean
	 * @param userName
	 * @return
	 */
	SearchParameters prepareQueryParameters(SearchBean searchBean, String userName, RoleType roleType)  ;
		
}
