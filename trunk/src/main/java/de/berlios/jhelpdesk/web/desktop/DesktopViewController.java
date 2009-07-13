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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.dao.BugEventDAO;
import de.berlios.jhelpdesk.dao.InformationDAO;
import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.model.BugStatus;

public class DesktopViewController implements Controller {

    private static Log log = LogFactory.getLog(DesktopViewController.class);
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

    public ModelAndView handleRequest(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("desktop/main");
        mav.addObject("lastBugs", bugDAO.getBugsByStatus(BugStatus.NOTIFIED, NUMBER_OF_NONASSIGNED_BUGS));
        mav.addObject("lastEvents", eventDAO.getLastFewEvents(NUMBER_OF_EVENTS_IN_DESKTOP));
        mav.addObject("lastArticles", knowledgeDAO.getLastAddedArticles(NUMBER_OF_LAST_ADDED_ARTICLES));
        mav.addObject("lastInformations", informationDAO.getLastFew(NUMBER_OF_LAST_INFORMATIONS));
        return mav;
    }
}
