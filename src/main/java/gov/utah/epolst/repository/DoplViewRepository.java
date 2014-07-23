package gov.utah.epolst.repository;

import java.util.List;

import gov.utah.epolst.model.DoplView;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("doplViewRepository")
@PersistenceContext(unitName="punit")
public interface DoplViewRepository extends JpaRepository<DoplView, Integer>  {
	
		
		@Query("Select d from DoplView d where d.licenseNumber = :licenseNumber")
		DoplView findDoplRecordByLicense(@Param("licenseNumber") String  licenseNumber) ; 
	 
		@Query("Select d from DoplView d where d.professionName = :professionName")
		List<DoplView> findDoplRecordsProfessionName(@Param("professionName") String  professionName) ; 
}
