package gov.utah.epolst.model;

 
 /**
  *  This is written to a view that represents a data link from DOPL
  *  CREATE SYNONYM DOPL_VIEW FOR lcps.v_epolst@L2_EPOLST;
  */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "DOPL_VIEW")
public class DoplView{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="license_no")
	private String licenseNumber;
	
	
	@Column(name="profession_name")
	private String professionName;
	
	@Column(name="license_name")
	private String licenseName;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="addr_state")
	private String state;
	
	@Column(name="addr_email")
	private String email;
	
	@Column(name="addr_phone")
	private String phone;

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	
	
	
	
}
