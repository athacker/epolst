package gov.utah.epolst.model;


//used to save/update polstDiscussions on polstDetail.jsp
public class DiscussionBean {
	
	private String ePolstId;  
	private int ePolstDiscussionId;
	private String discussorsRelationship;
	private String name;
	private String phone;
	private String type;
	private String patientId;
	
	
	public String getePolstId() {
		return ePolstId;
	}
	public void setePolstId(String ePolstId) {
		this.ePolstId = ePolstId;
	}
	public int getePolstDiscussionId() {
		return ePolstDiscussionId;
	}
	public void setePolstDiscussionId(int ePolstDiscussionId) {
		this.ePolstDiscussionId = ePolstDiscussionId;
	}
	public String getDiscussorsRelationship() {
		return discussorsRelationship;
	}
	public void setDiscussorsRelationship(String discussorsRelationship) {
		this.discussorsRelationship = discussorsRelationship;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	  
 
	
	 
	
	 
	
	
	
	
	
	
}
