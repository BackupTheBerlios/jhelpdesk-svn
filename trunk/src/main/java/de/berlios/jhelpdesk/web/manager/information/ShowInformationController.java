package de.berlios.jhelpdesk.web.manager.information;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.InformationDAO;

public class ShowInformationController implements Controller {
	private static Log log = LogFactory.getLog( ShowInformationController.class );
	private InformationDAO informationDAO;
	
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		ModelAndView mav = new ModelAndView( "/manage/information/show.html" );
		try {
			mav.addObject( 
				"information", 
				informationDAO.getById( Long.parseLong( request.getParameter( "infoId" ) ) ) 
			);
		} catch( Exception e ) {
			mav.addObject( "errorInfo", e.getMessage() );
			log.error( e );
		}
		return mav;
	}
	
	/** @param informationDAO the informationDAO to set */
	public void setInformationDAO( InformationDAO informationDAO ) {
		this.informationDAO = informationDAO;
	}
}
