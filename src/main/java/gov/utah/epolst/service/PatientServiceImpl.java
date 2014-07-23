package gov.utah.epolst.service;

import gov.utah.epolst.model.Patient;
import gov.utah.epolst.model.PatientBean;
import gov.utah.epolst.model.PatientMedCertVw;
import gov.utah.epolst.model.PatientVw;
import gov.utah.epolst.model.Polst;
import gov.utah.epolst.model.PolstBeanLite;
import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.model.SearchParameters;
import gov.utah.epolst.model.enums.Gender;
import gov.utah.epolst.model.enums.RoleType;
import gov.utah.epolst.repository.PatientMedCertVwRepository;
import gov.utah.epolst.repository.PatientRepository;
import gov.utah.epolst.repository.PatientVwRepository;
import gov.utah.epolst.repository.PolstRepository;
import gov.utah.epolst.util.PatientAddedIdSpecifications;
import gov.utah.epolst.util.PatientMedCertVwSpecifications;
import gov.utah.epolst.util.PatientPhysicianSpecifications;
import gov.utah.epolst.util.PatientSpecifications;
import gov.utah.epolst.util.PatientVwDobSpecifications;
import gov.utah.epolst.util.PatientVwSpecifications;

 
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



@Service("patientService")
public class PatientServiceImpl implements PatientService{
	
	@Autowired private AuditService auditService;
	@Autowired private PatientRepository patientRepository;
	@Autowired private PatientVwRepository patientVwRepository;
	@Autowired private PatientMedCertVwRepository patientMedCertVwRepository;
	@Autowired private PolstRepository polstRepository;
	@Autowired private PolstService polstService;
	@Autowired private PolstUserService polstUserService;
	@Autowired private SearchService searchService;
	@Autowired private SecurityService securityService;
	@Autowired private UtilityService utilityService;
	
	 
	private static final Logger LOG = LoggerFactory.getLogger(PatientServiceImpl.class);
	
	@Override
	@Transactional
    public PatientBean savePatient(PatientBean bean) {
		Patient pt;
		if (null == bean.getPatientId() ){
			pt = new Patient();
			pt.setAddedUserId(securityService.getCurrentUser().getUsername());
			pt.setCreatedDate(new Date());
		 }else{	
			pt = patientRepository.findOne(Integer.valueOf(bean.getPatientId()));
		 }
 
		pt.setFirstName(bean.getFirstName());
		pt.setLastName(bean.getLastName());
		pt.setMiddleName(bean.getMiddleName());
		pt.setSuffix(bean.getSuffix());
	  	pt.setDob(utilityService.formatDate(bean.getDob())   );
	  	pt.setPatientPhoneNumber(bean.getPatientPhoneNumber());
	    
	 	if (! StringUtils.isEmpty(bean.getDateOfDeath() )  ){
			pt.setDateOfDeath(utilityService.formatDate(bean.getDateOfDeath()) );
			LOG.info("Patient Died -- De-Activate  their ePOLSTS.");
			polstService.deactivateDeathOfPatient( pt );
			bean.setPolstHistory((List<PolstBeanLite>)polstService.getPolstHistoryForPatient(pt.getId()));
		 }else {
			 //the guy didn't die after all -- empty out his dod record.
			 pt.setDateOfDeath(null);
		 }
		
		pt.setGender("MALE".equals(bean.getGender())? Gender.MALE: Gender.FEMALE  );
		
		pt.setPhysicianUserName(bean.getPhysicianUserName());
		pt.setPhysicianFullName(polstUserService.getUserFullName(bean.getPhysicianUserName()));
		pt.setPrimaryCareProvider(bean.getPrimaryCareProvider() );
		pt.setPhysicianPhoneNumber(bean.getPhysicianPhoneNumber());
		pt.setPrimaryCarePhoneNumber(bean.getPrimaryCarePhysicianPhoneNumber());
		pt.setPhysicianPhoneNumber(bean.getPhysicianPhoneNumber());
		pt.setAddressCurrent(bean.getAddressCurrent());
		pt.setCityCurrent(bean.getCityCurrent());
		pt.setStateCurrent(bean.getStateCurrent());
		pt.setZipCurrent(bean.getZipCurrent());
		
		pt.setAddressPerm(bean.getAddressPerm());
		pt.setCityPerm(bean.getCityPerm());
		pt.setStatePerm(bean.getStatePerm());
		pt.setZipPerm(bean.getZipPerm());
		
		pt.setUpdatedUserId(securityService.getCurrentUser().getUsername());
		pt.setCreatorName(polstUserService.getUserFullName(securityService.getCurrentUser().getUsername()) );
		pt.setUpdatedDate(new Date());
		Patient p = patientRepository.saveAndFlush(pt);
		 
		bean.setPatientId(String.valueOf( p.getId() ));
		
		
		auditService.createPatientAuditRow(p);
		
	 	return bean;
	 }
	 
	
	@Override
	public PatientBean getPatient(String patientId){
		return  convertToBean(patientRepository.findOne(Integer.valueOf(patientId) ));
	}
	
    //Review this code -- could be stream lined
 	@Override
	public Page<Patient> searchPatientsForPhysician(SearchBean searchBean) {
 		
		SearchParameters params =  searchService.prepareQueryParameters(searchBean, securityService.getCurrentUser().getUsername(), securityService.getCurrentRole().getRoleType());
	 	Pageable  pageable = searchService.constructPageSpecification(searchBean);
	 	Page<Patient> requestedPage  = patientRepository.findAll( PatientPhysicianSpecifications.recordsAreLike(params ) ,  pageable  );
 		if (null !=requestedPage ){
 			requestedPage.getTotalPages();
 		}	
	    return requestedPage;
	}

 	 //Review this code -- could be stream lined
	@Override
	public Page<Patient> searchPatientsCreatedBy(SearchBean searchBean)	 {
		
		SearchParameters params =  searchService.prepareQueryParameters(searchBean, securityService.getCurrentUser().getUsername(), securityService.getCurrentRole().getRoleType() );
	 	Pageable  pageable = searchService.constructPageSpecification(searchBean);
	 	Page<Patient> requestedPage  = patientRepository.findAll( PatientAddedIdSpecifications.recordsAreLike(params ) ,  pageable  );
	 	if (null !=requestedPage ){
	 		requestedPage.getTotalPages();
	 	}	
	    return requestedPage;
	    
	}
	
	
	//patientList.jps -- view all checkbox
    @Override 
	public Page<Patient> searchPatients(SearchBean searchBean) {
    	
		SearchParameters params =  searchService.prepareQueryParameters(searchBean,securityService.getCurrentUser().getUsername(), securityService.getCurrentRole().getRoleType());
		Pageable  pageable = searchService.constructPageSpecification(searchBean);  
	 	Page<Patient> requestedPage  = patientRepository.findAll( PatientSpecifications.recordsAreLike(params ) ,  pageable  );
	 	if (null != requestedPage){
	 		requestedPage.getTotalPages();
	 	}	
	    return requestedPage;
	}
	
	


	//utility method converts patient pojo to a bean
	public PatientBean convertToBean(Patient pt){
		 
		 PatientBean bean = new  PatientBean ();
		 bean.setCreatedBy(polstUserService.getUserFullName(pt.getAddedUserId()));
		 bean.setPatientId(String.valueOf( pt.getId() ));
		 bean.setFirstName(pt.getFirstName());
		 bean.setLastName(pt.getLastName());
		 bean.setMiddleName(pt.getMiddleName());
		 bean.setSuffix(pt.getSuffix());
		 bean.setGender(pt.getGender().toString());
		 bean.setDob(utilityService.formatDateToString(pt.getDob()));
		 bean.setPatientPhoneNumber( utilityService.formatPhone( pt.getPatientPhoneNumber() ) );
		 
		 if (null !=pt.getDateOfDeath() ){
			 bean.setDateOfDeath(utilityService.formatDateToString(pt.getDateOfDeath()  ));   
		 }

		 if (   null != pt.getPolst() ){ 
			 for(Polst p: pt.getPolst() ){
				 bean.setCurrentPolstStatus(p.getPolstStatus().getDescription());
				 bean.setCurrentPolstId( p.getId() );
				 bean.setPolstLastUpdatedDate(utilityService.formatDateToString(p.getUpdatedDate()));
				 bean.setPolstEnteredDate(utilityService.formatDateToString(p.getCreatedDate()));
			   }
		 } 

		 bean.setPolstHistory((List<PolstBeanLite>)polstService.getPolstHistoryForPatient(pt.getId()));
	 	 bean.setPhysicianUserName(pt.getPhysicianUserName());
	 	 	 	 
	     bean.setPhysicianFullName( polstUserService.getUserFullName(pt.getPhysicianUserName()) ); 
	
		 bean.setPrimaryCareProvider(pt.getPrimaryCareProvider() );
	
		 bean.setPhysicianPhoneNumber(utilityService.formatPhone(pt.getPhysicianPhoneNumber() ) );
		 bean.setPrimaryCarePhysicianPhoneNumber(utilityService.formatPhone( pt.getPrimaryCarePhoneNumber()));
		 
		 bean.setAddressCurrent(pt.getAddressCurrent());
		 bean.setCityCurrent(pt.getCityCurrent());
		 bean.setStateCurrent(pt.getStateCurrent());
		 bean.setZipCurrent(pt.getZipCurrent());
		 
		 bean.setAddressPerm(pt.getAddressPerm());
		 bean.setCityPerm(pt.getCityPerm());
		 bean.setStatePerm(pt.getStatePerm());
		 bean.setZipPerm(pt.getZipPerm());
		 return bean;
		
	}
		
		
	   /**
	    * EMT Search-> patient_view only contains active ePOLSTS for faster queries
	    */
	    @Override 	
		public Page<PatientVw> searchPatientsActivePolsts(SearchBean searchBean){
	    	
	     	SearchParameters params =  searchService.prepareQueryParameters(searchBean,securityService.getCurrentUser().getUsername(), RoleType.ROLE_EMS);
			Pageable  pageable = searchService.constructPageSpecification(searchBean);
			Page<PatientVw> requestedPage  = patientVwRepository.findAll(StringUtils.isEmpty( searchBean.getQuickSearchDob()) ?PatientVwSpecifications.recordsAreLike(params ): PatientVwDobSpecifications.recordsAreLike(params ),  pageable  );
			if (null != requestedPage){
				requestedPage.getTotalPages();
			}
		    return requestedPage;
	 	}
		

 
	
		/**
		 * Polst List for MED_CERT AND MED_STAFF view contains everything except IN-ACTIVE polsts.
		 */
	    @Override 	
	 	public Page<PatientMedCertVw> searchPatients(RoleType roleType, SearchBean searchBean){
	    	SearchParameters params =  searchService.prepareQueryParameters(searchBean,securityService.getCurrentUser().getUsername(),roleType);
			Pageable  pageable = searchService.constructPageSpecification(searchBean);
			Page<PatientMedCertVw> requestedPage  = patientMedCertVwRepository.findAll ( PatientMedCertVwSpecifications.recordsAreLike(params ),  pageable );
			if (null != requestedPage){
				requestedPage.getTotalPages();
			}
		    return requestedPage;
	    }
		
	
	
	
	
	
	
	
	
	
	
}
