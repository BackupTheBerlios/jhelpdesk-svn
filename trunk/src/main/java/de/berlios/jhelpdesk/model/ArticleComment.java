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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jjhop
 */
@Entity
@Table(name = "article_comment")
public class ArticleComment implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_comment_id")
    private Long articleCommentId;

    /**
     * // TODO: migrujemy do article, stąd też w mapowaniu to:
     *     insertable = false, updatable = false
     */
    @Column(name = "article_id", nullable = false, insertable = false, updatable = false)
    private Long articleId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "author", referencedColumnName = "user_id")
    private User authorId;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    /**
     *
     */
    @Column(name = "title", length = 255)
    private String title;

    /**
     * 
     */
    @Column(name = "body")
    private String body;

    /**
     *
     * @return
     */
    public Long getArticleCommentId() {
        return articleCommentId;
    }

    /**
     *
     * @param articleCommentId
     */
    public void setArticleCommentId(Long articleCommentId) {
        this.articleCommentId = articleCommentId;
    }

    /**
     *
     * @return
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     *
     * @param articleId
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     *
     * @return
     */
    public Article getArticle() {
        return article;
    }

    /**
     * 
     * @param article
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     *
     * @return
     */
    public User getAuthorId() {
        return authorId;
    }

    /**
     * 
     * @param authorId
     */
    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    /**
     * 
     * @return
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Zwraca zawartość pola body artykułu.
     * 
     * @return zawartość pola body artykułu
     */
    public String getBody() {
        return body;
    }

    /**
     * 
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }
}
