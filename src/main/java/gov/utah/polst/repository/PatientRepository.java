package gov.utah.polst.repository;

import gov.utah.polst.model.Patient;
import gov.utah.polst.model.enums.Gender;

import java.util.Collection;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("patientRepository")
@PersistenceContext(unitName="punit")
public interface PatientRepository extends JpaRepository<Patient, Integer> {

	
	
	@Query("Select p from Patient p where p.gender = :gender and p.lastName like :likeLastName and p.firstName like :likeFirstName  ") //add filter for active polst...
	Collection<Patient>searchPatientsWithActivePolst(@Param("gender")Gender gender, @Param("likeLastName")  String likeLastName,  @Param("likeFirstName")  String likeFirstName );
	
	
	@Query("Select p from Patient p where p.physician = :physician")
	Collection<Patient>getPatientsForPhysician(@Param("physician") String physician);
	
	 
}
