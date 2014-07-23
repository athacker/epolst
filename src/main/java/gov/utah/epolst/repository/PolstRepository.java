package gov.utah.epolst.repository;

import gov.utah.epolst.model.Polst;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository("polstRepository")
@PersistenceContext(unitName="punit")
public interface PolstRepository extends JpaRepository<Polst, Integer> {

	 
	/**
	 * 
	 * @param polstId
	 * @param patientId
	 * @param userId
	 */
	@Modifying 
	@Transactional (isolation=Isolation.READ_UNCOMMITTED, propagation =Propagation.SUPPORTS,rollbackFor=Exception.class) 	 
	@Query("Update Polst po set po.polstStatus = 'IN_ACTIVE',  po.statusChangeReason='REPLACED', updatedUserId = :userId  where po.id != :polstId and po.patientId = :patientId and po.polstStatus != 'IN_ACTIVE' ")
	void deactivateOtherPolstForPatient(  @Param("polstId") Integer polstId, @Param("patientId") Integer patientId,  @Param("userId")String userId);
	
 
	
	
	/**
	 * 
	 * @param patientId
	 * @param currentDate
	 * @param loggedInUser
	 */
	@Modifying 
	@Transactional (isolation=Isolation.READ_UNCOMMITTED, propagation =Propagation.SUPPORTS,rollbackFor=Exception.class) 	 
	@Query("Update Polst po set po.polstStatus = 'IN_ACTIVE', po.statusChangeReason='REPLACED', po.updatedDate= :currentDate, po.updatedUserId = :loggedInUser where po.patientId = :patientId and po.polstStatus in('IN_PROCESS', 'PENDING_CERTIFICATION' )")
	void deactivatePendingInProcessForPatient(   @Param("patientId") Integer patientId,   @Param("currentDate") Date currentDate , @Param("loggedInUser") String loggedInUser  );
	
	/**
	 * history square in patient detail.sjp
	 * @param patientId
	 * @return
	 */
	@Query ("Select p from Polst p where p.patientId = :patientId order by updatedDate desc ")
	Collection<Polst>getPolstHistoryForPatient(@Param("patientId") Integer patientId) ;
	
	
 	/**used from patientDetail.jsp  ePOLST button
	*used to find latest IN_PROCESS polst for a patient when adding a new record
	*we might want to re-think this -- because we found out a patient could have MANY polst in process!!*/
	@Query ("Select p from Polst p where p.patientId = :patientId and p.polstStatus='IN_PROCESS' ")
	Polst findInProcessPolstForPatient(  @Param("patientId") Integer patientId) ;
	
	/**EMT POLST VIEW*/
 	@Query ("Select distinct p from Polst p where p.patientId = :patientId and p.polstStatus = 'ACTIVE' ")
 	Polst getActivePolstByPatientId(@Param("patientId") Integer patientId);
 	
	
 	/** used to provide list to de-active when dod has been entered for a pt **/
 	@Query ("Select p from Polst p where p.patientId = :patientId and p.polstStatus <>'IN-ACTIVE' ")
 	List<Polst> getPolstByPatientId(@Param("patientId") Integer patientId);
 	
	
	
}
