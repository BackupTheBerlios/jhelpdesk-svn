package com.jjhop.helpdesk.web.manager.information;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.jjhop.helpdesk.dao.InformationDAO;
import com.jjhop.helpdesk.model.Information;

public class EditInformationController extends SimpleFormController {
	private static Log log = LogFactory.getLog( EditInformationController.class );
	private InformationDAO informationDAO;
	
	@Override
	protected Object formBackingObject( HttpServletRequest request ) throws Exception {
		log.debug( "formBackingObject" );
		Information information = null;
		try {
			information = 
				informationDAO.getById(
					Long.parseLong( request.getParameter( "infoId" ) )
			);
		} catch( Exception ex ) {
			log.warn( ex );
			information = new Information();
		}
		return information;
	}
	
	@Override
	protected ModelAndView onSubmit( HttpServletRequest request, HttpServletResponse response, 
				Object command, BindException errors ) throws Exception {
		log.debug( "onSubmit()" );
		ModelAndView mav = null;
		try {
			informationDAO.save( (Information) command );
			mav = new ModelAndView( new RedirectView( "/manage/information/showAll.html", true ) );
		} catch( Exception ex ) {
			log.error( ex );
			mav = new ModelAndView( getFormView() );
		}
		return mav;
	}
	
	/** @param informationDAO the informationDAO to set */
	public void setInformationDAO( InformationDAO informationDAO ) {
		log.debug( "setInformationDAO" );
		this.informationDAO = informationDAO;
	}
}
