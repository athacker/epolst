package gov.utah.polst.repository;

import gov.utah.polst.model.Patient;
import gov.utah.polst.model.SearchBean;
import gov.utah.polst.model.enums.Gender;
import gov.utah.polst.util.SearchUtilityService;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-Test.xml")
@Transactional
public class PatientRepositoryTest {

	@Autowired private PatientRepository patientRepository;
	@Autowired private SearchUtilityService searchUtilityService;

	@Test 
	public void getAllPatients() throws Exception{
		try{
		  patientRepository.findAll();
		}catch(Exception e){
			 throw e;
		}
 	}
	
	@Test
	public void getPatientsByPhysician() throws Exception{
		try{
			ArrayList<Patient> pts = (ArrayList<Patient>)patientRepository.findAll();
			String dr = pts.get(0).getPhysician();
			Collection<Patient>  ptsForDoctor = patientRepository.getPatientsForPhysician(dr);
		}catch(Exception e){
			throw e;
		}
	}

	@Test
	public void searchPatientsWithActivePolst(){
		
		ArrayList<Patient> pts = (ArrayList<Patient>)patientRepository.findAll();
		Gender searchByGender = pts.get(0).getGender();
		String searchByLastName = pts.get(0).getLastName();
		String searchByFirstName = pts.get(0).getFirstName();
		
	 
		searchByLastName = "%" +  searchByLastName.substring(1, 4)  +"%";
		searchByFirstName = "%" +  searchByFirstName.substring(1, 2)  +"%";
		
		
		System.out.println("\nSearch By Gender " + searchByGender.toString() );
		System.out.println("Search By Last Name: " + searchByLastName );
		System.out.println("Search By First Name: " + searchByFirstName );
		 
		Collection<Patient>  searchPatients = patientRepository.searchPatientsWithActivePolst(searchByGender,  searchByLastName, searchByFirstName );
		
		for (Patient p: searchPatients ){
			System.out.println("\n\tFound:  " + p.getLastName() + " - " +p.getFirstName() + " - " + p.getGender().toString() );
	 	}
	}
	
	
	@Test
	public void searchPatientsUsingBean(){
		
		ArrayList<Patient> pts = (ArrayList<Patient>)patientRepository.findAll();
	 
		String searchByLastName = pts.get(0).getLastName().substring(1, 4);
		String searchByFirstName = pts.get(0).getFirstName().substring(1,2);
				
		SearchBean bean = searchUtilityService.preparSearchString( pts.get(0).getGender().toString(), searchByLastName  + " " + searchByFirstName );
		System.out.println("\nSearch By Gender " + bean.getGender() );
		System.out.println("Search By Last Name: " + bean.getLastNameLike() );
		System.out.println("Search By First Name: " + bean.getFirstNameLike() );
		 
		Collection<Patient>  searchPatients = patientRepository.searchPatientsWithActivePolst(bean.getGender() ,  bean.getLastNameLike(), bean.getFirstNameLike());
		
		for (Patient p: searchPatients ){
			System.out.println("\n\tFound:  " + p.getLastName() + " - " +p.getFirstName() + " - " + p.getGender().toString() );
	 	}
	}
	
	@Test
	public void searchPatientsFixed(){
	 	
		SearchBean bean = searchUtilityService.preparSearchString( "FEMALE", "Smi Caro" );
		 
		System.out.println("\nSearch By Gender " + bean.getGender() );
		System.out.println("Search By Last Name: " + bean.getLastNameLike() );
		System.out.println("Search By First Name: " + bean.getFirstNameLike() );
	
		
		
	 	
		Collection<Patient>  searchPatients = patientRepository.searchPatientsWithActivePolst(bean.getGender() ,  bean.getLastNameLike(), bean.getFirstNameLike());
		
		System.out.println("Count " + searchPatients.size() );
		
		for (Patient p: searchPatients ){
			System.out.println("\n\tFound:  " + p.getLastName() + " - " +p.getFirstName() + " - " + p.getGender().toString() );
	 	}
	}
	
	
}