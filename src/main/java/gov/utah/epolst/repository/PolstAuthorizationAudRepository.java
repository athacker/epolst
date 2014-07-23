package gov.utah.epolst.repository;

import gov.utah.epolst.model.PolstAuthorizationAud;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("polstAuthorizationAudRepository")
@PersistenceContext(unitName="punit")
public interface PolstAuthorizationAudRepository extends JpaRepository<PolstAuthorizationAud, Integer> {
 	
	 

	 
}
