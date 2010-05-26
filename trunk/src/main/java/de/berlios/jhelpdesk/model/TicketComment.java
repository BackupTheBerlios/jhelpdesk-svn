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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author jjhop
 */
@Entity
@Table(name = "ticket_comment") // -> ticket_comments
@SequenceGenerator(name = "ticket_comment_sequence", sequenceName = "ticket_comment_id_seq", allocationSize = 1)
public class TicketComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ticket_comment_sequence")
    @Column(name = "comment_id")
    private Long ticketCommentId;

    @ManyToOne
    @JoinColumn(name="ticket_id")
    private Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User commentAuthor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_date")
    private Date commentDate;

    @Column(name="comment_text")
    private String commentText;

    /**
     * Warto to zmienic na private lub coś w ten deseń...
     */
    @Column(name="not_for_plain_user")
    private boolean notForPlainUser;

    public TicketComment() {
        this.notForPlainUser = true;
        this.commentDate = new Date();
    }

    /**
     * @return Returns the ticketCommentId.
     */
    public Long getTicketCommentId() {
        return ticketCommentId;
    }

    /**
     * @param ticketCommentId The ticketCommentId to set.
     */
    public void setTicketCommentId(Long ticketCommentId) {
        this.ticketCommentId = ticketCommentId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * @return Returns the commentAuthor.
     */
    public User getCommentAuthor() {
        return commentAuthor;
    }

    /**
     * @param commentAuthor The commentAuthor to set.
     */
    public void setCommentAuthor(User commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    /**
     * @return Returns the commentDate.
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * @param commentDate The commentDate to set.
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * @return Returns the commentText.
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * @param commentText The commentText to set.
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     * @param notForPlainUser The notForPlainUser to set.
     */
    public void setNotForPlainUser(boolean notForPlainUser) {
        this.notForPlainUser = notForPlainUser;
    }

    /**
     * @return Returns the notForPlainUser.
     */
    public boolean isNotForPlainUser() {
        return notForPlainUser;
    }
}
