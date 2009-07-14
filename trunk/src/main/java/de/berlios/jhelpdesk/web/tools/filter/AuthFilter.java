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
package de.berlios.jhelpdesk.web.tools.filter;

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

	public void init(FilterConfig filter) throws ServletException {}

	public void doFilter(ServletRequest req, ServletResponse res, 
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRreq = (HttpServletRequest) req;
		HttpSession sess = servletRreq.getSession();

		String reqUrl = ((HttpServletRequest) servletRreq).getRequestURL()
				.toString().toLowerCase();
		if (reqUrl.endsWith("/login.html") 
				|| reqUrl.endsWith(".ico") 
				|| reqUrl.endsWith(".gif")
				|| reqUrl.endsWith(".png") 
				|| reqUrl.endsWith(".htc")) {
			chain.doFilter(req, res);
			return;
		}
		Boolean logged = (Boolean) sess.getAttribute("logged");

		if ((logged == null) || (!logged.booleanValue())) {
			sess.invalidate();
			((HttpServletResponse) res).sendRedirect(
					servletRreq.getContextPath() + "/login.html");
			return;
		}
		chain.doFilter(req, res);
	}

	public void destroy() {}
}
