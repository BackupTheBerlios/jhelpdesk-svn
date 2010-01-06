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
package de.berlios.jhelpdesk.web.form;

import de.berlios.jhelpdesk.model.NotifyFrequency;

public class EventNotifyForm {

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
    public EventNotifyForm() {
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
