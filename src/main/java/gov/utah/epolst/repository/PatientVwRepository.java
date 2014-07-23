package gov.utah.epolst.repository;

import gov.utah.epolst.model.PatientVw;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository("patientVwRepository")
@PersistenceContext(unitName="punit")
public interface PatientVwRepository extends JpaRepository<PatientVw, Integer>,JpaSpecificationExecutor<PatientVw> {

	 
	
}
