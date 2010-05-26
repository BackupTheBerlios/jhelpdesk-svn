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
package de.berlios.jhelpdesk.web.ticket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.dao.TicketFilterDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketFilter;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.TicketCategoryEditor;
import de.berlios.jhelpdesk.web.tools.TicketPriorityEditor;
import de.berlios.jhelpdesk.web.tools.UserEditor;

/**
 *
 * @author jjhop
 */
@Controller
public class TicketsByFilterViewController {

    @Autowired
    private TicketFilterDAO ticketFilterDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TicketCategoryDAO ticketCategoryDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserEditor userEditor;

    @Autowired
    private TicketCategoryEditor ticketCategoryEditor;

    @Autowired
    private TicketPriorityEditor ticketPriorityEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(User.class, userEditor);
        binder.registerCustomEditor(TicketCategory.class, ticketCategoryEditor);
        binder.registerCustomEditor(TicketPriorityEditor.class, ticketPriorityEditor);
    }

    @ModelAttribute("categories")
    public List<TicketCategory> populateCategories() {
        return ticketCategoryDAO.getAllCategoriesForView();
    }

    @ModelAttribute("priorities")
    public List<TicketPriority> populatePriorities() {
        return TicketPriority.getPriorities();
    }

    @ModelAttribute("statuses")
    public TicketStatus[] populateStatuses() {
        return TicketStatus.getAllStatuses();
    }

    @ModelAttribute("saviours")
    public List<User> populateSaviuours() {
        return userDAO.getByRole(Role.TICKETKILLER);
    }

    @ModelAttribute("notifiers")
    public List<User> populateNotifiers() {
        return userDAO.getAllUser();
    }

    @RequestMapping("/tickets/byFilter/{filterId}/list.html")
    public String processRequest(@PathVariable("filterId") Long filterId,
                                 @ModelAttribute("filter") TicketFilter filter,
                                 @RequestParam(value = "_formSent", defaultValue = "false",
                                               required = false) boolean formSent,
                                 ModelMap map, HttpSession session){
        User currentUser = (User) session.getAttribute("user");

        List<Ticket> result = null;
        TicketFilter currentFilter = null;

        if (formSent) {
            currentFilter = filter;
            result = ticketDAO.getTicketsWithFilter(currentFilter);
        } else {
            currentFilter = ticketFilterDAO.getById(filterId);
            if (currentFilter != null && currentFilter.isOwnedBy(currentUser)) {
                result = ticketDAO.getTicketsWithFilter(currentFilter);
            }
        }

        if (currentFilter == null) {
            map.addAttribute("message", "nie znaleziono filtra...");
        }

        map.addAttribute("requestURI", "?cf=true");
        map.addAttribute("filter", currentFilter);
        map.addAttribute("ticketsListSize", result != null ? result.size() : 0);
        map.addAttribute("tickets", result);
        return "tickets/byFilter";
    }

}

//  PagingParamsEncoder enc = new PagingParamsEncoder("ticketsIterator", "p_id", request, PAGE_SIZE);
//  refData.put("ticketsListSize", ticketDao.countTicketsWithFilter(ff).intValue());
//  refData.put("tickets", ticketDao.getTicketsWithFilter(ff, PAGE_SIZE, enc.getOffset()));