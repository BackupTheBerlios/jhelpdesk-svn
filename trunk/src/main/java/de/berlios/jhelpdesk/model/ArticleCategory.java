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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jjhop
 */
@Entity
@Table(name = "article_category")
@SequenceGenerator(name="article_category_sequence", sequenceName="article_category_id_seq")
@NamedQueries({
    @NamedQuery(name = "ArticleCategory.getAllOrderByPositionASC", query = "SELECT a FROM ArticleCategory a ORDER BY a.categoryPosition ASC")
})
public class ArticleCategory implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="article_category_sequence")
    @Column(name = "article_category_id")
    private Long articleCategoryId;

    /**
     *
     */
    @Column(name = "category_position")
    private Long categoryPosition;

    /**
     *
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     *
     */
    @Column(name = "articles_count")
    private int articlesCount;

    /**
     * 
     */
    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE})
    private Set<Article> articles;

    public ArticleCategory() {
        this.articles = new HashSet<Article>();
    }

    /**
     * @return Returns the articleCategoryId.
     */
    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

    /**
     * @param articleCategoryId The articleCategoryId to set.
     */
    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    /**
     * @return Returns the categoryName.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName The categoryName to set.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return Returns the categoryPosition.
     */
    public Long getCategoryPosition() {
        return categoryPosition;
    }

    /**
     * @param categoryPosition The categoryPosition to set.
     */
    public void setCategoryPosition(Long categoryPosition) {
        this.categoryPosition = categoryPosition;
    }

    /**
     * @return the articlesCount
     */
    //todo: trochę to bez sensu, że się articlesCount nie zgadza z articles.size()
    public int getArticlesCount() {
        if (articles != null) {
            articlesCount = articles.size();
        }
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
