package de.berlios.jhelpdesk.web.manager.bugcategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;

public class ShowAllController implements Controller {
	private static Log log = LogFactory.getLog( ShowAllController.class );
	private BugCategoryDAO categoryDAO;
	
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		ModelAndView mav = new ModelAndView( "manager/category/showAll" );
		mav.addObject( "categories", categoryDAO.getAllCategories() );
		return mav;
	}
	/** @param categoryDAO the categoryDAO to set */
	public void setCategoryDAO( BugCategoryDAO categoryDAO ) {
		log.debug( "setCategoryDAO( IHDBugCategoryDAO categoryDAO )" );
		this.categoryDAO = categoryDAO;
	}
}
