package gov.utah.polst.repository;

import gov.utah.polst.model.Audit;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("auditRepository")
@PersistenceContext(unitName="punit")
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	
	 
}
