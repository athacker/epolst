package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.Gender;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * This class is used to construct JPA queries with the associated Specifications
 * example:  patientVwRepository.findAll(*Specification) 
 *
 */
 @StaticMetamodel(PatientMedCertVw.class)
public class PatientMedCertVw_ {
	public static volatile SingularAttribute<PatientMedCertVw, String> patientLastName;
    public static volatile SingularAttribute<PatientMedCertVw, String> patientFirstName;
    public static volatile SingularAttribute<PatientMedCertVw, String> createdBy;
    public static volatile SingularAttribute<PatientMedCertVw, String> physicianUserName;
    public static volatile SingularAttribute<PatientMedCertVw, Date> dob;
    public static volatile SingularAttribute<PatientMedCertVw, Gender> gender;
    public static volatile SingularAttribute<PatientMedCertVw, String> polstStatus;
 
   
}
