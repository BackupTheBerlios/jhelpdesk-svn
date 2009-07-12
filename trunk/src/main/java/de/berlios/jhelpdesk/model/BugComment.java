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
public class BugComment {
	private Long bugCommentId;
	private Long bugId;
	private User commentAuthor;
	private Date commentDate;
	private String commentText;
	private boolean notForPlainUser;
	
	public BugComment() {
		this.notForPlainUser = true;
	}

	/**
	 * @return Returns the bugCommentId.
	 */
	public Long getBugCommentId() {
		return bugCommentId;
	}

	/**
	 * @param bugCommentId The bugCommentId to set.
	 */
	public void setBugCommentId(Long bugCommentId) {
		this.bugCommentId = bugCommentId;
	}
	

	/**
	 * @return Returns the bugId.
	 */
	public Long getBugId() {
		return bugId;
	}

	/**
	 * @param bugId The bugId to set.
	 */
	public void setBugId(Long bugId) {
		this.bugId = bugId;
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
