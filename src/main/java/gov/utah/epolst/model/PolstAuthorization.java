package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.AgentType;
import gov.utah.epolst.model.enums.AuthorizationType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

 
@Entity
@Table(name = "polst_authorization")
@SuppressWarnings("serial")
@PersistenceContext(unitName = "punit")
public class PolstAuthorization extends AbstractBaseEntity {
	
	
	private static final long serialVersionUID = 752388956228866777L;

	@Id
	@SequenceGenerator(schema="HL_POLST", name = "POLST_AUTH_SEQ", sequenceName="POLST_AUTH_SEQ", allocationSize=1)
	@GeneratedValue(generator = "POLST_AUTH_SEQ", strategy = GenerationType.AUTO)
 	@Column(name="ID" , nullable = false)
	private Integer id;
		
	@Column(name="polst_id",   insertable = false, updatable = false)
	private Integer polstId;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "polst_id")
	private Polst polst;
	
	@Column(name = "authorizer_type")
	@Enumerated(EnumType.STRING)
	private AuthorizationType authorizerType;
	
	@Column(name="authorizer_name")
	private String authorizerName;
	
	@Column(name="authorizer_phone")
	private String authorizerPhone="";
	
	@Column(name="date_authorized")
	private Date dateAuthorized;
	
	@Column(name="appointed_agent")
	@Enumerated(EnumType.STRING)
	private AgentType appointedAgent;
	
	@Column(name="relationship")
	private String relationship;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
		
 
	
	public Integer getPolstId() {
		return polstId;
	}

	public void setPolstId(Integer polstId) {
		this.polstId = polstId;
	}

	public Polst getPolst() {
		return polst;
	}

	public void setPolst(Polst polst) {
		this.polst = polst;
	}

	public AuthorizationType getAuthorizerType() {
		return authorizerType;
	}

	public void setAuthorizerType(AuthorizationType authorizerType) {
		this.authorizerType = authorizerType;
	}

	public String getAuthorizerName() {
		return authorizerName;
	}

	public void setAuthorizerName(String authorizerName) {
		this.authorizerName = authorizerName;
	}

	public String getAuthorizerPhone() {
		return authorizerPhone;
	}

	public void setAuthorizerPhone(String authorizerPhone) {
		this.authorizerPhone = authorizerPhone;
	}

	public Date getDateAuthorized() {
		return dateAuthorized;
	}

	public void setDateAuthorized(Date dateAuthorized) {
		this.dateAuthorized = dateAuthorized;
	}

	public AgentType getAppointedAgent() {
		return appointedAgent;
	}

	public void setAppointedAgent(AgentType appointedAgent) {
		this.appointedAgent = appointedAgent;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}	
	
	
}
