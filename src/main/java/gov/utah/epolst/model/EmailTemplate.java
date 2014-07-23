package gov.utah.epolst.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "EMAIL_TEMPLATE")
public class EmailTemplate {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	 
	@Column(name="template_name")
	private String templateName;
	
	@Column(name="template_subject")
	private String templateSubject;
	
	  
	@Column(name="template_text", length = 2000)
	private String templateText;

 

	public String getTemplateSubject() {
		return templateSubject;
	}

	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateText() {
		return templateText;
	}

	public void setTemplateText(String templateText) {
		this.templateText = templateText;
	}

	
	
	
}
