package gov.utah.epolst.repository;

import gov.utah.epolst.model.PolstUser;

import java.util.Collection;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("polstUserRepository")
@PersistenceContext(unitName="punit")
public interface PolstUserRepository extends JpaRepository<PolstUser, Integer>,JpaSpecificationExecutor<PolstUser> {

	
	@Query ("Select u from PolstUser u where u.userName =  ?1 ")
	PolstUser findUserByUserName(String userName);
	
	@Query ("Select u from PolstUser u where u.licenseId =  :licenseId ")
	PolstUser findUserByUserLicenseBadge(@Param("licenseId") String licenseId);
	
	@Query ("Select u from PolstUser u  where u.role.id in(select r.id from Role r where r.roleType ='ROLE_MEDCERT' ) order by u.lastName, u.firstName")
	Collection<PolstUser> getActivePhysicians();
	
	@Query ("Select u from PolstUser u where u.userName in (Select p.physicianUserName from Patient p where p.id = :patientId)")
	PolstUser getPhysicianForPatient(@Param("patientId") Integer patientId);
	
}
