package gov.utah.polst.model;

import gov.utah.polst.model.enums.AuditAction;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name="audit")
public class Audit  extends AbstractBaseEntity  {
	
	private static final long serialVersionUID = 7523882295622776147L;
	
	@Column(name="audit_transaction")
	private String auditTransaction;
	
	@Column(name="updated_by")
    private String updatedBy;
	 
	@Column(name="updated_by_id")
    private long updatedById;
	
	@Column(name="audit_time")
    private Timestamp auditTime;
	
	@Column(name="impacted_table")
    private String impactedTable;
	
	@Column(name="record_id")
    private String recordId;
	
	@Column(name="field_name")
    private String fieldName;
	
	@Column(name="previous_value")
    private String previousValue;
	
	@Column(name="new_value")
    private String newValue;
	
	@Column(name="action")
	@Enumerated(EnumType.STRING)
	private AuditAction action;

	@Column(name="note")
    private String note;
	
	
	

	public String getAuditTransaction() {
		return auditTransaction;
	}

	public void setAuditTransaction(String auditTransaction) {
		this.auditTransaction = auditTransaction;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public long getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(long updatedById) {
		this.updatedById = updatedById;
	}

	public Timestamp getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	public String getImpactedTable() {
		return impactedTable;
	}

	public void setImpactedTable(String impactedTable) {
		this.impactedTable = impactedTable;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(String previousValue) {
		this.previousValue = previousValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public AuditAction getAction() {
		return action;
	}

	public void setAction(AuditAction action) {
		this.action = action;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	
	
	
}
