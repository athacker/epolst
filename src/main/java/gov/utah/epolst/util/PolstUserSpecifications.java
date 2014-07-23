package gov.utah.epolst.util;

import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.model.PolstUser_;
import gov.utah.epolst.model.SearchParameters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class PolstUserSpecifications {
	
	
	 public static Specification<PolstUser> recordsAreLike(final SearchParameters params) {
	        
	        return new Specification<PolstUser>() {
	           @Override
	            public Predicate toPredicate(Root<PolstUser> polstUserRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
        	   
	        	   return   cb.or(
	        					  cb.and( 
	        						cb.like(cb.lower(  polstUserRoot.<String>get(PolstUser_.lastName)),params.getLastNameLike()  ),
	        						cb.like(cb.lower(  polstUserRoot.<String>get(PolstUser_.firstName)),params.getFirstNameLike()   ) 
	        					  ),  
	        					  cb.and( 
	     	        					cb.like(cb.lower(  polstUserRoot.<String>get(PolstUser_.lastName)),params.getFirstNameLike()  ),
	     	        					cb.like(cb.lower(  polstUserRoot.<String>get(PolstUser_.firstName)),params.getLastNameLike()  ) 
	     	        				 )  
	        			    
	        			    ); 
	        			     
	        	
	        	  
	        	    
	        	   
	        	   
	           }
	            

	        };
	    }
}

