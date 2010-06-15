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

import de.berlios.jhelpdesk.dao.UserDAO;
import java.util.Locale;

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

import de.berlios.jhelpdesk.dao.UserPreferencesDAO;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.model.LookAndFeelPreferences;

/**
 * 
 * @author jjhop
 */
@Controller
public class LookAndFeelEditController {

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
            ModelMap map, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (isPrefsOwnedByUser(lafPreferences, currentUser)) {
            lafPreferences.setUser(currentUser);
            currentUser.setLafPreferences(lafPreferences);
            this.userPreferencesDAO.save(lafPreferences);
            session.setAttribute("user", currentUser);
            map.addAttribute("preferences", lafPreferences);
        }
        return "preferences/lookAndFeel";
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
