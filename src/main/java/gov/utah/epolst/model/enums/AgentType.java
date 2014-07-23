package gov.utah.epolst.model.enums;

public enum AgentType {
	
		APPOINTED_AGENT("Appointed Agent"),
		APPOINTED_GUARDIAN("Court Appointed Guardian"),
		DEFAULT_SURROGATE("Default Surrogate");
		
    private String label;
	private AgentType(String label){
		label=label;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
}
