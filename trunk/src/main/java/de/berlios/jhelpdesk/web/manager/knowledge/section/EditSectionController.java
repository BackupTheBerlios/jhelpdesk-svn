package de.berlios.jhelpdesk.web.manager.knowledge.section;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;
import de.berlios.jhelpdesk.model.KnowledgeSection;

public class EditSectionController extends SimpleFormController {
	private static Log log = LogFactory.getLog( EditSectionController.class );
	private KnowledgeSectionDAO sectionDAO;
	
	@Override
	protected Object formBackingObject( HttpServletRequest request ) throws Exception {
		KnowledgeSection section = null;
		try {
			section = 
				sectionDAO.getById(
					Long.parseLong( request.getParameter( "sectionId" ) )
			);
		} catch( Exception ex ) {
			log.warn( ex );
			section = new KnowledgeSection();
		}
		return section;
	}
	
	@Override
	protected ModelAndView onSubmit( HttpServletRequest request, HttpServletResponse response, 
				Object command, BindException errors ) throws Exception {
		ModelAndView mav = new ModelAndView( new RedirectView( "/manage/knowledge/section/showAll.html", true ) );
		KnowledgeSection section = ( KnowledgeSection ) command;
		sectionDAO.save( section );
		return mav;
	}

	/**
	 * @param sectionDAO the sectionDAO to set
	 */
	public void setSectionDAO( KnowledgeSectionDAO sectionDAO ) {
		this.sectionDAO = sectionDAO;
	}
}
