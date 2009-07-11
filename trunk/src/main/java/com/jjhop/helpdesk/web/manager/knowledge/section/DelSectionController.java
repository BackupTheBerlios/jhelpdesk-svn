package com.jjhop.helpdesk.web.manager.knowledge.section;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.jjhop.helpdesk.dao.KnowledgeSectionDAO;

public class DelSectionController implements Controller {
	private static Log log = LogFactory.getLog( DelSectionController.class );
	private KnowledgeSectionDAO sectionDAO;

	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		try {
			sectionDAO.delete( Long.parseLong( request.getParameter( "sectionId" ) ) );
		} catch( Exception ex ) {
			log.error( ex );
		}
		return new ModelAndView( new RedirectView( "/manage/knowledge/section/showAll.html", true ) );
	}
	
	/**
	 * @param sectionDAO the sectionDAO to set
	 */
	public void setSectionDAO( KnowledgeSectionDAO sectionDAO ) {
		this.sectionDAO = sectionDAO;
	}
}
