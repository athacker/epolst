package gov.utah.epolst.service;

import java.util.Map;

/**
 * Provides methods to access application_properties
 * table that holds configuration type data..email configurations
 * etc etc
 */

public interface ApplicationPropertyService {
	/**
	 * returns the property for a given name
	 * @param name
	 * @return
	 */
	String getPropertyAsString(String name);
	
	/**
	 * provides a map of all the properties in the table
	 * @return
	 */
	Map<String, String>getPropertyMap();
	
	
	
}
