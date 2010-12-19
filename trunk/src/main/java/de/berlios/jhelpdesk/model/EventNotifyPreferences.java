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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "laf_preferences")
@SequenceGenerator(name = "en_preferences_sequence",
                   sequenceName = "en_preferences_id_seq", allocationSize = 1)
public class EventNotifyPreferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="en_preferences_sequence")
    @Column(name = "en_preferences_id")
    private Long id;

    @OneToOne(mappedBy = "lafPreferences")
    private User user;

    /**
     * Przypisanie zgłoszenia
     */
    private NotifyFrequency ticketAssign;

    /**
     * Przypisanie zgłoszenia komuś innemu
     */
    private NotifyFrequency ticketAssignOther;

    /**
     * Zmiana rozwiązującego
     */
    private NotifyFrequency ticketReassign;

    /**
     * Odrzucenie zgłoszenia
     */
    private NotifyFrequency ticketReject;

    /**
     *
     */
    private NotifyFrequency ticketResolve;

    /**
     * 
     */
    private NotifyFrequency ticketClose;

    /**
     *
     */
    private NotifyFrequency ticketFileAddOrRemove;

    /**
     *
     */
    private NotifyFrequency ticketCommentAdd;

    /**
     *
     */
    private NotifyFrequency ticketCategoryChange;

    /**
     *
     */
    private NotifyFrequency ticketPriorityChange;

    /**
     * 
     */
    private NotifyFrequency addDelTicketCategory;

    /**
     * 
     */
    public EventNotifyPreferences() {
        this.ticketAssign = NotifyFrequency.IMMEDIATELY;
        this.ticketAssignOther = NotifyFrequency.IMMEDIATELY;
        this.ticketCategoryChange = NotifyFrequency.DAILY;
        this.ticketClose = NotifyFrequency.IMMEDIATELY;
        this.ticketCommentAdd = NotifyFrequency.DAILY;
        this.ticketFileAddOrRemove = NotifyFrequency.DAILY;
        this.ticketPriorityChange = NotifyFrequency.IMMEDIATELY;
        this.ticketReassign = NotifyFrequency.IMMEDIATELY;
        this.ticketReject = NotifyFrequency.IMMEDIATELY;
        this.ticketResolve = NotifyFrequency.IMMEDIATELY;
        this.addDelTicketCategory = NotifyFrequency.DAILY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NotifyFrequency getTicketAssign() {
        return ticketAssign;
    }

    public void setTicketAssign(NotifyFrequency ticketAssign) {
        this.ticketAssign = ticketAssign;
    }

    public NotifyFrequency getTicketAssignOther() {
        return ticketAssignOther;
    }

    public void setTicketAssignOther(NotifyFrequency ticketAssignOther) {
        this.ticketAssignOther = ticketAssignOther;
    }

    public NotifyFrequency getTicketReassign() {
        return ticketReassign;
    }

    public void setTicketReassign(NotifyFrequency ticketReassign) {
        this.ticketReassign = ticketReassign;
    }

    public NotifyFrequency getTicketReject() {
        return ticketReject;
    }

    public void setTicketReject(NotifyFrequency ticketReject) {
        this.ticketReject = ticketReject;
    }

    public NotifyFrequency getTicketResolve() {
        return ticketResolve;
    }

    public void setTicketResolve(NotifyFrequency ticketResolve) {
        this.ticketResolve = ticketResolve;
    }

    public NotifyFrequency getTicketClose() {
        return ticketClose;
    }

    public void setTicketClose(NotifyFrequency ticketClose) {
        this.ticketClose = ticketClose;
    }

    public NotifyFrequency getTicketFileAddOrRemove() {
        return ticketFileAddOrRemove;
    }

    public void setTicketFileAddOrRemove(NotifyFrequency ticketFileAddOrRemove) {
        this.ticketFileAddOrRemove = ticketFileAddOrRemove;
    }

    public NotifyFrequency getTicketCommentAdd() {
        return ticketCommentAdd;
    }

    public void setTicketCommentAdd(NotifyFrequency ticketCommentAdd) {
        this.ticketCommentAdd = ticketCommentAdd;
    }

    public NotifyFrequency getTicketCategoryChange() {
        return ticketCategoryChange;
    }

    public void setTicketCategoryChange(NotifyFrequency ticketCategoryChange) {
        this.ticketCategoryChange = ticketCategoryChange;
    }

    public NotifyFrequency getTicketPriorityChange() {
        return ticketPriorityChange;
    }

    public void setTicketPriorityChange(NotifyFrequency ticketPriorityChange) {
        this.ticketPriorityChange = ticketPriorityChange;
    }

    public NotifyFrequency getAddDelTicketCategory() {
        return addDelTicketCategory;
    }

    public void setAddDelTicketCategory(NotifyFrequency addDelTicketCategory) {
        this.addDelTicketCategory = addDelTicketCategory;
    }
}
