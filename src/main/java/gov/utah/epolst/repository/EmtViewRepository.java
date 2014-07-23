package gov.utah.epolst.repository;

import gov.utah.epolst.model.EmtView;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("emtViewRepository")
@PersistenceContext(unitName="punit")
public interface EmtViewRepository extends JpaRepository<EmtView, Integer>  {
	
	 @Query("Select distinct e from EmtView e where emsId = :badgeNumber)")
	 EmtView findEmtByBadgeNumber( @Param("badgeNumber") String  badgeNumber) ; 
	
}
