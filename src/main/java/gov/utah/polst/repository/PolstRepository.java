package gov.utah.polst.repository;

import gov.utah.polst.model.Polst;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("polstRepository")
@PersistenceContext(unitName="punit")
public interface PolstRepository extends JpaRepository<Polst, Integer> {

	@Query ("Select p from Polst p where p.patient.id = :patientId")
	public Polst findPolstForPatient(@Param("patientId") Integer patientId);
}
