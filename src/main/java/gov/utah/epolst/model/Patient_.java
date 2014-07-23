package gov.utah.epolst.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * This class is used to construct JPA queries with the associated Specifications
 * example:  patientRepository.findAll(*Specification) 
 *
 */
@StaticMetamodel(Patient.class)
public class Patient_ {
	public static volatile SingularAttribute<Patient, String> lastName;
    public static volatile SingularAttribute<Patient, String> firstName;
    public static volatile SingularAttribute<Patient, String> addedUserId;
    public static volatile SingularAttribute<Patient, String> physicianUserName;
    public static volatile SingularAttribute<Patient, Date> dob;
    
//    public static volatile SingularAttribute<Patient, String> middleName;
//    public static volatile SingularAttribute<Patient, String> suffix;
//    public static volatile SingularAttribute<Patient, String> physicianPhoneNumber;
//    public static volatile SingularAttribute<Patient, String> primaryCarePhoneNumber;
//    public static volatile SingularAttribute<Patient, String> addressPerm;
//    public static volatile SingularAttribute<Patient, String> cityPerm;
//    public static volatile SingularAttribute<Patient, String> statePerm;
//    public static volatile SingularAttribute<Patient, String> zipPerm;
//    public static volatile SingularAttribute<Patient, String> addressCurrent;
//    public static volatile SingularAttribute<Patient, String> cityCurrent;
//    public static volatile SingularAttribute<Patient, String> stateCurrent;
//    public static volatile SingularAttribute<Patient, String> zipCurrent;
//    public static volatile SingularAttribute<Patient, String> primaryCareProvider;
//    public static volatile SingularAttribute<Patient, String> updatedUserId;
//    public static volatile SingularAttribute<Patient, Gender> gender;
    
    
  
 
}
