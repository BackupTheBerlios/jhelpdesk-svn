package com.jjhop.helpdesk.web.manager.knowledge.article;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jjhop.helpdesk.dao.KnowledgeDAO;

public class DelKnowledgeController implements Controller {
	
	private static Log log = LogFactory.getLog( DelKnowledgeController.class );
	private KnowledgeDAO knowledgeDAO;

	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		if( knowledgeDAO != null ) {
			
		}
		return null;
	}
	
	/**
	 * @param knowledgeDAO the knowledgeDAO to set
	 */
	public void setKnowledgeDAO( KnowledgeDAO knowledgeDAO ) {
		log.debug( "setKnowledgeDAO( IHDKnowledgeDAO knowledgeDAO )" );
		this.knowledgeDAO = knowledgeDAO;
	}
}
