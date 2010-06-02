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
import javax.servlet.http.HttpSession;

import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.NotAuthorizedAccessException;

public class ManagerFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession sess = req.getSession();
        User user = (User) sess.getAttribute("user");
        if (user == null) {
            throw new NotAuthorizedAccessException("not authorized access!");
        }
        if (user.getUserRole().toInt() < Role.MANAGER.toInt()) {
            throw new NotAuthorizedAccessException("not authorized access!");
        }
        chain.doFilter(request, response);
    }
    public void destroy() {}
}
