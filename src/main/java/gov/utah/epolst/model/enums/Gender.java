package gov.utah.epolst.model.enums;

public enum Gender {
	FEMALE("FEMALE"),
	MALE("MALE"),
	ALL("ALL");
	 
	private String value;
	
	Gender(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

 
	
	
 }
