package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.Gender;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
 
 

//@Audited 
@Entity
@Table(name = "PATIENT" )
@PersistenceContext(unitName="punit")
public class Patient implements Serializable  {

	private static final long serialVersionUID = 7523882295622777788L;
	
	@Id
	@SequenceGenerator(schema="HL_POLST", name = "PT_SEQ", sequenceName="PT_SEQ", allocationSize=1)
	@GeneratedValue(generator = "PT_SEQ", strategy = GenerationType.AUTO)
	private Integer id;
	
	
	@Column(name="gender" )
	@Enumerated(EnumType.STRING)
	private Gender gender;
 		
	@Column(name="first_name" )
	private String firstName;
	
	@Column(name="middle_name" )
	private String middleName;
	
	@Column(name="last_name" )
	private String lastName;
	
	@Column(name="suffix")
	private String suffix;
	
	@Column(name="dob" )
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(name="date_of_death")
	@Temporal(TemporalType.DATE)
	private Date dateOfDeath;
	
	@Column(name="patient_phone")
	private String patientPhoneNumber;
	
	@Column(name="physician_phone_number")
	private String physicianPhoneNumber;
	
	@Column(name="primary_care_phone_number")
	private String primaryCarePhoneNumber;
	
	@Column(name="address_perm")
	private String addressPerm;
	
	@Column(name="city_perm")
	private String cityPerm;
	
	@Column(name="state_perm")
	private String statePerm;
	
	@Column(name="zip_perm")
	private String zipPerm;
	
	@Column(name="address_current")
	private String addressCurrent;
	
	@Column(name="city_current")
	private String cityCurrent;
	
	@Column(name="state_current")
	private String stateCurrent;
	
	@Column(name="zip_current")
	private String zipCurrent;
	 
	//certified physician ->relates to polst_user.user_name 
	@Column(name="physician_user_name", nullable=false) 
	private String physicianUserName;
	
	@Column(name="physician_name" ) 
	private String physicianFullName;
	
	@Column(name="primary_care_provider") 
	private String primaryCareProvider;
	
	@JsonIgnore
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Polst> polst;


	@Column(name = "updated_timestamp")
	private Date updatedDate;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "added_user_id")
	private String addedUserId;
	
	@Column(name = "creator_name")
	private String creatorName;

	@Column(name = "updated_user_id")
	private String updatedUserId;
	
	@Column(name = "gender")
	public Gender getGender() {
		return gender;
	}
	
	@Transient
	public String fullName;

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public String getPhysicianPhoneNumber() {
		return physicianPhoneNumber;
	}

	public void setPhysicianPhoneNumber(String physicianPhoneNumber) {
		this.physicianPhoneNumber = physicianPhoneNumber;
	}

	public String getPrimaryCarePhoneNumber() {
		return primaryCarePhoneNumber;
	}

	public void setPrimaryCarePhoneNumber(String primaryCarePhoneNumber) {
		this.primaryCarePhoneNumber = primaryCarePhoneNumber;
	}

	public String getAddressPerm() {
		return addressPerm;
	}

	public void setAddressPerm(String addressPerm) {
		this.addressPerm = addressPerm;
	}

	public String getCityPerm() {
		return cityPerm;
	}

	public void setCityPerm(String cityPerm) {
		this.cityPerm = cityPerm;
	}

	public String getStatePerm() {
		return statePerm;
	}

	public void setStatePerm(String statePerm) {
		this.statePerm = statePerm;
	}

	public String getZipPerm() {
		return zipPerm;
	}

	public void setZipPerm(String zipPerm) {
		this.zipPerm = zipPerm;
	}

	public String getAddressCurrent() {
		return addressCurrent;
	}

	public void setAddressCurrent(String addressCurrent) {
		this.addressCurrent = addressCurrent;
	}

	public String getCityCurrent() {
		return cityCurrent;
	}

	public void setCityCurrent(String cityCurrent) {
		this.cityCurrent = cityCurrent;
	}

	public String getStateCurrent() {
		return stateCurrent;
	}

	public void setStateCurrent(String stateCurrent) {
		this.stateCurrent = stateCurrent;
	}

	public String getZipCurrent() {
		return zipCurrent;
	}

	public void setZipCurrent(String zipCurrent) {
		this.zipCurrent = zipCurrent;
	}

	public String getPhysicianUserName() {
		return physicianUserName;
	}

	public void setPhysicianUserName(String physicianUserName) {
		this.physicianUserName = physicianUserName;
	}

	public String getPrimaryCareProvider() {
		return primaryCareProvider;
	}

	public void setPrimaryCareProvider(String primaryCareProvider) {
		this.primaryCareProvider = primaryCareProvider;
	}

	public List<Polst> getPolst() {
		return polst;
	}

	public void setPolst(List<Polst> polst) {
		this.polst = polst;
	}

   
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAddedUserId() {
		return addedUserId;
	}

	public void setAddedUserId(String addedUserId) {
		this.addedUserId = addedUserId;
	}

	public String getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public String getFullName() {
		StringBuilder sb = new StringBuilder(getLastName());
		sb.append(", ");
		sb.append(getFirstName());
		sb.append(" ");
		sb.append( (getMiddleName() == null) ? "" : getMiddleName() );
		sb.append(" ");
		sb.append(  (getSuffix() == null) ? "" : getSuffix() );
		return sb.toString()  ;
	}

	public void setFullName(String fullName) {
		StringBuilder sb = new StringBuilder(getLastName());
		sb.append(", ");
		sb.append(getFirstName());
		sb.append(" ");
		sb.append( (getMiddleName() == null) ? "" : getMiddleName() );
		sb.append(" ");
		sb.append(  (getSuffix() == null) ? "" : getSuffix() );
		this.fullName = sb.toString();
	}

	public String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}

	public void setPatientPhoneNumber(String patientPhoneNumber) {
		this.patientPhoneNumber = patientPhoneNumber;
	}

	public String getPhysicianFullName() {
		return physicianFullName;
	}

	public void setPhysicianFullName(String physicianFullName) {
		this.physicianFullName = physicianFullName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	 
	
 
	
	 

 
	
 
 
 
	
	
	
	
	
	

}
