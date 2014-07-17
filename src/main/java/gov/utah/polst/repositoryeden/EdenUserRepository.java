package gov.utah.polst.repositoryeden;

import javax.persistence.PersistenceContext;

import gov.utah.polst.model.EdenUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository("edenUserRepository")
@PersistenceContext(unitName="punitEden")
public interface EdenUserRepository extends JpaRepository<EdenUser, Integer> {

	
	@Query ("Select u from EdenUser u where u.userName =  ?1")
	public EdenUser findEdenUserByUserName(String email);
}
