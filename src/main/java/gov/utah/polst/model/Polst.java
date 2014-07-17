package gov.utah.polst.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

@Entity
@Table(name = "polst")
@SuppressWarnings("serial")
@PersistenceContext(unitName = "punit")
public class Polst extends AbstractBaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PATIENT_ID")
	private Patient patient;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "effective_date")
	private Date effectiveDate;

	@Column(name = "physician_name")
	private String physicianName; 

	@Column(name = "physician_phone")
	private String physicianPhone; 
	
	@Column(name="recusitate_no_breathing_no_pulse")
    private Boolean recusitateNoBreathingNoPulse;
	
	@Column(name="instructions_no_breathing_no_pulse")
	private String instructionsNoBreathingNoPulse;
	
	@Column(name="instructions_breathing_pulse")
	private String instructionsBreathingPulse;

	@Column(name="instructions_full_treatment")
	private String instructionsFullTreatment;
	
	@Column(name="administer_antibiotics")
	private Boolean administerAntibiotics;

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getPhysicianName() {
		return physicianName;
	}

	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}

	public String getPhysicianPhone() {
		return physicianPhone;
	}

	public void setPhysicianPhone(String physicianPhone) {
		this.physicianPhone = physicianPhone;
	}

	public Boolean getRecusitateNoBreathingNoPulse() {
		return recusitateNoBreathingNoPulse;
	}

	public void setRecusitateNoBreathingNoPulse(Boolean recusitateNoBreathingNoPulse) {
		this.recusitateNoBreathingNoPulse = recusitateNoBreathingNoPulse;
	}

	public String getInstructionsNoBreathingNoPulse() {
		return instructionsNoBreathingNoPulse;
	}

	public void setInstructionsNoBreathingNoPulse(
			String instructionsNoBreathingNoPulse) {
		this.instructionsNoBreathingNoPulse = instructionsNoBreathingNoPulse;
	}

	public String getInstructionsBreathingPulse() {
		return instructionsBreathingPulse;
	}

	public void setInstructionsBreathingPulse(String instructionsBreathingPulse) {
		this.instructionsBreathingPulse = instructionsBreathingPulse;
	}

	public String getInstructionsFullTreatment() {
		return instructionsFullTreatment;
	}

	public void setInstructionsFullTreatment(String instructionsFullTreatment) {
		this.instructionsFullTreatment = instructionsFullTreatment;
	}

	public Boolean getAdministerAntibiotics() {
		return administerAntibiotics;
	}

	public void setAdministerAntibiotics(Boolean administerAntibiotics) {
		this.administerAntibiotics = administerAntibiotics;
	}
	
	
	
	
	
}
