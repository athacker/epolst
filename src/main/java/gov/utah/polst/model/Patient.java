package gov.utah.polst.model;

import gov.utah.polst.model.enums.Gender;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;



@Entity
@Table(name = "patient")
@SuppressWarnings("serial")
@PersistenceContext(unitName="punit")
public class Patient extends AbstractBaseEntity{

	
	@Column(name="gender", nullable=false)
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name="first_name", nullable=false)
	private String firstName;
	
	@Column(name="last_name", nullable=false)
	private String lastName;
	
	@Column(name="dob", nullable=false)
	private Date dob;
	
	@Column(name="physician", nullable=false) //@todo -- needs to relate to either the user or physician table
	private String physician;
	
	@JsonIgnore
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Polst> polst;
	 
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhysician() {
		return physician;
	}

	public void setPhysician(String physician) {
		this.physician = physician;
	}

	public List<Polst> getPolst() {
		return polst;
	}

	public void setPolst(List<Polst> polst) {
		this.polst = polst;
	}

 
	
	
	
	
	
	

}
