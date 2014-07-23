package gov.utah.epolst.model.enums;

/**
 *  
 * Polst Form Code Status
 *
 */

public enum CodeStatus {
	ATTEMPT("Attempt Resuscitation"),
	DNR("Do Not Resuscitate "),
	NO_CHOICE("No Choice");
	
	private String description;
	private CodeStatus(String desc){
		description=desc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
