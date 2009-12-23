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
package de.berlios.jhelpdesk.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.DataAccessException;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketComment;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.form.ShowTicketsFilterForm;

/**
 *
 * @author jjhop
 */
@Repository
@Qualifier("jpa")
public class TicketDAOJpa implements TicketDAO {

    private JpaTemplate jpaTemplate;

    @Autowired
    public TicketDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    public void addComment(TicketComment comm) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Integer countTicketsWithFilter(ShowTicketsFilterForm filterForm) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ticket> getAllTickets() throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Ticket getTicketById(Long ticketId) throws DataAccessException {
        return this.jpaTemplate.find(Ticket.class, ticketId);
    }

    public List<Ticket> getTicketsByCategory(TicketCategory ticketCategory) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ticket> getTicketsByPriority(TicketPriority ticketPriority) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ticket> getTicketsByStatus(TicketStatus ticketStatus) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ticket> getTicketsByStatus(final TicketStatus ticketStatus, final int howMuch) throws DataAccessException {
        return this.jpaTemplate.executeFind(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNamedQuery("Ticket.byStatusOrderByCreateDateDESC");
                query.setParameter(1, ticketStatus.toInt());
                query.setMaxResults(howMuch);
                return query.getResultList();
            }
        });
    }

    public List<Ticket> getTicketsNotifyiedByUser(User user) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ticket> getTicketsResolvedByUser(User user) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ticket> getTicketsWithFilter(ShowTicketsFilterForm filterForm, int limit, long offset) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(Long ticketId) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeTicket(Ticket ticket) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void save(Ticket ticket) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
