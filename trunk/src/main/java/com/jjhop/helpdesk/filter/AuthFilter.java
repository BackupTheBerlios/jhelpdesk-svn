package com.jjhop.helpdesk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

	public void init( FilterConfig filter ) throws ServletException {
	}

	public void doFilter( ServletRequest req, ServletResponse res,	FilterChain chain ) throws IOException, ServletException {
		HttpServletRequest servletRreq = ( HttpServletRequest ) req;
		HttpSession sess = servletRreq.getSession( );
		
		String reqUrl = ( ( HttpServletRequest ) servletRreq ).getRequestURL().toString().toLowerCase();
		if ( reqUrl.endsWith( "/login.html" ) || reqUrl.endsWith( ".ico" )
				|| reqUrl.endsWith( ".gif" ) || reqUrl.endsWith( ".png" )
				|| reqUrl.endsWith( ".htc" ) )
		{
			chain.doFilter( req, res );
			return;
		}
		Boolean logged = ( Boolean ) sess.getAttribute( "logged" ); 
		
		if( ( logged == null ) || ( !logged.booleanValue() ) ) {
			sess.invalidate();
			( ( HttpServletResponse ) res ).sendRedirect( 
				servletRreq.getContextPath() + "/login.html" );
			return;
		}
		chain.doFilter( req, res );
	}

	public void destroy() {
	}
}
