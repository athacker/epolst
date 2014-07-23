package gov.utah.epolst.model;

import java.util.ArrayList;
import java.util.List;


public class PolstBean {
	
	private String advancedDirective;
	private String advancedDirectiveCompleteDate;
	private String advancedDirectiveInstructions;
	private String antibioticInstructions;
	private String antibiotics;
	private String codeStatus;
	private String codeStatusInstructions;
	private String codeStatusTitle;
	private String createdByUserName;
	private String feedingTube;
	private String ivFluids;
	private String licensedProvider;
	private String certifiedDate;
	private String medicalCare;
	private String medicalCareInstructions;
	private String medicalCareTitle;
	private String medicalCondition;
	private String nutritionInstuctions;
	private boolean patientDiscussed;
	private boolean patientAuthorized;
	private String patientId;
	private String physicianUserName;
	private String polstId;
	private String preparedBy;
	private String preparedDate;
	private SectionStatus sectionStatus;
	private String status;
	private String statusDescription;
	private String statusChangeReason;
	private List<DiscussionBean>otherDiscussions=new ArrayList<DiscussionBean>();
	private List<DiscussionBean>parentDiscussions= new ArrayList<DiscussionBean>();
	private List<DiscussionBean>surrogateDiscussions= new ArrayList<DiscussionBean>();
	private List<AuthorizationBean>surrogateAuthorizations=new ArrayList<AuthorizationBean>();
	private List<AuthorizationBean>parentAuthorizations=new ArrayList<AuthorizationBean>();
	
	
	
	public String getCodeStatusTitle() {
		return codeStatusTitle;
	}
	public void setCodeStatusTitle(String codeStatusTitle) {
		this.codeStatusTitle = codeStatusTitle;
	}
	public String getMedicalCareTitle() {
		return medicalCareTitle;
	}
	public void setMedicalCareTitle(String medicalCareTitle) {
		this.medicalCareTitle = medicalCareTitle;
	}
	public String getAdvancedDirective() {
		return advancedDirective;
	}
	public void setAdvancedDirective(String advancedDirective) {
		this.advancedDirective = advancedDirective;
	}
	public String getAdvancedDirectiveCompleteDate() {
		return advancedDirectiveCompleteDate;
	}
	public void setAdvancedDirectiveCompleteDate(
			String advancedDirectiveCompleteDate) {
		this.advancedDirectiveCompleteDate = advancedDirectiveCompleteDate;
	}
	public String getAdvancedDirectiveInstructions() {
		return advancedDirectiveInstructions;
	}
	public void setAdvancedDirectiveInstructions(
			String advancedDirectiveInstructions) {
		this.advancedDirectiveInstructions = advancedDirectiveInstructions;
	}
	public String getAntibioticInstructions() {
		return antibioticInstructions;
	}
	public void setAntibioticInstructions(String antibioticInstructions) {
		this.antibioticInstructions = antibioticInstructions;
	}
	public String getAntibiotics() {
		return antibiotics;
	}
	public void setAntibiotics(String antibiotics) {
		this.antibiotics = antibiotics;
	}
	public String getCodeStatus() {
		return codeStatus;
	}
	public void setCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
	}
	public String getCodeStatusInstructions() {
		return codeStatusInstructions;
	}
	public void setCodeStatusInstructions(String codeStatusInstructions) {
		this.codeStatusInstructions = codeStatusInstructions;
	}
	public String getFeedingTube() {
		return feedingTube;
	}
	public void setFeedingTube(String feedingTube) {
		this.feedingTube = feedingTube;
	}
	public String getIvFluids() {
		return ivFluids;
	}
	public void setIvFluids(String ivFluids) {
		this.ivFluids = ivFluids;
	}
	public String getLicensedProvider() {
		return licensedProvider;
	}
	public void setLicensedProvider(String licensedProvider) {
		this.licensedProvider = licensedProvider;
	}
	public String getMedicalCare() {
		return medicalCare;
	}
	public void setMedicalCare(String medicalCare) {
		this.medicalCare = medicalCare;
	}
	public String getMedicalCareInstructions() {
		return medicalCareInstructions;
	}
	public void setMedicalCareInstructions(String medicalCareInstructions) {
		this.medicalCareInstructions = medicalCareInstructions;
	}
	public String getMedicalCondition() {
		return medicalCondition;
	}
	public void setMedicalCondition(String medicalCondition) {
		this.medicalCondition = medicalCondition;
	}
	public String getNutritionInstuctions() {
		return nutritionInstuctions;
	}
	public void setNutritionInstuctions(String nutritionInstuctions) {
		this.nutritionInstuctions = nutritionInstuctions;
	}
	public List<DiscussionBean> getOtherDiscussions() {
		return otherDiscussions;
	}
	public void setOtherDiscussions(List<DiscussionBean> otherDiscussions) {
		this.otherDiscussions = otherDiscussions;
	}
	public List<AuthorizationBean> getParentAuthorizations() {
		return parentAuthorizations;
	}
	public void setParentAuthorizations(List<AuthorizationBean> parentAuthorizations) {
		this.parentAuthorizations = parentAuthorizations;
	}
	public List<DiscussionBean> getParentDiscussions() {
		return parentDiscussions;
	}
	public void setParentDiscussions(List<DiscussionBean> parentDiscussions) {
		this.parentDiscussions = parentDiscussions;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getPolstId() {
		return polstId;
	}
	public void setPolstId(String polstId) {
		this.polstId = polstId;
	}
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}
	public String getPreparedDate() {
		return preparedDate;
	}
	public void setPreparedDate(String preparedDate) {
		this.preparedDate = preparedDate;
	}
	public SectionStatus getSectionStatus() {
		return sectionStatus;
	}
	public void setSectionStatus(SectionStatus sectionStatus) {
		this.sectionStatus = sectionStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public List<AuthorizationBean> getSurrogateAuthorizations() {
		return surrogateAuthorizations;
	}
	public void setSurrogateAuthorizations(
			List<AuthorizationBean> surrogateAuthorizations) {
		this.surrogateAuthorizations = surrogateAuthorizations;
	}
	public List<DiscussionBean> getSurrogateDiscussions() {
		return surrogateDiscussions;
	}
	public void setSurrogateDiscussions(List<DiscussionBean> surrogateDiscussions) {
		this.surrogateDiscussions = surrogateDiscussions;
	}
	public boolean isPatientDiscussed() {
		return patientDiscussed;
	}
	public void setPatientDiscussed(boolean patientDiscussed) {
		this.patientDiscussed = patientDiscussed;
	}
	public boolean isPatientAuthorized() {
		return patientAuthorized;
	}
	public void setPatientAuthorized(boolean patientAuthorized) {
		this.patientAuthorized = patientAuthorized;
	}
	public String getCreatedByUserName() {
		return createdByUserName;
	}
	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}
	public String getPhysicianUserName() {
		return physicianUserName;
	}
	public void setPhysicianUserName(String physicianUserName) {
		this.physicianUserName = physicianUserName;
	}
	public String getCertifiedDate() {
		return certifiedDate;
	}
	public void setCertifiedDate(String certifiedDate) {
		this.certifiedDate = certifiedDate;
	}
	public String getStatusChangeReason() {
		return statusChangeReason;
	}
	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}
	
	
	 
	
 
	
}
