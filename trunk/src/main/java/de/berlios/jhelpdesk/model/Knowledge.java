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
	private Set<Ticket> associatedTickets;

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
	 * @return Returns the associatedTickets.
	 */
	public Set<Ticket> getAssociatedTickets() {
		return associatedTickets;
	}

	/**
	 * @param associatedTickets The associatedTickets to set.
	 */
	public void setAssociatedTickets(Set<Ticket> associatedTickets) {
		this.associatedTickets = associatedTickets;
	}
}
