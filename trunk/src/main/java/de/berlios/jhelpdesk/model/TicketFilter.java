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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author jjhop
 */
@Entity
@Table(name = "ticket_filters")
@SequenceGenerator(name = "ticket_filter_sequence", sequenceName = "ticket_filter_id_seq")
//@SecondaryTable(name="announcement_body",
//    pkJoinColumns=@PrimaryKeyJoinColumn(name="announcement_id", referencedColumnName="announcement_id"))
@NamedQueries({
//    @NamedQuery(name = "Announcement.allOrderByCreateDateDesc", query = "FROM Announcement a ORDER BY a.createDate DESC"),
    @NamedQuery(name = "TicketFilter.forUserOrderByNameASC", query = "SELECT tf FROM TicketFilter tf WHERE tf.owner=?1")
})
public class TicketFilter implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_filter_sequence")
    @Column(name = "id")
    private Long ticketFilterId;

    @Column(name = "tf_stamp", updatable = false, unique = true)
    private String tfStamp;
    
    /**
     *
     */
    @Column(name = "name")
    private String name;

    @Column(name = "begin_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Transient
    private List<TicketCategory> ticketCategories;

    @Transient
    private List<TicketPriority> ticketPriorities;

    @Transient
    private List<TicketStatus> ticketStatuses;

    @Transient
    private List<User> notifiers;

    @Transient
    private List<User> saviours;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;

    public Long getTicketFilterId() {
        return ticketFilterId;
    }

    public void setTicketFilterId(Long ticketFilterId) {
        this.ticketFilterId = ticketFilterId;
    }

    public String getTfStamp() {
        return tfStamp;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<TicketCategory> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(List<TicketCategory> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    public List<TicketPriority> getTicketPriorities() {
        return ticketPriorities;
    }

    public void setTicketPriorities(List<TicketPriority> ticketPriorities) {
        this.ticketPriorities = ticketPriorities;
    }

    public List<TicketStatus> getTicketStatuses() {
        return ticketStatuses;
    }

    public void setTicketStatuses(List<TicketStatus> ticketStatuses) {
        this.ticketStatuses = ticketStatuses;
    }

    public List<User> getNotifiers() {
        return notifiers;
    }

    public void setNotifiers(List<User> notifiers) {
        this.notifiers = notifiers;
    }

    public List<User> getSaviours() {
        return saviours;
    }

    public void setSaviours(List<User> saviours) {
        this.saviours = saviours;
    }

    @PrePersist
    protected void prePersist() {
        this.tfStamp = DigestUtils.shaHex(
            Thread.currentThread().getName() + Thread.currentThread().getId() +
            owner.getUserId() + owner.toString() + System.nanoTime() + name
        );
    }
}
