
package gov.utah.epolst.controller;

import gov.utah.epolst.model.AuthorizationBean;
import gov.utah.epolst.model.DiscussionBean;
import gov.utah.epolst.model.EmailHistory;
import gov.utah.epolst.model.EmailHistoryBean;
import gov.utah.epolst.model.PatientMedCertVw;
import gov.utah.epolst.model.PolstBean;
import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.model.enums.AuthorizationType;
import gov.utah.epolst.model.enums.DiscussionType;
import gov.utah.epolst.model.enums.PolstStatus;
import gov.utah.epolst.model.enums.RoleType;
import gov.utah.epolst.repository.PatientRepository;
import gov.utah.epolst.service.EmailService;
import gov.utah.epolst.service.PatientService;
import gov.utah.epolst.service.PolstService;
import gov.utah.epolst.service.SecurityService;
import gov.utah.epolst.view.ClientCommand;
import gov.utah.epolst.view.ClientCommandType;
import gov.utah.epolst.view.Message;
import gov.utah.epolst.view.Response;

import java.util.List;

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

 
@Controller
public class EPolstController {
 
	private static Logger LOG = LoggerFactory.getLogger(EPolstController.class);
   
	@Autowired private EmailService emailService;
	@Autowired private PatientService patientService;
	@Autowired private PatientRepository patientRepository;
	@Autowired private PolstService polstService;
	@Autowired private SecurityService securityService;
 

    @RequestMapping(value = "med/searchPolst/", method = RequestMethod.POST)
	public @ResponseBody Response searchPolst(@RequestBody SearchBean searchBean) {
		Response response = new Response();
		//this list might contain 2 ePOLSTS for a single patient (example ACTIVE and IN_PROCESS)
		//this list should NOT contain IN_ACTIVE ePOLST.
	 	Page<PatientMedCertVw>patientListForMed = patientService.searchPatients(securityService.getCurrentRole().getRoleType(), searchBean);
	 	
		StringBuffer message = new StringBuffer();
		if (patientListForMed.getContent().isEmpty()){
			//display message on grid if no records to display
			message.append( "No Records To Display for Status: ");
			message.append( PolstStatus.valueOf(searchBean.getPolstStatus()).getDescription() );
			message.append( "    Search Field: ");
			message.append( searchBean.getQuickSearchName());
		} 
 		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onViewChange", message));
		
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"patientListForMed", patientListForMed.getContent()));
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"currentPage", searchBean.getCurrentPage() ));
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalRecords", patientListForMed.getTotalElements() ));
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"totalPages", patientListForMed.getTotalPages() ));
		response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onSearchComplete", searchBean));
	 
		return response;
	

	}
    
 
	//polstDetail.jsp 
	@RequestMapping(value = "/getPolstByIdByPatient/{polstId}/{patientId}", method = RequestMethod.GET)
	public @ResponseBody Response getPolstByIdByPatient(@PathVariable String polstId,@PathVariable String patientId) {
		Response response = new Response();
		PolstBean bean=  polstService.getPolstBeanByPolstId(Integer.valueOf( polstId)   );
		bean.setPatientId(patientId);
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean", bean ));
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
 		return response;
	}
	
	//polstDetail.jsp 	 
	@RequestMapping(value = "/createNewEpolst/{patientId}", method = RequestMethod.GET)
	public @ResponseBody Response createNewEpolst(@PathVariable String patientId) {
		Response response = new Response();
		PolstBean emptyBean = new PolstBean();
		if( RoleType.ROLE_EMS.equals(securityService.getCurrentRole().getRoleType() )) {
			LOG.warn(securityService.getCurrentUser() + " attempted to access ePOLST screens.");
		 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","User is not allowed to this view.",  Message.STYLE_ERROR)));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   emptyBean)); 
 	 	}else { 
		 	emptyBean.setPatientId(patientId);
			emptyBean.setStatus(PolstStatus.IN_PROCESS.toString());
		 	try{
				emptyBean = polstService.createNewEpolst(Integer.valueOf(patientId));
				//polstId, patientId, polstStatus is populated.
				response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   emptyBean)); 
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",emptyBean));
			}catch(Exception e){
				LOG.error("An issue was encountered while saving ePOLST.",e);
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onPolstSaveError", emptyBean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Failure!","An issue was encountered while creating a new ePOLST.",  Message.STYLE_ERROR)));
			}
 		}	
		 return response;
	}

	//polstDetail.jsp 
	@RequestMapping(value = "/cancelEPolst", method = RequestMethod.POST)
	public @ResponseBody Response cancelEPolst(@RequestBody PolstBean polstBean) {
		Response response = new Response();
		if( RoleType.ROLE_EMS.equals(securityService.getCurrentRole().getRoleType() )) {
			LOG.warn(securityService.getCurrentUser() + " attempted to access ePOLST screens.");
		 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onPolstSaveError", polstBean));
			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","EMS is not allowed to in-activate an ePOLST.",  Message.STYLE_ERROR)));
		}
		else{
			try{
				PolstBean bean = polstBean;
				bean = polstService.cancelPolst(polstBean);
				response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Success!","ePOLST was successfully canceled..",  Message.STYLE_SUCCESS)));
			} catch(Exception e){
				LOG.error("An issue was encountered while saving ePOLST.",e);
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onPolstSaveError", polstBean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Failure!","An issue was encountered while cancelling ePOLST.",  Message.STYLE_ERROR)));
			}
		}	
		return response;		
	}  
	
	
	//ePOLST Management polstDetail.jsp
	@RequestMapping(value = "/saveEPolst", method = RequestMethod.POST)
	public @ResponseBody Response saveEPolst(@RequestBody PolstBean polstBean) {
		Response response = new Response();
 
		if( RoleType.ROLE_EMS.equals(securityService.getCurrentRole().getRoleType() )) {
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   polstBean));  
			LOG.warn(securityService.getCurrentUser() + " attempted to access ePOLST screens.");
		}else {	
			try{
				polstService.savePolstBean(polstBean);
				PolstBean bean = polstService.getPolstBeanByPolstId(Integer.valueOf(polstBean.getPolstId()));
				response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
			    response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage" , new Message("Success!","ePOLST Data has been saved.",  Message.STYLE_SUCCESS)));
			} catch(Exception e){
				LOG.error("An issue was encountered while saving ePOLST.",e);
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onPolstSaveError", polstBean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Failure!","An issue was encountered while saving ePOLST.",  Message.STYLE_ERROR)));
			}
		}	
		return response;
	}
	
	
	
	
	/**
	 * ePOLST Management polstDetail.jsp
	 * prepare section has it's own button on form.
	 * @param polstBean
	 * @return
	 */
	@RequestMapping(value = "/prepareEPolst", method = RequestMethod.POST)
	public @ResponseBody Response prepareEPolst(@RequestBody PolstBean polstBean) {
	 	Response response = new Response();
	 
		if( RoleType.ROLE_EMS.equals(securityService.getCurrentRole().getRoleType() )) {
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   polstBean));  
			LOG.warn(securityService.getCurrentUser() + " attempted to access ePOLST screens.");
		}else {	
			try{
				polstService.prepareEpolst(polstBean);
				PolstBean bean = polstService.getPolstBeanByPolstId(Integer.valueOf(polstBean.getPolstId()));
			 	response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage" , new Message("Success!","ePOLST status has been updated to: " + bean.getStatus(),  Message.STYLE_SUCCESS)));
			} catch(Exception e){
				LOG.error("An issue was encountered while saving ePOLST.",e);
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onPolstSaveError", polstBean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Failure!","An issue was encountered while saving ePOLST.",  Message.STYLE_ERROR)));
			}
		}	
		return response;
	}
	
	
   /**
    * Menu-item on navigation bar
    * ePOLST Management polstDetail.jsp
    * @param polstBean
    * @return
    */
	@RequestMapping(value = "/sendReminderEmail/{polstId}", method = RequestMethod.GET)
	public @ResponseBody Response sendReminderEmail(@PathVariable String polstId) {
		
		LOG.debug("Email Reminder to Physician that ePOLST is waiting their certification... " + polstId);
		Response response = new Response();
	 	try{
		  	polstService.sendReminderEmail(Integer.parseInt(polstId));
	 		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage" , new Message("Email Sent", "A reminder email has been re-sent to certifying physician.",   Message.STYLE_SUCCESS)));
		} catch(Exception e){
			LOG.error("An issue was encountered while sending reminder email.",e);
			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Failure!","An issue while sending reminder email to physician.",  Message.STYLE_ERROR)));
		}
		return response;
	}
	
	/**
	 * 
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "/getEmailHistory", method = RequestMethod.POST)
	public @ResponseBody Response getEmailHistory(@RequestBody String polstId) {
		Response response = new Response();
		List<EmailHistoryBean>  hx = emailService.getEmailHistoryForPolst(Integer.parseInt(polstId));
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"emailHistory", hx ));
		response.addCommand(new ClientCommand(ClientCommandType.METHOD,"onHistoryCheck", hx));

		return response;
 	}
		
	
	
	
    /**
     * Certify section should only be viewable to MED_CERT(physicians)
     * it is the last section on the poslt form and has it's own button.
     * ePOLST Management polstDetail.jsp
     * @param polstBean
     * @return
     */
	@RequestMapping(value = "/certifyEPolst", method = RequestMethod.POST)
	public @ResponseBody Response certifyEPolst(@RequestBody PolstBean polstBean) {
		Response response = new Response();
		if( !RoleType.ROLE_MEDCERT.equals(securityService.getCurrentRole().getRoleType() )) {
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   polstBean));  
			LOG.warn(securityService.getCurrentUser() + " attempted to certify ePOLST.");
		}else {	
		  	try{
				polstService.certifyEpolst(polstBean);
				PolstBean bean  = polstService.getPolstBeanByPolstId(Integer.valueOf(polstBean.getPolstId()));
				response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage" , new Message("Success!","ePOLST and been Certified and Activated.",  Message.STYLE_SUCCESS)));
			} catch(Exception e){
				LOG.error("An issue was encountered while saving ePOLST.",e);
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onPolstSaveError", polstBean));
				response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
						, new Message("Failure!","An issue was encountered while saving ePOLST.",  Message.STYLE_ERROR)));
			}
		}	
		return response;
	}

	
	
	
	/**
	 * Discussion section of ePOLST form -- save polstDiscussion records
	 * @param discussionBean
	 * @return
	 */
	@RequestMapping(value = "/savePolstDiscussion", method = RequestMethod.POST)
	public @ResponseBody Response savePolstDiscussion(@RequestBody DiscussionBean discussionBean) {
		Response response = new Response();
		LOG.debug( discussionBean.getType()  );
 
	 	try{
 			discussionBean = polstService.savePolstDiscussion(discussionBean);
 			PolstBean bean = polstService.getPolstBeanByPolstId(Integer.valueOf( discussionBean.getePolstId() ));
 			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
		 	response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
 					, new Message("Success!", discussionBean.getType() + " Discussion has been saved.",  Message.STYLE_SUCCESS)));
		} catch(Exception e) {
 			LOG.error("An issue was encountered while saving ePOLST Discussion.",e);
 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onDiscSaveErr", discussionBean));
 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
 					, new Message("Failure!","An issue was encountered while saving ePOLST Discussion.",  Message.STYLE_ERROR)));
		}
		return response;
	
	}	
	/**
	 * removes individual discussion records
	 * @param discussionBean
	 * @return
	 */
	@RequestMapping(value = "/deletePolstDiscussion", method = RequestMethod.POST)
	public @ResponseBody Response deletePolstDiscussion(@RequestBody DiscussionBean discussionBean) {
		Response response = new Response();
		LOG.debug("Delete discussion id:  " + discussionBean.getePolstDiscussionId());
	 
		if (DiscussionType.PATIENT.toString().equals(discussionBean.getType())){
			polstService.deletePatientDiscussion(Integer.valueOf(discussionBean.getePolstId() ));
		}else{
			polstService.deleteDiscussion( discussionBean.getePolstDiscussionId()  );
		}
		 
		PolstBean bean = polstService.getPolstBeanByPolstId(Integer.valueOf( discussionBean.getePolstId() ));
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Success!",  "Discussion has been removed.",  Message.STYLE_SUCCESS)));
	
		return  response;
	}
	
	/**
	 * remove all discussions for a DiscussionType (OTHER, PARENT, SURROGATE)
	 * called when user un-checks discussion check box for a TYPE section.
	 * @param discussionBean
	 * @return
	 */
	@RequestMapping(value = "/deleteDiscussionsForType", method = RequestMethod.POST)
	public @ResponseBody Response deleteDiscussionsForType(@RequestBody DiscussionBean discussionBean) {
		Response response = new Response();
	 	LOG.info("Remove all PolstDiscussions for this ePolst type: " + discussionBean.getType());
	
	    polstService.deleteDiscussionsForType( Integer.parseInt( discussionBean.getePolstId() ), DiscussionType.valueOf(discussionBean.getType()) );
	 	 
		PolstBean bean = polstService.getPolstBeanByPolstId(Integer.valueOf( discussionBean.getePolstId() ));
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Success!",  "Discussion has been removed.",  Message.STYLE_SUCCESS)));
	
		return  response;
	}
	
	
	
	/**
	 * Authorization section of ePOLST form, saves polstAuthorization records.
	 * @param authorizationBean
	 * @return
	 */
	@RequestMapping(value = "/savePolstAuthorization", method = RequestMethod.POST)
	public @ResponseBody Response savePolstAuthorization(@RequestBody AuthorizationBean authorizationBean) {
		Response response = new Response();
		LOG.debug( authorizationBean.getType()  );
	
	 	try{
	 		authorizationBean = polstService.savePolstAuthorization(authorizationBean);
			PolstBean bean = polstService.getPolstBeanByPolstId(Integer.valueOf( authorizationBean.getePolstId() ));
			response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
 					, new Message("Success!", authorizationBean.getType() + " Authorization has been saved.",  Message.STYLE_SUCCESS)));
		} catch(Exception e) {
 			LOG.error("An issue was encountered while saving ePOLST Discussion.",e);
 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onAuthSaveErr", authorizationBean));
 			response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
 					, new Message("Failure!","An issue was encountered while saving ePOLST Authorization.",  Message.STYLE_ERROR)));
		}
		return response;
	
	}	
	
	/**
	 * called when user removes an individual authorization record when selecting the remove button
	 * @param authorizationBean
	 * @return
	 */
	@RequestMapping(value = "/deletePolstAuthorization", method = RequestMethod.POST)
	public @ResponseBody Response deletePolstAuthorization(@RequestBody AuthorizationBean authorizationBean) {
		Response response = new Response();
		 
	  	if (AuthorizationType.PATIENT.toString().equals(authorizationBean.getType())  ){
			polstService.deletePatientAuthorization(Integer.valueOf( authorizationBean.getePolstId()  ));
		}else{
			polstService.deleteAuthorization( authorizationBean.getePolstAuthorizationId()  );
		}
		PolstBean bean =   polstService.getPolstBeanByPolstId(Integer.valueOf(authorizationBean.getePolstId()  ));
	   	response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Success!",  "Authorization has been removed.",  Message.STYLE_SUCCESS)));
	
		return  response;
	}
	
	/**
	 * remove all authorizations for a AuthorizationType (OTHER, PARENT, SURROGATE)
	 * called when user un-checks authorization check box for a TYPE section.
	 * @param authorizationBean
	 * @return
	 */
	@RequestMapping(value = "/deleteAuthorizationsForType", method = RequestMethod.POST)
	public @ResponseBody Response deleteAuthorizationsForType(@RequestBody AuthorizationBean authorizationBean) {
		Response response = new Response();
		LOG.info("Remove all PolstAuthorizations for this ePolst type: " + authorizationBean.getType());
		polstService.deleteAuthorizationsForType( Integer.parseInt(authorizationBean.getePolstId()), AuthorizationType.valueOf(authorizationBean.getType())  );
		PolstBean bean =   polstService.getPolstBeanByPolstId(Integer.valueOf(authorizationBean.getePolstId()  ));
	   	response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"polstBean",   bean));  
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "onLoadPolstForm",bean));
		response.addCommand(new ClientCommand(ClientCommandType.METHOD, "addMessage"
					, new Message("Success!",  "Authorization has been removed.",  Message.STYLE_SUCCESS)));
	
		return  response;
	}
	
}
