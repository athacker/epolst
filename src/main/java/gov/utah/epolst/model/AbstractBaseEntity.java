package gov.utah.epolst.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractBaseEntity implements Serializable {
 
	

	@Column(name = "updated_timestamp")
	private Date updatedDate;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "added_user_id")
	private String addedUserId;

	@Column(name = "updated_user_id")
	private String updatedUserId;

	 

	public String getAddedUserId() {
		return addedUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
 

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getUpdatedUserId() {
		return updatedUserId;
	}

 
	@PrePersist
	void prePersist() {
		 createdDate = new Date();
		 preUpdate();
	}

	@PreUpdate
	void preUpdate() {
		 updatedDate = new Date();
	}

	public void setAddedUserId(String addedUserId) {
		this.addedUserId = addedUserId;
	}
 

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

 
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

}
