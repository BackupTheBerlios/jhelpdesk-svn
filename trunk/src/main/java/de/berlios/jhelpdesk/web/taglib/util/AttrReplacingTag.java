/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright: (C) 2006 jHelpdesk Developers Team
 */
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
		log.debug("doStartTag()");
		String[] values = 
			pageContext.getRequest().getParameterValues(paramGetName);
		if ((paramReturnName == null) || (paramReturnName.length() < 1))
			paramReturnName = "_".concat(paramGetName);
		pageContext.getRequest().setAttribute(paramReturnName, values);
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		log.debug("doEndTag()");
		return EVAL_PAGE;
	}

	/**
	 * @param paramGetName The paramGetName to set.
	 */
	public void setParamGetName(String paramGetName) {
		log.debug("setParamGetName( String paramGetName )");
		this.paramGetName = paramGetName;
	}

	/**
	 * @param paramReturnName The paramReturnName to set.
	 */
	public void setParamReturnName(String paramReturnName) {
		log.debug("setParamReturnName( String paramReturnName )");
		this.paramReturnName = paramReturnName;
	}
}
