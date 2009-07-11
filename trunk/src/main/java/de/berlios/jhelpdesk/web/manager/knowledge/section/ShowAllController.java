package de.berlios.jhelpdesk.web.manager.knowledge.section;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;

public class ShowAllController implements Controller {
//	private static Log log = LogFactory.getLog(  ShowAllController.class );
	private KnowledgeSectionDAO sectionDAO;

	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		ModelAndView mav = new ModelAndView( "manager/knowledge/section/showAll" );
		mav.addObject( "sections", sectionDAO.getAllShortSections() );
		return mav;
	}

	/**
	 * @param sectionDAO the sectionDAO to set
	 */
	public void setSectionDAO( KnowledgeSectionDAO sectionDAO ) {
		this.sectionDAO = sectionDAO;
	}
}
