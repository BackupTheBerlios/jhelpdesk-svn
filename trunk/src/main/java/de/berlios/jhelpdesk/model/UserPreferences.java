package de.berlios.jhelpdesk.model;

import java.util.Locale;

public class UserPreferences {
	
	// Look & Feel
	private String theme;
	private String startingPage;
	private String lang;
	
	// Ustawieni list
	
	
	// Powiadomienia
	
	
	/** @return the lang */
	public String getLang() {
		return lang;
	}
	
	/** @param lang the lang to set */
	public void setLang( String lang ) {
		this.lang = lang;
	}
	
	/** @return the startingPage */
	public String getStartingPage() {
		return startingPage;
	}
	/** @param startingPage the startingPage to set */
	public void setStartingPage( String startingPage ) {
		this.startingPage = startingPage;
	}
	
	/** @return the theme */
	public String getTheme() {
		return theme;
	}
	
	/** @param theme the theme to set */
	public void setTheme( String theme ) {
		this.theme = theme;
	}
	
	public Locale getLocale() {
		return (!lang.isEmpty()) ? new Locale( lang ) : Locale.getDefault(); 
	}
	
}
