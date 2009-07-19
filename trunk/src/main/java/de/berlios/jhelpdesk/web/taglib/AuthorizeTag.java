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
package de.berlios.jhelpdesk.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;

public class AuthorizeTag extends TagSupport {
	
	private static final long serialVersionUID = 2870805082059648632L;
	private static Log log = LogFactory.getLog(AuthorizeTag.class);
	private int roleAsInt;

	@Override
	public int doStartTag() throws JspException {
		// return SKIP_BODY;
		User user = (User) pageContext.getSession().getAttribute("user");
		if (user == null)
			return SKIP_BODY;
		if (user.getUserRole().toInt() < roleAsInt)
			return SKIP_BODY;

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public void setRequiredRole(String role) {
		try {
			roleAsInt = Integer.parseInt(role);
			if ((roleAsInt != Role.TICKETKILLER.toInt()) 
					&& (roleAsInt != Role.CLIENT.toInt())
					&& (roleAsInt != Role.MANAGER.toInt())) {
				throw new RuntimeException("Dopuszczalne wartosci atrybutu requiredRole to: 1, 10, 100.");
			}
		} catch (NumberFormatException nfe) {
			log.error(nfe.getMessage());
			throw nfe;
		}
	}
}
