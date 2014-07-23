package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.AuditAction;
import gov.utah.epolst.model.enums.Login;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="login_aud")
public class LoginAudit    {
	
	private static final long serialVersionUID = 7523882295622776147L;
		
	@Id
	@SequenceGenerator(schema="HL_POLST", name = "LOGIN_AUD_SEQ", sequenceName="LOGIN_AUD_SEQ", allocationSize=1)
	@GeneratedValue(generator = "LOGIN_AUD_SEQ", strategy = GenerationType.AUTO)
 	@Column(name="ID" , nullable = false)
	private Integer id;
	
	@Column(name="umd_login_id")
	private String umdLoginId;
	
	@Column(name="action")
	@Enumerated(EnumType.STRING)
	private AuditAction action;
	 	
	@Column(name="user_ip")
	private String userIp;
	
	@Column(name="user_role")
	private String userRole;
	
	@Column(name="license_number")
	private String licenseNumber;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="success_failure")
	@Enumerated(EnumType.STRING)
	private Login loginSuccessFailure;
	
	@Column(name="audit_time",  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
    private Date auditTime;
  
	@Column(name="note")
    private String note;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUmdLoginId() {
		return umdLoginId;
	}

	public void setUmdLoginId(String umdLoginId) {
		this.umdLoginId = umdLoginId;
	}

	public AuditAction getAction() {
		return action;
	}

	public void setAction(AuditAction action) {
		this.action = action;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

 	public Login getLoginSuccessFailure() {
		return loginSuccessFailure;
	}

	public void setLoginSuccessFailure(Login loginSuccessFailure) {
		this.loginSuccessFailure = loginSuccessFailure;
	}

 

 

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
 
	
	
}
