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

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;
import org.springframework.web.util.WebUtils;

import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
public class LocaleCustomResolver extends AbstractLocaleResolver {

    public Locale resolveLocale(HttpServletRequest request) {
        Locale raLocale = (Locale) request.getAttribute("jhd_locale");
        if (raLocale != null) {
            return raLocale;
        }
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser != null && currentUser.getUserId() != null) {
            request.setAttribute("jhd_locale", currentUser.getPreferredLocale());
            return currentUser.getPreferredLocale();
        }
        Cookie localeCookie = WebUtils.getCookie(request, "jhd_locale");
        if (localeCookie != null) {
            Locale locale = StringUtils.parseLocaleString(localeCookie.getValue());
            if (locale != null) {
                request.setAttribute("jhd_locale", locale);
                return locale;
            }
        }
        request.setAttribute("jhd_locale", request.getLocale());
        return request.getLocale();
    }

    public void setLocale(HttpServletRequest req, HttpServletResponse res, Locale locale) {
    }
}
