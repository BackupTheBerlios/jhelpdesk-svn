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
import org.springframework.transaction.annotation.Transactional;

import de.berlios.jhelpdesk.dao.TicketFilterDAO;
import de.berlios.jhelpdesk.model.TicketFilter;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Repository
@Qualifier("jpa")
@Transactional(readOnly = true)
public class TicketFilterDAOJpa implements TicketFilterDAO {

    private static final String[] tables =
            new String[]{"notifiers", "saviours", "ticket_categories"};

    private final JpaTemplate jpaTemplate;

    @Autowired
    public TicketFilterDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    @Transactional(readOnly = false)
    public void saveOrUpdate(final TicketFilter filter) {
        if (filter.getId() == null) {
            this.jpaTemplate.persist(filter);
        } else {
            this.jpaTemplate.execute(new JpaCallback<TicketFilter>() {
                public TicketFilter doInJpa(EntityManager em) throws PersistenceException {
                    TicketFilterDAOJpa.this.deleteFilterItems(em, filter.getId());
                    em.merge(filter);
                    // WORKAROUND: Jest jakiś problem z zapisaniem do bazy pól z datą
                    // ustawionych na null, jeśli wcześniej miały jakąś wartość... (OpenJPA 1.2.2)
                    Query updateQuery = em.createNativeQuery(
                        "UPDATE ticket_filters SET begin_date=?1, end_date=?2 WHERE id=?3");
                    updateQuery.setParameter(1, filter.getBeginDate());
                    updateQuery.setParameter(2, filter.getEndDate());
                    updateQuery.setParameter(3, filter.getId());
                    updateQuery.executeUpdate();
                    return null;
                }
            });
        }
    }

    @Transactional(readOnly = false)
    public void delete(final Long filterId) {
        this.jpaTemplate.execute(new JpaCallback<TicketFilter>() {
            public TicketFilter doInJpa(EntityManager em) throws PersistenceException {
                Query q = em.createQuery("DELETE FROM TicketFilter f WHERE f.ticketFilterId = ?1");
                q.setParameter(1, filterId);
                q.executeUpdate();
                return null;
            }
        });
    }

    @Transactional(readOnly = false)
    public void delete(final TicketFilter filter) {
        this.jpaTemplate.execute(new JpaCallback<TicketFilter>() {
            public TicketFilter doInJpa(EntityManager em) throws PersistenceException {
                em.remove(filter);
                return null;
            }
        });
    }

    public List<TicketFilter> getAllFiltersForUser(User user) {
        return this.jpaTemplate.findByNamedQuery("TicketFilter.forUserOrderByNameASC", user);
    }

    public TicketFilter getById(Long filterId) {
        return this.jpaTemplate.find(TicketFilter.class, filterId);
    }

    private void deleteFilterItems(EntityManager em, Long filterId) {
        for (String table : tables) {
            Query query = em.createNativeQuery(
                    "DELETE FROM ticket_filters_" + table + " WHERE ticket_filter_id=?1");
            query.setParameter(1, filterId);
            query.executeUpdate();
        }
    }
}