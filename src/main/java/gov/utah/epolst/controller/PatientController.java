
package gov.utah.epolst.controller;

import gov.utah.epolst.model.Patient;
import gov.utah.epolst.model.PatientBean;
import gov.utah.epolst.model.PolstUserDetails;
import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.model.enums.RoleType;
import gov.utah.epolst.service.PatientService;
import gov.utah.epolst.service.SecurityService;
import gov.utah.epolst.view.ClientCommand;
import gov.utah.epolst.view.ClientCommandType;
import gov.utah.epolst.view.Message;
import gov.utah.epolst.view.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  Ajax calls for patientList.jsp and patientDetail.jsp 
 *  
 *
 */
@Controller
public class PatientController {
 
	private static final Logger LOG = LoggerFactory.getLogger(PatientController.class);
    
	@Autowired private SecurityService securityService;
	@Autowired private PatientService patientService;
  
	/**
	 * User Role determines what data they may see.
	 * MED_CERT(physician) may see what they have been assigned to (patient.physician)
	 * MED_STAFF(nurse) may see what they have created (patient.added_user_id)
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "/patient/searchPatientsForRole", method = RequestMethod.POST)
	public @ResponseBody Response searchPatientsForRole(@RequestBody SearchBean searchBean) {
		RoleType loggedInUserRole = securityService.getCurrentRole().getRoleType() ;
	 	Response response = new Response();
		 
	 	try{	
	 		Page<Patient> pts;
	 		if(RoleType.ROLE_EMS.equals(loggedInUserRole)) {
	 			LOG.warn(securityService.getCurrentUser() + " attempted to access patient screens.");
	 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Failure!","Role EMT is not allowed to this view.",  Message.STYLE_ERROR)));
	 			return response;
	 		}else if (RoleType.ROLE_ADMIN.equals(loggedInUserRole)){
	 			pts = patientService.searchPatients(searchBean);
	 		}else if (RoleType.ROLE_MEDSTAFF.equals(loggedInUserRole)){
	 			pts = patientService.searchPatientsCreatedBy(searchBean);
	 		}else{	
	 			pts = patientService.searchPatientsForPhysician(searchBean);
	 		}
	 		//response commands control javascript behavior when the list is returned.
		 	response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"patientList", pts.getContent() ));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"currentPage", searchBean.getCurrentPage() ));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalRecords", pts.getTotalElements() ));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalPages", pts.getTotalPages() ));
			response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onSearchComplete", searchBean));
	  	}catch(Exception e){
	 		LOG.error("Exception was caught searching for patients.",e);
			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onSearchError",searchBean));
			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","An exception was encountered when requesting patient data.",  Message.STYLE_ERROR)));
	 	}	
		 	
		 	return response;
 	 }
	
	
	
    /**
     * GIVE USER THE ENTIRE LIST REGARDLESS OF THEIR ROLE  
     * @param searchBean
     * @return
     */
	@RequestMapping(value = "/patient/searchAllPatients", method = RequestMethod.POST)
	public @ResponseBody Response searchAllPatients(@RequestBody SearchBean searchBean) {
 		Response response = new Response();
  		
  		if(RoleType.ROLE_EMS.equals(securityService.getCurrentRole().getRoleType() )) {
			LOG.warn(securityService.getCurrentUser() + " attempted to access patient screens.");
 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","Role EMT is not allowed to this view.",  Message.STYLE_ERROR)));
 		 } else {
 			try{
 				Page<Patient>pts = patientService.searchPatients(searchBean);
				response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"patientList", pts.getContent() ));
				response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"currentPage", searchBean.getCurrentPage() ));
				response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalRecords", pts.getTotalElements() ));
				response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalPages", pts.getTotalPages() ));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onSearchComplete", searchBean));
 			}catch(Exception e){
 				LOG.error("Exception was caught searching for patients.",e);
 				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onSearchError",searchBean));
 				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","An exception was encountered when requesting patient data.",  Message.STYLE_ERROR)));
		 	}
 		 }
  		return response;	
 	}
	
		
		
	/**
	 * after user enters patient Last Name -- then give them a warning if that user already exists.
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "/patient/nameCheck", method = RequestMethod.POST)
	public @ResponseBody Response nameCheck(@RequestBody SearchBean searchBean) {
		Response response = new Response();
		Page<Patient>pts;
		pts = patientService.searchPatients(searchBean);
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"patientsWithSameNameDob", pts.getContent() ));
		response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onNameCheck", pts.getContent()));
		return response;
 	}
			
		
		
	/**
	 * Patient Data on patientDetail.jsp and polstDetail.jsp(header section)
	 * @param patientId  (view for ROLE_EMS, ROLE_MEDCERT, ROLE_MEDSTAFF, ROLE_ADMIN)
	 * @return
	 */
	@RequestMapping(value = "patient/getPatient/{patientId}", method = RequestMethod.GET)
	public @ResponseBody Response getPatient(@PathVariable String patientId) {
	 	Response response = new Response();
		PatientBean	 bean = patientService.getPatient(patientId);
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"patient", bean ));
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onPatientLoaded",  	bean));
		return response;
	}
	
	/**
	 * Save Patient Data on patientDetail.jsp save
	 * @param patientBean
	 * @return
	 */
	@RequestMapping(value = "patient/savePatient", method = RequestMethod.POST)
	public @ResponseBody Response savePatient(@RequestBody PatientBean patientBean) {
	 	Response response = new Response();
		if(RoleType.ROLE_EMS.equals(securityService.getCurrentRole().getRoleType() )) {
			LOG.warn(securityService.getCurrentUser() + " attempted to access patient screens.");
		}else {	
		 	try{
				PatientBean bean = patientService.savePatient(patientBean);
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onPatientLoaded",  	bean));
				response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"patient", bean ));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Success!","Patient Data has been saved.",  Message.STYLE_SUCCESS)));
			}catch(Exception e){
				LOG.error("An issue was encountered while saving patient data.",e);
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onPatientSaveError", patientBean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Failure!","An issue was encountered while saving patient data.",  Message.STYLE_ERROR)));
			}
		}	
		return response;
	}
		
	
	
}
