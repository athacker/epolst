package gov.utah.epolst.repository;

import gov.utah.epolst.model.Patient;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository("patientRepository")
@PersistenceContext(unitName="punit")
public interface PatientRepository extends JpaRepository<Patient, Integer>,JpaSpecificationExecutor<Patient> {


}
