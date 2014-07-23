package gov.utah.epolst.util;

import gov.utah.epolst.model.PatientVw;
import gov.utah.epolst.model.PatientVw_;
import gov.utah.epolst.model.SearchParameters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class PatientVwSpecifications {
	
	
	 public static Specification<PatientVw> recordsAreLike(final SearchParameters params) {
	        
	        return new Specification<PatientVw>() {
	           @Override
	            public Predicate toPredicate(Root<PatientVw> patientRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
        	   
	        	   return   cb.or(
	        					  cb.and( 
	        						cb.like(cb.lower( patientRoot.<String>get(PatientVw_.lastName)),params.getLastNameLike()  ),
	        						cb.like(cb.lower( patientRoot.<String>get(PatientVw_.firstName)),params.getFirstNameLike()   ) 
	        					  ),  
	        					  cb.and(  
	     	        					cb.like(cb.lower( patientRoot.<String>get(PatientVw_.lastName)),params.getFirstNameLike()  ),
	     	        					cb.like(cb.lower( patientRoot.<String>get(PatientVw_.firstName)),params.getLastNameLike()  ) 
	     	        				 )  
	        			    
	        			    ); 
	        			     
	        	
	        	  
	        	    
	        	   
	        	   
	           }
	            

	        };
	    }
}

