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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author jjhop
 */
@Entity
@Table(name = "ticket_event")
public class TicketEvent implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long ticketEventId;

    /**
     * TODO: przerobic na Ticket
     */
    private Long ticketId;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_date")
    private Date evtDate;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User evtAuthor;

    /**
     *
     */
    @Column(name = "event_subject")
    private String evtSubject;

    /**
     * @see #eventTypeAsInt
     * @see #populateEventTypeDB()
     * @see #populateEventTypeTransient()
     */
    @Transient
    private EventType eventType;

    /**
     * Pole służące do utrwalania w bazie typu zdarzenia. Niedostępne
     * za pomocą żadnych metod i używane tylko przez JPA.
     *
     * @see #populateEventTypeDB()
     * @see #populateEventTypeTransient()
     */
    @Basic
    @Column(name = "event_type")
    @SuppressWarnings("unused")
    private int eventTypeAsInt;

    /**
     * @return Returns the ticketEventId.
     */
    public Long getTicketEventId() {
        return ticketEventId;
    }

    /**
     * @param ticketEventId The ticketEventId to set.
     */
    public void setTicketEventId(Long ticketEventId) {
        this.ticketEventId = ticketEventId;
    }

    /**
     * @return Returns the evtAuthor.
     */
    public User getEvtAuthor() {
        return evtAuthor;
    }

    /**
     * @param evtAuthor The evtAuthor to set.
     */
    public void setEvtAuthor(User evtAuthor) {
        this.evtAuthor = evtAuthor;
    }

    /**
     * @return Returns the evtDate.
     */
    public Date getEvtDate() {
        return evtDate;
    }

    /**
     * @param evtDate The evtDate to set.
     */
    public void setEvtDate(Date evtDate) {
        this.evtDate = evtDate;
    }

    /**
     * @return Returns the evtSubject.
     */
    public String getEvtSubject() {
        return evtSubject;
    }

    /**
     * @param evtSubject The evtSubject to set.
     */
    public void setEvtSubject(String evtSubject) {
        this.evtSubject = evtSubject;
    }

    /**
     * @return Returns the eventType.
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * @param eventType The eventType to set.
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * @return Returns the ticketId.
     */
    public Long getTicketId() {
        return ticketId;
    }

    /**
     * @param ticketId The ticketId to set.
     */
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "[" + ticketEventId + "] => [" + evtSubject + "]";
    }
 
    @PrePersist
    protected void populateEventTypeDB() {
        this.eventTypeAsInt = this.eventType.toInt();
    }

    @PostLoad
    protected void populateEventTypeTransient() {
        this.eventType = EventType.fromInt(this.eventTypeAsInt);
    }
}
