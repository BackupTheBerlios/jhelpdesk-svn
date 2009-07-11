package de.berlios.jhelpdesk.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

public class LogoutController implements Controller {

	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		
		request.getSession().removeAttribute( "user" );
		request.getSession().setAttribute( "logged", Boolean.FALSE );
		request.getSession().invalidate();
		return new ModelAndView( new RedirectView( "/login.html", true ) );
	}
}
