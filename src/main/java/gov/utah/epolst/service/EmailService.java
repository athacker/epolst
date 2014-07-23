package gov.utah.epolst.service;

import gov.utah.epolst.model.EmailHistory;
import gov.utah.epolst.model.EmailHistoryBean;
import gov.utah.epolst.model.Polst;

import java.util.List;

/**
 * 
 * Email Service Methods
 *
 */
public interface EmailService {
 
	 /**
	  * Send eMAIL to physician 
	  * @param recipients
	  * @return
	  */
	 Boolean sendPolstPendingNotification(Polst polst, String loggedInUser );
	 
	 
	 /**
	  * Returns a EmailHistory list for a POLST.
	  * @param polstId
	  * @return
	  */
	 List<EmailHistoryBean> getEmailHistoryForPolst(Integer polstId);
	 
}
