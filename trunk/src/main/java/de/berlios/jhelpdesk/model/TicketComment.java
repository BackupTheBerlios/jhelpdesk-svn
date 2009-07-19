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

/**
 * @author jjhop
 */
public class TicketComment {
	private Long ticketCommentId;
	private Long ticketId;
	private User commentAuthor;
	private Date commentDate;
	private String commentText;
	private boolean notForPlainUser;
	
	public TicketComment() {
		this.notForPlainUser = true;
	}

	/**
	 * @return Returns the ticketCommentId.
	 */
	public Long getTicketCommentId() {
		return ticketCommentId;
	}

	/**
	 * @param ticketCommentId The ticketCommentId to set.
	 */
	public void setTicketCommentId(Long ticketCommentId) {
		this.ticketCommentId = ticketCommentId;
	}
	

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
	 * @return Returns the commentAuthor.
	 */
	public User getCommentAuthor() {
		return commentAuthor;
	}

	/**
	 * @param commentAuthor The commentAuthor to set.
	 */
	public void setCommentAuthor(User commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	/**
	 * @return Returns the commentDate.
	 */
	public Date getCommentDate() {
		return commentDate;
	}

	/**
	 * @param commentDate The commentDate to set.
	 */
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}


	/**
	 * @return Returns the commentText.
	 */
	public String getCommentText() {
		return commentText;
	}

	/**
	 * @param commentText The commentText to set.
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	/**
	 * @param notForPlainUser The notForPlainUser to set.
	 */
	public void setNotForPlainUser(boolean notForPlainUser) {
		this.notForPlainUser = notForPlainUser;
	}

	/**
	 * @return Returns the notForPlainUser.
	 */
	public boolean isNotForPlainUser() {
		return notForPlainUser;
	}

}
