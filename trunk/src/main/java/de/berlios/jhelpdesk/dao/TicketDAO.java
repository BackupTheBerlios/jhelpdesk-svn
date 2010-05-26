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
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketFilter;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;

/**
 * Definiuje zestaw metod do obsługi trwałości obiektów Ticket.
 * 
 * @author jjhop
 */
public interface TicketDAO {

    /**
     * Zwraca zgłoszenie o podanym identyfikatorze.
     * 
     * @param ticketId identyfikator zgłoszenia
     * @return
     * @throws de.berlios.jhelpdesk.dao.DAOException
     */
    Ticket getTicketById(Long ticketId) throws DAOException;

    /**
     * Zwraca wszystkie zgłoszenia o podanym statusie.
     * 
     * @param ticketStatus
     * @return
     * @throws de.berlios.jhelpdesk.dao.DAOException
     */
    List<Ticket> getTicketsByStatus(TicketStatus ticketStatus) throws DAOException;

    /**
     * 
     * @param ticketStatus
     * @param howMuch
     * @return
     */
    List<Ticket> getTicketsByStatus(TicketStatus ticketStatus, int howMuch) throws DAOException;

    /**
     * Zwraca wszystkie zgłoszenia o podanej ważności.
     * 
     * @param ticketPriority
     * @return
     */
    List<Ticket> getTicketsByPriority(TicketPriority ticketPriority) throws DAOException;

    /**
     * Zwraca wszystkie zgłoszenia wybranej kategorii.
     * 
     * @param ticketCategory
     * @return
     */
    List<Ticket> getTicketsByCategory(TicketCategory ticketCategory) throws DAOException;

    /**
     * 
     * @param user
     * @return
     */
    List<Ticket> getTicketsNotifyiedByUser(User user) throws DAOException;

    /**
     * Usuwa wybrane zgłoszenie.
     * 
     * @param ticket
     * @throws Exception
     */
    void removeTicket(Ticket ticket) throws DAOException;

    /**
     * Usuwa zgłoszenie o podanym identyfikatorze.
     * 
     * @param ticketId
     */
    void remove(Long ticketId) throws DAOException;

    /**
     * Zapisuje podane zgłoszenie. Jesli jest to zgłoszenie istniejący
     * uaktualnie związane z nim dane.
     * 
     * @param ticket
     */
    void save(Ticket ticket) throws DAOException;

    /**
     * Zwraca wszystkie błędy z bazy.
     */
    List<Ticket> getAllTickets() throws DAOException;

    /**
     * 
     * @param ticketFilter
     * @param limit
     * @param offset
     * @return
     * @throws DAOException
     */
    List<Ticket> getTicketsWithFilter(TicketFilter ticketFilter, int limit, int offset)
            throws DAOException;

    /**
     * 
     * @param ticketFilter
     * @return
     */
    Long countTicketsWithFilter(TicketFilter ticketFilter) throws DAOException;

    /**
     * 
     * @param filter
     */
    List<Ticket> getTicketsWithFilter(TicketFilter filter);

}
