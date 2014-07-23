package gov.utah.epolst.model;
 

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

 public class PolstUserDetails extends User{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 111113L;

	/** The agency name. */
	private String agencyName;
	
	/** The name. */
	private String name;
	
	/** The id. */
	private Long id;
	
	/** The allowed agencies. */
	private String allowedAgencies;
	
	/** The agency. */
	private String agency;
	
	/** The admin. */
	private Boolean admin;
	
 
	
	
	public PolstUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	
	/**
	 * Gets the agency name.
	 *
	 * @return the agency name
	 */
	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * Sets the agency name.
	 *
	 * @param agencyName the new agency name
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the allowed agencies.
	 *
	 * @return the allowed agencies
	 */
	public String getAllowedAgencies() {
		return allowedAgencies;
	}

	/**
	 * Sets the allowed agencies.
	 *
	 * @param allowedAgencies the new allowed agencies
	 */
	public void setAllowedAgencies(String allowedAgencies) {
		this.allowedAgencies = allowedAgencies;
	}

	 

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	/**
	 * Gets the admin.
	 *
	 * @return the admin
	 */
	public Boolean getAdmin() {
		return admin;
	}

	/**
	 * Sets the admin.
	 *
	 * @param admin the new admin
	 */
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	

}
