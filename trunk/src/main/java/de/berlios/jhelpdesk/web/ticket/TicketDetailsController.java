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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketComment;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Controller
@SessionAttributes("user")
public class TicketDetailsController {
    
    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TicketCategoryDAO ticketCategoryDAO;

    /**
     *
     *
     * @param ticketId
     * @param addComm
     * @param user
     * @return
     */
    @RequestMapping(value = "/tickets/{ticketId}/details.html", method = RequestMethod.POST)
    public String processAddComment(@PathVariable("ticketId") Long ticketId,
                                    @RequestParam("addComm") String addComm,
                                    @RequestParam(value = "notForPlainUser", required = false,
                                                  defaultValue = "false") boolean notForPlain,
                                    @ModelAttribute("user") User user) throws Exception {

        TicketComment comm = new TicketComment();
        Ticket ticket = ticketDAO.getTicketById(ticketId);
        comm.setTicket(ticket);
        comm.setNotForPlainUser(notForPlain);
        comm.setCommentAuthor(user);
        comm.setCommentText(addComm);
        ticket.addComment(comm);
        ticketDAO.save(ticket);
        // tutaj jeszcze id komentarz jako część strony w URLu
        return "redirect:/tickets/" + ticketId + "/details.html";
    }

    /**
     *
     * 
     * @param ticketId
     * @param mav
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/tickets/{ticketId}/details.html", method = RequestMethod.GET)
    public String showTicket(@PathVariable("ticketId") Long ticketId,
                             ModelMap mav) throws Exception {

        Ticket ticket = ticketDAO.getTicketById(ticketId);
        mav.addAttribute("ticket", ticket);
        mav.addAttribute("ticketPriorities", TicketPriority.values());
        mav.addAttribute("ticketStatuses", TicketStatus.values());
        mav.addAttribute("ticketCategories", ticketCategoryDAO.getAllCategories());
        return "ticketDetails";
    }
}
