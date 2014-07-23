package gov.utah.epolst.model;

 
 /**
  *  BEMS.EPOLST_EMT_VW DB LINK  
  *  CREATE SYNONYM DOPL_VIEW FOR lcps.v_epolst@L2_EPOLST;
  */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "EPOLST_EMT_VW")
public class EmtView{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ems_id")
	private String emsId;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
 	
	@Column(name="homephone")
	private String homePhone;
	
	@Column(name="mobilephone")
	private String mobilePhone;
	
	@Column(name="workphone")
	private String workPhone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="state")
	private String state;

	public String getEmsId() {
		return emsId;
	}

	public void setEmsId(String emsId) {
		this.emsId = emsId;
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

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	

	 
	
	
	
}
