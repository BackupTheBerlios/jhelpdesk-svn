package de.berlios.jhelpdesk.web.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class IndexViewController implements Controller {

	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		return new ModelAndView( "help/index" );
	}
}
