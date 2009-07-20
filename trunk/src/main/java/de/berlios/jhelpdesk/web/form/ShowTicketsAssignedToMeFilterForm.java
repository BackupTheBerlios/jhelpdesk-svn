/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright: (C) 2006 jHelpdesk Developers Team
 */
package de.berlios.jhelpdesk.web.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;

public class ShowTicketsAssignedToMeFilterForm {
	private Date startDate;
	private Date endDate;
	private List<TicketPriority> priorities;
	private List<User> notifyiers;
	private List<TicketCategory> categories;
	private List<TicketStatus> statuses;
	
	private TicketCategoryDAO ticketCategoryDAO;
	private UserDAO userDAO;
	
	public void setStatusesFromRequest(HttpServletRequest req) {
		if (statuses != null)
			statuses.clear();
		else
			statuses = new ArrayList<TicketStatus>();
		
		String[] _statuses = req.getParameterValues("statuses");
		if ((_statuses != null) && (_statuses.length > 0)) {
			for (String value : _statuses) {
				statuses.add(TicketStatus.fromInt(Integer.parseInt(value)));
			}
		}
	}
	
	public void setPrioritiesFromRequest(HttpServletRequest req) {
		if (priorities != null)
			priorities.clear();
		priorities = new ArrayList<TicketPriority>();
		
		String[] _priorities = req.getParameterValues("priorities");
		if ((_priorities != null) && (_priorities.length > 0)) {
			for (String value : _priorities) {
                priorities.add(TicketPriority.fromInt(Integer.parseInt(value)));
			}
		}
	}
	
	public void setCategoriesFromRequest(HttpServletRequest req) {
		if (categories != null)
			categories.clear();
		else
			categories = new ArrayList<TicketCategory>();
		
		String[] _categories = req.getParameterValues("categories");
		if ((_categories != null) && (_categories.length > 0)) {
			for (String value : _categories) {
				categories.add(ticketCategoryDAO.getById(Long.parseLong(value)));
			}
		}
	}
	
	public void setNotifyiersFromRequest(HttpServletRequest req) {
		if (notifyiers != null)
			notifyiers.clear();
		else 
			notifyiers = new ArrayList<User>();
		
		String[] _notifyiers = req.getParameterValues("notifyiers");
		if ((_notifyiers != null) && (_notifyiers.length > 0)) {
			for (String value : _notifyiers) {
				notifyiers.add(userDAO.getById(Long.parseLong(value)));
			}
		}
	}
	/**
	 * @return Returns the categories.
	 */
	public List<TicketCategory> getCategories() {
		return categories;
	}
	/**
	 * @param categories The categories to set.
	 */
	public void setCategories(List<TicketCategory> categories) {
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
	 * @return Returns the priorities.
	 */
	public List<TicketPriority> getPriorities() {
		return priorities;
	}
	/**
	 * @param priorities The priorities to set.
	 */
	public void setPriorities(List<TicketPriority> priorities) {
		this.priorities = priorities;
	}
	/**
	 * @return Returns the saviours.
	 */
	public List<User> getNotifyiers() {
		return notifyiers;
	}
	/**
	 * @param saviours The saviours to set.
	 */
	public void setNotifyiers(List<User> saviours) {
		this.notifyiers = saviours;
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
	/**
	 * @return Returns the statuses.
	 */
	public List<TicketStatus> getStatuses() {
		return statuses;
	}
	/**
	 * @param statuses The statuses to set.
	 */
	public void setStatuses(List<TicketStatus> statuses) {
		this.statuses = statuses;
	}

	/**
	 * @param ticketCategoryDAO The ticketCategoryDAO to set.
	 */
	public void setTicketCategoryDAO(TicketCategoryDAO ticketCategoryDAO) {
		this.ticketCategoryDAO = ticketCategoryDAO;
	}

	/**
	 * @param userDAO The userDAO to set.
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
}