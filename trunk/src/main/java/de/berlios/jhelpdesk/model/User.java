package de.berlios.jhelpdesk.model;

/**
 * @author jjhop
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
	
	public User() {
		
	}
	
	/**
	 * @param userId
	 * @param login
	 * @param firstName
	 * @param lastName
	 */
	public User(Long userId, String login, String firstName, String lastName) {
		this.userId = userId;
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
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
	public void setPassword(String password) {
		this.password = password;
	}

	/**
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
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

	public static boolean authorizeUser(String login, String passw) {
		return false;
	}

	public String toString() {
		return new StringBuilder("").append(firstName).append(" ").append(lastName).toString();
	}

	public String getFullName() {
		return toString();
	}
}
