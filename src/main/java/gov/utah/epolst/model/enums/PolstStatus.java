package gov.utah.epolst.model.enums;

public enum PolstStatus {
	 IN_PROCESS("In-Process" ),
	 PENDING_CERTIFICATION("Pending Certification"),
	 ACTIVE("Active"),
	 IN_ACTIVE("In-Active"),
	 DEATH("Patient has died."),
	 ALL("All");
	 private String description;
	 
	 PolstStatus(String desc) {
		 this.description= desc;
	 }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	 
	 
	 
	 
}
