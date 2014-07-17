package gov.utah.polst.service;

import gov.utah.polst.model.Patient;
import gov.utah.polst.model.Polst;
import gov.utah.polst.model.SearchBean;
import gov.utah.polst.model.enums.Gender;
import gov.utah.polst.repository.PatientRepository;
import gov.utah.polst.repository.PolstRepository;
import gov.utah.polst.util.SearchUtilityService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("patientService")
public class PatientServiceImpl implements PatientService{

	@Autowired private PatientRepository patientRepository;
	@Autowired private PolstRepository polstRepository;
	@Autowired private SearchUtilityService searchUtilityService;
	
	@Override
	public Patient savePatient(Patient patient) {
		return patientRepository.save(patient);
	}
	
	@Override
	public Collection<Patient> getPatientsWithActivePolst(String gender, String searchString) {
		
		SearchBean bean = searchUtilityService.preparSearchString(gender, searchString);
		
		System.out.println("\nSearch By Gender " + bean.getGender() );
		System.out.println("Search By Last Name: " + bean.getLastNameLike() );
		System.out.println("Search By First Name: " + bean.getFirstNameLike() );
		Collection<Patient> pts = patientRepository.searchPatientsWithActivePolst( bean.getGender(), bean.getLastNameLike(), bean.getFirstNameLike() );
		System.out.println(pts.size());
				
		return pts;
	}

	
	@Override
	public Collection<Patient> getPatientsForPhysician(String physicianId) {
		return patientRepository.getPatientsForPhysician(physicianId);
	}

	@Override
	public void createTestData() {
		String first[] ={"Sam","Adam","Fred","Ruth","Buddy","Sue","Holly","Joe","Kim","Ann","Darci","Floyd","Dennis","Matt","Kevin","Carl","Mark","Ron","Jim","Dave", "Kari","Stacy", "Jeff", "Mike", "Steve", "Karen","Kyla","Alan"};
		String letters[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","m","n","o","p","q","r","s","t","u","v","q","x","y","z","w"};
		
		
//		for (int i= first.length; i>first.length; i--){
//			String fn=first[i];
//			String ln=letters[i]+letters[i+1];
//			Patient p = new Patient();
//			p.setFirstName(fn);
//			p.setLastName(ln);
//			p.setDob(new Date());
//			p.setGender(Gender.FEMALE);
//			p.setPhysician("Dr. J");
//		//	patientRepository.save(p);
//		}
//		for (int i= 0; i<first.length; i++){
//			String fn=first[i];
//			String ln=letters[i];
//			Patient p = new Patient();
//			p.setFirstName(fn);
//			p.setLastName(ln);
//			p.setDob(new Date());
//			p.setGender(Gender.MALE);
//			p.setPhysician("Dr Rachet");
//		//	patientRepository.save(p);
//		}
		
	ArrayList<Patient> tests = (ArrayList<Patient>)patientRepository.findAll();
		
		 
		
		
		for (Patient pt: tests){
			Polst p = new Polst();
			p.setPatient(pt);
			p.setEffectiveDate(new Date());
			p.setActive(Boolean.FALSE);
			p.setAdministerAntibiotics(Boolean.TRUE) ;
			p.setInstructionsBreathingPulse("If I am breathing and have a pulse -- please save me.");
			p.setInstructionsFullTreatment("Please give me the full treatment, thank you.");
			p.setInstructionsNoBreathingNoPulse("If I am not breathing and have no pulse, please DNR. Thank you.");
			p.setPhysicianName(pt.getPhysician());
			p.setPhysicianPhone("333 203 9111");
					
			polstRepository.save(p);
		}
		
		
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
