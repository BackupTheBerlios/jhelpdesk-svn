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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jjhop
 */
@Entity
@Table(name = "article")
@SequenceGenerator(name = "article_sequence", sequenceName = "article_id_seq", allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "Article.lastAdded", query = "SELECT a FROM Article a ORDER BY a.createDate DESC"),
    @NamedQuery(name = "Article.getForCategory", 
        query = "SELECT a FROM Article a WHERE a.category.articleCateogryId=?1 ORDER BY a.createDate DESC")
})
public class Article implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="article_sequence")
    @Column(name = "article_id")
    private Long articleId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_category_id")
    private ArticleCategory category;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User author;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    /**
     *
     */
    @Column(name = "title")
    private String title;

    /**
     *
     */
    @Column(name = "lead")
    private String lead;

    /**
     *
     */
    @Column(name = "body")
    private String body;

    /**
     *
     */
    @OneToMany(mappedBy = "article", cascade = {CascadeType.REMOVE})
    private Set<ArticleComment> comments;

    /**
     *
     */
    @ManyToMany
    @JoinTable(name = "article_ticket",
        joinColumns = {@JoinColumn(name = "article_id")},
        inverseJoinColumns = {@JoinColumn(name = "ticket_id")})
    private Set<Ticket> associatedTickets;

    /**
     * 
     */
    public Article() {
        this.comments = new HashSet<ArticleComment>();
        this.createDate = new Date();
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
     * @return Returns the articleId.
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * @param articleId The articleId to set.
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * 
     * @return
     */
    public ArticleCategory getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     */
    public void setCategory(ArticleCategory category) {
        this.category = category;
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
    public Set<ArticleComment> getComments() {
        return comments;
    }

    /**
     * @param comments The comments to set.
     */
    public void setComments(Set<ArticleComment> comments) {
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
