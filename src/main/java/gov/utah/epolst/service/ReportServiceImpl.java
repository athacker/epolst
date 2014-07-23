package gov.utah.epolst.service;

import gov.utah.epolst.model.Polst;
import gov.utah.epolst.model.PolstAuthorization;
import gov.utah.epolst.model.PolstDiscussion;
import gov.utah.epolst.model.PolstReportBean;
import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.model.enums.Antibiotics;
import gov.utah.epolst.model.enums.AuthorizationType;
import gov.utah.epolst.model.enums.CodeStatus;
import gov.utah.epolst.model.enums.DiscussionType;
import gov.utah.epolst.model.enums.FeedingTube;
import gov.utah.epolst.model.enums.IvFluids;
import gov.utah.epolst.model.enums.MedicalCare;
import gov.utah.epolst.model.enums.PolstStatus;
import gov.utah.epolst.repository.PolstRepository;
import gov.utah.epolst.repository.PolstUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportServiceImpl implements ReportService{

	@Autowired PolstRepository polstRepository;
	@Autowired PolstUserRepository polstUserRepository;
	@Autowired UtilityService utilityService;
	
	private static String checked = " X ";
	
	@Override
	public PolstReportBean generateReportInformation(Integer polstId) {
		PolstReportBean bean = new PolstReportBean();
		Polst p = polstRepository.findOne(polstId);
		bean.setPatientId(p.getPatientId());
		bean.setPolstStatus(p.getPolstStatus());
		//CODE STATUS
		if (null != p.getCodeStatus()){
			if(p.getCodeStatus().equals(CodeStatus.ATTEMPT)){
				bean.setCodeStatusAttempt(checked);
			} else if (p.getCodeStatus().equals(CodeStatus.DNR)){
				bean.setCodeStatusDoNotAttempt(checked);
			}
			bean.setCodeStatusInstructions(p.getCodeStatusInstructions());
		}
		
		//MEDICAL CARE
		if (null != p.getMedicalCare()){
			if (p.getMedicalCare().equals(MedicalCare.COMFORT)){
				bean.setMedCareComfort(checked);
			}else if (p.getMedicalCare().equals(MedicalCare.LIMITED)){
				bean.setMedCareLimited(checked);
			}else if(p.getMedicalCare().equals(MedicalCare.FULL_TREATMENT)){
				bean.setMedCareFull(checked);
			}
			bean.setMedicalCareInstructions(p.getMedicalCareInstructions());
		}
		
		//ANTIBIOTICS
		if (null != p.getAntibiotics()){
			if (p.getAntibiotics().equals(Antibiotics.YES)){
				bean.setAntibioticsYes(checked);
			}else if (p.getAntibiotics().equals(Antibiotics.NO)){
				bean.setAntibioticsNo(checked);
			}
			bean.setAntibioticInstructions(p.getAntibioticInstructions());
			
		}
		
		//NUTRITION
		if (null != p.getFeedingTube()){
			if (p.getFeedingTube().equals(FeedingTube.NO_FEEDING_TUBE)){
				bean.setFeedingTubeNo(checked);
			}else if(p.getFeedingTube().equals(FeedingTube.DEFINED_TRIAL_PERIOD)){
				bean.setFeedingTubeDefinedTrial(checked);
			}else if (p.getFeedingTube().equals(FeedingTube.LONG_TERM)){
				bean.setFeedingTubeLongTerm(checked);
			}
		}
		if (null != p.getIvFluids()){
			if (p.getIvFluids().equals(IvFluids.NO_IV_FLUIDS)){
				bean.setIvNo(checked);
			}else if (p.getIvFluids().equals(IvFluids.DEFINED_TRIAL_PERIOD)){
				bean.setIvDefinedTrial(checked);
			}else if (p.getIvFluids().equals(IvFluids.LONG_TERM_IV_FLUIDS)){
				bean.setIvLongTerm(checked);
			}
		}
		bean.setNutritionInstructions(p.getNutritionInstuctions());
		
		if (null != p.getPolstDiscussions() && !p.getPolstDiscussions().isEmpty()){
				for (PolstDiscussion pd: p.getPolstDiscussions()){
					pd.setDiscussorsPhone(utilityService.formatPhone(pd.getDiscussorsPhone()));
					if (DiscussionType.PATIENT.equals(pd.getDiscussionType()) ){
						bean.setDiscussedPatient(checked);
					}else if( DiscussionType.PARENT_OF_MINOR.equals(pd.getDiscussionType())   ){
						bean.setDiscussedParent(checked);
						bean.getDiscussedParentInfo().add(pd);
					}else if(DiscussionType.SURROGATE.equals(pd.getDiscussionType()) ){
						bean.setDiscussedSurrogate(checked);
						bean.getDiscussedSurrogateInfo().add(pd);
				 	}else if (DiscussionType.OTHER.equals(pd.getDiscussionType())){
						bean.setDiscussedOther(checked);
						bean.getDiscussedOtherInfo().add(pd);
					}
				 }//for
	 	}//null polst discussions
		
		
		//ADVANCED DIRECTIVE
		if ( null != p.getAdvancedDirective() &&  null != p.getAdvancedDirectiveCompleteDate() ){
			bean.setAdvancedDirectiveYes(checked);
		}else{
			bean.setAdvancedDirectiveNo(checked);
		}
		
		bean.setAdvancedDirectiveInfo(p.getAdvancedDirectiveInstructions());
		
		//SECTION F Medical Condition
		bean.setMedicalCondition(p.getMedicalCondition());
						
		//SIGNATURE SECTION
		if (null != p.getPreparedBy()) {
			bean.setSignaturePreparerName(p.getPreparedBy());
			bean.setSignaturePreparedDate( utilityService.formatDateToString(p.getPreparedDate() )   );
		} 
		
		PolstUser physician = polstUserRepository.findUserByUserName(p.getPatient().getPhysicianUserName());
		bean.setSignaturePhysicianName(physician.getDisplayName());
		bean.setSignaturePhysicianLicenseNumber(physician.getLicenseId());
		if (null != p.getCertifiedDate()) {
			bean.setSignaturePhysicianDateCertified(utilityService.formatDateToString( p.getCertifiedDate() ));
		}
		
		//put patient data into authorization section of .pdf
		PolstAuthorization ptAuth = new PolstAuthorization();
		ptAuth.setAuthorizerName(p.getPatient().getFullName());
		if (null != p.getPatient().getPatientPhoneNumber()) {
			ptAuth.setAuthorizerPhone(utilityService.formatPhone( p.getPatient().getPatientPhoneNumber() )) ;
		}
		
		bean.getSignatureParentAuthorizationInfo().add(ptAuth);
		if (null != p.getPolstAuthorizations() && !p.getPolstAuthorizations().isEmpty()){
		 
			for (PolstAuthorization pa: p.getPolstAuthorizations()){
					pa.setAuthorizerPhone(utilityService.formatPhone(pa.getAuthorizerPhone()));
				 if(AuthorizationType.PARENT_OF_MINOR.equals(pa.getAuthorizerType())  ){
			    	bean.getSignatureParentAuthorizationInfo().add(pa);
			 	}else if (AuthorizationType.SURROGATE.equals(pa.getAuthorizerType())){
			 		bean.getSignatureSurrogateAuthorizationInfo().add(pa);
				}
			 }//for
	 	}//
		
	
		return bean;
	}

	
	
	
	
	
	
	
	
}
