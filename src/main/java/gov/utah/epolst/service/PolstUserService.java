package gov.utah.epolst.service;

import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.model.UserBean;

import java.util.Collection;

import org.springframework.data.domain.Page;

public interface PolstUserService {
	/**
	 * 
	 * @param userName
	 * @return
	 */
	UserBean findUserByUserName(String userName);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	UserBean findUserByUserId(Integer id);
	
	/**
	 * Does the logic to determine if LOGGED IN user
	 * can access the system.  Returns PolstUser with proper
	 * Roles
	 *  
	 */
	PolstUser determineLoggedInUserPermissions(String umdLoginName);
	 
	/**
	 * 
	 * @param bean
	 * @return
	 */
	UserBean saveUser(UserBean bean);
	
	
	/**
	 * 
	 * @param loginUserName
	 * @param license
	 * @return
	 */
	UserBean verifyUser(String loginUserName, String license);
	
	/**
	 * Verify User against Polaris/EMT View
	 * @param loginUserName
	 * @param registerBean
	 * @return
	 */
	UserBean verifyEmt( String loginUserName, String badgeNumber);
	
	
	/**
	 * 
	 * @return
	 */
	Collection<UserBean>getActivePhysicians();
	
	/**
	 * 
	 * @param userLoginName
	 * @return
	 */
	String getUserFullName(String userLoginName);
	
	/** 
	 * 
	 * @param searchBean
	 * @return
	 */
	Page<PolstUser> searchUsers(SearchBean searchBean);
	
	/**
	 * getLicenseId -- displays on ePOLST form
	 * @param userLoginName
	 * @return
	 */
	String getLicenseId(String userLoginName);
	
	/**
	 * 
	 * @param license
	 * @return
	 */
	String formatLicense(String license);
	
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	boolean isCurrentUser(String userName);
	
	
	
	/**
	 * 
	 * @param roleId
	 * @param license
	 * @return
	 */
	UserBean verifyLicense(String roleId, String license);
	
	 
}
