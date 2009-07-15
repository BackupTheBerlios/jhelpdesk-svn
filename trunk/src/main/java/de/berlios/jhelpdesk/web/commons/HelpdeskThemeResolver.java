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
package de.berlios.jhelpdesk.web.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ThemeResolver;
import org.springframework.stereotype.Component;

import de.berlios.jhelpdesk.model.User;

@Component("themeResolver")
public class HelpdeskThemeResolver implements ThemeResolver {

	public String resolveThemeName(HttpServletRequest request) {
		User user = (User) (request.getSession()).getAttribute("user");
		// TODO: jesli user ustalony to nalezy wyciagnac jego preferencje
		return (user == null) ? "hd_blue_theme" : "hd_blue_theme";
	}

	public void setThemeName(HttpServletRequest request, 
			HttpServletResponse response, String themeName) {}
}
