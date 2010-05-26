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
package de.berlios.jhelpdesk.web.preferences.filter;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.berlios.jhelpdesk.dao.TicketFilterDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.TicketFilter;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Controller
public class CustomFilterController {

    @Autowired
    private MessageSource ms;
    
    @Autowired
    private TicketFilterDAO ticketFilterDAO;

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/preferences/filters/list.html", method = RequestMethod.GET)
    public String showAllFilters(ModelMap map, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        map.addAttribute("filters", ticketFilterDAO.getAllFiltersForUser(currentUser));

        return "preferences/filters/showAll";
    }

    @RequestMapping(value = "/preferences/filters/{filterId}/details.html", method = RequestMethod.GET)
    public String showFilter(@PathVariable("filterId") Long filterId, ModelMap map,
                             Locale currentLocale, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        TicketFilter filter = ticketFilterDAO.getById(filterId);
        if (filter != null && filter.isOwnedBy(currentUser)) {
            map.addAttribute("filter", filter);
        } else {
            map.addAttribute("errorMessage",
                             ms.getMessage("customFilterController.showFilter.404",
                             null, currentLocale));
        }
        return "preferences/filters/show";
    }

    @RequestMapping(value = "/preferences/filters/{filterId}/delete.html", method = RequestMethod.GET)
    public String deleteFilter(@PathVariable("filterId") Long filterId,  ModelMap map,
                               HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        TicketFilter filter = ticketFilterDAO.getById(filterId);
        if (filter != null && filter.isOwnedBy(currentUser)) {
            ticketFilterDAO.delete(filter);
            session.setAttribute("user", userDAO.getById(currentUser.getUserId()));
            map.addAttribute("message", null);
        }
        map.addAttribute("filters", ticketFilterDAO.getAllFiltersForUser(currentUser));
        return "preferences/filters/showAll";
    }
}
