package gov.utah.polst.repository;

import gov.utah.polst.model.Role;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("roleRepository")
@PersistenceContext(unitName="punit")
public interface RoleRepository extends JpaRepository<Role, Integer> {

	
	 
}
