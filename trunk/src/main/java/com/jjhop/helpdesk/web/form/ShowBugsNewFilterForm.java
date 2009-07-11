package com.jjhop.helpdesk.web.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jjhop.helpdesk.dao.BugCategoryDAO;
import com.jjhop.helpdesk.dao.BugPriorityDAO;
import com.jjhop.helpdesk.dao.UserDAO;
import com.jjhop.helpdesk.model.BugCategory;
import com.jjhop.helpdesk.model.BugPriority;
import com.jjhop.helpdesk.model.User;

public class ShowBugsNewFilterForm {
	private Date startDate;
	private Date endDate;
	private List<BugPriority> priorities;
	private List<User> notifyiers;
	private List<BugCategory> categories;
	
	private BugPriorityDAO bugPriorityDAO;
	private BugCategoryDAO bugCategoryDAO;
	private UserDAO userDAO;
	
	
	public void setPrioritiesFromRequest( HttpServletRequest req ) {
		if( priorities != null )
			priorities.clear();
		priorities = new ArrayList<BugPriority>();
		
		String[] _priorities = req.getParameterValues( "priorities" );
		if( ( _priorities != null ) && ( _priorities.length > 0 ) ) {
			for( String value : _priorities ) {
				priorities.add( bugPriorityDAO.getById( Long.parseLong( value ) ) );
			}
		}
	}
	
	public void setCategoriesFromRequest( HttpServletRequest req ) {
		if( categories != null )
			categories.clear();
		else
			categories = new ArrayList<BugCategory>();
		
		String[] _categories = req.getParameterValues( "categories" );
		if( ( _categories != null ) && ( _categories.length > 0 ) ) {
			for( String value : _categories ) {
				categories.add( bugCategoryDAO.getById( Long.parseLong( value ) ) );
			}
		}
	}
	
	public void setNotifyiersFromRequest( HttpServletRequest req ) {
		if( notifyiers != null )
			notifyiers.clear();
		else 
			notifyiers = new ArrayList<User>();
		
		String[] _saviours = req.getParameterValues( "notifyiers" );
		if( ( _saviours != null ) && ( _saviours.length > 0 ) ) {
			for( String value : _saviours ) {
				notifyiers.add( userDAO.getById( Long.parseLong( value ) ) );
			}
		}
	}
	
	/**
	 * @param bugCategoryDAO The bugCategoryDAO to set.
	 */
	public void setBugCategoryDAO(BugCategoryDAO bugCategoryDAO) {
		this.bugCategoryDAO = bugCategoryDAO;
	}
	/**
	 * @param bugPriorityDAO The bugPriorityDAO to set.
	 */
	public void setBugPriorityDAO(BugPriorityDAO bugPriorityDAO) {
		this.bugPriorityDAO = bugPriorityDAO;
	}
	/**
	 * @param userDAO The userDAO to set.
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	/**
	 * @return Returns the categories.
	 */
	public List<BugCategory> getCategories() {
		return categories;
	}
	/**
	 * @param categories The categories to set.
	 */
	public void setCategories(List<BugCategory> categories) {
		this.categories = categories;
	}
	/**
	 * @return Returns the endDate.
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the notifyiers.
	 */
	public List<User> getNotifyiers() {
		return notifyiers;
	}
	/**
	 * @param notifyiers The notifyiers to set.
	 */
	public void setNotifyiers(List<User> notifyiers) {
		this.notifyiers = notifyiers;
	}
	/**
	 * @return Returns the priorities.
	 */
	public List<BugPriority> getPriorities() {
		return priorities;
	}
	/**
	 * @param priorities The priorities to set.
	 */
	public void setPriorities(List<BugPriority> priorities) {
		this.priorities = priorities;
	}
	/**
	 * @return Returns the startDate.
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
