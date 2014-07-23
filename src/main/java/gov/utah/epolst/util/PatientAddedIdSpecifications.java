package gov.utah.epolst.util;

import gov.utah.epolst.model.Patient;
import gov.utah.epolst.model.Patient_;
import gov.utah.epolst.model.SearchParameters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class PatientAddedIdSpecifications {
	
	
	 public static Specification<Patient> recordsAreLike(final SearchParameters params) {
	 
		 	return new Specification<Patient>() {
		           @Override
		            public Predicate toPredicate(Root<Patient> patientRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
	        	   
		        	return  cb.or(
			        			cb.and( 
			        			cb.like(cb.lower( patientRoot.<String>get(Patient_.lastName)), params.getLastNameLike()  ),
			        			cb.like(cb.lower( patientRoot.<String>get(Patient_.firstName)), params.getFirstNameLike()   ),
			        			cb.like(  patientRoot.<String>get(Patient_.addedUserId) , params.getAddedUserId() )
			        			),   
								cb.and( 
								cb.like(cb.lower( patientRoot.<String>get(Patient_.lastName)), params.getFirstNameLike()  ),
								cb.like(cb.lower( patientRoot.<String>get(Patient_.firstName)), params.getLastNameLike()  ),
								cb.like(  patientRoot.<String>get(Patient_.addedUserId) , params.getAddedUserId() )
								)     
		        			    
		        			); 
		        			     
		        	
		        	  
		        	    
		        	   
		        	   
		           }
		            

		        };
		    }
	}

