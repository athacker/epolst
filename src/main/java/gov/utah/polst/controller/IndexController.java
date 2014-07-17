
package gov.utah.polst.controller;

import gov.utah.polst.model.PolstUserDetails;
import gov.utah.polst.service.PatientService;
import gov.utah.polst.service.SecurityService;
import gov.utah.polst.view.ClientCommand;
import gov.utah.polst.view.ClientCommandType;
import gov.utah.polst.view.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

 
@Controller
public class IndexController {
 
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
   
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		PolstUserDetails u = securityService.getCurrentUser() ;
		
		mav.addObject("currentUser", u.getUsername()  );
				
		for (GrantedAuthority ga: u.getAuthorities() ){
			mav.addObject("role", ga.getAuthority() );
		}
		return mav;
	}
 
	@RequestMapping(value = "/quickSearch/{gender}/{searchString}", method = RequestMethod.GET)
	public @ResponseBody Response quickSearch(@PathVariable String gender, @PathVariable String searchString) {
	
		System.out.println("Gender:  " + gender);
		Response response = new Response();
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"searchPatients",patientService.getPatientsWithActivePolst( gender, searchString) ));
		return response;
	}
	
	
	@RequestMapping(value = "/searchPatient/{physicianId}", method = RequestMethod.GET)
	public @ResponseBody Response searchPatients(@PathVariable String physicianId) {
		
		System.out.println("\n\n\tFind patients for this physician: " + physicianId);
	 	
		Response response = new Response();
		response.addCommand(new ClientCommand(ClientCommandType.PROPERTY,"patientsForPhysician",patientService.getPatientsForPhysician(physicianId) ));
		return response;
	}
	
	
	
	
}
