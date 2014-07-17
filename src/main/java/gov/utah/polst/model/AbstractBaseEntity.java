package gov.utah.polst.model;

import gov.utah.polst.service.SecurityService;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractBaseEntity implements Serializable {

	@Autowired
	@Transient
	private SecurityService securityService;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "UPDATED_TIMESTAMP")
	private Date updatedDate;

	@Column(name = "ADDED_TIMESTAMP")
	private Date createdDate;

	@Column(name = "ADDED_USER_ID")
	private String addedUserId;

	@Column(name = "UPDATED_USER_ID")
	private String updatedUserId;

	public boolean equals(Object other) {
		// memory address equals check
		if (this == other) {
			return true;
		}

		// class type equals check
		if (!(other instanceof AbstractBaseEntity)) {
			return false;
		}
		if (!(this.getClass().isInstance(other))) {
			return false;
		}

		final AbstractBaseEntity otherAbstractEntity = (AbstractBaseEntity) other;

		return new EqualsBuilder().append(this.getId(),
				otherAbstractEntity.getId()).isEquals();
	}

	public String getAddedUserId() {
		return addedUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Integer getId() {
		return id;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getUpdatedUserId() {
		return updatedUserId;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@PrePersist
	void prePersist() {
		addedUserId = "system"; //securityService.getCurrentUser().getUsername();
		createdDate = new Date();
		preUpdate();
	}

	@PreUpdate
	void preUpdate() {
		updatedUserId =  "system"; //securityService.getCurrentUser().getUsername();
		updatedDate = new Date();
	}

	public void setAddedUserId(String addedUserId) {
		this.addedUserId = addedUserId;
	}
 

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

}
