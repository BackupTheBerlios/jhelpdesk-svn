package de.berlios.jhelpdesk.web.taglib.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AttrReplacingTag extends TagSupport {

	private static final long serialVersionUID = 2326189120695661635L;
	private static Log log = LogFactory.getLog( AttrReplacingTag.class );
	
	private String paramGetName;
	private String paramReturnName;
	
	@Override
	public int doStartTag() throws JspException {
		log.debug( "doStartTag()" );
		String[] values = 
			pageContext.getRequest().getParameterValues( paramGetName );
		if( ( paramReturnName == null ) || ( paramReturnName.length() < 1 ) )
			paramReturnName = "_".concat( paramGetName );
		pageContext.getRequest().setAttribute( paramReturnName, values );
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		log.debug( "doEndTag()" );
		return EVAL_PAGE;
	}

	/**
	 * @param paramGetName The paramGetName to set.
	 */
	public void setParamGetName( String paramGetName ) {
		log.debug( "setParamGetName( String paramGetName )" );
		this.paramGetName = paramGetName;
	}

	/**
	 * @param paramReturnName The paramReturnName to set.
	 */
	public void setParamReturnName( String paramReturnName ) {
		log.debug( "setParamReturnName( String paramReturnName )" );
		this.paramReturnName = paramReturnName;
	}
}
