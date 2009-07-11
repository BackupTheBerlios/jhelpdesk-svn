package de.berlios.jhelpdesk.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;
import de.berlios.jhelpdesk.dao.BugPriorityDAO;
import de.berlios.jhelpdesk.dao.BugStatusDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.BugCategory;
import de.berlios.jhelpdesk.model.BugPriority;
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.model.User;

public class ShowBugsFilterForm {
	private String startDate;
	private String endDate;
	private List<BugPriority> priorities;
	private List<User> saviours;
	private List<User> notifyiers;
	private List<BugCategory> categories;
	private List<BugStatus> statuses;
	
	private BugStatusDAO bugStatusDAO;
	private BugPriorityDAO bugPriorityDAO;
	private BugCategoryDAO bugCategoryDAO;
	private UserDAO userDAO;
	
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

	public void setStatusesFromRequest( HttpServletRequest req ) {
		if( statuses != null )
			statuses.clear();
		else
			statuses = new ArrayList<BugStatus>();
		
		String[] _statuses = req.getParameterValues( "statuses" );
		if( ( _statuses != null ) && ( _statuses.length > 0 ) ) {
			for( String value : _statuses ) {
				statuses.add( bugStatusDAO.getById( Long.parseLong( value ) ) );
			}
		}
	}
	
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
	
	public void setSavioursFromRequest( HttpServletRequest req ) {
		if( saviours != null )
			saviours.clear();
		else 
			saviours = new ArrayList<User>();
		
		String[] _saviours = req.getParameterValues( "saviours" );
		if( ( _saviours != null ) && ( _saviours.length > 0 ) ) {
			for( String value : _saviours ) {
				saviours.add( userDAO.getById( Long.parseLong( value ) ) );
			}
		}
	}
	
	public void setNotifyiersFromRequest( HttpServletRequest req ) {
		if( notifyiers != null )
			notifyiers.clear();
		else
			notifyiers = new ArrayList<User>();
		
		String[] _notifyiers = req.getParameterValues( "notifyiers" );
		if( ( _notifyiers != null ) && ( _notifyiers.length > 0 ) ) {
			for( String value : _notifyiers ) {
				notifyiers.add( userDAO.getById( Long.parseLong( value ) ) );
			}
		}
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
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate( String endDate ) {
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
	 * @return Returns the saviours.
	 */
	public List<User> getSaviours() {
		return saviours;
	}
	/**
	 * @param saviours The saviours to set.
	 */
	public void setSaviours(List<User> saviours) {
		this.saviours = saviours;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate( String startDate ) {
		this.startDate = startDate;
	}
	/**
	 * @return Returns the statuses.
	 */
	public List<BugStatus> getStatuses() {
		return statuses;
	}
	/**
	 * @param statuses The statuses to set.
	 */
	public void setStatuses(List<BugStatus> statuses) {
		this.statuses = statuses;
	}
}
