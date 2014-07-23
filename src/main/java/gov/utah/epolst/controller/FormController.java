
package gov.utah.epolst.controller;

import gov.utah.epolst.model.PolstReportBean;
import gov.utah.epolst.model.PolstUserDetails;
import gov.utah.epolst.service.PatientService;
import gov.utah.epolst.service.ReportService;
import gov.utah.epolst.service.SecurityService;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Form to control printing of ePOST pdf and probably other reports 
 * 
 */
@Controller
public class FormController {
 
	private static final Logger LOG = LoggerFactory.getLogger(FormController.class);
   
	@Autowired private ReportService reportService;
	@Autowired PatientService patientService;
	@Autowired private SecurityService securityService;
	
	/**
	 * IText version of ePOLST form (might want to redo using Jasper/IREports and a template)
	 * @param request
	 * @param response
	 * @return
	 *  
	 */
	@RequestMapping(value = "/printEPolst/{polstId}", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(@PathVariable String polstId) {
		PolstUserDetails u = securityService.getCurrentUser() ;
		LOG.debug("Print ePOLST form for polstId: " +polstId  ); 	
		Map<String,Object> formData = new HashMap<String,Object>();
		ModelAndView mav;
		PolstReportBean bean;
		try{
			if (null == u) {
				 bean = new PolstReportBean();
			}else{
				bean = reportService.generateReportInformation(Integer.parseInt(polstId));
			}
				
			formData.put("polst", bean);
			formData.put("patient", patientService.getPatient(String.valueOf( bean.getPatientId()) ));
			mav = new ModelAndView("ePolstFormPdfView","formData", formData);
			 
		}catch( Exception de){
			LOG.error("Exception was caught generating ePOLST report. ", de);
			mav = new ModelAndView("index");
		
		 	mav.addObject("currentUser", u.getUsername()  );
			mav.addObject("role", securityService.getCurrentRole() );
		}
	    
		return mav;
	}	
		



}	
	
	
	
	
	

