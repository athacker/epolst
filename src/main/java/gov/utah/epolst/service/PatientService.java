package gov.utah.epolst.service;

import gov.utah.epolst.model.Patient;
import gov.utah.epolst.model.PatientBean;
import gov.utah.epolst.model.PatientMedCertVw;
import gov.utah.epolst.model.PatientVw;
import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.model.enums.RoleType;

import java.text.ParseException;

import org.springframework.data.domain.Page;

/**
 * 
 * Services Methods for Patient Screens
 * 
 */
public interface PatientService {
	/**
	 * patientList for MED_CERT
	 * 
	 * @param searchBean
	 * @return
	 * 
	 */
	Page<Patient> searchPatientsForPhysician(SearchBean searchBean);

	/**
	 * patientList for MED_staff
	 * 
	 * @param searchBean
	 * @return
	 * 
	 */
	Page<Patient> searchPatientsCreatedBy(SearchBean searchBean);

	/**
	 * patientList for ADMIN
	 * 
	 * @param searchBean
	 * @return
	 * 
	 */
	Page<Patient> searchPatients(SearchBean searchBean);

	/**
	 * 
	 * @param patientBean
	 * @return
	 * @throws ParseException
	 */
	PatientBean savePatient(PatientBean patientBean) throws ParseException;

	/**
	 * 
	 * @param patientId
	 * @return
	 */
	PatientBean getPatient(String patientId);

	/**
	 * patientList for EMT
	 * 
	 * @param searchBean
	 * @return
	 * 
	 */
	Page<PatientVw> searchPatientsActivePolsts(SearchBean searchBean);

	
	/**
	 * Method to convert patient to a bean.
	 * @param pt
	 * @return
	 */
	PatientBean convertToBean(Patient pt);
	
	
	
	/**
	 * Polst List for MED_CERT AND MED_STAFF
	 */
 	Page<PatientMedCertVw> searchPatients(RoleType roleType, SearchBean searchBean);
	
	
}
