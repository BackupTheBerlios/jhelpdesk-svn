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
package de.berlios.jhelpdesk.model;

import java.util.Date;

public class Announcement {
	private Long announcementId;
	private Date createDate;
	private String title;
	private String lead;
	private String body;
	
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * @param body the body to set
	 */
	public void setBody( String body ) {
		this.body = body;
	}
	
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate( Date createDate ) {
		this.createDate = createDate;
	}
	
	/**
	 * @return the announcementId
	 */
	public Long getAnnouncementId() {
		return announcementId;
	}
	
	/**
	 * @param announcementId the announcementId to set
	 */
	public void setAnnouncementId( Long announcementId ) {
		this.announcementId = announcementId;
	}
	
	/**
	 * @return the lead
	 */
	public String getLead() {
		return lead;
	}
	
	/**
	 * @param lead the lead to set
	 */
	public void setLead( String lead ) {
		this.lead = lead;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle( String title ) {
		this.title = title;
	}
}
