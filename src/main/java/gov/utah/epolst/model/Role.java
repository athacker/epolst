package gov.utah.epolst.model;

import gov.utah.epolst.model.enums.RoleType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="role")
 public class Role extends AbstractBaseEntity {
	private static final long serialVersionUID = 7523432295622776147L;
	public Role(){
		super();
	}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	public Role(RoleType roleType){
		 this.roleType=roleType;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	 
	
	 
	 
	
	
	
}
