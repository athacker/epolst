package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.AdvancedDirective;
import gov.utah.epolst.model.enums.Antibiotics;
import gov.utah.epolst.model.enums.CodeStatus;
import gov.utah.epolst.model.enums.FeedingTube;
import gov.utah.epolst.model.enums.IvFluids;
import gov.utah.epolst.model.enums.MedicalCare;
import gov.utah.epolst.model.enums.PolstStatus;

import java.beans.Transient;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

 
 
@Entity
@Table(name = "polst")
@SuppressWarnings("serial")
@PersistenceContext(unitName = "punit")
public class Polst extends AbstractBaseEntity {
	
	
	private static final long serialVersionUID = 7523882295622779988L;

	@Id
	@SequenceGenerator(schema="HL_POLST", name = "POLST_SEQ", sequenceName="POLST_SEQ", allocationSize=1)
	@GeneratedValue(generator = "POLST_SEQ", strategy = GenerationType.AUTO)
 	@Column(name="ID" , nullable = false)
	private Integer id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PATIENT_ID")
	@JsonIgnore
	private Patient patient;
	
	@Column(name="PATIENT_ID",   insertable = false, updatable = false)
	private Integer patientId;
	
 	@OneToMany(mappedBy = "polst", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PolstDiscussion> polstDiscussions;
 	
 	@OneToMany(mappedBy = "polst", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PolstAuthorization> polstAuthorizations;

	@Column(name = "polst_status")
	@Enumerated(EnumType.STRING)
	private PolstStatus polstStatus;

	// certification
	@Column(name = "license_provider")
	private String licensedProvider; 
	
	// certification
	@Column(name = "certified_date")
	@Temporal(TemporalType.DATE)
	private Date certifiedDate; 
	
	
	// code status
	@Column(name = "code_status")
	@Enumerated(EnumType.STRING)
	private CodeStatus codeStatus; 

	@Column(name = "code_status_instructions")
	private String codeStatusInstructions;

	@Column(name = "medical_care")
	@Enumerated(EnumType.STRING)
	private MedicalCare medicalCare;

	@Column(name = "medical_care_instructions")
	private String medicalCareInstructions;

	@Column(name = "advanced_directive")
	@Enumerated(EnumType.STRING)
	private AdvancedDirective advancedDirective;
	
	@Column(name = "advanced_directive_complete")
	private Date advancedDirectiveCompleteDate;
	
	@Column(name = "advanced_directive_inst")
	private String advancedDirectiveInstructions;
	
	@Column(name = "antibiotics")
	@Enumerated(EnumType.STRING)
	private Antibiotics antibiotics;

	@Column(name = "antibiotic_instructions")
	private String antibioticInstructions;

	@Column(name = "feeding_tube")
	@Enumerated(EnumType.STRING)
	private FeedingTube feedingTube;

	@Column(name = "iv_fluids")
	@Enumerated(EnumType.STRING)
	private IvFluids ivFluids;

	@Column(name = "nutrition_instructions")
	private String nutritionInstuctions;

	@Column(name = "medical_condition")
	private String medicalCondition;

	@Column(name = "prepared_by")
	private String preparedBy;

	@Column(name = "prepared_date")
	@Temporal(TemporalType.DATE)
	private Date preparedDate;
	
	@Column(name = "status_change_reason")
	private String statusChangeReason;
	

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getLicensedProvider() {
		return licensedProvider;
	}

	public void setLicensedProvider(String licensedProvider) {
		this.licensedProvider = licensedProvider;
	}

	public CodeStatus getCodeStatus() {
		return codeStatus;
	}

	public void setCodeStatus(CodeStatus codeStatus) {
		this.codeStatus = codeStatus;
	}

	public String getCodeStatusInstructions() {
		return codeStatusInstructions;
	}

	public void setCodeStatusInstructions(String codeStatusInstructions) {
		this.codeStatusInstructions = codeStatusInstructions;
	}
	 
	public MedicalCare getMedicalCare() {
		return medicalCare;
	}

	public void setMedicalCare(MedicalCare medicalCare) {
		this.medicalCare = medicalCare;
	}

	public String getMedicalCareInstructions() {
		return medicalCareInstructions;
	}

	public void setMedicalCareInstructions(String medicalCareInstructions) {
		this.medicalCareInstructions = medicalCareInstructions;
	}

	public AdvancedDirective getAdvancedDirective() {
		return advancedDirective;
	}

	public void setAdvancedDirective(AdvancedDirective advancedDirective) {
		this.advancedDirective = advancedDirective;
	}

	public String getAdvancedDirectiveInstructions() {
		return advancedDirectiveInstructions;
	}

	public void setAdvancedDirectiveInstructions(
			String advancedDirectiveInstructions) {
		this.advancedDirectiveInstructions = advancedDirectiveInstructions;
	}

	public Date getAdvancedDirectiveCompleteDate() {
		return advancedDirectiveCompleteDate;
	}

	public void setAdvancedDirectiveCompleteDate(Date advancedDirectiveCompleteDate) {
		this.advancedDirectiveCompleteDate = advancedDirectiveCompleteDate;
	}

	public Antibiotics getAntibiotics() {
		return antibiotics;
	}

	public void setAntibiotics(Antibiotics antibiotics) {
		this.antibiotics = antibiotics;
	}

	public String getAntibioticInstructions() {
		return antibioticInstructions;
	}

	public void setAntibioticInstructions(String antibioticInstructions) {
		this.antibioticInstructions = antibioticInstructions;
	}

	public FeedingTube getFeedingTube() {
		return feedingTube;
	}

	public void setFeedingTube(FeedingTube feedingTube) {
		this.feedingTube = feedingTube;
	}

	public IvFluids getIvFluids() {
		return ivFluids;
	}

	public void setIvFluids(IvFluids ivFluids) {
		this.ivFluids = ivFluids;
	}

	public String getNutritionInstuctions() {
		return nutritionInstuctions;
	}

	public void setNutritionInstuctions(String nutritionInstuctions) {
		this.nutritionInstuctions = nutritionInstuctions;
	}

	public String getMedicalCondition() {
		return medicalCondition;
	}

	public void setMedicalCondition(String medicalCondition) {
		this.medicalCondition = medicalCondition;
	}
	
	public List<PolstAuthorization> getPolstAuthorizations() {
		return polstAuthorizations;
	}

	public void setPolstAuthorizations(List<PolstAuthorization> polstAuthorizations) {
		this.polstAuthorizations = polstAuthorizations;
	}

	public String getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	public Date getPreparedDate() {
		return preparedDate;
	}

	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	public PolstStatus getPolstStatus() {
		return polstStatus;
	}

 
	public List<PolstDiscussion> getPolstDiscussions() {
		return polstDiscussions;
	}

	public void setPolstDiscussions(List<PolstDiscussion> polstDiscussions) {
		this.polstDiscussions = polstDiscussions;
	}

	public void setPolstStatus(PolstStatus polstStatus) {
		this.polstStatus = polstStatus;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	@Transient
	public String getPatientFirst() {
		return patient.getFirstName();
	}

	@Transient
	public String getPatientLast() {
		return patient.getLastName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCertifiedDate() {
		return certifiedDate;
	}

	public void setCertifiedDate(Date certifiedDate) {
		this.certifiedDate = certifiedDate;
	}

	public String getStatusChangeReason() {
		return statusChangeReason;
	}

	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}

	
	
	
}
