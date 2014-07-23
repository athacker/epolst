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
 @StaticMetamodel(PatientVw.class)
public class PatientVw_ {
	public static volatile SingularAttribute<PatientVw, String> lastName;
    public static volatile SingularAttribute<PatientVw, String> firstName;
    public static volatile SingularAttribute<PatientVw, String> addedUserId;
    public static volatile SingularAttribute<PatientVw, String> physicianUserName;
    public static volatile SingularAttribute<PatientVw, Date> dob;
    public static volatile SingularAttribute<PatientVw, Gender> gender;
    
//    public static volatile SingularAttribute<PatientVw, String> middleName;
//    public static volatile SingularAttribute<PatientVw, String> suffix;
//    public static volatile SingularAttribute<PatientVw, String> physicianPhoneNumber;
//    public static volatile SingularAttribute<PatientVw, String> primaryCarePhoneNumber;
//    public static volatile SingularAttribute<PatientVw, String> addressPerm;
//    public static volatile SingularAttribute<PatientVw, String> cityPerm;
//    public static volatile SingularAttribute<PatientVw, String> statePerm;
//    public static volatile SingularAttribute<PatientVw, String> zipPerm;
//    public static volatile SingularAttribute<PatientVw, String> addressCurrent;
//    public static volatile SingularAttribute<PatientVw, String> cityCurrent;
//    public static volatile SingularAttribute<PatientVw, String> stateCurrent;
//    public static volatile SingularAttribute<PatientVw, String> zipCurrent;
//    public static volatile SingularAttribute<PatientVw, String> primaryCareProvider;
//    public static volatile SingularAttribute<PatientVw, String> updatedUserId;
   
}
