package gov.utah.epolst.repository;

import gov.utah.epolst.model.PolstAuthorization;
import gov.utah.epolst.model.enums.AuthorizationType;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("polstAuthorizationRepository")
@PersistenceContext(unitName="punit")
public interface PolstAuthorizationRepository extends JpaRepository<PolstAuthorization, Integer> {
 	
	 

	@Modifying
	@Transactional
	@Query( "delete from PolstAuthorization p where p.polstId = :polstId and p.authorizerType = 'PATIENT'")
	void deletePatientAuthorizationsForPolstId(@Param("polstId") Integer polstId  );
	
	@Modifying
	@Transactional
	@Query( "delete from PolstAuthorization p where p.polstId = :polstId and p.authorizerType = :authorizationType")
	void deleteAuthorizationsForPolstIdType(@Param("polstId") Integer polstId,@Param("authorizationType") AuthorizationType authorizationType );
 
}
