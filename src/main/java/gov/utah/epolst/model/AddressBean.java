package gov.utah.epolst.model;

public class AddressBean {
 	
	private boolean isValidZip;
	private String zipCode;
	private String city;
	private String state;
	private boolean isCurrent;
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isCurrent() {
		return isCurrent;
	}
	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	public boolean isValidZip() {
		return isValidZip;
	}
	public void setValidZip(boolean isValidZip) {
		this.isValidZip = isValidZip;
	}
	
 
	
	
	
}
