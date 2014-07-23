package gov.utah.epolst.service;

import gov.utah.epolst.model.PolstReportBean;

public interface ReportService {
	/**
	 * Gathers data to be assembled into pdf report
	 * @param polstId
	 * @return
	 */
	PolstReportBean generateReportInformation(Integer polstId);
	
	
	
}
