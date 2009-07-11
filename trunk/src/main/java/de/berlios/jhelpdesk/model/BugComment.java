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
