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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.TicketFilterDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketFilter;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.TicketCategoryEditor;
import de.berlios.jhelpdesk.web.tools.TicketFilterValidator;
import de.berlios.jhelpdesk.web.tools.UserEditor;

/**
 *
 * @author jjhop
 */
@Controller
@RequestMapping("/preferences/filters/new.html")
public class CustomFilterEditController {

    @Autowired
    private MessageSource ms;

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private TicketFilterDAO ticketFilterDAO;

    @Autowired
    private TicketCategoryDAO  ticketCategoryDAO;

    @Autowired
    private UserEditor userEditor;

    @Autowired
    private TicketCategoryEditor categoryEditor;

    @Autowired
    private TicketFilterValidator ticketFilterValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(User.class, userEditor);
        binder.registerCustomEditor(TicketCategory.class, categoryEditor);
    }

    @ModelAttribute("ticketCategories")
    public List<TicketCategory> populateTicketCategories() {
        return ticketCategoryDAO.getAllCategoriesForView();
    }

    @ModelAttribute("ticketPriorities")
    public List<TicketPriority> populateTicketPriorities() {
        return TicketPriority.getPriorities();
    }

    @ModelAttribute("ticketStatuses")
    public TicketStatus[] spopulateTicketStatuses() {
        return TicketStatus.getAllStatuses();
    }

    @ModelAttribute("activeUsers")
    public List<User> populateActiveUsers() {
        return userDAO.getAllUser();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String prepareForm(ModelMap map) {
        map.addAttribute(new TicketFilter());
        return "preferences/filters/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processForm(
                  @ModelAttribute("ticketFilter") TicketFilter ticketFilter,
                  BindingResult result, ModelMap map) {

        ticketFilterValidator.validate(ticketFilter, result);
        if (result.hasErrors()) {
            map.addAttribute(ticketFilter);
            return "preferences/filters/new";
        } else {
            map.addAttribute(ticketFilter);
            return "preferences/filters/new";
            // jesli valid to
            // ticketFilterDAO.saveOrUpdate(ticketFilter);
        }
    }
}
