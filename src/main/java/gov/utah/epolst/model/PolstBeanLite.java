package gov.utah.epolst.model;


 
public class PolstBeanLite {
	private String action;
	private String lastModifiedDate;
	private String ePolstId;
	private String label;
	private String patientId;
	//formatted description
	private String status;
	//enumerated value
	private String polstStatus; 
	
	 
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getePolstId() {
		return ePolstId;
	}
	public void setePolstId(String ePolstId) {
		this.ePolstId = ePolstId;
	}
	public String getLabel() {
		StringBuilder sb = new StringBuilder();
		sb.append(getStatus());
		sb.append(" [");
		sb.append( getLastModifiedDate());
		sb.append("]");
		return sb.toString();
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPolstStatus() {
		return polstStatus;
	}
	public void setPolstStatus(String polstStatus) {
		this.polstStatus = polstStatus;
	}
	
	
 
	
	
}
