package gov.utah.polst.model.enums;

public enum Gender {
	FEMALE("FEMALE"),
	MALE("MALE") ;
	 
	private String value;
	
	Gender(String value_) {
		this.value = value_;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

 
	
	
 }
