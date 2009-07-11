package de.berlios.jhelpdesk.web.manager.bugcategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;

public class MoveUpDownCategoryController extends MultiActionController {

	private static Log log = LogFactory.getLog( MoveUpDownCategoryController.class );
	private BugCategoryDAO categoryDAO;
	
	public ModelAndView moveUp( HttpServletRequest request, HttpServletResponse response ) {
		log.debug( "moveUp( HttpServletRequest request, HttpServletResponse response )" );
		if( categoryDAO != null ) {
			
		}
		
		return null;
	}
	
	public ModelAndView moveDown( HttpServletRequest request, HttpServletResponse response ) {
		log.debug( "moveDown( HttpServletRequest request, HttpServletResponse response )" );
		
		return null;
	}
	
	/** @param categoryDAO the categoryDAO to set */
	public void setCategoryDAO( BugCategoryDAO categoryDAO ) {
		this.categoryDAO = categoryDAO;
	}
}
