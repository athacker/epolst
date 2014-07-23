package gov.utah.epolst.model;


public class EmailHistoryBean {
	
	private int emailHistoryId;
	private int polstId;
	private String requestorNameString;
	private String receiverName;
	private String dateSent;
	
	
	public int getEmailHistoryId() {
		return emailHistoryId;
	}
	public void setEmailHistoryId(int emailHistoryId) {
		this.emailHistoryId = emailHistoryId;
	}
	public String getRequestorNameString() {
		return requestorNameString;
	}
	public void setRequestorNameString(String requestorNameString) {
		this.requestorNameString = requestorNameString;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getDateSent() {
		return dateSent;
	}
	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}
	public int getPolstId() {
		return polstId;
	}
	public void setPolstId(int polstId) {
		this.polstId = polstId;
	}
	 
	
	
	
	
}
