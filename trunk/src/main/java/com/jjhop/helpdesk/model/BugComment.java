package com.jjhop.helpdesk.model;

import java.util.Date;

/**
 * @hibernate.class
 * 		table="hd_bug_comment"
 * 
 * @author jjhop
 *
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
	 * @hibernate.id
	 * 		generator-class="sequence"
	 * 		column="comment_id"
	 * 		type="java.lang.Long"
	 * @hibernate.generator-param
	 * 		name="sequence"
	 * 		value="hd_bug_comment_comment_id_seq" 
	 * 
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
	 * @hibernate.property
	 * 		column="bug_id"
	 * 		type="java.lang.Long"
	 * 		not-null="true"
	 * 
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
	 * @hibernate.many-to-one
	 * 		column="comment_author"
	 * 		not-null="true"
	 * 
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
	 * @hibernate.property
	 * 		column="comment_date"
	 * 		type="java.util.Date"
	 * 		length="255"
	 * 
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
	 * @hibernate.property
	 * 		column="comment_text"
	 * 		type="java.lang.String"
	 * 		length="255"
	 * 
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
	 * @hibernate.property
	 * 		column="not_for_plain_user"
	 * 		type="boolean"
	 * 		not-null="true"
	 * @return Returns the notForPlainUser.
	 */
	public boolean isNotForPlainUser() {
		return notForPlainUser;
	}

}
