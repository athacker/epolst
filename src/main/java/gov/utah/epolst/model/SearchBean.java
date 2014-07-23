package gov.utah.epolst.model;


public class SearchBean {
	/**
	 * pagination
	 */
	private int currentPage;
	/**
	 * pagination
	 */
	private int recordsPerPage;
	
	/**
	 * Field to Sort 
	 */
	private String sortField;
	
	
	/*
	 * Patient DOB for search
	 */
	private String quickSearchDob;
	/**
	 * Search name that will be parsed into first_last for the query
	 */
	private String quickSearchName;
	/**
	 * PolstStatus  ACTIVE IN_PROCESS 
	 */
	private String polstStatus;
	/**
	 * Show all records reguardless to who they are assigned to or created by
	 */
	private boolean showAll =false;
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public String getQuickSearchDob() {
		return quickSearchDob;
	}
	public void setQuickSearchDob(String quickSearchDob) {
		this.quickSearchDob = quickSearchDob;
	}
	public String getQuickSearchName() {
		return quickSearchName;
	}
	public void setQuickSearchName(String quickSearchName) {
		this.quickSearchName = quickSearchName;
	}
	public String getPolstStatus() {
		return polstStatus;
	}
	public void setPolstStatus(String polstStatus) {
		this.polstStatus = polstStatus;
	}
	public boolean isShowAll() {
		return showAll;
	}
	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	 
	
	
	
	
}
