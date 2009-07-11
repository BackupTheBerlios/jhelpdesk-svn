package de.berlios.jhelpdesk.web.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ThemeResolver;

import de.berlios.jhelpdesk.model.User;

public class HelpdeskThemeResolver implements ThemeResolver {

	public String resolveThemeName( HttpServletRequest request ) {
		User user = ( User ) (request.getSession()).getAttribute( "user" );
		// TODO: jesli user ustalony to nalezy wyciagnac jego preferencje
		return (user == null) ? "hd_blue_theme" : "hd_blue_theme";
	}

	public void setThemeName( HttpServletRequest request, HttpServletResponse response, String themeName ) {
	}
}
