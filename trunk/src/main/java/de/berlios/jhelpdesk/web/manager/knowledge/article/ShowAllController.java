package de.berlios.jhelpdesk.web.manager.knowledge.article;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.KnowledgeDAO;

public class ShowAllController implements Controller {
	private static Log log = LogFactory.getLog( ShowAllController.class );
	private KnowledgeDAO knowledgeDAO;

	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		ModelAndView mav = new ModelAndView( "manage/knowledge/article/showAll" );
		try {
			mav.addObject( 
				"articles",
				knowledgeDAO.getForSection( Long.parseLong( request.getParameter( "sectionId" ) ) )
			);
		} catch( Exception ex ) {
			log.error( ex );
			mav.setView( new RedirectView( "/manage/knowledge/section/showAll.html", true ) );
		}
		return mav;
	}

	/**
	 * @param knowledgeDAO the knowledgeDAO to set
	 */
	public void setKnowledgeDAO( KnowledgeDAO knowledgeDAO ) {
		this.knowledgeDAO = knowledgeDAO;
	}

}
