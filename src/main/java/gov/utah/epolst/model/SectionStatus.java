package gov.utah.epolst.model;

public class SectionStatus {
	private boolean codeStatusComplete;
	private boolean medicalCareComplete;
 
	private boolean antibioticsComplete;
	private boolean nutritionComplete;
	private boolean discussionComplete;
	private boolean advDirectiveComplete;
	private boolean medicalConditionComplete;
	private boolean authorizedComplete;
	private boolean preparerComplete;
	private boolean legalTermsComplete;
	private boolean certifiedComplete;
	private boolean readyToPrepare;

	public boolean isCodeStatusComplete() {
		return codeStatusComplete;
	}

	public void setCodeStatusComplete(boolean codeStatusComplete) {
		this.codeStatusComplete = codeStatusComplete;
	}

	public boolean isMedicalCareComplete() {
		return medicalCareComplete;
	}

	public void setMedicalCareComplete(boolean medicalCareComplete) {
		this.medicalCareComplete = medicalCareComplete;
	}

 

	public boolean isAntibioticsComplete() {
		return antibioticsComplete;
	}

	public void setAntibioticsComplete(boolean antibioticsComplete) {
		this.antibioticsComplete = antibioticsComplete;
	}

	public boolean isNutritionComplete() {
		return nutritionComplete;
	}

	public void setNutritionComplete(boolean nutritionComplete) {
		this.nutritionComplete = nutritionComplete;
	}

	public boolean isDiscussionComplete() {
		return discussionComplete;
	}

	public void setDiscussionComplete(boolean discussionComplete) {
		this.discussionComplete = discussionComplete;
	}

	public boolean isAdvDirectiveComplete() {
		return advDirectiveComplete;
	}

	public void setAdvDirectiveComplete(boolean advDirectiveComplete) {
		this.advDirectiveComplete = advDirectiveComplete;
	}

	public boolean isMedicalConditionComplete() {
		return medicalConditionComplete;
	}

	public void setMedicalConditionComplete(boolean medicalConditionComplete) {
		this.medicalConditionComplete = medicalConditionComplete;
	}

	public boolean isAuthorizedComplete() {
		return authorizedComplete;
	}

	public void setAuthorizedComplete(boolean authorizedComplete) {
		this.authorizedComplete = authorizedComplete;
	}

	public boolean isPreparerComplete() {
		return preparerComplete;
	}

	public void setPreparerComplete(boolean preparerComplete) {
		this.preparerComplete = preparerComplete;
	}

	public boolean isLegalTermsComplete() {
		return legalTermsComplete;
	}

	public void setLegalTermsComplete(boolean legalTermsComplete) {
		this.legalTermsComplete = legalTermsComplete;
	}

	public boolean isCertifiedComplete() {
		return certifiedComplete;
	}

	public void setCertifiedComplete(boolean certifiedComplete) {
		this.certifiedComplete = certifiedComplete;
	}

	public boolean isReadyToPrepare() {
		if (advDirectiveComplete && antibioticsComplete && authorizedComplete
				&& codeStatusComplete && discussionComplete
				&& medicalCareComplete && nutritionComplete) {
			readyToPrepare = true;
		} else {
			readyToPrepare = false;
		}

		return readyToPrepare;
	}

	public void setReadyToPrepare(boolean readyToPrepare) {
		this.readyToPrepare = readyToPrepare;
	}

}
