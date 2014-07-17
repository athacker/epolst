package gov.utah.polst.model;

import gov.utah.polst.model.enums.Gender;

import java.util.Date;

public class SearchBean {

	private Gender gender;
	private String lastNameLike;
	private String firstNameLike;
	private Date dateOfBirth;
	private String lastNameEquals;
	private String firstNameEquals;
	
	
	
	
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getLastNameLike() {
		return lastNameLike;
	}
	public void setLastNameLike(String lastNameLike) {
		this.lastNameLike = lastNameLike;
	}
	public String getFirstNameLike() {
		return firstNameLike;
	}
	public void setFirstNameLike(String firstNameLike) {
		this.firstNameLike = firstNameLike;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getLastNameEquals() {
		return lastNameEquals;
	}
	public void setLastNameEquals(String lastNameEquals) {
		this.lastNameEquals = lastNameEquals;
	}
	public String getFirstNameEquals() {
		return firstNameEquals;
	}
	public void setFirstNameEquals(String firstNameEquals) {
		this.firstNameEquals = firstNameEquals;
	}
	
	
	
	
}
