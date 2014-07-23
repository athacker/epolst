package gov.utah.epolst.model.enums;

public enum Profession {
	Physician (RoleType.ROLE_MEDCERT),
	SocialWork(RoleType.ROLE_MEDSTAFF),
	Nurse(RoleType.ROLE_MEDSTAFF),
	PhysicianAssistant(RoleType.ROLE_MEDCERT),
	OsteopathicPhysician (RoleType.ROLE_MEDCERT); 
	
	private RoleType roleType;
	
	Profession(RoleType roleType){
		this.roleType = roleType;
	}
		
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	
	
	
	
}
