package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.PolstStatus;

import java.util.ArrayList;
import java.util.Collection;

public class PolstReportBean {

	private int patientId;
	
	private String codeStatusAttempt ="";
	private String codeStatusDoNotAttempt="";
	private String codeStatusInstructions="";
	
	private String medCareComfort="";
	private String medCareLimited="";
	private String medCareFull="";
	private String medicalCareInstructions="";
	
	private String antibioticsYes="";
	private String antibioticsNo="";
	private String antibioticInstructions="";
	
	private String feedingTubeNo="";
	private String feedingTubeDefinedTrial="";
	private String feedingTubeLongTerm="";
	
	private String ivNo="";
	private String ivDefinedTrial="";
	private String ivLongTerm="";
	private String nutritionInstructions="";
	private PolstStatus polstStatus;
	
	private String discussedPatient="";
	
	private String discussedParent="";
	private Collection<PolstDiscussion> discussedParentInfo = new ArrayList<PolstDiscussion>();
	
	private String discussedSurrogate="";
	private Collection<PolstDiscussion> discussedSurrogateInfo = new ArrayList<PolstDiscussion>();
		
	private String discussedOther="";
	private Collection<PolstDiscussion> discussedOtherInfo = new ArrayList<PolstDiscussion>();
	
	private String advancedDirectiveYes="____";
	private String advancedDirectiveNo="____";
	private String advancedDirectiveInfo="";
	
	private String medicalCondition="";

	
	private String signaturePreparerName="";
	private String signaturePreparerPhone="";
	private String signaturePreparedDate="";
	
	private String signaturePhysicianName="";
	private String signaturePhysicianLicenseNumber="";
	private String signaturePhysicianDateCertified="";
	
	private Collection<PolstAuthorization> signatureParentAuthorizationInfo = new ArrayList<PolstAuthorization>();
	private Collection<PolstAuthorization> signatureSurrogateAuthorizationInfo = new ArrayList<PolstAuthorization>();
	
	
	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getCodeStatusAttempt() {
		return codeStatusAttempt;
	}

	public void setCodeStatusAttempt(String codeStatusAttempt) {
		this.codeStatusAttempt = codeStatusAttempt;
	}

	public String getCodeStatusDoNotAttempt() {
		return codeStatusDoNotAttempt;
	}

	public void setCodeStatusDoNotAttempt(String codeStatusDoNotAttempt) {
		this.codeStatusDoNotAttempt = codeStatusDoNotAttempt;
	}

	public String getCodeStatusInstructions() {
		return codeStatusInstructions;
	}

	public void setCodeStatusInstructions(String codeStatusInstructions) {
		this.codeStatusInstructions = codeStatusInstructions;
	}

	public String getMedCareComfort() {
		return medCareComfort;
	}

	public void setMedCareComfort(String medCareComfort) {
		this.medCareComfort = medCareComfort;
	}

	public String getMedCareLimited() {
		return medCareLimited;
	}

	public void setMedCareLimited(String medCareLimited) {
		this.medCareLimited = medCareLimited;
	}

	public String getMedCareFull() {
		return medCareFull;
	}

	public void setMedCareFull(String medCareFull) {
		this.medCareFull = medCareFull;
	}

	public String getMedicalCareInstructions() {
		return medicalCareInstructions;
	}

	public void setMedicalCareInstructions(String medicalCareInstructions) {
		this.medicalCareInstructions = medicalCareInstructions;
	}

	public String getAntibioticsYes() {
		return antibioticsYes;
	}

	public void setAntibioticsYes(String antibioticsYes) {
		this.antibioticsYes = antibioticsYes;
	}

	public String getAntibioticsNo() {
		return antibioticsNo;
	}

	public void setAntibioticsNo(String antibioticsNo) {
		this.antibioticsNo = antibioticsNo;
	}

	public String getAntibioticInstructions() {
		return antibioticInstructions;
	}

	public void setAntibioticInstructions(String antibioticInstructions) {
		this.antibioticInstructions = antibioticInstructions;
	}

	public String getFeedingTubeNo() {
		return feedingTubeNo;
	}

	public void setFeedingTubeNo(String feedingTubeNo) {
		this.feedingTubeNo = feedingTubeNo;
	}

	public String getFeedingTubeDefinedTrial() {
		return feedingTubeDefinedTrial;
	}

	public void setFeedingTubeDefinedTrial(String feedingTubeDefinedTrial) {
		this.feedingTubeDefinedTrial = feedingTubeDefinedTrial;
	}

	public String getFeedingTubeLongTerm() {
		return feedingTubeLongTerm;
	}

	public void setFeedingTubeLongTerm(String feedingTubeLongTerm) {
		this.feedingTubeLongTerm = feedingTubeLongTerm;
	}

	public String getIvNo() {
		return ivNo;
	}

	public void setIvNo(String ivNo) {
		this.ivNo = ivNo;
	}

	public String getIvDefinedTrial() {
		return ivDefinedTrial;
	}

	public void setIvDefinedTrial(String ivDefinedTrial) {
		this.ivDefinedTrial = ivDefinedTrial;
	}

	public String getIvLongTerm() {
		return ivLongTerm;
	}

	public void setIvLongTerm(String ivLongTerm) {
		this.ivLongTerm = ivLongTerm;
	}

	public String getNutritionInstructions() {
		return nutritionInstructions;
	}

	public void setNutritionInstructions(String nutritionInstructions) {
		this.nutritionInstructions = nutritionInstructions;
	}
 
	public String getDiscussedSurrogate() {
		return discussedSurrogate;
	}

	public void setDiscussedSurrogate(String discussedSurrogate) {
		this.discussedSurrogate = discussedSurrogate;
	}

	public Collection<PolstDiscussion> getDiscussedSurrogateInfo() {
		return discussedSurrogateInfo;
	}

	public void setDiscussedSurrogateInfo(
			Collection<PolstDiscussion> discussedSurrogateInfo) {
		this.discussedSurrogateInfo = discussedSurrogateInfo;
	}

	public String getDiscussedOther() {
		return discussedOther;
	}

	public void setDiscussedOther(String discussedOther) {
		this.discussedOther = discussedOther;
	}

	public Collection<PolstDiscussion> getDiscussedOtherInfo() {
		return discussedOtherInfo;
	}

	public void setDiscussedOtherInfo(Collection<PolstDiscussion> discussedOtherInfo) {
		this.discussedOtherInfo = discussedOtherInfo;
	}

	public String getAdvancedDirectiveYes() {
		return advancedDirectiveYes;
	}

	public void setAdvancedDirectiveYes(String advancedDirectiveYes) {
		this.advancedDirectiveYes = advancedDirectiveYes;
	}

	public String getAdvancedDirectiveNo() {
		return advancedDirectiveNo;
	}

	public void setAdvancedDirectiveNo(String advancedDirectiveNo) {
		this.advancedDirectiveNo = advancedDirectiveNo;
	}

	public String getAdvancedDirectiveInfo() {
		return advancedDirectiveInfo;
	}

	public void setAdvancedDirectiveInfo(String advancedDirectiveInfo) {
		this.advancedDirectiveInfo = advancedDirectiveInfo;
	}

	public String getMedicalCondition() {
		return medicalCondition;
	}

	public void setMedicalCondition(String medicalCondition) {
		this.medicalCondition = medicalCondition;
	}

	public Collection<PolstDiscussion> getDiscussedParentInfo() {
		return discussedParentInfo;
	}

	public void setDiscussedParentInfo(
			Collection<PolstDiscussion> discussedParentInfo) {
		this.discussedParentInfo = discussedParentInfo;
	}

	public String getDiscussedPatient() {
		return discussedPatient;
	}

	public void setDiscussedPatient(String discussedPatient) {
		this.discussedPatient = discussedPatient;
	}

	public String getDiscussedParent() {
		return discussedParent;
	}

	public void setDiscussedParent(String discussedParent) {
		this.discussedParent = discussedParent;
	}

	public String getSignaturePreparerName() {
		return signaturePreparerName;
	}

	public void setSignaturePreparerName(String signaturePreparerName) {
		this.signaturePreparerName = signaturePreparerName;
	}

	public String getSignaturePreparerPhone() {
		return signaturePreparerPhone;
	}

	public void setSignaturePreparerPhone(String signaturePreparerPhone) {
		this.signaturePreparerPhone = signaturePreparerPhone;
	}

	public String getSignaturePreparedDate() {
		return signaturePreparedDate;
	}

	public void setSignaturePreparedDate(String signaturePreparedDate) {
		this.signaturePreparedDate = signaturePreparedDate;
	}

	public String getSignaturePhysicianName() {
		return signaturePhysicianName;
	}

	public void setSignaturePhysicianName(String signaturePhysicianName) {
		this.signaturePhysicianName = signaturePhysicianName;
	}

	public String getSignaturePhysicianLicenseNumber() {
		return signaturePhysicianLicenseNumber;
	}

	public void setSignaturePhysicianLicenseNumber(
			String signaturePhysicianLicenseNumber) {
		this.signaturePhysicianLicenseNumber = signaturePhysicianLicenseNumber;
	}

	public String getSignaturePhysicianDateCertified() {
		return signaturePhysicianDateCertified;
	}

	public void setSignaturePhysicianDateCertified(
			String signaturePhysicianDateCertified) {
		this.signaturePhysicianDateCertified = signaturePhysicianDateCertified;
	}

	public Collection<PolstAuthorization> getSignatureParentAuthorizationInfo() {
		return signatureParentAuthorizationInfo;
	}

	public void setSignatureParentAuthorizationInfo(
			Collection<PolstAuthorization> signatureParentAuthorizationInfo) {
		this.signatureParentAuthorizationInfo = signatureParentAuthorizationInfo;
	}

	public Collection<PolstAuthorization> getSignatureSurrogateAuthorizationInfo() {
		return signatureSurrogateAuthorizationInfo;
	}

	public void setSignatureSurrogateAuthorizationInfo(
			Collection<PolstAuthorization> signatureSurrogateAuthorizationInfo) {
		this.signatureSurrogateAuthorizationInfo = signatureSurrogateAuthorizationInfo;
	}

	public PolstStatus getPolstStatus() {
		return polstStatus;
	}

	public void setPolstStatus(PolstStatus polstStatus) {
		this.polstStatus = polstStatus;
	}

 
	
	
	

	
	
}
