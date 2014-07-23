package gov.utah.epolst.model;

import java.util.Date;

public class SearchParameters  {

 
	private String addedUserId;
	private String currentPage;
	private Date dateOfBirth;
	private String firstNameEquals;
	private String firstNameLike;
	private String lastNameEquals;
	private String lastNameLike;
	private String maxRecordsPerPage;
	private String physicianUserName;
	private String polstStatus;
	public String getAddedUserId() {
		return addedUserId;
	}
	public void setAddedUserId(String addedUserId) {
		this.addedUserId = addedUserId;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
 
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getFirstNameEquals() {
		return firstNameEquals;
	}
	public void setFirstNameEquals(String firstNameEquals) {
		this.firstNameEquals = firstNameEquals;
	}
	public String getFirstNameLike() {
		return firstNameLike;
	}
	public void setFirstNameLike(String firstNameLike) {
		this.firstNameLike = firstNameLike;
	}
	public String getLastNameEquals() {
		return lastNameEquals;
	}
	public void setLastNameEquals(String lastNameEquals) {
		this.lastNameEquals = lastNameEquals;
	}
	public String getLastNameLike() {
		return lastNameLike;
	}
	public void setLastNameLike(String lastNameLike) {
		this.lastNameLike = lastNameLike;
	}
	public String getMaxRecordsPerPage() {
		return maxRecordsPerPage;
	}
	public void setMaxRecordsPerPage(String maxRecordsPerPage) {
		this.maxRecordsPerPage = maxRecordsPerPage;
	}
	public String getPhysicianUserName() {
		return physicianUserName;
	}
	public void setPhysicianUserName(String physicianUserName) {
		this.physicianUserName = physicianUserName;
	}
	public String getPolstStatus() {
		return polstStatus;
	}
	public void setPolstStatus(String polstStatus) {
		this.polstStatus = polstStatus;
	}
	
	
	 
 
 
	
	
	
}
