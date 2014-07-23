package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.DiscussionType;

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
@Table(name = "polst_discussion")
@SuppressWarnings("serial")
@PersistenceContext(unitName = "punit")
public class PolstDiscussion extends AbstractBaseEntity {

	private static final long serialVersionUID = 752388956228811777L;

	@Id
	@SequenceGenerator(schema="HL_POLST", name = "POLST_DISC_SEQ", sequenceName="POLST_DISC_SEQ", allocationSize=1)
	@GeneratedValue(generator = "POLST_DISC_SEQ", strategy = GenerationType.AUTO)
 	@Column(name="ID" , nullable = false)
	private Integer id;
	
	
	@Column(name="polst_id",   insertable = false, updatable = false)
	private Integer polstId;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "polst_id")
	private Polst polst;
		
	//patient, parent, surrogate, other
	@Column(name = "discussion_type")
	@Enumerated(EnumType.STRING)
	private DiscussionType discussionType;

	//family or legal authority
	@Column(name = "discussors_relationship") 
	private String discussorsRelationship;  

	@Column(name = "discussors_name")
	private String discussorsName;

	@Column(name = "discussors_phone")
	private String discussorsPhone;

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

	public DiscussionType getDiscussionType() {
		return discussionType;
	}

	public void setDiscussionType(DiscussionType discussionType) {
		this.discussionType = discussionType;
	}

	public String getDiscussorsRelationship() {
		return discussorsRelationship;
	}

	public void setDiscussorsRelationship(String discussorsRelationship) {
		this.discussorsRelationship = discussorsRelationship;
	}

	public String getDiscussorsName() {
		return discussorsName;
	}

	public void setDiscussorsName(String discussorsName) {
		this.discussorsName = discussorsName;
	}

	public String getDiscussorsPhone() {
		return discussorsPhone;
	}

	public void setDiscussorsPhone(String discussorsPhone) {
		this.discussorsPhone = discussorsPhone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

 

	
	
	
	
	
}
