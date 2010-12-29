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
package de.berlios.jhelpdesk.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.User;

/**
 * Kontroler obsługujący uwierzytelniania użytkowników w systemie oraz
 * wylogowanie użytkowników z systemu.
 * 
 * @author jjhop
 */
@Controller
public class AuthenticationController {

    @Autowired
    private UserDAO userDAOJpa;

    /**
     * Przygotowuje formularz logowania.
     *
     * @param map model widoku
     * @return identyfikator widoku formularza
     */
    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String setupLoginForm(ModelMap map) {
        map.addAttribute("user", new User());
        return "login";
    }

    /**
     * Uwierzytelenia użytkownika w systemie. Jeśli operacja powiedzie się,
     * uzupełnia sesję.
     *
     * @param user użytkownik do uwierzytelnienia
     * @return identyfikator widoku do wyświetlenia, będzie to widok formularza
     *         jeśli nie uwierzytelnienie nie powiedzie się i widok domyślny
     *         dla użytkownika jeśli uda się uwierzytelnić
     */
    // TODO: przeniesienie na domyślny widok użytkownika
    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    protected String processLogin(@ModelAttribute("user") User user, HttpSession session) {
        // TODO: w DAO metoda authenticate do wywalenia... uwierzytleniamy sprawdzajac
        // czy gosc podal pasujace haslo i login (email) oraz czy moze sie logowac (isActive)
        boolean isAuthenticatedWithJpa = userDAOJpa.authenticate(user.getLogin(), user.getPassword());
        if (isAuthenticatedWithJpa) {
            User loggedUser = userDAOJpa.getByLoginFetchFilters(user.getLogin());
            session.setAttribute("user", loggedUser);
            session.setAttribute("logged", Boolean.TRUE);
            return "redirect:" + loggedUser.getWelcomePage();
        }
        return "login";
    }

    /**
     * Metoda unieważnia sesję użytkownika w systemie.
     *
     * @param session sesja użytkownika
     * @return identyfikator widoku do wyświetlenia po wylogowaniu
     */
    @RequestMapping(value = "/logout.html")
    public String processLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.html";
    }
}
