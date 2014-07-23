package gov.utah.epolst.repository;

import gov.utah.epolst.model.PatientMedCertVw;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository("patientMedCertVwRepository")
@PersistenceContext(unitName="punit")
public interface PatientMedCertVwRepository extends JpaRepository<PatientMedCertVw , Integer>,JpaSpecificationExecutor<PatientMedCertVw>  {

	 
	
}
