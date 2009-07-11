package com.jjhop.helpdesk.web.taglib.auth;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jjhop.helpdesk.model.Role;
import com.jjhop.helpdesk.model.User;

public class AuthorizeTag extends TagSupport {
	private static final long serialVersionUID = 2870805082059648632L;
	private static Log log = LogFactory.getLog( AuthorizeTag.class );
	private int roleAsInt;
	
	@Override
	public int doStartTag() throws JspException {
		// return SKIP_BODY;
		User user =
			( User ) pageContext.getSession().getAttribute( "user" );
		if( user == null ) return SKIP_BODY;
		if( user.getUserRole().toInt() < roleAsInt ) return SKIP_BODY;
		
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public void setRequiredRole( String role ) {
		try {
			roleAsInt = Integer.parseInt( role );
			if( ( roleAsInt != Role.BUGKILLER.toInt() )
				&&
				( roleAsInt != Role.CLIENT.toInt() )
				&&
				( roleAsInt != Role.MANAGER.toInt() )
			) {
				throw new RuntimeException( "Dopuszczalne wartosci atrybutu requiredRole to: 1, 10, 100." );
			}
		} catch ( NumberFormatException nfe ) {
			log.error( nfe.getMessage() );
			throw nfe;
		}
	}
}
