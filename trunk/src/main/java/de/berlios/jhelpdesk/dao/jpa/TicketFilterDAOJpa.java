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

import de.berlios.jhelpdesk.model.User;
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

/**
 *
 * @author jjhop
 */
@Repository
@Qualifier("jpa")
@Transactional(readOnly = true)
public class TicketFilterDAOJpa implements TicketFilterDAO {

    private JpaTemplate jpaTemplate;

    @Autowired
    public TicketFilterDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    @Transactional(readOnly = false)
    public void saveOrUpdate(TicketFilter ticketFilter) {
        if (ticketFilter.getTicketFilterId() == null) {
            this.jpaTemplate.persist(ticketFilter);
        } else {
            this.jpaTemplate.merge(ticketFilter);
        }
    }

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

    public void delete(TicketFilter filter) {
        this.jpaTemplate.remove(filter);
    }

    public List<TicketFilter> getAllFiltersForUser(User user) {
        return this.jpaTemplate.findByNamedQuery("TicketFilter.forUserOrderByNameASC", user);
    }

    public TicketFilter getById(Long filterId) {
        return this.jpaTemplate.find(TicketFilter.class, filterId);
    }
}
