package de.berlios.jhelpdesk.web.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;

public class BaseViewController implements Controller {

	private static Log log = LogFactory.getLog(BaseViewController.class);

	private KnowledgeDAO knowledgeDAO;
	private KnowledgeSectionDAO knowledgeSectionDAO;

	public ModelAndView handleRequest(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String key = request.getParameter("key");
		String id = request.getParameter("id");
		ModelAndView mav = null;
		if ((key != null) && key.equalsIgnoreCase("details")) {
			mav = new ModelAndView("help/base/one");
			mav.addObject("article", knowledgeDAO.getById(Long.parseLong(id)));
		} else {
			mav = new ModelAndView("help/base");
			mav.addObject("sections", knowledgeSectionDAO.getAllSections());
		}
		return mav;
	}

	/**
	 * @param knowledgeDAO The knowledgeDAO to set.
	 */
	public void setKnowledgeDAO(KnowledgeDAO knowledgeDAO) {
		log.debug("setKnowledgeDAO( IHDKnowledgeDAO knowledgeDAO )");
		this.knowledgeDAO = knowledgeDAO;
	}

	/**
	 * @param knowledgeSectionDAO The knowledgeSectionDAO to set.
	 */
	public void setKnowledgeSectionDAO(KnowledgeSectionDAO knowledgeSectionDAO) {
		log.debug("setKnowledgeSectionDAO(KnowledgeSectionDAO)");
		this.knowledgeSectionDAO = knowledgeSectionDAO;
	}
}