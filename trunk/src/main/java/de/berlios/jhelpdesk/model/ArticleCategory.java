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
    @NamedQuery(name = "ArticleCategory.getAllOrderByPositionASC", query = "FROM ArticleCategory a ORDER BY a.cateogryPosition ASC")
})
public class ArticleCategory implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="article_category_sequence")
    @Column(name = "article_category_id")
    private Long articleCateogryId; // TODO: refaktoryzacja na articleCategoryId

    /**
     *
     */
    @Column(name = "category_position")
    private Long cateogryPosition; // TODO: refaktoryzacja na categoryPosition

    /**
     *
     */
    @Column(name = "category_name")
    private String cateogryName; // TODO: refaktoryzacja na categoryName

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
