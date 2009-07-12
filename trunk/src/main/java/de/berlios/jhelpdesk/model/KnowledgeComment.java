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

public class KnowledgeComment {
	
	private Long knowledgeCommentId;
	private Long knowledgeId;
	private User authorId;
	private Date createDate;
	private String title;
	private String body;

	/**
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
