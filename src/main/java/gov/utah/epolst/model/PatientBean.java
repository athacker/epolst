package gov.utah.epolst.model;

import java.util.List;


public class PatientBean {

	private String addressCurrent;
	private String addressPerm;
	private String cityCurrent;
	private String cityPerm;
    private String createdBy;
	private int currentPolstId;
	//enum value
	private String currentPolstStatus;
	private String dateOfDeath;
	private String dob;
	private String firstName;
	//leave fullName here --otherwise it breaks ajax/jason saves!!
	private String fullName;
	private String gender;
	private String lastName;
	private String middleName="";
	private String patientId;
	private String patientPhoneNumber="";
	private List<PolstBeanLite>polstHistory;
	private String primaryCarePhysicianPhoneNumber; 
	//polst_user.user_name critical field used in many queries!
	private String physicianUserName;
	//full name for display
	private String physicianFullName;
	private String physicianPhoneNumber; 
	private String polstEnteredDate;
	private String polstLastUpdatedDate;
	//display label
	private String polstStatus;
	private String primaryCareProvider;
	private String stateCurrent;
	private String statePerm;
	private String suffix;
	private String zipCurrent;
	private String zipPerm;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  firstName: ");
		sb.append(firstName);
		sb.append("  lastName: ");
		sb.append(lastName);
		sb.append("  dob: ");
		sb.append(dob);
		sb.append("  gender: ");
		sb.append(gender);
		sb.append("  addressCurrent ");
		sb.append(addressCurrent);
		sb.append("  cityCurrent: ");
		sb.append(cityCurrent);
		sb.append("  stateCurrent: ");
		sb.append(stateCurrent);
		sb.append("  zipCurrent: ");
		sb.append(zipCurrent);
		sb.append("  physician user name: ");
		sb.append(physicianUserName);
		sb.append("  primaryCareProvider: ");
		sb.append(primaryCareProvider);
		sb.append("  cityPerm: ");
		sb.append(cityPerm);
		sb.append("  statePerm: ");
		sb.append(statePerm);
		sb.append("  zipPerm: ");
		sb.append(zipPerm);

		return sb.toString();
	}

	public String getAddressCurrent() {
		return addressCurrent;
	}

	public void setAddressCurrent(String addressCurrent) {
		this.addressCurrent = addressCurrent;
	}

	public String getAddressPerm() {
		return addressPerm;
	}

	public void setAddressPerm(String addressPerm) {
		this.addressPerm = addressPerm;
	}

	public String getCityCurrent() {
		return cityCurrent;
	}

	public void setCityCurrent(String cityCurrent) {
		this.cityCurrent = cityCurrent;
	}

	public String getCityPerm() {
		return cityPerm;
	}

	public void setCityPerm(String cityPerm) {
		this.cityPerm = cityPerm;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public int getCurrentPolstId() {
		return currentPolstId;
	}

	public void setCurrentPolstId(int currentPolstId) {
		this.currentPolstId = currentPolstId;
	}

	public String getCurrentPolstStatus() {
		return currentPolstStatus;
	}

	public void setCurrentPolstStatus(String currentPolstStatus) {
		this.currentPolstStatus = currentPolstStatus;
	}

	public String getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFullName() {
		StringBuilder sb = new StringBuilder (getFirstName() );
		sb.append(" ");
		sb.append(getLastName() );
		setFullName(sb.toString() );
		return sb.toString();
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public List<PolstBeanLite> getPolstHistory() {
		return polstHistory;
	}

	public void setPolstHistory(List<PolstBeanLite> polstHistory) {
		this.polstHistory = polstHistory;
	}

	public String getPrimaryCarePhysicianPhoneNumber() {
		return primaryCarePhysicianPhoneNumber;
	}

	public void setPrimaryCarePhysicianPhoneNumber(
			String primaryCarePhysicianPhoneNumber) {
		this.primaryCarePhysicianPhoneNumber = primaryCarePhysicianPhoneNumber;
	}

	public String getPhysicianUserName() {
		return physicianUserName;
	}

	public void setPhysicianUserName(String physicianUserName) {
		this.physicianUserName = physicianUserName;
	}

	public String getPhysicianFullName() {
		return physicianFullName;
	}

	public void setPhysicianFullName(String physicianFullName) {
		this.physicianFullName = physicianFullName;
	}

	public String getPhysicianPhoneNumber() {
		return physicianPhoneNumber;
	}

	public void setPhysicianPhoneNumber(String physicianPhoneNumber) {
		this.physicianPhoneNumber = physicianPhoneNumber;
	}

	public String getPolstEnteredDate() {
		return polstEnteredDate;
	}

	public void setPolstEnteredDate(String polstEnteredDate) {
		this.polstEnteredDate = polstEnteredDate;
	}

	public String getPolstLastUpdatedDate() {
		return polstLastUpdatedDate;
	}

	public void setPolstLastUpdatedDate(String polstLastUpdatedDate) {
		this.polstLastUpdatedDate = polstLastUpdatedDate;
	}

	public String getPolstStatus() {
		return polstStatus;
	}

	public void setPolstStatus(String polstStatus) {
		this.polstStatus = polstStatus;
	}

	public String getPrimaryCareProvider() {
		return primaryCareProvider;
	}

	public void setPrimaryCareProvider(String primaryCareProvider) {
		this.primaryCareProvider = primaryCareProvider;
	}

	public String getStateCurrent() {
		return stateCurrent;
	}

	public void setStateCurrent(String stateCurrent) {
		this.stateCurrent = stateCurrent;
	}

	public String getStatePerm() {
		return statePerm;
	}

	public void setStatePerm(String statePerm) {
		this.statePerm = statePerm;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getZipCurrent() {
		return zipCurrent;
	}

	public void setZipCurrent(String zipCurrent) {
		this.zipCurrent = zipCurrent;
	}

	public String getZipPerm() {
		return zipPerm;
	}

	public void setZipPerm(String zipPerm) {
		this.zipPerm = zipPerm;
	}

	public String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}

	public void setPatientPhoneNumber(String patientPhoneNumber) {
		this.patientPhoneNumber = patientPhoneNumber;
	}

	 
	
	
	
	
}
