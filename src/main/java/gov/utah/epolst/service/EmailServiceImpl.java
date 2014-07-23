package gov.utah.epolst.service;

import gov.utah.epolst.model.EmailHistory;
import gov.utah.epolst.model.EmailHistoryBean;
import gov.utah.epolst.model.EmailTemplate;
import gov.utah.epolst.model.Polst;
import gov.utah.epolst.model.PolstUser;
import gov.utah.epolst.repository.EmailHistoryRepository;
import gov.utah.epolst.repository.EmailTemplateRepository;
import gov.utah.epolst.repository.PolstUserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



/**
 * Sends system generated emails.
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService{
	
	@Autowired ApplicationPropertyService applicationPropertyService;
	@Autowired EmailTemplateRepository emailTemplateRepository;
	@Autowired EmailHistoryRepository emailHistoryRepository;
 	@Autowired PolstUserRepository polstUserRepository;
	@Autowired PolstUserService polstUserService;
	@Autowired UtilityService utilityService;
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl .class);
	private static final String PENDING_NOTICE="PENDING_NOTICE";
	
	
	/**
	 * When an ePOLST has been prepared, then we need to alert the certifying physician
	 * that they have an ePOLST waiting their certification that will make them active.
	 * We also allow users to send reminder emails.
	 * 
	 */
	@Override
	@Transactional
	public Boolean sendPolstPendingNotification(Polst polst, String loggedInUser ) {
		
		Boolean success=Boolean.TRUE;
		Properties emailProperties = new Properties();
		PolstUser physician = polstUserRepository.getPhysicianForPatient( polst.getPatient().getId() ) ;
		String[] recipients = { physician.getEmail() }; 
	  
	
		/**
		 * load email properties from application_properties table
		 */
		Map<String,String> systemProperties =  applicationPropertyService.getPropertyMap();
		String emailOverride = systemProperties.get("email.override");
		
		/**
		 * load the template for PENDING_NOTIFICATION
		 */
		EmailTemplate template  = emailTemplateRepository.findTemplateByName(PENDING_NOTICE);
	 
		emailProperties.put("mail.smtp.host", systemProperties.get("email.smtp.host"));

        Session session = Session.getDefaultInstance( emailProperties );
        Message mimeMessage = new MimeMessage(session);
 	   
        InternetAddress from = new InternetAddress();
 	    from.setAddress(systemProperties.get("email.address.from"));
 	    try{
 	    	from.setPersonal(systemProperties.get("email.address.from.personal"));
 	   
 	    	InternetAddress[] recipientAddress = new InternetAddress[recipients.length];
 	    
	 	    int i=0;
	 	    for (String recipient : recipients) {
	 	    	String toAddress = StringUtils.isEmpty(emailOverride) ? recipient: emailOverride ;
	 	    	recipientAddress[i] = new InternetAddress(toAddress);
	 	    	i++;
	 	    	EmailHistory hx = new EmailHistory();
	 	    	hx.setDateSent(new Date());
	 	    	hx.setPatientId(polst.getPatientId());
	 	    	hx.setPolstId(polst.getId());
	 	    	hx.setReceiverUserId(polst.getPatient().getPhysicianUserName());
	 	    	hx.setRequesterUserId(loggedInUser);
	 	    	hx.setTemplateId(template.getId());
	 	    	emailHistoryRepository.save(hx);
	 	    }
	 	    
	 	    mimeMessage.setRecipients(RecipientType.TO,recipientAddress);
 	        mimeMessage.setFrom(from);
	        mimeMessage.setSubject("Patient Polst Form requires Certification");
	        
	        
	        mimeMessage.setRecipients(RecipientType.TO,recipientAddress);
 	        mimeMessage.setFrom(from);
	        mimeMessage.setSubject(template.getTemplateSubject());
	       
	        mimeMessage.setContent(template.getTemplateText(), "text/plain");
	        Transport.send(mimeMessage);
 	    
 	    }catch(Exception e){
	    	LOG.error("Exception was caught while sending eMail notification.",e);
	    	success=Boolean.FALSE;
 	    }
 	    
 	    return success;    
	}


	@Override
	public List<EmailHistoryBean> getEmailHistoryForPolst(Integer polstId) {
		 List<EmailHistoryBean> history = new  ArrayList<EmailHistoryBean>();
		 List<EmailHistory> hxs =	 emailHistoryRepository.getEmailHistoryForPolst(polstId);
		 for (EmailHistory hx: hxs) {
			 EmailHistoryBean bean = new EmailHistoryBean();
			 bean.setDateSent(utilityService.formatDateToString(hx.getDateSent()));
			 bean.setReceiverName(polstUserService.getUserFullName(hx.getReceiverUserId()  ));
			 bean.setRequestorNameString(polstUserService.getUserFullName(hx.getRequesterUserId()  ));
			 bean.setEmailHistoryId(hx.getId());
			 bean.setPolstId(hx.getPolstId());
			 history.add(bean);
		 }
		 	 
		 	 
		 	 return history;
	}

	
}
