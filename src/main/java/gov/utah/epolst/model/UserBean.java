package gov.utah.epolst.model;

public class UserBean {

		private boolean active;
		private String displayName;
		private String email;
		private String firstName;
		private String lastName;
		private String license;
		private String phoneNumber;
		private String role;
		private int roleId;
		private String stateLicensed;
		private String userId;
		private String userName;
		private boolean verified;
		private boolean verifiedManual;
		
		
		public boolean isActive() {
			return active;
		}
		public void setActive(boolean active) {
			this.active = active;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getLicense() {
			return license;
		}
		public void setLicense(String license) {
			this.license = license;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public int getRoleId() {
			return roleId;
		}
		public void setRoleId(int roleId) {
			this.roleId = roleId;
		}
		public String getStateLicensed() {
			return stateLicensed;
		}
		public void setStateLicensed(String stateLicensed) {
			this.stateLicensed = stateLicensed;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public boolean isVerified() {
			return verified;
		}
		public void setVerified(boolean verified) {
			this.verified = verified;
		}
		public boolean isVerifiedManual() {
			return verifiedManual;
		}
		public void setVerifiedManual(boolean verifiedManual) {
			this.verifiedManual = verifiedManual;
		}
		
		
		public String getDisplayName() {
			StringBuffer sb = new StringBuffer();
			sb.append(getLastName());
			sb.append(", ");
			sb.append(getFirstName());
			
			return sb.toString();
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		
		
	 
		
		
		
		
		
		
	
}
