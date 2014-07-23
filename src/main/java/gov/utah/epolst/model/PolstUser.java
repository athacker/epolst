package gov.utah.epolst.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "polst_user")
@PersistenceContext(unitName="punit")
public class PolstUser extends AbstractBaseEntity {

	private static final long serialVersionUID = 752388956228866777L;
 	public PolstUser() {
		super();
	}
	
	public PolstUser(String name, Role aRole) {
		userName = name;
		role = aRole;
	}
	
	@Id
	@SequenceGenerator(schema="HL_POLST", name = "POLST_USER_SEQ", sequenceName="POLST_USER_SEQ", allocationSize=1)
	@GeneratedValue(generator = "POLST_USER_SEQ", strategy = GenerationType.AUTO)
 	@Column(name="ID" , nullable = false)
	private Integer id;
	

	@Column(name = "email" )
	private String email;

	@Column(name = "user_name", nullable = false)
	private String userName;
	

 

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

 
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="license_id")
	private String licenseId;
	
	@Column(name="state_licensed")
	private String stateLicensed;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="active", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean active;
	
	@Column(name="verified_system",nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean verfiedSystem;
	
	@Column(name="verified_manual",nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean verfiedManual;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
 

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

 	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getStateLicensed() {
		return stateLicensed;
	}

	public void setStateLicensed(String stateLicensed) {
		this.stateLicensed = stateLicensed;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	 
	public Boolean getVerfiedSystem() {
		return verfiedSystem;
	}

	public void setVerfiedSystem(Boolean verfiedSystem) {
		this.verfiedSystem = verfiedSystem;
	}

	public Boolean getVerfiedManual() {
		return verfiedManual;
	}

	public void setVerfiedManual(Boolean verfiedManual) {
		this.verfiedManual = verfiedManual;
	}
	
	@Transient
	public String getDisplayName() {
		StringBuffer sb = new StringBuffer();
		sb.append(getLastName());
		sb.append(", ");
		sb.append(getFirstName());
		
		return sb.toString();
	}
	 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
 
	
	
	
	
	
}
