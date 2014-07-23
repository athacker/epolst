package gov.utah.epolst.model.enums;




public enum MedicalCare {

	COMFORT("Comfort Measures - Allow Death"),
	LIMITED("Limited Addition Intervention"),
	FULL_TREATMENT("Full Treatment"),
	NO_CHOICE("No Choice");
	
	private String description;
	
	private MedicalCare(String desc){
		description = desc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
