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
import de.berlios.jhelpdesk.model.TicketComment;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.form.ShowTicketsFilterForm;

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
    public Ticket getTicketById(Long ticketId) throws DAOException;

    /**
     * Zwraca wszystkie zgłoszenia o podanym statusie.
     * 
     * @param ticketStatus
     * @return
     * @throws de.berlios.jhelpdesk.dao.DAOException
     */
    public List<Ticket> getTicketsByStatus(TicketStatus ticketStatus)
            throws DAOException;

    /**
     * 
     * @param ticketStatus
     * @param howMuch
     * @return
     */
    public List<Ticket> getTicketsByStatus(TicketStatus ticketStatus,
            int howMuch) throws DAOException;

    /**
     * Zwraca wszystkie zgłoszenia o podanej ważności.
     * 
     * @param ticketPriority
     * @return
     */
    public List<Ticket> getTicketsByPriority(TicketPriority ticketPriority)
            throws DAOException;

    /**
     * Zwraca wszystkie zgłoszenia wybranej kategorii.
     * 
     * @param ticketCategory
     * @return
     */
    public List<Ticket> getTicketsByCategory(TicketCategory ticketCategory)
            throws DAOException;

    /**
     * 
     * @param user
     * @return
     */
    public List<Ticket> getTicketsNotifyiedByUser(User user)
            throws DAOException;

    /**
     * 
     * @param user
     * @return
     */
    public List<Ticket> getTicketsResolvedByUser(User user)
            throws DAOException;

    /**
     * Usuwa wybrane zgłoszenie.
     * 
     * @param ticket
     * @throws Exception
     */
    public void removeTicket(Ticket ticket) throws DAOException;

    /**
     * Usuwa zgłoszenie o podanym identyfikatorze.
     * 
     * @param ticketId
     */
    public void remove(Long ticketId) throws DAOException;

    /**
     * Zapisuje podane zgłoszenie. Jesli jest to zgłoszenie istniejący
     * uaktualnie związane z nim dane.
     * 
     * @param ticket
     */
    public void save(Ticket ticket) throws DAOException;

    /**
     * Zwraca wszystkie błędy z bazy.
     */
    public List<Ticket> getAllTickets() throws DAOException;

    /**
     * @param filterForm
     */
    public List<Ticket> getTicketsWithFilter(ShowTicketsFilterForm filterForm,
            int limit, long offset) throws DAOException;

    /**
     * 
     * @param filterForm
     * @return
     */
    public Integer countTicketsWithFilter(ShowTicketsFilterForm filterForm)
            throws DAOException;

}
