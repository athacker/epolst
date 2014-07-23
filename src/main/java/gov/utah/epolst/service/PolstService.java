package gov.utah.epolst.service;

import gov.utah.epolst.model.AuthorizationBean;
import gov.utah.epolst.model.DiscussionBean;
import gov.utah.epolst.model.Patient;
import gov.utah.epolst.model.PatientBean;
import gov.utah.epolst.model.Polst;
import gov.utah.epolst.model.PolstBean;
import gov.utah.epolst.model.PolstBeanLite;
import gov.utah.epolst.model.enums.AuthorizationType;
import gov.utah.epolst.model.enums.DiscussionType;

import java.util.Collection;



/**
 * Contains methods used to list and update ePOLSTs form
 */
public interface PolstService {
 
	/**polstDetail.jsp **/
	PolstBean getPolstBeanByPolstId(Integer polstId);
	/**polstDetail.jsp **/
	PolstBean createNewEpolst(Integer patientId);
 	
	/**patientDetail page (list in upper corner)**/
	Collection<PolstBeanLite>getPolstHistoryForPatient(Integer patientId);
	
	/**polstDetail -- user might want to in-active/cancel a polst for a patient.**/
	PolstBean cancelPolst(PolstBean polstBean);
	
	/**CODE STATUS, MEDICAL CARE, ANTIBIOTICS, NUTRITION, ADVANCED, MEDICAL CONDITION, AUTHORIZED BY**/
	PolstBean savePolstBean(PolstBean polstBean );
	
	/**DISCUSSION section of ePOLST form**/
	DiscussionBean savePolstDiscussion(DiscussionBean discussionBean)  ;
	void deleteDiscussion(Integer disussionId);
	void deletePatientDiscussion(Integer polstId);
	void deleteDiscussionsForType(Integer polstId, DiscussionType discussionType);
	
	/**AUTHORIZATION section of ePOLST form**/
	AuthorizationBean savePolstAuthorization(AuthorizationBean bean) ;
	void deleteAuthorization(Integer authorizationId);
	void deletePatientAuthorization(Integer polstId);
	void deleteAuthorizationsForType(Integer polstId, AuthorizationType authorizationType);
	
	/**these sections appear when epolst form has been completed.**/
	/**prepare and legal terms are visible for all**/
	/**PREPARE section of ePOLST form -- sets status to PENDING_CERTIFICATION and send email to Physician.**/
	PolstBean prepareEpolst(PolstBean polstBean); 
	/**
	 * 
	 * @param polstId
	 */
	void sendReminderEmail(Integer polstId);
	/**LEGAL TERMS 2 check boxes@todo isn't wired in, waiting customer feedback**/
	PolstBean saveLegalTerms(PolstBean polstBean);
 	/**CERTIFY only available to physicians, sets form to ACTIVE**/
	PolstBean certifyEpolst(PolstBean polstBean);   
	     
	 /**helper method to convert patient to a bean for better ajax transport**/
     PatientBean convertToPatientBean(Polst polst);
	 /**helper method to convert patient to a bean for better ajax transport**/
     PolstBean convertToBean(Polst p);
     
     
	/**ems view**/
	PolstBean getActivePolstByPatientId(Integer patientId);
	
	
	/**
	 * Methods that need to happen when pt has a date of death.
	 */
	void deactivateDeathOfPatient(Patient pt);
}
