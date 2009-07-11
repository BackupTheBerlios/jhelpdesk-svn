package com.jjhop.helpdesk.model;

import java.util.Date;

/**
 * @hibernate.class
 * 		table="hd_knowledge_comment"
 */
public class KnowledgeComment {
	private Long knowledgeCommentId;
	private Long knowledgeId;
	private User authorId;
	private Date createDate;
	private String title;
	private String body;
	
	/**
	 * @hibernate.id
	 * 		generator-class="sequence"
	 * 		column="hd_knowledge_comment_id"
	 * 
	 * @hibernate.generator-param
	 * 		name="sequence"
	 * 		value="hd_knowledge_comment_hd_knowledge_comment_id_seq"
	 * 
	 * @return Returns the knowledgeCommentId.
	 */
	public Long getKnowledgeCommentId() {
		return knowledgeCommentId;
	}
	/**
	 * @param knowledgeCommentId The knowledgeCommentId to set.
	 */
	public void setKnowledgeCommentId(Long knowledgeCommentId) {
		this.knowledgeCommentId = knowledgeCommentId;
	}
	
	/**
	 * @return Returns the knowledgeId.
	 */
	public Long getKnowledgeId() {
		return knowledgeId;
	}
	/**
	 * @param knowledgeId The knowledgeId to set.
	 */
	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	/**
	 * @hibernate.many-to-one
	 * 		column="author"
	 * @return Returns the authorId.
	 */
	public User getAuthorId() {
		return authorId;
	}
	/**
	 * @param authorId The authorId to set.
	 */
	public void setAuthorId(User authorId) {
		this.authorId = authorId;
	}
	
	/**
	 * @hibernate.property
	 * 		column="create_date"
	 * 		type="java.util.Date"
	 * 		not-null="true"
	 * 
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
	 * @hibernate.property
	 * 		column="title"
	 * 		type="java.lang.String"
	 * 		not-null="true"
	 * 
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @hibernate.property
	 * 		column="body"
	 * 		type="java.lang.String"
	 * 		not-null="true"
	 * 
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body The body to set.
	 */
	public void setBody(String body) {
		this.body = body;
	}
}
