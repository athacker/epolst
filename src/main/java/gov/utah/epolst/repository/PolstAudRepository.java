package gov.utah.epolst.repository;

import gov.utah.epolst.model.PolstAud;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("polstAudRepository")
@PersistenceContext(unitName="punit")
public interface PolstAudRepository extends JpaRepository<PolstAud, Integer> {

	 
	 
	
	
}
