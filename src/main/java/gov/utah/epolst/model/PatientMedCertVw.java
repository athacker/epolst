 package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.Gender;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

 
 
	@Entity
	@Table(name = "PATIENT_MEDCERT_VW")
	@PersistenceContext(unitName="punit")
	public class PatientMedCertVw  {
	
		@Id
		@Column(name="polst_id" )
	    private Integer polstId;
		
		@Column(name="patient_id" )
	    private Integer patientId;
		
		@Column(name="polst_status" )
	    private String polstStatus;
		
		@Column(name="gender" )
		@Enumerated(EnumType.STRING)
		private Gender gender;
	 		
		@Column(name="patient_first_name" )
		private String patientFirstName;
		
		@Column(name="middle_name" )
		private String middleName;
		
		@Column(name="patient_last_name" )
		private String patientLastName;
		
		@Column(name="suffix")
		private String suffix;
		
		@Column(name="dob" )
		@Temporal(TemporalType.DATE)
		private Date dob;
		
		@Column(name="date_of_death")
		@Temporal(TemporalType.DATE)
		private Date dateOfDeath;
		
		@Column(name="physician_first_name")
		private String physicianFirstName;
		
		@Column(name="physician_last_name")
		private String physicianLastName;
		
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
		
	 	@Column(name="physician_user_name" ) 
		private String physicianUserName;   
		
		@Column(name="primary_care_provider") 
		private String primaryCareProvider;
		
		@Column(name="created_by") 
		private String createdBy;
		
		@Column(name="CREATED_DATE") 
		private Date createdDate;
		
		@Column(name="LAST_UPDATED") 
		private Date lastUpdatedDate;

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

		public String getPolstStatus() {
			return polstStatus;
		}

		public void setPolstStatus(String polstStatus) {
			this.polstStatus = polstStatus;
		}

		public Gender getGender() {
			return gender;
		}

		public void setGender(Gender gender) {
			this.gender = gender;
		}

		public String getPatientFirstName() {
			return patientFirstName;
		}

		public void setPatientFirstName(String patientFirstName) {
			this.patientFirstName = patientFirstName;
		}

		public String getMiddleName() {
			return middleName;
		}

		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}

		public String getPatientLastName() {
			return patientLastName;
		}

		public void setPatientLastName(String patientLastName) {
			this.patientLastName = patientLastName;
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

		public String getPhysicianFirstName() {
			return physicianFirstName;
		}

		public void setPhysicianFirstName(String physicianFirstName) {
			this.physicianFirstName = physicianFirstName;
		}

		public String getPhysicianLastName() {
			return physicianLastName;
		}

		public void setPhysicianLastName(String physicianLastName) {
			this.physicianLastName = physicianLastName;
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

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public Date getLastUpdatedDate() {
			return lastUpdatedDate;
		}

		public void setLastUpdatedDate(Date lastUpdatedDate) {
			this.lastUpdatedDate = lastUpdatedDate;
		}

 
		
		
		
		
		

	}

 
