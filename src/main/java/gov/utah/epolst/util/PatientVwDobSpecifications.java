package gov.utah.epolst.util;

import gov.utah.epolst.model.PatientVw;
import gov.utah.epolst.model.PatientVw_;
import gov.utah.epolst.model.SearchParameters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class PatientVwDobSpecifications {
	
	
	 public static Specification<PatientVw> recordsAreLike(final SearchParameters params) {
	        
	        return new Specification<PatientVw>() {
	           @Override
	            public Predicate toPredicate(Root<PatientVw> patientRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
        	   
	        	   return   cb.or(
	        					  cb.and(//capenter karen
	        						cb.like(cb.lower( patientRoot.<String>get(PatientVw_.lastName)),params.getLastNameLike()  ),
	        						cb.like(cb.lower( patientRoot.<String>get(PatientVw_.firstName)),params.getFirstNameLike()   ),
	        						cb.equal(  patientRoot.get("dob") , params.getDateOfBirth())
	        					  ),  
	        					  cb.and( //karen carpenter
	     	        					cb.like(cb.lower( patientRoot.<String>get(PatientVw_.lastName)),params.getFirstNameLike()  ),
	     	        					cb.like(cb.lower( patientRoot.<String>get(PatientVw_.firstName)),params.getLastNameLike()  ),
	     	        					cb.equal(  patientRoot.get("dob") , params.getDateOfBirth())
	     	        				 )  
	        			    
	        			    ); 
	        			     
	        	
	        	  
	        	    
	        	   
	        	   
	           }
	            

	        };
	    }
}

