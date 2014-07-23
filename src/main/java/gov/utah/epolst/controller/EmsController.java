
package gov.utah.epolst.controller;

import gov.utah.epolst.model.PatientVw;
import gov.utah.epolst.model.PolstBean;
import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.service.PatientService;
import gov.utah.epolst.service.PolstService;
import gov.utah.epolst.view.ClientCommand;
import gov.utah.epolst.view.ClientCommandType;
import gov.utah.epolst.view.Message;
import gov.utah.epolst.view.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Contains code required for EMS/EMT ajax calls
 * 
 */
@Controller
public class EmsController {
 
	private static final Logger LOG = LoggerFactory.getLogger(EmsController.class);
   
	@Autowired private PatientService patientService;
	@Autowired private PolstService polstService;

	/**
	 * Emt Quick Search -- searches patient_view that only contains patients who have an active ePOLST.
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "ems/quickSearch/", method = RequestMethod.POST)
	public @ResponseBody Response quickSearch(@RequestBody SearchBean searchBean) {
		Response response = new Response();
		LOG.debug("EMT Quick Search..  "  + searchBean.getQuickSearchName() );
		LOG.debug("EMT Quick Search..  "  + searchBean.getQuickSearchDob() );
		
		try{
			Page<PatientVw> patients = patientService.searchPatientsActivePolsts(searchBean);
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"searchPatients", patients.getContent()));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"currentPage", searchBean.getCurrentPage() ));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalRecords", patients.getTotalElements() ));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalPages",patients.getTotalPages() ));
			response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onSearchComplete", searchBean));
		}catch(Exception e){
			LOG.error("Exception was caught peforming patient search. ", e);
			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","An issue was encountered while searching for an ePOLST.",  Message.STYLE_ERROR)));
		}
			
			
		return response;
	}
	
		 
	/**
	 * EMS polstDetail.jsp 
	 * @param patientId
	 * @return
	 */
	@RequestMapping(value = "ems/getActivePolstByPatientId/{patientId}", method = RequestMethod.GET)
	public @ResponseBody Response getActivePolstByPatientId(@PathVariable String patientId) {
		LOG.info("EMS viewing ePOLST for patient: " + patientId);
		Response response = new Response();
		try{
			PolstBean bean = polstService.getActivePolstByPatientId(Integer.valueOf(patientId ));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean", bean));
			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
		}catch(Exception e){
			LOG.error("Exception was caught attempting to pull up ePOLST record.", e);
			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","An issue was encountered while searching for an ePOLST.",  Message.STYLE_ERROR)));
	
		}	
 		return response;
	}

	
	
	
	
	
	
	
	
	
	
	
}
