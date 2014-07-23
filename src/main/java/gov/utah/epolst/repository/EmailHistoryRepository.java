package gov.utah.epolst.repository;

import gov.utah.epolst.model.EmailHistory;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("emailHistoryRepository")
@PersistenceContext(unitName="punit")
public interface EmailHistoryRepository extends JpaRepository<EmailHistory, Integer> {

	@Query("Select h from EmailHistory h where h.polstId = :polstId order by h.dateSent asc")
	List<EmailHistory> getEmailHistoryForPolst(@Param("polstId") Integer polstId);
	
	
}
