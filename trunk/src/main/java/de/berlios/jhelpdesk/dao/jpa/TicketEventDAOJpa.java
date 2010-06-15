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
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.berlios.jhelpdesk.dao.TicketEventDAO;
import de.berlios.jhelpdesk.model.EventType;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketEvent;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Repository
@Transactional(readOnly = true)
public class TicketEventDAOJpa implements TicketEventDAO {

    private final JpaTemplate jpaTemplate;

    @Autowired
    public TicketEventDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    public TicketEvent getById(Long eventId) {
        return this.jpaTemplate.find(TicketEvent.class, eventId);
    }

    public List<TicketEvent> getByTicket(Ticket ticket) {
        return this.jpaTemplate.findByNamedQuery("TicketEvent.getByTicketOrderByEventDateDESC", ticket);
    }

    public List<TicketEvent> getByTicket(Long ticketId) {
       return this.jpaTemplate.findByNamedQuery("TicketEvent.getByTicketIdOrderByEventDateDESC", ticketId);
    }

    public List<EventType> getByType(EventType type) {
        return this.jpaTemplate.findByNamedQuery("TicketEvent.getByEventTypeOrderByEventDateDESC", type.toInt());
    }

    public List<TicketEvent> getByUser(User user) {
        return this.jpaTemplate.findByNamedQuery("TicketEvent.getByUserOrderByEventDateDESC", user);
    }

    public List<TicketEvent> getByUser(Long userId) {
        return this.jpaTemplate.findByNamedQuery("TicketEvent.getByUserIdOrderByEventDateDESC", userId);
    }

    public List<TicketEvent> getLastFewEvents(final int howMuch) {
        return (List<TicketEvent>)this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = em.createNamedQuery("TicketEvent.getLastFewEventsOrderByEventDateDESC");
                q.setMaxResults(howMuch);
                return q.getResultList();
            }
        });
    }

    @Transactional(readOnly = false)
    public void save(TicketEvent event) {
        if (event.getTicketEventId() == null) {
            this.jpaTemplate.persist(event);
        } else {
            this.jpaTemplate.merge(event);
        }
    }
}
