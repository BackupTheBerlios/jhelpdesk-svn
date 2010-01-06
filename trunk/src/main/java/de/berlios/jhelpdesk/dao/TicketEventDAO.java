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
package de.berlios.jhelpdesk.dao;

import java.util.List;

import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketEvent;
import de.berlios.jhelpdesk.model.EventType;
import de.berlios.jhelpdesk.model.User;

/**
 * Definiuje zestaw metod do obsługi trwałości obiektów TicketEvent.
 * 
 * @author jjhop
 */
public interface TicketEventDAO {

    /**
     *
     * @param eventId
     * @return
     */
    TicketEvent getById(Long eventId);

    /**
     *
     * @param user
     * @return
     */
    List<TicketEvent> getByUser(User user);

    /**
     *
     * @param userId
     * @return
     */
    List<TicketEvent> getByUser(Long userId);

    /**
     *
     * @param ticket
     * @return
     */
    List<TicketEvent> getByTicket(Ticket ticket);

    /**
     *
     * @param ticketId
     * @return
     */
    List<TicketEvent> getByTicket(Long ticketId);

    /**
     *
     * @param type
     * @return
     */
    List<EventType> getByType(EventType type);

    /**
     *
     * @param howMuch
     * @return
     */
    List<TicketEvent> getLastFewEvents(int howMuch);

    /**
     *
     * @param event
     */
    void save(TicketEvent event);
}
