package gov.utah.epolst.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
@Table(name = "EMAIL_HISTORY")
public class EmailHistory{
	
	@Id
	@SequenceGenerator(schema="HL_POLST", name = "EMAIL_HISTORY_SEQ", sequenceName="EMAIL_HISTORY_SEQ", allocationSize=1)
	@GeneratedValue(generator = "EMAIL_HISTORY_SEQ", strategy = GenerationType.AUTO)
 	@Column(name="ID" , nullable = false)
	private Integer id;
	 
	@Column(name="POLST_ID")
	private Integer polstId;
	
	@Column(name="PATIENT_ID")
	private Integer patientId;
	
	@Column(name="EMAIL_TEMPLATE_ID")
	private Integer templateId;
	
	@Column(name="REQUESTER_USER_ID")
	private String requesterUserId;
	
	@Column(name="RECEIVER_USER_ID")
	private String receiverUserId;
	
	@Column(name="DATE_EMAIL_SENT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSent;

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPolstId() {
		return polstId;
	}

	public void setPolstId(Integer polstId) {
		this.polstId = polstId;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getRequesterUserId() {
		return requesterUserId;
	}

	public void setRequesterUserId(String requesterUserId) {
		this.requesterUserId = requesterUserId;
	}

	public String getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	
	
	
	
	
	
}
