package gov.utah.epolst.model;

//used to save/update polstAuthorizations on polstDetail.jsp
public class AuthorizationBean {
	
	private String ePolstId;
	private int ePolstAuthorizationId;
	private String patientId;
	//parent, surrogate, other
	private String type; 
	private String authorizerName;
	private String authorizerRelationship;
	private String authorizerPhone="";
	private String authorizedDate;
	//appointed, court appointed, default
	private String appointedAgent; 
	
	
 
	public String getePolstId() {
		return ePolstId;
	}
	public void setePolstId(String ePolstId) {
		this.ePolstId = ePolstId;
	}
	public int getePolstAuthorizationId() {
		return ePolstAuthorizationId;
	}
	public void setePolstAuthorizationId(int ePolstAuthorizationId) {
		this.ePolstAuthorizationId = ePolstAuthorizationId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAuthorizerName() {
		return authorizerName;
	}
	public void setAuthorizerName(String authorizerName) {
		this.authorizerName = authorizerName;
	}
	public String getAuthorizerRelationship() {
		return authorizerRelationship;
	}
	public void setAuthorizerRelationship(String authorizerRelationship) {
		this.authorizerRelationship = authorizerRelationship;
	}
	public String getAuthorizerPhone() {
		return authorizerPhone;
	}
	public void setAuthorizerPhone(String authorizerPhone) {
		this.authorizerPhone = authorizerPhone;
	}
	public String getAuthorizedDate() {
		return authorizedDate;
	}
	public void setAuthorizedDate(String authorizedDate) {
		this.authorizedDate = authorizedDate;
	}
	public String getAppointedAgent() {
		return appointedAgent;
	}
	public void setAppointedAgent(String appointedAgent) {
		this.appointedAgent = appointedAgent;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	 
 
	
	
	
}
