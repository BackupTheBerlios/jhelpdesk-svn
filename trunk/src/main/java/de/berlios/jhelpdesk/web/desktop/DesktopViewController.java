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

import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.dao.BugEventDAO;
import de.berlios.jhelpdesk.dao.InformationDAO;
import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.model.BugStatus;

@Controller
public class DesktopViewController  {

    private static int NUMBER_OF_EVENTS_IN_DESKTOP = 5;
    private static int NUMBER_OF_NONASSIGNED_BUGS = 5;
    private static int NUMBER_OF_LAST_ADDED_ARTICLES = 5;
    private static int NUMBER_OF_LAST_INFORMATIONS = 10;

    @Autowired
    private BugDAO bugDAO;

    @Autowired
    private BugEventDAO eventDAO;

    @Autowired
    private KnowledgeDAO knowledgeDAO;
    
    @Autowired
    private InformationDAO informationDAO;

    @RequestMapping("/desktop/main.html")
    public String showDesktop(ModelMap map) {
        map.addAttribute("lastBugs", bugDAO.getBugsByStatus(BugStatus.NOTIFIED, NUMBER_OF_NONASSIGNED_BUGS));
        map.addAttribute("lastEvents", eventDAO.getLastFewEvents(NUMBER_OF_EVENTS_IN_DESKTOP));
        map.addAttribute("lastArticles", knowledgeDAO.getLastAddedArticles(NUMBER_OF_LAST_ADDED_ARTICLES));
        map.addAttribute("lastInformations", informationDAO.getLastFew(NUMBER_OF_LAST_INFORMATIONS));
        return "desktop/main";
    }
}
