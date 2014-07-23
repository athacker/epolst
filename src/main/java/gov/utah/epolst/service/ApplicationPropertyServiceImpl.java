package gov.utah.epolst.service;

import gov.utah.epolst.model.ApplicationProperty;
import gov.utah.epolst.repository.ApplicationPropertyRepository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *Provides methods to access application_properties
 * table that holds configuration type data..email configurations
 * etc etc
 */

@Service("applicationPropertyService")
public class ApplicationPropertyServiceImpl implements ApplicationPropertyService {

	@Autowired private ApplicationPropertyRepository applicationPropertyRepository;
	
	/**
	 * Gets single property 
	 */
	@Override
	public String getPropertyAsString(String name) {
		return applicationPropertyRepository.findByName(name).getValue();
	}
	/**
	 * returns all properties in a Map.
	 */
	@Override
	public Map<String, String>getPropertyMap(){
		Map<String,String>properties = new HashMap<String,String>();
		 for (ApplicationProperty prop: applicationPropertyRepository.findAll()){
			 properties.put(prop.getName().trim(), prop.getValue().trim());
		 }
		 return properties;
	}

}
