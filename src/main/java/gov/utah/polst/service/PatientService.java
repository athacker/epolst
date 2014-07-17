package gov.utah.polst.service;

import gov.utah.polst.model.Patient;

import java.util.Collection;




public interface PatientService {

	void createTestData();
	Patient savePatient(Patient patient);
	Collection<Patient>getPatientsWithActivePolst(String gender, String searchString);
	Collection<Patient>getPatientsForPhysician(String physicianId); //@todo will need to be re-worked after logic is figured out.
	
}
