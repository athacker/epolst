package gov.utah.polst.service;

 

import gov.utah.polst.model.PolstUserDetails;
import gov.utah.polst.model.enums.AuditAction;

import java.util.Date;

public interface AuditService {
	 public String createAuditRow(Date when,PolstUserDetails who,String field,String table,String value,String beforeValue,String transaction,AuditAction  action,String note,String recordId);
	    
}
