package de.berlios.jhelpdesk.web.manager.information;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.InformationDAO;

public class ShowAllInformationsController implements Controller {

	private InformationDAO informationDAO;
	
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		ModelAndView mav = new ModelAndView( "manager/information/showAll" );
		mav.addObject( "informations", informationDAO.getAll() );
		return mav;
	}

	/** @param informationDAO the informationDAO to set */
	public void setInformationDAO( InformationDAO informationDAO ) {
		this.informationDAO = informationDAO;
	}
}
