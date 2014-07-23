package gov.utah.epolst.service;

import gov.utah.epolst.model.DoplView;
import gov.utah.epolst.model.EmtView;
import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.model.Role;
import gov.utah.epolst.model.SearchBean;
import gov.utah.epolst.model.SearchParameters;
import gov.utah.epolst.model.UserBean;
import gov.utah.epolst.model.enums.AuditAction;
import gov.utah.epolst.model.enums.Login;
import gov.utah.epolst.model.enums.Profession;
import gov.utah.epolst.model.enums.RoleType;
import gov.utah.epolst.repository.DoplViewRepository;
import gov.utah.epolst.repository.EmtViewRepository;
import gov.utah.epolst.repository.PolstUserRepository;
import gov.utah.epolst.repository.RoleRepository;
import gov.utah.epolst.util.PolstUserSpecifications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("polstUserService")
public class PolstUserServiceImpl implements PolstUserService{
	
	
	@Autowired AuditService auditService;
	@Autowired DoplViewRepository doplViewRepository;
	@Autowired EmtViewRepository emtViewRepository;
	@Autowired PolstUserRepository polstUserRepository;
	@Autowired RoleRepository roleRepository;
	@Autowired SearchService searchService;
	@Autowired SecurityService securityService;
		
	
	private static final Logger LOG= LoggerFactory.getLogger(PolstUserServiceImpl.class);

	
	/**
	 * User this method to determine roles when logging in and if they are still valid.
	 */
	@Override
	@Transactional
	public PolstUser determineLoggedInUserPermissions(String dmwUserName) {
	 	PolstUser polstUser = polstUserRepository.findUserByUserName(dmwUserName);
		 
		if (null == polstUser){
			//new user into system.. automatically save them as active role_not_verified.
			polstUser = new PolstUser(dmwUserName, roleRepository.getRoleByType(RoleType.ROLE_NOT_VERIFIED)); 
			polstUser.setActive(Boolean.TRUE);
			polstUser = polstUserRepository.saveAndFlush(polstUser);
			auditService.createLoginAuditRow(polstUser, AuditAction.COULD_NOT_VERIFY,"Brand New User, set role to ROLE_NOT_VERFIED", Login.SUCCESS, "New User", "", "", "");
		 }else if (RoleType.ROLE_EMS.equals(polstUser.getRole().getRoleType()) ){
			EmtView currentEmt = emtViewRepository.findEmtByBadgeNumber(polstUser.getLicenseId());
			//EMT Badge number is no longer showing up in the BEMS/POLARIS EPOLST_EMT_VW
			if (null == currentEmt){
				LOG.warn("Emt Badge Number is no longer valid, user cannot be verified.");
				auditService.createLoginAuditRow(polstUser, AuditAction.COULD_NOT_VERIFY,"Emt/Ems User no longer in EPOLST_EMT_VW/POLARIS view.", Login.FAILURE, polstUser.getLicenseId(), "", "", "");
			 	polstUser.setRole(roleRepository.getRoleByType(RoleType.ROLE_NOT_VERIFIED));  
				polstUser.setActive(false);
				polstUser.setVerfiedManual(false);
				polstUser.setVerfiedSystem(false);
				polstUser.setUpdatedDate(new Date());
				polstUser.setUpdatedUserId(dmwUserName);
			    polstUserRepository.saveAndFlush(polstUser);
			}
			
		}else if(RoleType.ROLE_NOT_VERIFIED.equals(polstUser.getRole().getRoleType())) {
			LOG.warn("Existing User -- ROLE NOT VERIFIED has logged into ePOLST.");
			auditService.createLoginAuditRow(polstUser, AuditAction.COULD_NOT_VERIFY,"Existing User, ROLE_NOT_VERIFIED has logged into system.", Login.SUCCESS, polstUser.getLicenseId(), "", "", "");
	 	}else if (!RoleType.ROLE_ADMIN.equals(polstUser.getRole().getRoleType())){
			//LICENSE is no longer showing up in the DOPL_VIEW
			DoplView currentMed = doplViewRepository.findDoplRecordByLicense(polstUser.getLicenseId());
			if (null == currentMed ){
				LOG.warn("Med_Cert/Staff license is no longer valid, user cannot be verified.");
				auditService.createLoginAuditRow(polstUser, AuditAction.COULD_NOT_VERIFY,"MedCert/Staff User no longer in DOPL view.", Login.FAILURE, polstUser.getLicenseId(), "", "", "");
			 	polstUser.setRole(roleRepository.getRoleByType(RoleType.ROLE_NOT_VERIFIED));  
				polstUser.setActive(false);
				polstUser.setVerfiedManual(false);
				polstUser.setVerfiedSystem(false);
				polstUser.setUpdatedDate(new Date());
				polstUser.setUpdatedUserId(dmwUserName);
				polstUserRepository.saveAndFlush(polstUser);
			}
 		}
		
		
		 return polstUser;
	}
	
	/**
	 * finds user full or display name by their user login name
	 */
	public String getUserFullName(String userLoginName){
		String displayName="";
		
		PolstUser user = polstUserRepository.findUserByUserName(userLoginName);
		if (null != user){
			displayName= user.getDisplayName();
		}
		return displayName;
	}

 
	/**
	 * finds User by random primary key ID)
	 */
	public UserBean findUserByUserId(Integer id){
	   return this.convertToBean(   polstUserRepository.findOne(id)  );
	}
	
	/**
	 * Return a UserBean object for a given user_name
	 */
	public UserBean findUserByUserName(String userLoginName){
		   return this.convertToBean( polstUserRepository.findUserByUserName(userLoginName) );
	 }
	
	/**
	 * get All users of the epOLSt System.
	 * @return
	 */
	public Collection<UserBean>getAllUsers(){
		List <UserBean>userList = new ArrayList<UserBean>();
		List<PolstUser> usrs =  polstUserRepository.findAll();
		for (PolstUser pu: usrs){
		  userList.add(convertToBean(pu));
		}
	 	return userList;
	}
	
	/**
	 * physician list patientDetail.jsp
	 */
	public Collection<UserBean>getActivePhysicians(){
		List <UserBean>physicianList = new ArrayList<UserBean>();
		Collection<PolstUser> medCerts =  polstUserRepository.getActivePhysicians();
		for (PolstUser pu: medCerts){
			physicianList.add(convertToBean(pu));
		}
		return physicianList;
	}
	
	
	/**
	 * Converts a PolstUser to a userBean
	 * @param pu
	 * @return
	 */
	private UserBean convertToBean(PolstUser pu){
		UserBean b = new UserBean();
		if (null != pu.getActive() ){
			b.setActive(pu.getActive() );
		}
		
		b.setEmail(pu.getEmail());
		b.setFirstName(pu.getFirstName());
		b.setLastName(pu.getLastName());
		b.setPhoneNumber(pu.getPhoneNumber());
		b.setLicense(pu.getLicenseId());
		b.setRole(pu.getRole().toString());
		b.setRoleId(  roleRepository.getRoleByType(pu.getRole().getRoleType()).getId()  );
		b.setStateLicensed(pu.getLicenseId());
		b.setStateLicensed(pu.getStateLicensed());
		b.setUserId(String.valueOf( pu.getId()  ));
		b.setUserName(pu.getUserName());
	 	
	 	if (null !=pu.getVerfiedSystem()){
	 		b.setVerified(pu.getVerfiedSystem() );
	 	}else{
	 		b.setVerified(false);
	 	}
	 	if (null != pu.getVerfiedManual() ){
	 		b.setVerifiedManual(pu.getVerfiedManual() ); 
	 	}else{
	 		b.setVerifiedManual(false);
	 	}	
		
		return b;
	}
	
	/**
	 * Save PolstUser
	 */
	public UserBean saveUser(UserBean bean){
		PolstUser pu;
		if (null != bean.getUserId() ){
			pu = this.polstUserRepository.findOne(Integer.valueOf( bean.getUserId() ));
		} else {
			pu = this.polstUserRepository.findUserByUserName(bean.getUserName());
			if (null == pu){
				pu = new PolstUser(); 
			}
			pu.setAddedUserId(securityService.getCurrentUser().getUsername());
		}
		pu.setEmail(bean.getEmail());
		pu.setFirstName(bean.getFirstName());
		pu.setLastName(bean.getLastName());
		pu.setPhoneNumber(bean.getPhoneNumber());
		pu.setLicenseId(bean.getLicense());
		
		if (  bean.getRoleId()>0 ){
			//id from user admin screen
			pu.setRole( roleRepository.findOne(bean.getRoleId())); 
		}else{
			if (null == bean.getRole()){
				pu.setRole(roleRepository.getRoleByType(RoleType.ROLE_NOT_VERIFIED) );
			}else{
				//need to work to deal with physician assistant etc etc!!
				pu.setRole(roleRepository.getRoleByType(RoleType.valueOf(bean.getRole()))); 
			}
		}	
	    
		pu.setActive( bean.isActive()   );
		//if Admin de-activates a user change their role to NOT_VERIFIED -- just in case they forgot to.
		if (!pu.getActive()){
			pu.setRole(roleRepository.getRoleByType(RoleType.ROLE_NOT_VERIFIED) );
		}
		
		pu.setVerfiedSystem( bean.isVerified()   );
		pu.setVerfiedManual( bean.isVerifiedManual()   );
	 	pu.setStateLicensed(bean.getStateLicensed());
		pu.setUserName(bean.getUserName());
		pu.setUpdatedUserId(securityService.getCurrentUser().getUsername());
		PolstUser p = polstUserRepository.save(pu);
		 
		bean.setUserId(String.valueOf(p.getId() ));
		 	
		return bean;
		
	}


	
	
	/**
	 *  
	 *  Verify User
	 * 
	 *  
	 * 
	 */
	
	public UserBean verifyUser(String loginUserName, String license){
		 
	 	UserBean bean = new UserBean();
	 	
		//if user didn't enter a dash -- add it.
	 	char dash='-';
		if(license.indexOf(dash) <0){
			license = formatLicense(license);
		}
		
	 	//should always be null
	 	PolstUser user =   polstUserRepository.findUserByUserName(loginUserName);
		if  (null != user ){
			//just in case we have a user who hasn't been validated.. 
			bean.setUserId(String.valueOf(user.getId()) );
		}
		DoplView doplRecord = doplViewRepository.findDoplRecordByLicense(license);
		 if (null != user && !user.getActive()){
			 LOG.warn(loginUserName + " Logged In User was previously In-Activated, Admin needs to update record. ");
			 auditService.createLoginAuditRow(securityService.getCurrentUser(), AuditAction.INACTIVE_USER," Logged In User was IN-ACTIVED", Login.FAILURE, license, "", "", "");
	 	 } else if ( null == doplRecord){
			 LOG.warn(loginUserName + " Logged In User could not be verified against DOPL database ");
			 auditService.createLoginAuditRow(securityService.getCurrentUser(), AuditAction.COULD_NOT_VERIFY," Logged In User could not be verified against DOPL database ", Login.FAILURE, license, "", "", "");
		 }else{
			 LOG.debug(loginUserName + " has been successfully verified against DOPL database");
			 bean.setFirstName(doplRecord.getFirstName() );
			 bean.setLastName(doplRecord.getLastName());
			 bean.setLicense(license);
			 bean.setEmail(doplRecord.getEmail());
			 bean.setActive(user.getActive());
			 bean.setVerified(true);
			 bean.setStateLicensed(doplRecord.getState());
			 String profession = doplRecord.getProfessionName().replaceAll("\\s","");
		 	 bean.setRole(Profession.valueOf(profession).getRoleType().toString() );  
			 LOG.debug("Set new user to role type: " + bean.getRole() );
			 bean.setUserName(loginUserName);
			 bean.setPhoneNumber(doplRecord.getPhone());
			 saveUser(bean);
			 auditService.createLoginAuditRow(securityService.getCurrentUser(), AuditAction.USER_VERIFIED, "User successfully verified into system", Login.SUCCESS, license, doplRecord.getFirstName(), doplRecord.getLastName(),doplRecord.getPhone());
	    }
	 	
		return bean;
	}  

	
	 
	
	public UserBean verifyEmt(String loginUserName, String badgeNumber){
		 
	 	UserBean bean = new UserBean();
	 	//should always be null
	 	PolstUser user =   polstUserRepository.findUserByUserName(loginUserName);
		if  (null != user ){
			//just in case we have a user who hasn't been validated.. 
			bean.setUserId(String.valueOf(user.getId()) );
		}
		 
		EmtView emtRecord = emtViewRepository.findEmtByBadgeNumber(badgeNumber);
 
		
		 if ( null == emtRecord){
			 LOG.warn(loginUserName + " Logged In User could not be verified against DOPL database ");
			 auditService.createLoginAuditRow(securityService.getCurrentUser(), AuditAction.COULD_NOT_VERIFY," Logged In User could not be verified against POLARIS/EMT database ", Login.FAILURE, "", user.getFirstName(), user.getLastName(), user.getPhoneNumber());
		 }else{
			 LOG.debug(loginUserName + " has been successfully verified against POLARIS/EMT database");
			 bean.setUserName(loginUserName);
			 bean.setFirstName(emtRecord.getFirstName() );
			 bean.setLastName(emtRecord.getLastName());
			 bean.setLicense(badgeNumber);
			 bean.setEmail(emtRecord.getEmail());
			 bean.setActive(true);
			 bean.setVerified(true);
			 bean.setStateLicensed(emtRecord.getState());
		 	 bean.setRole(RoleType.ROLE_EMS.toString());  
			 LOG.debug("Set new user to role type: " + bean.getRole() );
			 bean.setPhoneNumber(null==emtRecord.getHomePhone()? (null==emtRecord.getWorkPhone()? emtRecord.getMobilePhone() : emtRecord.getWorkPhone()   ): emtRecord.getHomePhone()  );
			 saveUser(bean);
			 auditService.createLoginAuditRow(securityService.getCurrentUser(), AuditAction.USER_VERIFIED, "User successfully verified into system", Login.SUCCESS, "", emtRecord.getFirstName(), emtRecord.getLastName(),emtRecord.getEmail());
	    }
	 	
		return bean;
	}  
	
	
	
	/**
	 * Search Users -> generated by Search text boxes.
	 */
	@Override
	public Page<PolstUser> searchUsers(SearchBean searchBean)   {
		SearchParameters params =  searchService.prepareQueryParameters(searchBean, securityService.getCurrentUser().getUsername(), RoleType.ROLE_ADMIN);
		Pageable  pageable = searchService.constructPageSpecification(searchBean);
		Page<PolstUser> requestedPage  = polstUserRepository.findAll( PolstUserSpecifications.recordsAreLike(params ), pageable);
	
	 	return requestedPage;
	}
	
	/**
	 * getLicenseId to display on EPOLST form.
	 * @param userLoginName
	 * @return
	 */
	public String getLicenseId(String userLoginName){
		PolstUser user = polstUserRepository.findUserByUserName(userLoginName);
		return user.getLicenseId();
	}
	
	
	public String formatLicense(String license){
		String lic = license.replaceAll("\\s+","");
		StringBuffer fLic = new StringBuffer();
		String prefix = lic.substring(0, lic.length()-4);
		String suffix = lic.substring(lic.length() - 4);
	
		fLic.append(prefix);
		fLic.append("-");
		fLic.append(suffix);
		
		
		return fLic.toString();
		
	}

	@Override
	public boolean isCurrentUser(String userName) {
		return ( null != polstUserRepository.findUserByUserName(userName)  );
	}

	@Override
	public UserBean verifyLicense(String roleId, String license) {
		
		PolstUser polstUser = polstUserRepository.findUserByUserLicenseBadge(license);
	 	
		Role externalView = roleRepository.findOne(Integer.parseInt(roleId));
		UserBean user = new UserBean();
		user.setLicense(license);
		//search ePOLST DB first..
		if (null != polstUser) {
			user=convertToBean(polstUser);
		}else if (RoleType.ROLE_EMS.equals(externalView.getRoleType())) {
			//look for emt badge id in EPOLST_EMT_VW
			EmtView emt = emtViewRepository.findEmtByBadgeNumber(license);
			if(null != emt) {
				user.setFirstName(emt.getFirstName() );
				user.setLastName(emt.getLastName());
				user.setEmail(emt.getEmail());
				user.setPhoneNumber(emt.getWorkPhone());
				user.setStateLicensed(emt.getState());
				user.setVerified(Boolean.FALSE);
				user.setActive(Boolean.FALSE);
				user.setVerifiedManual(Boolean.FALSE);
				user.setRoleId(externalView.getId());
				user.setRole(externalView.getRoleType().toString());
			}	
		}else {
			//look for dopl license number in DOPL_VIEW
			DoplView doplPerson = doplViewRepository.findDoplRecordByLicense(license);
			if (null != doplPerson) {
				user.setFirstName(doplPerson.getFirstName() );
				user.setLastName(doplPerson.getLastName());
				user.setEmail(doplPerson.getEmail());
				user.setPhoneNumber(doplPerson.getPhone());
				user.setStateLicensed(doplPerson.getState());
				user.setVerified(Boolean.FALSE);
				user.setActive(Boolean.FALSE);
				user.setVerifiedManual(Boolean.FALSE);
				
				Role userRole;
				//dopl profession might be ROLE_MEDCERT or ROLE_ADMIN
				if(Profession.Nurse.toString().equals(doplPerson.getProfessionName() ) || Profession.SocialWork.toString().equals(doplPerson.getProfessionName().replaceAll("\\s+",""))    ){
					userRole = roleRepository.getRoleByType(RoleType.ROLE_MEDSTAFF);
				}else {
					userRole = roleRepository.getRoleByType(RoleType.ROLE_MEDCERT);
				}
				user.setRoleId(userRole.getId());
				user.setRole(userRole.getRoleType().toString());
			}	
		}
		
		
		return user;
	}
	
	 
	
	
}