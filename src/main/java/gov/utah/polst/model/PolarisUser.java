package gov.utah.polst.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "polaris_user")
@SuppressWarnings("serial")
@PersistenceContext(unitName="punitPolaris")
public class PolarisUser extends AbstractBaseEntity {

	private static final long serialVersionUID = 7526782295622776147L;

	public PolarisUser() {
		super();
	}

	public PolarisUser(String name_, Role role_) {
		userName = name_;
		role = role_;
	}

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Transient
	private Role role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
