package gov.utah.epolst.repository;

import gov.utah.epolst.model.EmailTemplate;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("emailTemplateRepository")
@PersistenceContext(unitName="punit")
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Integer> {

	 @Query("Select et from EmailTemplate et where et.templateName = :templateName")
	 EmailTemplate findTemplateByName(@Param("templateName") String templateName);
	 
}
