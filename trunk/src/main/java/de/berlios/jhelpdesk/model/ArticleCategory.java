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

/**
 *
 * @author jjhop
 */
public class ArticleCategory {

    /**
     *
     */
	private Long articleCateogryId;

    /**
     *
     */
	private Long cateogryPosition;

    /**
     *
     */
	private String cateogryName;

    /**
     *
     */
	private int articlesCount;

    /**
     * 
     */
	private Set<Article> articles;

	/**
	 * @return Returns the articleCateogryId.
	 */
	public Long getArticleCategoryId() {
		return articleCateogryId;
	}

	/**
	 * @param articleCateogryId The articleCateogryId to set.
	 */
	public void setArticleCategoryId(Long articleCategoryId) {
		this.articleCateogryId = articleCategoryId;
	}

	/**
	 * @return Returns the cateogryName.
	 */
	public String getCategoryName() {
		return cateogryName;
	}

	/**
	 * @param cateogryName The cateogryName to set.
	 */
	public void setCategoryName(String cateogryName) {
		this.cateogryName = cateogryName;
	}

	/**
	 * @return Returns the cateogryPosition.
	 */
	public Long getCategoryPosition() {
		return cateogryPosition;
	}

	/**
	 * @param cateogryPosition The cateogryPosition to set.
	 */
	public void setCategoryPosition(Long categoryPosition) {
		this.cateogryPosition = categoryPosition;
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
	public Set<Article> getArticles() {
		return articles;
	}

	/**
	 * @param articles The articles to set.
	 */
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

}
