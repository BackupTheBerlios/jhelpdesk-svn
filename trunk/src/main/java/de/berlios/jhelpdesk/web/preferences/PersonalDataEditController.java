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

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.User;

/**
 * 
 * @author jjhop
 */
@Controller
public class PersonalDataEditController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/preferences/personalData.html", method = RequestMethod.GET)
    public String prepareForm(HttpSession session, ModelMap map) {
        map.addAttribute("personalData", getUserFromSession(session));
        return "preferences/personalData";
    }

    @RequestMapping(value = "/preferences/personalData/change.html", method = RequestMethod.POST)
    public String processPersonalDataChange(@ModelAttribute("personalData") User user,
                                            BindingResult result) {
        // 1. walidacje a jeśli jest ok to przepisujemy do usera z bazki i zapisujemy i zmykamy


        return "preferences/personalData";
    }

    @RequestMapping(value = "/preferences/password/change.html", method = RequestMethod.POST)
    public String processPasswordChange(@RequestParam("password") String password,
                                        @RequestParam("repeated") String repeated,
                                        HttpSession session, ModelMap map) {
        User currentUser = getUserFromSession(session);
        map.addAttribute("personalData", currentUser);

        if (passwordsAreSameAndValid(password, repeated)) {
            userDAO.updatePasswordAndSalt(currentUser, password);
            map.addAttribute("message", "wyciagamy info z messagesów"); // UDAŁO SIĘ!
        } else {
            map.addAttribute("message", "nie udało się!!!!"); // PORAŻKA!
        }
        return "preferences/personalData";
    }

    private boolean passwordsAreSameAndValid(String p1, String p2) {
        boolean containsNumber = StringUtils.containsAny(p1, "0123456789");
        boolean containsSpecial = StringUtils.containsAny(p1, "!@#$%^&*()_+-=/*,.;:\\\"'`~'");

        return (p1.length() < 6 && p1.equals(p2) && containsNumber && containsSpecial);
    }

    private User getUserFromSession(HttpSession session) {
        return userDAO.getById(((User) session.getAttribute("user")).getUserId());
    }
}
