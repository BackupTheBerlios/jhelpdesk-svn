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
package de.berlios.jhelpdesk.web.tools.view;

import java.util.Date;

import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;

class _HDTicketViewBean {
	private Long ticketId;
	private String subject;
	private TicketCategory category;
	private Date createDate;
	private TicketStatus status;
	private TicketPriority priority;
	
	private Long notifierId;
	private String notifier;
	
	private Long saviourId;
	private String saviour;
	
	private Long inputerId;
	private String inputer;
	
	
	/**
	 * @return Returns the ticketId.
	 */
	public Long getTicketId() {
		return ticketId;
	}
	/**
	 * @param ticketId The ticketId to set.
	 */
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	/**
	 * @return Returns the category.
	 */
	public TicketCategory getCategory() {
		return category;
	}
	/**
	 * @param category The category to set.
	 */
	public void setCategory(TicketCategory category) {
		this.category = category;
	}
	/**
	 * @return Returns the createDate.
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate The createDate to set.
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return Returns the notifier.
	 */
	public String getNotifier() {
		return notifier;
	}
	/**
	 * @param notifier The notifier to set.
	 */
	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}
	/**
	 * @return Returns the notifierId.
	 */
	public Long getNotifierId() {
		return notifierId;
	}
	/**
	 * @param notifierId The notifierId to set.
	 */
	public void setNotifierId(Long notifierId) {
		this.notifierId = notifierId;
	}
	/**
	 * @return Returns the priority.
	 */
	public TicketPriority getPriority() {
		return priority;
	}
	/**
	 * @param priority The priority to set.
	 */
	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}
	/**
	 * @return Returns the status.
	 */
	public TicketStatus getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return Returns the inputer.
	 */
	public String getInputer() {
		return inputer;
	}
	/**
	 * @param inputer The inputer to set.
	 */
	public void setInputer(String inputer) {
		this.inputer = inputer;
	}
	/**
	 * @return Returns the inputerId.
	 */
	public Long getInputerId() {
		return inputerId;
	}
	/**
	 * @param inputerId The inputerId to set.
	 */
	public void setInputerId(Long inputerId) {
		this.inputerId = inputerId;
	}
	/**
	 * @return Returns the saviour.
	 */
	public String getSaviour() {
		return saviour;
	}
	/**
	 * @param saviour The saviour to set.
	 */
	public void setSaviour(String saviour) {
		this.saviour = saviour;
	}
	/**
	 * @return Returns the saviourId.
	 */
	public Long getSaviourId() {
		return saviourId;
	}
	/**
	 * @param saviourId The saviourId to set.
	 */
	public void setSaviourId(Long saviourId) {
		this.saviourId = saviourId;
	}
	
}
