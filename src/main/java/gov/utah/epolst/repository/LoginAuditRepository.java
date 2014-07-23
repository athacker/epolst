package gov.utah.epolst.repository;

import gov.utah.epolst.model.LoginAudit;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("loginAuditRepository")
@PersistenceContext(unitName="punit")
public interface LoginAuditRepository extends JpaRepository<LoginAudit, Integer> {

	
	 
}
