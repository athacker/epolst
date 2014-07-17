package gov.utah.polst.repository;

import javax.persistence.PersistenceContext;

import gov.utah.polst.model.PolstUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository("polstUserRepository")
@PersistenceContext(unitName="punit")
public interface PolstUserRepository extends JpaRepository<PolstUser, Integer> {

	
	@Query ("Select u from PolstUser u where u.userName =  ?1")
	public PolstUser findUserByUserName(String userName);
}
