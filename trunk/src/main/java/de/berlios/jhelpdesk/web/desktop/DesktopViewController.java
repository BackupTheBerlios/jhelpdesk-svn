package de.berlios.jhelpdesk.web.desktop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

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
    private BugDAO bugDAO;
    private BugEventDAO eventDAO;
    private KnowledgeDAO knowledgeDAO;
    private InformationDAO informationDAO;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("desktop/main");
        mav.addObject("lastBugs", bugDAO.getBugsByStatus(BugStatus.NOTIFIED, NUMBER_OF_NONASSIGNED_BUGS));
        mav.addObject("lastEvents", eventDAO.getLastFewEvents(NUMBER_OF_EVENTS_IN_DESKTOP));
        mav.addObject("lastArticles", knowledgeDAO.getLastAddedArticles(NUMBER_OF_LAST_ADDED_ARTICLES));
        mav.addObject("lastInformations", informationDAO.getLastFew(NUMBER_OF_LAST_INFORMATIONS));
        return mav;
    }

    /** @param bugDAO the bugDAO to set */
    public void setBugDAO(BugDAO bugDAO) {
        log.debug("setBugDAO( IHDBugDAO bugDAO )");
        this.bugDAO = bugDAO;
    }

    /** @param eventDAO the eventDAO to set */
    public void setEventDAO(BugEventDAO eventDAO) {
        log.debug("setEventDAO( IHDBugEventDAO eventDAO )");
        this.eventDAO = eventDAO;
    }

    /** @param knowledgeDAO the knowledgeDAO to set */
    public void setKnowledgeDAO(KnowledgeDAO knowledgeDAO) {
        log.debug("setKnowledgeDAO( IHDKnowledgeDAO knowledgeDAO )");
        this.knowledgeDAO = knowledgeDAO;
    }

    /** @param informationDAO the informationDAO to set */
    public void setInformationDAO(InformationDAO informationDAO) {
        log.debug("setInformationDAO( IHDInformationDAO informationDAO )");
        this.informationDAO = informationDAO;
    }
}
