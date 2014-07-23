package gov.utah.epolst.repository;

import gov.utah.epolst.model.PatientAud;

import org.springframework.data.jpa.repository.JpaRepository;

 
	public interface PatientAudRepository extends JpaRepository<PatientAud, Integer> {
}
