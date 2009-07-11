package de.berlios.jhelpdesk.model;

/**
 * @hibernate.class
 * 		table="hd_plain_user"
 * 
 * @author jjhop
 *
 */
public class User {
	private Long userId;
	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private Role userRole;
	private String email;
	private String phone; // numer staacjonarny
	private String mobile; // numer kom�rkowy
	private boolean isActive;
	
	/**
	 *
	 */
	public User() {
		
	}
	
	/**
	 * 
	 * @param userId
	 * @param login
	 * @param firstName
	 * @param lastName
	 */
	public User( Long userId, String login, String firstName, String lastName ) {
		this.userId = userId;
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * @hibernate.property
	 * 		column="login"
	 * 		type="java.lang.String"
	 * 		not-null="true"
	 * 
	 * @return Returns the login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login The login to set.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword( String password ) {
		this.password = password;
	}

	/**
	 * @hibernate.property
	 * 		column="hd_role"
	 * 		not-null="true"
	 * @return Returns the userRole.
	 */
	public Role getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole The userRole to set.
	 */
	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	/**
	 * @hibernate.id
	 * 		generator-class="sequence"
	 * 		column="user_id"
	 * 		type="java.lang.Long"
	 * 		not-null="true"
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @hibernate.property
	 * 		column="email"
	 * 		type="java.lang.String"
	 * 		length="128"
	 * 		access="property"
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @hibernate.property
	 * 		column="first_name"
	 * 		type="java.lang.String"
	 * 		length="64"
	 * 		access="property"
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @hibernate.property
	 * 		column="is_active"
	 * 		type="boolean"
	 * 		access="property"
	 * @return Returns the isActive.
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive The isActive to set.
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @hibernate.property
	 * 		column="last_name"
	 * 		type="java.lang.String"
	 * 		length="128"
	 * 		access="property"
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @hibernate.property
	 * 		column="mobile"
	 * 		type="java.lang.String"
	 * 		length="20"
	 * 		access="property"
	 * @return Returns the mobile.
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile The mobile to set.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @hibernate.property
	 * 		column="phone"
	 * 		type="java.lang.String"
	 * 		length="20"
	 * 		access="property"
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static boolean authorizeUser( String login, String passw ) {
		return false;
	}

	public String toString() {
		return new StringBuffer(""+firstName).append( " " ).append(""+lastName).toString();
	}
    
	public String getFullName() {
		return toString();
	}
}
