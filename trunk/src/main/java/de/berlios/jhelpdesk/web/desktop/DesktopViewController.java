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

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import de.berlios.jhelpdesk.dao.AnnouncementDAO;
import de.berlios.jhelpdesk.dao.ArticleDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.dao.TicketEventDAO;
import de.berlios.jhelpdesk.model.TicketStatus;

/**
 *
 * @author jjhop
 */
@Controller
public class DesktopViewController  {

    /**
     * Stała określająca liczbę wyświetlanych na biurku zdarzeń.
     */
    private static int NUMBER_OF_EVENTS_IN_DESKTOP = 5;

    /**
     * Stała określająca liczbę wyświetlanych na biurku
     * nowych i nieprzypisanych do nikogo zgłoszeń.
     */
    private static int NUMBER_OF_NONASSIGNED_TICKETS = 5;

    /**
     * Stała określająca liczbę wyświetalnych na biurku
     * ostatnio dodanych artykułów.
     */
    private static int NUMBER_OF_LAST_ADDED_ARTICLES = 5;

    /**
     * Stała określająca liczbę wyświetalnych na biurku 
     * ostatnio dodanych ogłoszeń.
     */
    private static int NUMBER_OF_LAST_ANNOUNCEMENTS = 10;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TicketEventDAO eventDAO;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private AnnouncementDAO announcementDAO;

    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/desktop/main.html")
    public String showDesktop(ModelMap map, HttpSession sess) throws Exception {
        map.addAttribute("lastTickets", ticketDAO.getTicketsByStatus(TicketStatus.NOTIFIED, NUMBER_OF_NONASSIGNED_TICKETS));
        map.addAttribute("lastEvents", eventDAO.getLastEvents(NUMBER_OF_EVENTS_IN_DESKTOP));
        map.addAttribute("lastArticles", articleDAO.getLastArticles(NUMBER_OF_LAST_ADDED_ARTICLES));
        map.addAttribute("lastAnnouncements", announcementDAO.getLastAnnouncements(NUMBER_OF_LAST_ANNOUNCEMENTS));
        return "desktop/main";
    }
}
