package de.berlios.jhelpdesk.model;

import java.util.Date;
import java.util.Set;

public class Knowledge {

	private Long knowledgeId;
	private Long knowledgeSectionId;
	private User author;
	private Date createDate;
	private String title;
	private String lead;
	private String body;
	private Set<KnowledgeComment> comments;
	private Set<Bug> associatedBugs;

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
	 * @return Returns the comments.
	 */
	public Set<KnowledgeComment> getComments() {
		return comments;
	}

	/**
	 * @param comments The comments to set.
	 */
	public void setComments(Set<KnowledgeComment> comments) {
		this.comments = comments;
	}

	/**
	 * @return Returns the associatedBugs.
	 */
	public Set<Bug> getAssociatedBugs() {
		return associatedBugs;
	}

	/**
	 * @param associatedBugs The associatedBugs to set.
	 */
	public void setAssociatedBugs(Set<Bug> associatedBugs) {
		this.associatedBugs = associatedBugs;
	}
}
