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
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import de.berlios.jhelpdesk.utils.StampUtils;

/**
 * 
 * @author jjhop
 */
@Entity
@Table(name = "ticket_filters")
@SequenceGenerator(name = "ticket_filter_sequence", sequenceName = "ticket_filter_id_seq")
@NamedQueries({
    @NamedQuery(name = "TicketFilter.forUserOrderByNameASC", query = "SELECT tf FROM TicketFilter tf WHERE tf.owner=?1")
})
public class TicketFilter implements Serializable {

    /**
     * Identyfikator filtra.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_filter_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "tfstamp")
    private String tfStamp;
    
    /**
     * Nazwa dla filtru. Wyświetlana jest w menu.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Opis filtru. Wyświetla się po najechaniu na menu (oraz w panelu zarządzania filtrami).
     * Powinien dość czytelnie opisywać czego użytkownik spodziewa się po nałożenia filtra
     * na listę ticketów.
     */
    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "begin_date", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToMany
    @JoinTable(name="ticket_filters_ticket_categories",
               joinColumns={@JoinColumn(name="ticket_filter_id")},
               inverseJoinColumns={@JoinColumn(name="ticket_category_id")})
    private List<TicketCategory> ticketCategories;

    @ManyToMany
    @JoinTable(name="ticket_filters_notifiers",
               joinColumns={@JoinColumn(name="ticket_filter_id")},
               inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> notifiers;

    @ManyToMany
    @JoinTable(name="ticket_filters_saviours",
               joinColumns={@JoinColumn(name="ticket_filter_id")},
               inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> saviours;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;

    @Transient
    private List<TicketPriority> ticketPriorities;

    @Column(name = "priorities")
    private String ticketPrioritiesAsString;

    @Transient
    private List<TicketStatus> ticketStatuses;

    @Column(name = "statuses")
    private String ticketStatusesAsString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTfStamp() {
        return tfStamp;
    }

    public void setTfStamp(String tfStamp) {
        this.tfStamp = tfStamp;
    }

    public boolean isOwnedBy(User user) {
        return this.owner.equals(user);
    }

    public User getOwner() {
        return this.owner;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return ticketCategories == null
            ? Collections.<TicketCategory>emptyList()
            : ticketCategories;
    }

    public void setTicketCategories(List<TicketCategory> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    public List<TicketPriority> getTicketPriorities() {
        return ticketPriorities == null
            ? Collections.<TicketPriority>emptyList()
            : ticketPriorities;
    }

    public void setTicketPriorities(List<TicketPriority> ticketPriorities) {
        this.ticketPriorities = ticketPriorities;
        this.ticketPrioritiesAsString = TicketPriority.listAsString(this.ticketPriorities);
    }

    public List<TicketStatus> getTicketStatuses() {
        return ticketStatuses == null
            ? Collections.<TicketStatus>emptyList()
            : ticketStatuses;
    }

    public void setTicketStatuses(List<TicketStatus> ticketStatuses) {
        this.ticketStatuses = ticketStatuses;
        this.ticketStatusesAsString = TicketStatus.listAsString(this.ticketStatuses);
    }

    public List<User> getNotifiers() {
       return notifiers == null
            ? Collections.<User>emptyList()
            : notifiers;
    }

    public void setNotifiers(List<User> notifiers) {
        this.notifiers = notifiers;
    }

    public List<User> getSaviours() {
        return saviours == null
            ? Collections.<User>emptyList()
            : saviours;
    }

    public void setSaviours(List<User> saviours) {
        this.saviours = saviours;
    }

    @PrePersist
    @PreUpdate
    protected void prePersist() {
        this.tfStamp = createUniqueStamp();
    }

    @PostLoad
    protected void postLoad() {
        this.ticketPriorities = TicketPriority.listFromString(this.ticketPrioritiesAsString);
        this.ticketStatuses   = TicketStatus.listFromString(this.ticketStatusesAsString);
    }

    private String createUniqueStamp() {
        return StampUtils.craeteStampFromObjects(owner.getUserId(), owner.toString(), name);
    }

    public boolean sameAs(TicketFilter other) {
        boolean sameBeginDates =
            this.beginDate == null && other.beginDate == null
            || (this.beginDate != null && other.beginDate != null
                && this.beginDate.equals(other.beginDate));
        boolean sameEndDates =
            this.endDate == null && other.endDate == null
            || (this.endDate != null && other.endDate != null
                && this.endDate.equals(other.endDate));

        // todo: porównać listy rozwiązujących
        // todo: porównać listy zgłaszających
        // todo: porównać listy kategorii
        // todo: porównać listy statusów
        // todo: porównać listy ważności

        return sameBeginDates && sameEndDates;
    }
}
