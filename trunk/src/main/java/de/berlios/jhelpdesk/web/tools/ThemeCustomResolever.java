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
package de.berlios.jhelpdesk.web.tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.theme.AbstractThemeResolver;
import org.springframework.web.util.WebUtils;

import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
public class ThemeCustomResolever extends AbstractThemeResolver {

    // Tutaj jeszcze powinna byc lista dostępnych tematów
    private String defaultThemeName = "blue";

    public String resolveThemeName(HttpServletRequest req) {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser != null) {
            return currentUser.getPreferredTheme();
        }

        Cookie themeCookie = WebUtils.getCookie(req, "jhd_theme");
        if (themeCookie != null) {
            return themeCookie.getValue();
        }
        return defaultThemeName;
    }

    public void setThemeName(HttpServletRequest req, HttpServletResponse res, String themeName) {
    }
}
