package gov.utah.epolst.service;

 

import gov.utah.epolst.model.Patient;
import gov.utah.epolst.model.Polst;
import gov.utah.epolst.model.PolstAuthorization;
import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.model.PolstUserDetails;
import gov.utah.epolst.model.enums.AuditAction;
import gov.utah.epolst.model.enums.Login;


/**
 * 
 * This manages audit logging
 *
 */
public interface AuditService {
 
	/**
	 * 
	 * @param who
	 * @param action
	 * @param note
	 * @param successFailure
	 * @param licenseNumber
	 * @param firstName
	 * @param lastName
	 * @param phone
	 * @return
	 */
	Boolean createLoginAuditRow(  PolstUserDetails who, AuditAction  action, String note, Login successFailure, String licenseNumber, String firstName, String lastName, String phone);
	/**
	 * 
	 * @param polstUser
	 * @param action
	 * @param note
	 * @param successFailure
	 * @param licenseNumber
	 * @param firstName
	 * @param lastName
	 * @param phone
	 * @return
	 */
	Boolean createLoginAuditRow(  PolstUser polstUser, AuditAction  action, String note, Login successFailure, String licenseNumber, String firstName, String lastName, String phone);
		
	/**
	 * 
	 * @param pt
	 * @return
	 */
	
	Boolean createPatientAuditRow( Patient pt);
	
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	Boolean createPolstAuditRow(Polst p);
	
	/**
	 * 
	 * @param pa
	 * @return
	 */
	Boolean createPolstAuthorizationAuditRow(PolstAuthorization pa);
	
}
