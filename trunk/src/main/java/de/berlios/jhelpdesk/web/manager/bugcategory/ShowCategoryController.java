package de.berlios.jhelpdesk.web.manager.bugcategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;

public class ShowCategoryController implements Controller {
	private static Log log = LogFactory.getLog( ShowCategoryController.class );
	private BugCategoryDAO categoryDAO;
	
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		if( categoryDAO != null ) {
		
		}
		return null;
	}
	/** @param categoryDAO the categoryDAO to set */
	public void setCategoryDAO( BugCategoryDAO categoryDAO ) {
		log.debug( "setCategoryDAO( IHDBugCategoryDAO categoryDAO )" );
		this.categoryDAO = categoryDAO;
	}
}
