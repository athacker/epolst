package gov.utah.epolst.repository;

import gov.utah.epolst.model.Role;
import gov.utah.epolst.model.enums.RoleType;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("roleRepository")
@PersistenceContext(unitName="punit")
public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query ("Select r from Role r where r.roleType = :roleType")
	public Role getRoleByType(@Param("roleType") RoleType roleType);
	 
}
