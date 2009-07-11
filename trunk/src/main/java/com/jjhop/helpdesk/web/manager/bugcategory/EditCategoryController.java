package com.jjhop.helpdesk.web.manager.bugcategory;

import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.jjhop.helpdesk.dao.BugCategoryDAO;
import com.jjhop.helpdesk.model.BugCategory;

public class EditCategoryController extends SimpleFormController {
	private static Log log = LogFactory.getLog( EditCategoryController.class );
	private BugCategoryDAO categoryDAO;
	
	@Override
	protected void initBinder( HttpServletRequest request, ServletRequestDataBinder binder ) throws Exception {
		log.info( "initBinder()->start" );
		binder.registerCustomEditor( Long.class, null, new CustomNumberEditor( Long.class, NumberFormat.getNumberInstance(), true ) );
		binder.registerCustomEditor( Boolean.class, null, new CustomBooleanEditor( true ) );
	}
	
	@Override
	protected ModelAndView onSubmit( HttpServletRequest request, HttpServletResponse response, Object command, BindException errors ) throws Exception {
		BugCategory category = (BugCategory)command;
		if( category.getBugCategoryId() != null )
			categoryDAO.updateCategory( category );
		else {
			if( category.getParentCategory() != null ) {
				BugCategory parent = new BugCategory();
				parent.setBugCategoryId( category.getParentCategory() );
				categoryDAO.insertCategory( category, parent );
			} else {
				categoryDAO.insertRootCategory( category );
			}
		}
		ModelAndView mav = new ModelAndView( getSuccessView() );
		mav.addObject( "categories", categoryDAO.getAllCategories() );
		return mav;
	}
	
	@Override
	protected Object formBackingObject( HttpServletRequest request ) throws Exception {
		BugCategory category = new BugCategory();
		try {
			// jesli jest parentId to mamy nowego nie roota
			// jesli nie ma zadnych parametrow to mamy nowego roota
			// jesli mamy catId to edytujemy istniejaca kategorie
			
			category = categoryDAO.getById( Long.parseLong( request.getParameter( "catId" ) ) );
		} catch( Exception ex ) {
			log.error( "Category not found" );
		}
		return category;
	}
	
	/** @param categoryDAO the categoryDAO to set */
	public void setCategoryDAO( BugCategoryDAO categoryDAO ) {
		this.categoryDAO = categoryDAO;
	}
}
