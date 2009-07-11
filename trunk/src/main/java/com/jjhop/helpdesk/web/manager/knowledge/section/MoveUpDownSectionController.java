package com.jjhop.helpdesk.web.manager.knowledge.section;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.jjhop.helpdesk.dao.KnowledgeSectionDAO;

public class MoveUpDownSectionController extends MultiActionController {
	private Log log = LogFactory.getLog( MoveUpDownSectionController.class );
	private KnowledgeSectionDAO sectionDAO;
	private ModelAndView mav;
	
	public MoveUpDownSectionController() {
		mav = new ModelAndView( 
			new RedirectView( "/manage/knowledge/section/showAll.html", true )
		);
	}
	
	public ModelAndView moveUp( HttpServletRequest request, HttpServletResponse response ) {
		try {
			sectionDAO.moveUp( Long.parseLong( request.getParameter( "sectionId" ) ) );
		} catch( Exception ex ) {
			log.error( ex );
		}
		return mav;
	}
	
	public ModelAndView moveDown( HttpServletRequest request, HttpServletResponse response ) {
		try {
			sectionDAO.moveDown( Long.parseLong( request.getParameter( "sectionId" ) ) );
		} catch( Exception ex ) {
			log.error( ex );
		}
		return mav;
	}

	/**
	 * @param sectionDAO the sectionDAO to set
	 */
	public void setSectionDAO( KnowledgeSectionDAO sectionDAO ) {
		this.sectionDAO = sectionDAO;
	}
}
