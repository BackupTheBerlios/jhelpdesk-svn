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

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.berlios.jhelpdesk.dao.DAOException;
import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.TicketCategoryEditor;
import de.berlios.jhelpdesk.web.tools.TicketPriorityEditor;
import de.berlios.jhelpdesk.web.tools.TicketValidator;
import de.berlios.jhelpdesk.web.tools.UserEditor;

/**
 * Kontroler obsługujący jednokrokowe dodawnie zgłoszenia.
 * 
 * @author jjhop
 */
@Controller
public class TicketNewController {

    private static final Log log = LogFactory.getLog(TicketNewController.class);

    @Autowired
    private TicketValidator validator;

    @Autowired
    @Qualifier("jpa")
    private TicketDAO ticketDaoJpa;

    @Autowired
    private TicketCategoryDAO ticketCategoryDao;

    @Autowired
    private UserEditor userEditor;

    @Autowired
    private TicketPriorityEditor priorityEditor;

    @Autowired
    private TicketCategoryEditor categoryEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(User.class, "notifier", userEditor);
        binder.registerCustomEditor(TicketPriority.class, priorityEditor);
        binder.registerCustomEditor(TicketCategory.class, categoryEditor);
    }

    @ModelAttribute("priorities")
    public Collection<TicketPriority> populateTicketPriorities() {
        return TicketPriority.getPriorities();
    }

    @ModelAttribute("categories")
    public Collection<TicketCategory> populateTicketCategories() {
        return ticketCategoryDao.getAllCategoriesForView();
    }

    @RequestMapping(value = "/tickets/new.html", method = RequestMethod.GET)
    public String prepareForm(ModelMap map) {
        map.addAttribute("ticket", new Ticket());
        return "tickets/new";
    }

    @RequestMapping(value = "/tickets/new.html", method = RequestMethod.POST)
    public String processSubmit(
                  @ModelAttribute("ticket") Ticket ticket, 
                  BindingResult result, HttpSession session) throws DAOException {

        validator.validate(ticket, result);
        if (result.hasErrors()) {
            if (log.isDebugEnabled()) {
                for (Object o : result.getAllErrors()) {
                    log.debug("[" + o + "]");
                }
            }
            return "tickets/new";
        }
        ticket.setInputer((User)session.getAttribute("user"));
        ticketDaoJpa.save(ticket);
        return "redirect:/ticketDetails.html?ticketId=" + ticket.getTicketId();
    }
}
