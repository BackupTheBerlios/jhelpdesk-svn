package de.berlios.jhelpdesk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.exception.NotAuthorizedAccessException;

public class BugkillerFilter implements Filter {

	public void init( FilterConfig config ) throws ServletException {

	}

	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
		HttpServletRequest req = ( HttpServletRequest ) request;
		HttpSession sess = req.getSession( );
		User user = ( User )sess.getAttribute( "user" );
		if( user == null )
			throw new NotAuthorizedAccessException( "not authorized access!" );
		if( user.getUserRole().toInt() < Role.BUGKILLER.toInt() )
			throw new NotAuthorizedAccessException( "not authorized access!" );
		// ( ( HttpServletResponse ) response ).addHeader( "access", "403" );
		chain.doFilter( request, response );
	}

	public void destroy() {

	}
}
