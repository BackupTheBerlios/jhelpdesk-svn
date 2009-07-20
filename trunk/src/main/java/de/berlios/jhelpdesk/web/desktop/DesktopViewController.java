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
package de.berlios.jhelpdesk.web.desktop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import de.berlios.jhelpdesk.dao.AnnouncementDAO;
import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.dao.TicketEventDAO;
import de.berlios.jhelpdesk.model.TicketStatus;

@Controller
public class DesktopViewController  {

    private static int NUMBER_OF_EVENTS_IN_DESKTOP = 5;
    private static int NUMBER_OF_NONASSIGNED_TICKETS = 5;
    private static int NUMBER_OF_LAST_ADDED_ARTICLES = 5;
    private static int NUMBER_OF_LAST_ANNOUNCEMENTS = 10;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TicketEventDAO eventDAO;

    @Autowired
    private KnowledgeDAO knowledgeDAO;
    
    @Autowired
    private AnnouncementDAO announcementDAO;

    @RequestMapping("/desktop/main.html")
    public String showDesktop(ModelMap map) throws Exception {
        map.addAttribute("lastTickets", ticketDAO.getTicketsByStatus(TicketStatus.NOTIFIED, NUMBER_OF_NONASSIGNED_TICKETS));
        map.addAttribute("lastEvents", eventDAO.getLastFewEvents(NUMBER_OF_EVENTS_IN_DESKTOP));
        map.addAttribute("lastArticles", knowledgeDAO.getLastAddedArticles(NUMBER_OF_LAST_ADDED_ARTICLES));
        map.addAttribute("lastAnnouncements", announcementDAO.getLastFew(NUMBER_OF_LAST_ANNOUNCEMENTS));
        return "desktop/main";
    }
}
