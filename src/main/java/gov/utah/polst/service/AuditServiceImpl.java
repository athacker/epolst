package gov.utah.polst.service;

import gov.utah.polst.model.Audit;
import gov.utah.polst.model.PolstUserDetails;
import gov.utah.polst.model.enums.AuditAction;
import gov.utah.polst.repository.AuditRepository;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("auditService")
public class AuditServiceImpl implements AuditService {

	@Autowired
	private AuditRepository auditRepository;
	
	@Override
	public String createAuditRow(Date when, PolstUserDetails who, String field,
			String table, String value, String beforeValue, String transaction,
			AuditAction action, String note, String recordId) {
 
		
		Audit audit = new Audit();
		audit.setAction(action);
		audit.setAuditTime( new Timestamp(when.getTime() ));
		audit.setUpdatedBy(who.getUsername() );
		
		
		auditRepository.save(audit);
		return "success";
	}

	
	
	
	
}
