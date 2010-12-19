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

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.berlios.jhelpdesk.dao.UserPreferencesDAO;
import de.berlios.jhelpdesk.model.DisplayListsPreferences;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Controller
public class DisplayListsEditController {

    @Autowired
    private UserPreferencesDAO userPreferencesDAO;

    @RequestMapping(value = "/preferences/displayLists.html", method = RequestMethod.GET)
    public String prepareForm(@ModelAttribute("dlPrefs") DisplayListsPreferences preferences,
                              HttpSession session, ModelMap map) {

        User currentUser = (User) session.getAttribute("user");
        DisplayListsPreferences dlPreferences = currentUser.getDlPreferences();
        map.addAttribute("dlPrefs", dlPreferences != null
                                            ? dlPreferences
                                            : new DisplayListsPreferences());
        return "preferences/displayLists";
    }

    @RequestMapping(value = "/preferences/displayLists.html", method = RequestMethod.POST)
    public String processForm(@ModelAttribute("dlPrefs") DisplayListsPreferences dlPreferences,
                              HttpSession session, ModelMap map) {

        User currentUser = (User) session.getAttribute("user");
        if (isPrefsOwnedByUser(dlPreferences, currentUser)) {
            dlPreferences.setUser(currentUser);
            currentUser.setDlPreferences(dlPreferences);
            this.userPreferencesDAO.save(dlPreferences);
            session.setAttribute("user", currentUser);
            map.addAttribute("preferences", dlPreferences);
        }
        return "preferences/displayLists";
    }

    private boolean isPrefsOwnedByUser(DisplayListsPreferences dlPrefs, User user) {
        DisplayListsPreferences userDlPrefs = user.getDlPreferences();
        Long lafId = dlPrefs.getId();
        if (userDlPrefs == null || lafId == null) {
            return false;
        }
        return lafId.equals(userDlPrefs.getId());
    }
}
