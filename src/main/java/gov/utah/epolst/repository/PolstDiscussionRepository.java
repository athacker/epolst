package gov.utah.epolst.repository;

import gov.utah.epolst.model.PolstDiscussion;
import gov.utah.epolst.model.enums.DiscussionType;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("polstDiscussionRepository")
@PersistenceContext(unitName="punit")
public interface PolstDiscussionRepository extends JpaRepository<PolstDiscussion, Integer> {
 	
	@Modifying
	@Transactional
	@Query( "delete from PolstDiscussion p where p.polstId = :polstId and p.discussionType = 'PATIENT'")
	void deletePatientDiscussionsForPolstId(@Param("polstId") Integer polstId  );
	
	@Modifying
	@Transactional
	@Query( "delete from PolstDiscussion p where p.polstId = :polstId and p.discussionType = :discussionType")
	void deleteDiscussionsForPolstIdType(@Param("polstId") Integer polstId, @Param("discussionType") DiscussionType discussionType);
	
}
