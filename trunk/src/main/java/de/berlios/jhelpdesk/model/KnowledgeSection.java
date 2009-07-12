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

import java.util.Set;

public class KnowledgeSection {

	private Long hdKnowledgeSectionId;
	private Long sectionPosition;
	private String sectionName;
	private int articlesCount;
	private Set<Knowledge> articles;

	/**
	 * @return Returns the hdKnowledgeSectionId.
	 */
	public Long getHdKnowledgeSectionId() {
		return hdKnowledgeSectionId;
	}

	/**
	 * @param hdKnowledgeSectionId The hdKnowledgeSectionId to set.
	 */
	public void setHdKnowledgeSectionId(Long hdKnowledgeSectionId) {
		this.hdKnowledgeSectionId = hdKnowledgeSectionId;
	}

	/**
	 * @return Returns the sectionName.
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * @param sectionName The sectionName to set.
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * @return Returns the sectionPosition.
	 */
	public Long getSectionPosition() {
		return sectionPosition;
	}

	/**
	 * @param sectionPosition The sectionPosition to set.
	 */
	public void setSectionPosition(Long sectionPosition) {
		this.sectionPosition = sectionPosition;
	}

	/**
	 * @return the articlesCount
	 */
	public int getArticlesCount() {
		if (articles != null)
			articlesCount = articles.size();
		return articlesCount;
	}

	/**
	 * @param articlesCount the articlesCount to set
	 */
	public void setArticlesCount(int articlesCount) {
		this.articlesCount = articlesCount;
	}

	/**
	 * @return Returns the articles.
	 */
	public Set<Knowledge> getArticles() {
		return articles;
	}

	/**
	 * @param articles The articles to set.
	 */
	public void setArticles(Set<Knowledge> articles) {
		this.articles = articles;
	}

}
