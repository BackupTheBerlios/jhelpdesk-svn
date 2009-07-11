package com.jjhop.helpdesk.web.manager.bugcategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.jjhop.helpdesk.dao.BugCategoryDAO;

public class DeleteCategoryController implements Controller {
	private static Log log = LogFactory.getLog( DeleteCategoryController.class );
	private BugCategoryDAO categoryDAO;
	
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		try {
			categoryDAO.deleteCategory(
				categoryDAO.getById( 
					Long.parseLong( request.getParameter( "catId" ) ) 
				)
			);
		} catch( Exception ex ) {
			log.error( ex );
		}
		return new ModelAndView( new RedirectView( request.getContextPath().concat( "/manage/category/showAll.html" ) ) );
	}
	
	/** @param categoryDAO the categoryDAO to set */
	public void setCategoryDAO( BugCategoryDAO categoryDAO ) {
		this.categoryDAO = categoryDAO;
	}
}
