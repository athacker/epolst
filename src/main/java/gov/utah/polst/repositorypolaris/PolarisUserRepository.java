package gov.utah.polst.repositorypolaris;

import javax.persistence.PersistenceContext;

import gov.utah.polst.model.PolarisUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository("polarisUserRepository")
@PersistenceContext(unitName="punitPolaris")
public interface PolarisUserRepository extends JpaRepository<PolarisUser, Integer> {

	
	@Query ("Select u from PolarisUser u where u.userName =  ?1")
	public PolarisUser findPolarisUserByUserName(String userName);
}
