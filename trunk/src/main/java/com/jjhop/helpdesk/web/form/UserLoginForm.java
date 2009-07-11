package com.jjhop.helpdesk.web.form;

/**
 * Klasa jest opakowanie formularza logowania
 * 
 * @author jjhop
*/
public class UserLoginForm {
	private String login;
	private String passw;
	
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
	 * @return Returns the passw.
	 */
	public String getPassw() {
		return passw;
	}
	/**
	 * @param passw The passw to set.
	 */
	public void setPassw(String passw) {
		this.passw = passw;
	}
}