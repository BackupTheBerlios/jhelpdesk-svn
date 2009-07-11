package de.berlios.jhelpdesk.model;

import java.util.Date;
import java.util.Set;

/**
 * @hibernate.class
 * 		table="hd_knowledge"
 */
public class Knowledge {
	private Long knowledgeId;
	private Long knowledgeSectionId;
	private User author;
	private Date createDate;
	private String title;
	private String lead;
	private String body;
	private Set comments;
	private Set associatedBugs;
	
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
	 * @hibernate.id
	 * 		generator-class="sequence"
	 * 		column="hd_knowledge_id"
	 * 
	 * @hibernate.generator-param
	 * 		name="sequence"
	 * 		value="hd_knowledge_hd_knowledge_id_seq"
	 * 
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
	 * @hibernate.property
	 * 		column="title"
	 * 		type="java.lang.String"
	 * 		not-null="true"
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
	 * 		column="hd_knowledge_section_id"
	 * 		type="java.lang.Long"
	 * 		not-null="true"
	 * @return Returns the knowledgeSectionId.
	 */
	public Long getKnowledgeSectionId() {
		return knowledgeSectionId;
	}
	/**
	 * @param knowledgeSectionId The knowledgeSectionId to set.
	 */
	public void setKnowledgeSectionId(Long knowledgeSectionId) {
		this.knowledgeSectionId = knowledgeSectionId;
	}
	
	/**
	 * @return Returns the author.
	 */
	public User getAuthor() {
		return author;
	}
	/**
	 * @param author The author to set.
	 */
	public void setAuthor(User author) {
		this.author = author;
	}
	
	/**
	 * @hibernate.property
	 * 		column="body"
	 * 		type="java.lang.String"
	 * 		not-null="true"
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
	/**
	 * @hibernate.property
	 * 		column="lead"
	 * 		type="java.lang.String"
	 * 		not-null="true"
	 * @return Returns the lead.
	 */
	public String getLead() {
		return lead;
	}
	/**
	 * @param lead The lead to set.
	 */
	public void setLead(String lead) {
		this.lead = lead;
	}
	
	/**
	 * @hibernate.set
	 * 		lazy="false"
	 * 		cascade="all"
	 * @hibernate.collection-one-to-many
	 * 		class="de.berlios.jhelpdesk.model.HDKnowledgeComment"
	 * @hibernate.collection-key
	 * 		column="hd_knowledge_id"
	 * 
	 * @return Returns the comments.
	 */
	public Set getComments() {
		return comments;
	}
	/**
	 * @param comments The comments to set.
	 */
	public void setComments(Set comments) {
		this.comments = comments;
	}
	
	/**
	 * @hibernate.set
	 * 		table="hd_bug_knowledge"
	 * 		lazy="false"
	 * 		inverse="false"
	 * @hibernate.collection-many-to-many
	 * 		class="de.berlios.jhelpdesk.model.HDBug"
	 * 		column="hd_bug_id"
	 * @hibernate.collection-key
	 * 		column="hd_knowledge_id"
	 *
	 * @return Returns the associatedBugs.
	 */
	public Set getAssociatedBugs() {
		return associatedBugs;
	}
	/**
	 * @param associatedBugs The associatedBugs to set.
	 */
	public void setAssociatedBugs(Set associatedBugs) {
		this.associatedBugs = associatedBugs;
	}
}
