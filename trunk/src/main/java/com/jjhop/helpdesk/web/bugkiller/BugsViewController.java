package com.jjhop.helpdesk.web.bugkiller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.jjhop.helpdesk.dao.BugCategoryDAO;
import com.jjhop.helpdesk.dao.BugDAO;
import com.jjhop.helpdesk.dao.BugPriorityDAO;
import com.jjhop.helpdesk.dao.BugStatusDAO;
import com.jjhop.helpdesk.dao.UserDAO;
import com.jjhop.helpdesk.web.commons.PagingParamsEncoder;
import com.jjhop.helpdesk.web.form.ShowBugsFilterForm;

public class BugsViewController extends SimpleFormController {
	private static Log log = LogFactory.getLog( BugsViewController.class );
	private static int PAGE_SIZE = 25;
	private BugDAO bugDao;
	private BugCategoryDAO bugCategoryDAO;
	private BugPriorityDAO bugPriorityDAO;
	private BugStatusDAO bugStatusDAO;
	private UserDAO userDAO;
	private SimpleDateFormat dateFormat;
	private ShowBugsFilterForm filterForm;
	private Map refData;
	
	@Override
	protected ModelAndView showForm( HttpServletRequest request, HttpServletResponse arg1, BindException arg2 ) throws Exception {
		log.info( "showForm()->start" );
		if( request.getParameter( "format" ) != null && request.getParameter( "format" ).equalsIgnoreCase( "pdf" ) ) {
			ModelAndView pdfMaV = new ModelAndView( "full-list-pdf" );
			pdfMaV.addObject( "bugs", bugDao.getBugsWithFilter( filterForm, Integer.MAX_VALUE, 0 ) );
			return pdfMaV;
		}
		return super.showForm( request, arg1, arg2 );
	}

    @Override
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
		log.info( "initBinder()->start" );
		dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		binder.registerCustomEditor( Date.class, new CustomDateEditor(dateFormat, true)); 
		log.info( " initBinder()->end" );
	}
	
	/**
	 * tutaj chyba trzeba zapakowa� do view zestaw wszystkich interesujacych zgłoszeń
	 * przynajmniej na poczatek bo potem w onSubmit bedziemy robic to jeszcze raz
	*/
    @Override
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws ServletException {
		log.info( "referenceData()->start" );
		if( refData == null ) {
			refData = new HashMap();
			refData.put( "categories", bugCategoryDAO.getAllCategoriesForView() );
			refData.put( "priorities", bugPriorityDAO.getAllPriorities() );
			refData.put( "statuses", bugStatusDAO.getAllStatuses() );
			refData.put( "users", userDAO.getAllUser() );
			refData.put( "saviours", userDAO.getSaviours() );
			if( filterForm != null ) {
				PagingParamsEncoder enc = 
					new PagingParamsEncoder( "bugsIterator", "b_status",request,PAGE_SIZE);
				refData.put( "bugsListSize", bugDao.countBugsWithFilter( filterForm ) );
				refData.put( "bugs", bugDao.getBugsWithFilter( filterForm, PAGE_SIZE, enc.getOffset() ) );
				log.debug( "getBugsWithFilter()" );
			} else {
				refData.put( "bugs", bugDao.getAllBugs() );
				log.debug( "getAllBugsAsBean()" );
			}
		}
		return refData;
	}	
	
	/**
	 *
	*/
    @Override
	protected void onBind( HttpServletRequest req, Object command ) {
		log.info( "onBind()->start " );
		filterForm = ( ShowBugsFilterForm ) command;
		
		filterForm.setBugCategoryDAO( bugCategoryDAO );
		filterForm.setBugPriorityDAO( bugPriorityDAO );
		filterForm.setBugStatusDAO( bugStatusDAO );
		filterForm.setUserDAO( userDAO );
		
		prepareDate( req );
		filterForm.setStatusesFromRequest( req );
		filterForm.setPrioritiesFromRequest( req );
		filterForm.setCategoriesFromRequest( req );
		filterForm.setSavioursFromRequest( req );
		filterForm.setNotifyiersFromRequest( req );
		log.info( "onBind()->end " );
	}
	
	/**
	 * 
	*/
    @Override
	protected Object formBackingObject( HttpServletRequest req ) throws ServletException {
		log.info( "formBackingObject()->start" );
		if( filterForm == null ) {
			filterForm = new ShowBugsFilterForm();
			filterForm.setBugCategoryDAO( bugCategoryDAO );
			filterForm.setBugPriorityDAO( bugPriorityDAO );
			filterForm.setBugStatusDAO( bugStatusDAO );
			filterForm.setUserDAO( userDAO );
		}
		prepareDate( req );
		filterForm.setStatusesFromRequest( req );
		filterForm.setPrioritiesFromRequest( req );
		filterForm.setCategoriesFromRequest( req );
		filterForm.setSavioursFromRequest( req );
		filterForm.setNotifyiersFromRequest( req );
		return filterForm;
	}

	/**
	 * 
	*/
    @Override
	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit( HttpServletRequest request, HttpServletResponse res, Object command, BindException errors ) throws Exception {
		log.info( "onSubmit()->start" );
		
		ModelAndView mav = new ModelAndView( "bugsList" );

		if( refData == null ) {
			refData = new HashMap();
			refData.put( "categories", bugCategoryDAO.getAllCategoriesForView() );
			refData.put( "priorities", bugPriorityDAO.getAllPriorities() );
			refData.put( "statuses", bugStatusDAO.getAllStatuses() );
			refData.put( "users", userDAO.getAllUser() );
			refData.put( "saviours", userDAO.getSaviours() );
			if( filterForm != null ) {
				PagingParamsEncoder enc = 
					new PagingParamsEncoder( "bugsIterator", "b_status",request,PAGE_SIZE);
				refData.put( "bugsListSize", bugDao.countBugsWithFilter( filterForm ) );
				refData.put( "bugs", bugDao.getBugsWithFilter( filterForm, PAGE_SIZE, enc.getOffset() ) );
			} else
				refData.put( "bugs", bugDao.getAllBugs() );
		}
		refData.put( "filterForm", (ShowBugsFilterForm) command );
		mav.addAllObjects( refData );
		return mav;
	}
	
	/**
	 * 
	 * @param req
	*/
	private void prepareDate( HttpServletRequest req ) {
		String[] startDate_ = req.getParameterValues( "startDate" );
		if( startDate_ != null ) {
			filterForm.setStartDate( startDate_[startDate_.length-1] );	
		} else {
			filterForm.setStartDate( null );
		}
		
		String[] endDate_ = req.getParameterValues( "endDate" );
		if( endDate_ != null ) {
			filterForm.setEndDate( endDate_[endDate_.length-1] );	
		} else {
			filterForm.setEndDate( null );
		}		
	}
	
	/**
	 * @param bugDao
	 *            The bugDao to set.	 
	*/
	public void setBugDao(BugDAO bugDao) {
		this.bugDao = bugDao;
	}

	/**
	 * @param bugPriorityDAO The bugPriorityDAO to set.
	 */
	public void setBugPriorityDAO(BugPriorityDAO bugPriorityDAO) {
		this.bugPriorityDAO = bugPriorityDAO;
	}

	/**
	 * @param bugCategoryDAO The bugCategoryDAO to set.
	 */
	public void setBugCategoryDAO(BugCategoryDAO bugCategoryDAO) {
		this.bugCategoryDAO = bugCategoryDAO;
	}

	/**
	 * @param bugStatusDAO The bugStatusDAO to set.
	 */
	public void setBugStatusDAO(BugStatusDAO bugStatusDAO) {
		this.bugStatusDAO = bugStatusDAO;
	}

	/**
	 * @param userDAO The userDAO to set.
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}	
}
