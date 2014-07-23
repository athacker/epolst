package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.Gender;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
 


@Entity
@Table(name = "PATIENT_AUD" )
@PersistenceContext(unitName="punit")
public class PatientAud implements Serializable  {

	private static final long serialVersionUID = 7523882295622777788L;
	
	public PatientAud(){}
	public PatientAud(Patient p){
		setUpAuditRecord(p);
	}
	
	@Id
	@SequenceGenerator(schema="HL_POLST", name = "PATIENT_AUD_SEQ", sequenceName="PATIENT_AUD_SEQ", allocationSize=1)
	@GeneratedValue(generator = "PATIENT_AUD_SEQ", strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="patient_id" )
	private Integer patientId;
	
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Set<Patient>patients;
 

	@Column(name="gender" )
	@Enumerated(EnumType.STRING)
	private Gender gender;
 		
	@Column(name="first_name" )
	private String firstName="";
	
	@Column(name="middle_name" )
	private String middleName="";
	
	@Column(name="last_name" )
	private String lastName="";
	
	@Column(name="suffix")
	private String suffix="";
	
	@Column(name="dob" )
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(name="date_of_death")
	@Temporal(TemporalType.DATE)
	private Date dateOfDeath;
	 
	@Column(name = "updated_timestamp")
	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	@Column(name = "updated_user_id")
	private String updatedUserId;

	
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
	
	@Column(name="primary_care_provider") 
	private String primaryCareProvider;
		
	@Column(name = "added_user_id")
	private String addedUserId;
	
	@Column(name = "created_date")
	private Date createdDate;
	 
	private void setUpAuditRecord(Patient p){
	 
		setPatientId(p.getId());
		setDob(p.getDob());
		setDateOfDeath(p.getDateOfDeath());
	    setFirstName(p.getFirstName());
	    setLastName(p.getLastName());
	 	setMiddleName(p.getMiddleName());
	 	setSuffix(p.getSuffix());
	    setGender(p.getGender());
	    
	    setAddressCurrent(p.getAddressCurrent());
	    setCityCurrent(p.getCityCurrent() );
	    setStateCurrent(p.getStateCurrent());
	    
	    setAddressPerm(p.getAddressPerm());
	    setCityPerm(p.getCityPerm());
	    setStatePerm(p.getStatePerm());

	    setPrimaryCareProvider(p.getPrimaryCareProvider());
	    setPrimaryCarePhoneNumber(p.getPrimaryCarePhoneNumber());
	    setPhysicianUserName(p.getPhysicianUserName());
	    setPhysicianPhoneNumber(p.getPhysicianPhoneNumber());
	    
	    
	    setAddedUserId(p.getAddedUserId());
	    setCreatedDate(p.getCreatedDate());
	 	setUpdatedDate(p.getUpdatedDate());
		setUpdatedUserId(p.getUpdatedUserId());
	
	
	
	}
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public Set<Patient> getPatients() {
		return patients;
	}
	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}
	public Gender getGender() {
		return gender;
	}
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
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedUserId() {
		return updatedUserId;
	}
	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAddedUserId() {
		return addedUserId;
	}
	public void setAddedUserId(String addedUserId) {
		this.addedUserId = addedUserId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	 
 
	
 
 
 
	
	
	
	
	
	

}
