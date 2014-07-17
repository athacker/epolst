package gov.utah.polst.model;

import gov.utah.polst.model.enums.RoleType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name="role")
 public class Role extends AbstractBaseEntity {
	private static final long serialVersionUID = 7523432295622776147L;
	public Role(){
		super();
	}
	
	public Role(RoleType roleType_){
		 this.roleType=roleType_;
	}
	
  
	@Column(name="role", nullable=false)
	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	
	
	
	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return roleType.toString();
	}
	
	
	 
	
	 
	 
	
	
	
}
