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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.berlios.jhelpdesk.dao.DAOException;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketFilter;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Repository
@Transactional(readOnly = true)
public class TicketDAOJpa implements TicketDAO {

    private static final Log log = LogFactory.getLog(TicketDAOJpa.class);

    private final JpaTemplate jpaTemplate;

    @Autowired
    public TicketDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    public Long countTicketsWithFilter(final TicketFilter ticketFilter) throws DAOException {
        return (Long) this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = new QueryBuilder(ticketFilter, em).getFilteredQuery(true);
                return q.getSingleResult();
            }
        });
    }

    public List<Ticket> getAllTickets() throws DAOException {
        return this.jpaTemplate.findByNamedQuery("Ticket.orderByCreateDateDESC");
    }

    public Ticket getTicketById(Long ticketId) throws DAOException {
        return this.jpaTemplate.find(Ticket.class, ticketId);
    }

    public List<Ticket> getTicketsByCategory(TicketCategory ticketCategory) throws DAOException {
        return this.jpaTemplate.findByNamedQuery("Ticket.allByCategory", ticketCategory);
    }

    public List<Ticket> getTicketsByPriority(TicketPriority ticketPriority) throws DAOException {
        // INFO: ticketPriority.toInt() musi być dlatego ze jest trick w encji...
        return this.jpaTemplate.findByNamedQuery("Ticket.allByPriority", ticketPriority.toInt());
    }

    public List<Ticket> getTicketsByStatus(TicketStatus ticketStatus) throws DAOException {
        // INFO: ticketStatus.toInt() musi być dlatego ze jest trick w encji...
        return this.jpaTemplate.findByNamedQuery("Ticket.allByStatus", ticketStatus.toInt());
    }

    public List<Ticket> getTicketsByStatus(final TicketStatus ticketStatus, final int howMuch) throws DAOException {
        try {
            return this.jpaTemplate.executeFind(new JpaCallback() {
                public Object doInJpa(EntityManager em) throws PersistenceException {
                    Query query = em.createNamedQuery("Ticket.byStatusOrderByCreateDateDESC");
                    query.setParameter(1, ticketStatus.toInt());
                    query.setMaxResults(howMuch);
                    return query.getResultList();
                }
            });
        } catch(DataAccessException dae) {
            throw new DAOException(dae);
        }
    }

    public List<Ticket> getTicketsNotifyiedByUser(User user) throws DAOException {
        return this.jpaTemplate.findByNamedQuery("Ticket.allByNotifier", user);
    }

    public List<Ticket> getTicketsWithFilter(final TicketFilter ticketFilter, final int limit, final int offset) throws DAOException {
        return (List<Ticket>) this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = new QueryBuilder(ticketFilter, em).getFilteredQuery(false);
                q.setMaxResults(limit);
                q.setFirstResult(offset);
                return q.getResultList();
            }
        });
    }

    public List<Ticket> getTicketsWithFilter(final TicketFilter filter) {
        return (List<Ticket>)this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = new QueryBuilder(filter, em).getFilteredQuery(false);
                return q.getResultList();
            }
        });
    }

    @Transactional(readOnly = false)
    public void remove(Long ticketId) throws DAOException {
        try {
            Ticket toDelete = this.jpaTemplate.find(Ticket.class, ticketId);
            this.jpaTemplate.remove(toDelete);
        } catch (Exception ex) {
            log.error("Nie można usunąć artykułu o identyfikatorze [" + ticketId + "]", ex);
        }
    }

    @Transactional(readOnly = false)
    public void removeTicket(Ticket ticket) throws DAOException {
        this.jpaTemplate.remove(ticket);
    }

    @Transactional(readOnly = false)
    public void save(Ticket ticket) throws DAOException {
        if (ticket.getTicketId() == null) {
            this.jpaTemplate.persist(ticket);
        } else {
            this.jpaTemplate.merge(ticket);
        }
    }
}
