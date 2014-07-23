package gov.utah.epolst.service;

import gov.utah.epolst.model.AuthorizationBean;
import gov.utah.epolst.model.DiscussionBean;
import gov.utah.epolst.model.Patient;
import gov.utah.epolst.model.PatientBean;
import gov.utah.epolst.model.Polst;
import gov.utah.epolst.model.PolstAuthorization;
import gov.utah.epolst.model.PolstBean;
import gov.utah.epolst.model.PolstBeanLite;
import gov.utah.epolst.model.PolstDiscussion;
import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.model.SectionStatus;
import gov.utah.epolst.model.enums.AdvancedDirective;
import gov.utah.epolst.model.enums.AgentType;
import gov.utah.epolst.model.enums.Antibiotics;
import gov.utah.epolst.model.enums.AuthorizationType;
import gov.utah.epolst.model.enums.CodeStatus;
import gov.utah.epolst.model.enums.DiscussionType;
import gov.utah.epolst.model.enums.FeedingTube;
import gov.utah.epolst.model.enums.IvFluids;
import gov.utah.epolst.model.enums.MedicalCare;
import gov.utah.epolst.model.enums.PolstStatus;
import gov.utah.epolst.model.enums.RoleType;
import gov.utah.epolst.model.enums.StatusChangeReason;
import gov.utah.epolst.repository.PatientRepository;
import gov.utah.epolst.repository.PolstAuthorizationRepository;
import gov.utah.epolst.repository.PolstDiscussionRepository;
import gov.utah.epolst.repository.PolstRepository;
import gov.utah.epolst.repository.PolstUserRepository;

 





import java.text.ParseException;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
 



@Service("polstService")
public class PolstServiceImpl implements PolstService {
	
	@Autowired private AuditService auditService;
	@Autowired private EmailService emailService;
	@Autowired private PatientRepository patientRepository;
	@Autowired private PolstRepository polstRepository;
	@Autowired private PolstDiscussionRepository polstDiscussionRepository;
	@Autowired private PolstAuthorizationRepository polstAuthorizationRepository;
	@Autowired private PolstUserService polstUserService;
	@Autowired private PolstUserRepository polstUserRepository;
	@Autowired private SecurityService securityService;
	@Autowired private UtilityService utilityService;
 
	
	private static final Logger LOG = LoggerFactory.getLogger(PolstServiceImpl.class);
	


	/**
	 * polstList.jsp
	 */
	public PolstBean getPolstBeanByPolstId(Integer polstId){
	   return  convertToBean(polstRepository.findOne(polstId  ));
	}
	
	
	/**
	 * polstList.jsp ems view
	 */
	public PolstBean getActivePolstByPatientId(Integer patientId){
		return convertToBean(polstRepository.getActivePolstByPatientId(patientId) );
	}
 
	/**
	 *  creates an almost blank ePolst bean -- with only polstID, patientID and default polstStatus == IN_PROCESS
	 *  deactivate all other IN PROCESS or PENDING polsts -- only 1 is allowed per patient
	 *  a patient can have an ACTIVE and INPROCESS but NOT 2 IN PROCESS
	 */
	@Override
	@Transactional
	public PolstBean createNewEpolst(Integer patientId)   {
	
		String updatedDate = utilityService.formatDateToString(new Date());
		Date currentDate = utilityService.formatDate(updatedDate);
		String currentUser = securityService.getCurrentUser().getUsername();
		polstRepository.deactivatePendingInProcessForPatient(patientId, currentDate ,securityService.getCurrentUser().getUsername()     );
		
		Patient pt = patientRepository.findOne(patientId);
		//if a doctor is creating an epolst, update the patient.physician to that record.
		//create audit record of that change.
		if (RoleType.ROLE_MEDCERT.equals( securityService.getCurrentRole().getRoleType() )){
			pt.setPhysicianUserName(currentUser);
			pt.setUpdatedDate(new Date());
			pt.setUpdatedUserId(currentUser);
			auditService.createPatientAuditRow(pt);
		}
		
		Polst polst = new Polst();
		polst.setPatient( pt);
		polst.setPatientId(patientId);
		
		polst.setPolstStatus(PolstStatus.IN_PROCESS);
		polst.setAddedUserId(currentUser);
		polst.setUpdatedUserId(currentUser);
			
		polst = polstRepository.save(polst); 
		auditService.createPolstAuditRow(polst);
		
		return convertToBean(polst);
	 
	}


		/** 
		 *  Patient Bean to display on polst list 
		 **/
		public PatientBean convertToPatientBean(Polst polst){
			 
			 PatientBean bean = new  PatientBean ();
			 bean.setCurrentPolstId(polst.getId()  );
			 bean.setPatientId(String.valueOf(  polst.getPatient().getId() ));
			 bean.setFirstName( polst.getPatient().getFirstName());
			 bean.setLastName( polst.getPatient().getLastName());
			 bean.setMiddleName( polst.getPatient().getMiddleName());
			 bean.setGender( polst.getPatient().getGender().toString());
			 if (null != polst.getPatient().getDob()){
				 bean.setDob(utilityService.formatDateToString(polst.getPatient().getDob() ));
			 }
			 //display
			 bean.setPolstStatus(polst.getPolstStatus().getDescription());
			//logic
			 bean.setCurrentPolstStatus(polst.getPolstStatus().toString());
			 bean.setPhysicianFullName( polstUserService.findUserByUserName(polst.getPatient().getPhysicianUserName()).getDisplayName()  );
			 bean.setPolstLastUpdatedDate(utilityService.formatDateToString(polst.getUpdatedDate()));
			 bean.setPolstEnteredDate(utilityService.formatDateToString(polst.getCreatedDate()));
			 return bean;
			
		}
	/**POLST BEAN*/ 
	public PolstBean convertToBean(Polst p){
		//should never happen.. 
		if (null == p){
			return new PolstBean();
		}
	 
		PolstBean bean = new PolstBean();
		//controls checkmarks on polstDetail menu.
		SectionStatus sectionStatus = new SectionStatus();
		bean.setPolstId(String.valueOf(p.getId()  ));
		bean.setPatientId(String.valueOf(p.getPatient().getId() ));
		bean.setStatus(p.getPolstStatus().toString().trim() );
		bean.setStatusDescription(p.getPolstStatus().getDescription());
		bean.setCreatedByUserName(p.getAddedUserId());
		bean.setPhysicianUserName(p.getPatient().getPhysicianUserName());
		//ADVANCED DIRECTIVE
		if ( null != p.getAdvancedDirective()   ){
			bean.setAdvancedDirective(p.getAdvancedDirective().toString());
			//adv directive date isn't required.
			sectionStatus.setAdvDirectiveComplete(true);
			if ( null !=  p.getAdvancedDirectiveCompleteDate()){
				bean.setAdvancedDirectiveCompleteDate(utilityService.formatDateToString( p.getAdvancedDirectiveCompleteDate() ));
			 }		
		}
		bean.setAdvancedDirectiveInstructions(p.getAdvancedDirectiveInstructions());
		//DISCUSSIONS
		if ( null != p.getPolstDiscussions() && !p.getPolstDiscussions().isEmpty()){
			 //returns a bean with discussions good to go.
			 bean =  setDiscussions(bean, p.getPolstDiscussions());
			 sectionStatus.setDiscussionComplete(true);
		}
		if (null !=p.getPolstAuthorizations() && !p.getPolstAuthorizations().isEmpty()){
			 bean =  this.setAuthorizations(bean, p.getPolstAuthorizations());
			 sectionStatus.setAuthorizedComplete(true);
		}	
		
		if (null != p.getAntibiotics() && !StringUtils.isEmpty(p.getAntibiotics())){ 
			bean.setAntibiotics(p.getAntibiotics().toString());
			sectionStatus.setAntibioticsComplete(true);
		}
		bean.setAntibioticInstructions(p.getAntibioticInstructions());
		//controls text on top header icon Code Status A:
		if (null != p.getCodeStatus() && !StringUtils.isEmpty(p.getCodeStatus())){	
			bean.setCodeStatus(p.getCodeStatus().toString());
			bean.setCodeStatusTitle(p.getCodeStatus().getDescription());
			sectionStatus.setCodeStatusComplete(true);
		}
		bean.setCodeStatusInstructions(p.getCodeStatusInstructions());
		
		//NUTRITION
		if (null !=p.getFeedingTube()  && !StringUtils.isEmpty(p.getFeedingTube() )){ 
			bean.setFeedingTube(p.getFeedingTube().toString());
			if (null !=p.getIvFluids()){ 
				sectionStatus.setNutritionComplete(true);
			}
		}
		if (null != p.getIvFluids() && !StringUtils.isEmpty(p.getIvFluids() )){ 
			bean.setIvFluids(p.getIvFluids().toString());
			if (null !=p.getFeedingTube()){ 
				sectionStatus.setNutritionComplete(true);
			}
		}
		bean.setNutritionInstuctions(p.getNutritionInstuctions());
		
		//controls text on top header icon Code Status B:
		if (null != p.getMedicalCare() && !StringUtils.isEmpty(p.getMedicalCare() )){ 
			//radio button value
			bean.setMedicalCare(p.getMedicalCare().toString());
			//title on EPOLST
			bean.setMedicalCareTitle( p.getMedicalCare().getDescription() );
			sectionStatus.setMedicalCareComplete(true);
		}
		bean.setMedicalCareInstructions(p.getMedicalCareInstructions());
		
		
		if (null != p.getMedicalCondition() && !StringUtils.isEmpty(p.getMedicalCondition() )){ 
			bean.setMedicalCondition(p.getMedicalCondition());
			sectionStatus.setMedicalConditionComplete(true);
		}
		//current user
		bean.setPreparedBy(p.getPreparedBy());
	    if (null != p.getPreparedDate() && !StringUtils.isEmpty(p.getPreparedDate() )){ 
	    	bean.setPreparedDate(utilityService.formatDateToString(p.getPreparedDate() ));
	    	// PENDING_CERTIFICATION
	    	sectionStatus.setPreparerComplete(true);  
	    }
 
	    //CERTIFICATION section
	    //ACTIVE
	    if (null != p.getLicensedProvider() && !StringUtils.isEmpty(p.getLicensedProvider() )  &&  null != p.getCertifiedDate() ){ 
	     	bean.setLicensedProvider(polstUserService.getUserFullName(p.getLicensedProvider() ));
	    	bean.setCertifiedDate(utilityService.formatDateToString(p.getCertifiedDate()));
	    	sectionStatus.setCertifiedComplete(true);
	    }else{//form hasn't been certified, pull from polst_user
	    	bean.setLicensedProvider(polstUserService.getUserFullName(securityService.getCurrentUser().getUsername()) );
	    	sectionStatus.setCertifiedComplete(false);
	    }	
			
	    bean.setSectionStatus(sectionStatus);
	    return bean;
	}
	
	
	
	
	
	
	
	/*
	 * Discussion (groom discussions on PolstBean a bit for easier display on polstDetail.jsp)
	 */
	private PolstBean setDiscussions( PolstBean polstBean, List<PolstDiscussion> polstDisc){
		List<DiscussionBean> parentDiscussions = new ArrayList<DiscussionBean>();
		List<DiscussionBean> surrogateDiscussions = new ArrayList<DiscussionBean>();
		List<DiscussionBean> otherDiscussions = new ArrayList<DiscussionBean>();
		
		for(PolstDiscussion pd: polstDisc){
			DiscussionBean bean = new DiscussionBean();
			bean.setePolstDiscussionId(pd.getId());
			bean.setePolstId( String.valueOf( pd.getPolst().getId() )  );
			//legal authority ore relationship
			bean.setDiscussorsRelationship(pd.getDiscussorsRelationship()); 
			//surrogate, other or parent's name
			bean.setName(pd.getDiscussorsName());
			//surrogate, other or parent's phone
			bean.setPhone(pd.getDiscussorsPhone()); 
			bean.setType(pd.getDiscussionType().toString());
			
			if (DiscussionType.PATIENT.equals(pd.getDiscussionType())){
				polstBean.setPatientDiscussed(true);
			}else if(DiscussionType.PARENT_OF_MINOR.equals(pd.getDiscussionType())){
				parentDiscussions.add(bean);
			}else if(DiscussionType.SURROGATE.equals(pd.getDiscussionType())){
				surrogateDiscussions.add(bean);
			}else{
				otherDiscussions.add(bean);
			}
			
		}
	 
		polstBean.setParentDiscussions(parentDiscussions);
		polstBean.setSurrogateDiscussions(surrogateDiscussions);
		polstBean.setOtherDiscussions(otherDiscussions);
		return polstBean;
	}
	
	/*
	 * Discussion save (polstDetail.jsp)(non-Javadoc)
	 * @see gov.utah.epolst.service.PolstService#savePolstDiscussion(gov.utah.epolst.model.DiscussionBean)
	 */
	public DiscussionBean savePolstDiscussion(DiscussionBean bean)  {
		PolstDiscussion pd = new PolstDiscussion();
 	
		pd.setPolst(polstRepository.findOne(Integer.valueOf(bean.getePolstId()  )));
		pd.setDiscussionType(DiscussionType.valueOf(bean.getType()));
		pd.setDiscussorsRelationship(bean.getDiscussorsRelationship() );
		pd.setDiscussorsName(bean.getName());
		pd.setDiscussorsPhone(bean.getPhone());
		pd.setAddedUserId(securityService.getCurrentUser().getUsername());
		pd.setUpdatedUserId(securityService.getCurrentUser().getUsername());
		
		pd = polstDiscussionRepository.saveAndFlush(pd);
		bean.setePolstDiscussionId(pd.getId());
	    bean.setePolstId(String.valueOf(pd.getPolst().getId() ));
		return bean;
	}
	
	/*
	 * Discussion remove from (polstDetail.jsp)(non-Javadoc)
	 * @see gov.utah.epolst.service.PolstService#deleteDiscussion(java.lang.Integer)
	 */
	public void deleteDiscussion(Integer disussionId){
		polstDiscussionRepository.delete(disussionId);
	}
	/*
	 * Un-check PatientDiscustion (polstDetail.jsp)(non-Javadoc)
	 * @see gov.utah.epolst.service.PolstService#deletePatientDiscussion(java.lang.Integer)
	 */
	public void deletePatientDiscussion(Integer polstId){
	  polstDiscussionRepository.deletePatientDiscussionsForPolstId(polstId);
	}
	/*un-check parent, surrogate or other discussions(polstDetail.jsp*/
	public void deleteDiscussionsForType(Integer polstId, DiscussionType discussionType){
		polstDiscussionRepository.deleteDiscussionsForPolstIdType(polstId, discussionType);
	}
	
	/*Authorization save (polstDetail.jsp)*/
	public AuthorizationBean savePolstAuthorization(AuthorizationBean bean)  {
	 
 
		Polst polst=polstRepository.findOne(Integer.parseInt(bean.getePolstId() ));
	 	
		PolstAuthorization pa = new PolstAuthorization();
		pa.setPolst(polst);
		//parent, surrogate, other
		pa.setAuthorizerType(AuthorizationType.valueOf(bean.getType())); 
		pa.setRelationship(bean.getAuthorizerRelationship());
		pa.setAuthorizerName(bean.getAuthorizerName()); 
		pa.setAuthorizerPhone(bean.getAuthorizerPhone()); 
		
		if (!StringUtils.isEmpty(  bean.getAppointedAgent() )  ){
			pa.setAppointedAgent(AgentType.valueOf(bean.getAppointedAgent()));
		}
		//we don't collect this info from user, set default to current date.
		if(AuthorizationType.PATIENT.toString().equals(bean.getType()) ){
			bean.setAuthorizedDate(utilityService.formatDateToString(new Date() )); 
		}
		try{
			pa.setDateAuthorized(utilityService.formatDate(bean.getAuthorizedDate() ));
		}catch(Exception e){
			LOG.error("Exception was caught formatting ePOLST authorization date.",e);
			//should be date user saved record.
			pa.setDateAuthorized(new Date()); 
		}
		pa.setAddedUserId(securityService.getCurrentUser().getUsername());
		pa.setUpdatedUserId(securityService.getCurrentUser().getUsername());
		pa=polstAuthorizationRepository.saveAndFlush(pa);
		bean.setePolstAuthorizationId(pa.getId());
	
		//authorizations are the last section of the ePOLST form.
		auditService.createPolstAuditRow(polst);
		auditService.createPolstAuthorizationAuditRow(pa);
		return bean;
	}
	/**
	 * deletes authorization record from ePOLST form
	 */
	public void deleteAuthorization(Integer authorizationId){
		polstAuthorizationRepository.delete(authorizationId);
	}
	/**
	 * deletePatientAuthorization from ePOLST form
	 */
	public void deletePatientAuthorization(Integer polstId){
		polstAuthorizationRepository.deletePatientAuthorizationsForPolstId(polstId);
	}
	/**
	 * deleteAuthorizationsForType from ePOLST form (Patient, surrogate, other)
	 */
	public void deleteAuthorizationsForType(Integer polstId, AuthorizationType authorizationType){
		polstAuthorizationRepository.deleteAuthorizationsForPolstIdType(polstId, authorizationType);
	}
	
	/**
	 * Authorization (groom a bit for easier display on polstDetail.jsp)
	 * @param polstBean
	 * @param polstAuth
	 * @return
	 */
	private PolstBean setAuthorizations( PolstBean polstBean, List<PolstAuthorization> polstAuth){
	
		List<AuthorizationBean> parentAuthorizations = new ArrayList<AuthorizationBean>();
		List<AuthorizationBean> surrogateAuthorizations = new ArrayList<AuthorizationBean>();
		
		for(PolstAuthorization pd: polstAuth){
			AuthorizationBean bean = new AuthorizationBean();
			bean.setePolstAuthorizationId(pd.getId());
			bean.setePolstId( String.valueOf( pd.getPolst().getId() ) );
			bean.setAuthorizerName( pd.getAuthorizerName());
			bean.setAuthorizerRelationship(pd.getRelationship());
			if (null !=pd.getAppointedAgent() ){
				bean.setAppointedAgent(pd.getAppointedAgent().toString());
			}
			bean.setAuthorizerPhone(pd.getAuthorizerPhone());
			bean.setAuthorizedDate(utilityService.formatDateToString(pd.getDateAuthorized()  ));
			bean.setType(pd.getAuthorizerType().toString());
			if(AuthorizationType.PATIENT.equals(pd.getAuthorizerType())){
				//sets checkbox on eform
				polstBean.setPatientAuthorized(true);
			} else if(AuthorizationType.PARENT_OF_MINOR.equals(pd.getAuthorizerType())){
				parentAuthorizations.add(bean);
			}else { 
				surrogateAuthorizations.add(bean);
		 	}
			
		}
		polstBean.setParentAuthorizations(parentAuthorizations);
		polstBean.setSurrogateAuthorizations(surrogateAuthorizations);
		 
		return polstBean;
	}
	
	
	
	
	
	/**
	 * Saves polstBean from polstDetail.jsp
	 */
	
	@Transactional
	public PolstBean savePolstBean(PolstBean bean)  {
	 
		LOG.debug("Update an existing Polst, id : " + bean.getPolstId() );	
		Polst p  = polstRepository.findOne(Integer.valueOf( bean.getPolstId() ));
		p.setPatient(patientRepository.findOne(Integer.valueOf(bean.getPatientId() )) );
	   		
		if  ( !StringUtils.isEmpty(  bean.getAntibiotics() )){
			p.setAntibiotics(Antibiotics.valueOf(bean.getAntibiotics()  ));
		}
		p.setAntibioticInstructions(bean.getAntibioticInstructions());
		 
		if  ( !StringUtils.isEmpty(bean.getAdvancedDirectiveCompleteDate() )){
			Date today = new Date();
			p.setAdvancedDirectiveCompleteDate( utilityService.formatDate(bean.getAdvancedDirectiveCompleteDate() ) );
			if(today.before( p.getAdvancedDirectiveCompleteDate())  ) {
				p.setAdvancedDirectiveCompleteDate(null);
			}  
	 			 
	 	}else{
	 		//user might empty out completed date.
			p.setAdvancedDirectiveCompleteDate(null); 
	 	}		
			 
		 
		if  ( !StringUtils.isEmpty(bean.getAdvancedDirective())){
			p.setAdvancedDirective(AdvancedDirective.valueOf(bean.getAdvancedDirective()));
		}
		p.setAdvancedDirectiveInstructions(bean.getAdvancedDirectiveInstructions());
		
		if  ( !StringUtils.isEmpty( bean.getCodeStatus() ))	{
			p.setCodeStatus(CodeStatus.valueOf(bean.getCodeStatus()));
		}	
		p.setCodeStatusInstructions(bean.getCodeStatusInstructions());
		if  ( !StringUtils.isEmpty(bean.getFeedingTube())){
			p.setFeedingTube(FeedingTube.valueOf(bean.getFeedingTube()));
		}
		if  ( !StringUtils.isEmpty(bean.getIvFluids())){
			p.setIvFluids(IvFluids.valueOf(bean.getIvFluids()));
		}
	
		if (null !=bean.getMedicalCare()){
			p.setMedicalCare(MedicalCare.valueOf(bean.getMedicalCare()));
		}
		p.setMedicalCareInstructions(bean.getMedicalCareInstructions());
		
		p.setMedicalCondition(bean.getMedicalCondition());
		p.setNutritionInstuctions(bean.getNutritionInstuctions());
		
		p.setUpdatedUserId(securityService.getCurrentUser().getUsername());
		
		if (null == bean.getStatus()){
			bean.setStatus(PolstStatus.IN_PROCESS.toString());
		}
	    p.setPolstStatus(PolstStatus.valueOf(bean.getStatus()));
	 
		Polst px = polstRepository.save(p);
	 
	 	return this.convertToBean( px);
	}

	
	
	/*
	 * Prepares ePOLST -- change status to PENDING and let MEC_CERT know they have a record to certify
	 * (non-Javadoc)
	 * @see gov.utah.epolst.service.PolstService#prepareEpolst(gov.utah.epolst.model.PolstBean)
	 */

	@Override
	public PolstBean prepareEpolst(PolstBean polstBean)  {
	 
		Polst p  = polstRepository.findOne(Integer.valueOf( polstBean.getPolstId() ));
		String loggedInUser = securityService.getCurrentUser().getUsername();
		p.setPreparedBy(polstBean.getPreparedBy());
		if  ( !StringUtils.isEmpty( polstBean.getPreparedDate())){
			p.setPreparedDate(utilityService.formatDate(polstBean.getPreparedDate() ));
		 	p.setPolstStatus(PolstStatus.PENDING_CERTIFICATION);
			polstBean.setStatus(PolstStatus.PENDING_CERTIFICATION.toString());
			
		 	p.setUpdatedUserId(loggedInUser);
			polstRepository.save(p);
			emailService.sendPolstPendingNotification(p, loggedInUser );
		}else{
			LOG.info("ePOLST prepare date is empty, roll back to IN_PROCESS." );
			p.setUpdatedUserId(loggedInUser );
			p.setPolstStatus(PolstStatus.IN_PROCESS);
			polstBean.setStatus(PolstStatus.IN_PROCESS.toString());
			p.setPreparedDate(null);
			polstRepository.save(p);
		}
			auditService.createPolstAuditRow(p);
			return polstBean;
	}

	
	/**
	 * // TODO need to review with customer to see if we really need this section.
	 */
	@Override
	public PolstBean saveLegalTerms(PolstBean polstBean) {
		
		return null;
	}

	/**
	 * Certify ePOLST changes status to ACTIVE
	 */
	@Override
 	public PolstBean certifyEpolst(PolstBean polstBean) {
		Polst p  = polstRepository.findOne(Integer.valueOf( polstBean.getPolstId() ));
	 	p.setLicensedProvider( securityService.getCurrentUser().getUsername() );
	 	p.setCertifiedDate(  new Date() );
	    p.setPolstStatus(PolstStatus.ACTIVE);
	    polstBean.setStatus(PolstStatus.ACTIVE.toString());
		p.setUpdatedUserId(securityService.getCurrentUser().getUsername());
		polstRepository.saveAndFlush(p);
		polstRepository.deactivateOtherPolstForPatient(p.getId(), p.getPatientId(),securityService.getCurrentUser().getUsername());
		auditService.createPolstAuditRow(p);
		return polstBean;
		 
	}
	
	/**
	 * patientDetail.jsp polst History List 
	 */
	public Collection<PolstBeanLite>getPolstHistoryForPatient(Integer patientId){
		 
		List<PolstBeanLite> patientHistory = new ArrayList<PolstBeanLite>();
		Collection<Polst> polstHistory =  polstRepository.getPolstHistoryForPatient( patientId );
		
		for (Polst p: polstHistory){
			PolstBeanLite bean = new PolstBeanLite();
			bean.setLastModifiedDate(utilityService.formatDateToString(p.getUpdatedDate()) );
			bean.setePolstId(String.valueOf(p.getId()));
			bean.setPatientId(String.valueOf(patientId  ));
			//fomatted status
			bean.setStatus(p.getPolstStatus().getDescription()); 
			//enumerated value
			bean.setPolstStatus(p.getPolstStatus().toString());
			if (PolstStatus.IN_PROCESS.equals(p.getPolstStatus()  )) {
				bean.setAction("Edit");
			}else if( PolstStatus.PENDING_CERTIFICATION.equals(p.getPolstStatus()) ){
				bean.setAction("Edit");
			}else{
				bean.setAction("View");
			}
			patientHistory.add(bean);
		}
		
		return patientHistory;
	}
	
	/*
	 * Cancel's current ePOLSt the user is working on (from sticky nave on ePOLST form)(non-Javadoc)
	 * @see gov.utah.epolst.service.PolstService#cancelPolst(gov.utah.epolst.model.PolstBean)
	 */
	@Transactional
	public PolstBean cancelPolst(PolstBean polstBean){
		Polst polst = polstRepository.findOne(Integer.valueOf( polstBean.getPolstId() ) );
		polst.setPolstStatus(PolstStatus.IN_ACTIVE);
		polstBean.setStatusDescription(PolstStatus.IN_ACTIVE.getDescription());
		polst.setStatusChangeReason(polstBean.getStatusChangeReason());
		polstRepository.save(polst);
		polstBean.setStatus(PolstStatus.IN_ACTIVE.toString());
		auditService.createPolstAuditRow(polst);
		return polstBean;
	}
	
	/**
	 * Send reminder email to Physician.. 
	 */
	public void sendReminderEmail(Integer polstId){
		LOG.info("Sending reminder email to physician");
		Polst polst = polstRepository.findOne(polstId );
		String loggedInUser = securityService.getCurrentUser().getUsername();
	 	emailService.sendPolstPendingNotification( polst,loggedInUser );
	}


	
	
	
	@Override
	@Transactional
	public void deactivateDeathOfPatient(Patient pt) {
			List<Polst>polstToDeactivateList = polstRepository.getPolstByPatientId(pt.getId());
			for(Polst p: polstToDeactivateList) {
				p.setPolstStatus(PolstStatus.IN_ACTIVE);
				p.setStatusChangeReason(StatusChangeReason.DEATH.toString());
				polstRepository.save(p);
				auditService.createPolstAuditRow(p);
			}
		
	}
	
	
	
	
	
	
}
