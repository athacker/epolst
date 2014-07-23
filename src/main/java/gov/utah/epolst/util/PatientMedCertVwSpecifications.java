package gov.utah.epolst.util;

import gov.utah.epolst.model.PatientMedCertVw;
import gov.utah.epolst.model.PatientMedCertVw_;
import gov.utah.epolst.model.PatientVw_;
import gov.utah.epolst.model.SearchParameters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class PatientMedCertVwSpecifications {
	
	
	 public static Specification<PatientMedCertVw> recordsAreLike(final SearchParameters params) {
	        
	        return new Specification<PatientMedCertVw>() {
	           @Override
	            public Predicate toPredicate(Root<PatientMedCertVw> patientRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
        	   
	        	   return cb.or(
     					  cb.and( 
     						cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.patientLastName)),params.getLastNameLike().toLowerCase()  ),
     						cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.patientFirstName)),params.getFirstNameLike().toLowerCase()    ), 
     						cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.polstStatus )) , params.getPolstStatus().toLowerCase()  ),
     						cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.physicianUserName )) , params.getPhysicianUserName().toLowerCase()  ),
     					 	cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.createdBy)) , params.getAddedUserId().toLowerCase() ) 
     					  ),  
     					  cb.and(  
  	        				cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.patientLastName)),params.getFirstNameLike().toLowerCase()  ),
  	        				cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.patientFirstName)),params.getLastNameLike().toLowerCase() ),
  	        				cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.polstStatus )) , params.getPolstStatus().toLowerCase()  ),
  	        				cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.physicianUserName )) , params.getPhysicianUserName().toLowerCase()  ),
     					 	cb.like(cb.lower( patientRoot.<String>get(PatientMedCertVw_.createdBy)) , params.getAddedUserId().toLowerCase() ) 
     						)  
     			    
     			    ); 
	        			     
	        	
	        	  
	        	    
	        	   
	        	   
	           }
	            

	        };
	    }
}

