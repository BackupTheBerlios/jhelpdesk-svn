package com.jjhop.helpdesk.web.manager.knowledge.section;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jjhop.helpdesk.dao.KnowledgeSectionDAO;

public class ShowOneSectionController implements Controller {
	private static Log log = LogFactory.getLog(  ShowOneSectionController.class );
	private KnowledgeSectionDAO sectionDAO;

	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		ModelAndView mav = new ModelAndView( "manager/knowledge/section/show" );
		try {
			Long sectionId = Long.parseLong( request.getParameter( "sectionId" ) );
			mav.addObject( "section", sectionDAO.getById( sectionId ) );
		} catch( Exception ex ) {
			mav.setViewName( "manager/knowledge/section/showAll" );
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
