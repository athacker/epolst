package gov.utah.epolst.service;

 
import gov.utah.epolst.model.LoginAudit;
import gov.utah.epolst.model.Patient;
import gov.utah.epolst.model.PatientAud;
import gov.utah.epolst.model.Polst;
import gov.utah.epolst.model.PolstAud;
import gov.utah.epolst.model.PolstAuthorization;
import gov.utah.epolst.model.PolstAuthorizationAud;
import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.model.PolstUserDetails;
import gov.utah.epolst.model.enums.AuditAction;
import gov.utah.epolst.model.enums.Login;
import gov.utah.epolst.repository.LoginAuditRepository;
import gov.utah.epolst.repository.PatientAudRepository;
import gov.utah.epolst.repository.PolstAudRepository;
import gov.utah.epolst.repository.PolstAuthorizationAudRepository;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * This manages Login and Log out saves to the Audit table
 *
 */

@Service("auditService")
public class AuditServiceImpl implements AuditService {

	@Autowired private LoginAuditRepository loginAuditRepository;
	@Autowired private PatientAudRepository patientAudRepository;
	@Autowired private PolstAudRepository polstAudRepository;
	@Autowired private PolstAuthorizationAudRepository polstAuthorizationAudRepository;
	@Autowired private SecurityService securityService;
	
	private static Logger LOG = LoggerFactory.getLogger( AuditService.class);
	
	/*
	 * (non-Javadoc)
	 * @see gov.utah.epolst.service.AuditService#createNewUserLoginAuditRow(gov.utah.epolst.model.PolstUserDetails, gov.utah.epolst.model.enums.AuditAction, java.lang.String, gov.utah.epolst.model.enums.Login, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public Boolean createLoginAuditRow(  PolstUserDetails who, AuditAction  action, String note, Login loginSuccessFailure, String licenseNumber, String firstName, String lastName, String phone){
		Boolean auditRowCreated = Boolean.TRUE;
		try{
			LoginAudit audit = new LoginAudit();
			audit.setAction(action);
			audit.setLoginSuccessFailure(loginSuccessFailure);
			audit.setNote(note);
			audit.setUmdLoginId(who.getUsername());
			audit.setUserIp(securityService.getUserIPAddress());
			audit.setUserRole(who.getAuthorities().iterator().next().getAuthority() );
			audit.setLicenseNumber(licenseNumber);
			audit.setPhone(phone);
			audit.setFirstName(firstName);
			audit.setLastName(lastName);
			audit.setAuditTime(new Date());
			loginAuditRepository.save(audit);
		}catch(Exception e)	{
			LOG.error("Exception was caught creating a LoginAudit record.", e);
			auditRowCreated = Boolean.FALSE;
		}
		 
		return auditRowCreated;
	}
	/**
	 * Creates a login audit record
	 */
	public Boolean createLoginAuditRow(  PolstUser user, AuditAction  action, String note, Login successFailure, String licenseNumber, String firstName, String lastName, String phone){
		
		Boolean auditRowCreated = Boolean.TRUE;
		try{
		 	LoginAudit audit = new LoginAudit();
			audit.setAction(action);
			audit.setLoginSuccessFailure( successFailure);
			audit.setNote(note);
			audit.setUmdLoginId(user.getUserName());
			audit.setUserIp(securityService.getUserIPAddress());
			audit.setUserRole(user.getRole().getRoleType().toString() );
			audit.setLicenseNumber(licenseNumber);
			audit.setPhone(phone);
			audit.setFirstName(firstName);
			audit.setLastName(lastName);
			audit.setAuditTime(new Date());
			loginAuditRepository.save(audit);
		}catch(Exception e)	{
			LOG.error("Exception was caught creating a LoginAudit record.", e);
			auditRowCreated = Boolean.FALSE;
		}
		 
		return auditRowCreated;
	}
 
	@Override
	public Boolean createPatientAuditRow( Patient patient){
		Boolean auditRowCreated = Boolean.TRUE;
		PatientAud p = new PatientAud( patient);
		patientAudRepository.save(p);
		return auditRowCreated;
	}
	
	
	
	@Override
	public Boolean createPolstAuditRow(Polst polst) {
		Boolean auditRowCreated = Boolean.TRUE;
		PolstAud p = new PolstAud( polst);
		polstAudRepository.save(p);
		return auditRowCreated;
	}
	@Override
	public Boolean createPolstAuthorizationAuditRow(PolstAuthorization pa) {
		Boolean auditRowCreated = Boolean.TRUE;
		PolstAuthorizationAud polstAuthorizationAud = new PolstAuthorizationAud(pa);
		polstAuthorizationAudRepository.save(polstAuthorizationAud);
		return auditRowCreated;
	}
	
	
	
	
	
	
	
	
	
	
	
	}
	
	
	
	
	
	
	
 
