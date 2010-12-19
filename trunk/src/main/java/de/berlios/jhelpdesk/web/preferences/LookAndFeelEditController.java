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
package de.berlios.jhelpdesk.web.preferences;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import de.berlios.jhelpdesk.dao.UserPreferencesDAO;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.model.LookAndFeelPreferences;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author jjhop
 */
@Controller
public class LookAndFeelEditController {

    private static final int SECONDS_BY_WEEK = 604800; // 24*3600*7

    @Autowired
    private UserPreferencesDAO userPreferencesDAO;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Locale.class, new LocaleEditor());
    }

    @RequestMapping(value = "/preferences/lookAndFeel.html", method = RequestMethod.GET)
    public String prepareForm(ModelMap map, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        LookAndFeelPreferences lafPreferences = currentUser.getLafPreferences();
        map.addAttribute("preferences", lafPreferences != null
                                            ? lafPreferences
                                            : new LookAndFeelPreferences());
        return "preferences/lookAndFeel";
    }

    @RequestMapping(value = "/preferences/lookAndFeel.html", method = RequestMethod.POST)
    public String processForm(@ModelAttribute("preferences") LookAndFeelPreferences lafPreferences,
                              @RequestParam(value = "filterId", required = false) Long filterId,
                              HttpServletRequest request, HttpServletResponse respone,
                              ModelMap map, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (isPrefsOwnedByUser(lafPreferences, currentUser)) {
            lafPreferences.setUser(currentUser);
            currentUser.setLafPreferences(lafPreferences);
            this.userPreferencesDAO.save(lafPreferences);
            respone.addCookie(createCookie(request, lafPreferences.getPreferredLocale()));
            session.setAttribute("user", currentUser);
            map.addAttribute("preferences", lafPreferences);
        }
        return "preferences/lookAndFeel";
    }

    private Cookie createCookie(HttpServletRequest req, Locale loc) {
        Cookie cookie = new Cookie("jhd_locale", loc.getLanguage());
        cookie.setMaxAge(SECONDS_BY_WEEK);
        cookie.setPath(req.getContextPath());
        return cookie;
    }

    private boolean isPrefsOwnedByUser(LookAndFeelPreferences laf, User user) {
        LookAndFeelPreferences userLaf = user.getLafPreferences();
        Long lafId = laf.getId();
        if (userLaf == null || lafId == null) {
            return false;
        }
        return lafId.equals(userLaf.getId());
    }
}
