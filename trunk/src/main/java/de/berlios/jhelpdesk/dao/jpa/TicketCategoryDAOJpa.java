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

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.model.TicketCategory;

/**
 *
 * @author jjhop
 */
@Repository
@Transactional(readOnly = true)
public class TicketCategoryDAOJpa implements TicketCategoryDAO {

    private final JpaTemplate jpaTemplate;

    @Autowired
    public TicketCategoryDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    @Transactional(readOnly = false)
    public void deleteCategory(final TicketCategory category) {
        this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                if (category.hasChildNodes()) {
                    deleteChildNodes(category);
                    category.setRight(category.getLeft() + 1);
                }
                Query q1 = em.createNativeQuery(
                    "UPDATE ticket_category SET t_left=t_left-2 WHERE t_left>?1 AND t_left<?2");
                q1.setParameter(1, category.getRight());
                q1.setParameter(2, ((getNodeCount() * 2) + 1));
                q1.executeUpdate();

                Query q2 = em.createNativeQuery(
                    "UPDATE ticket_category SET t_right=t_right-2 WHERE t_right>=?1 AND t_right<=?2");
                q2.setParameter(1, category.getRight());
                q2.setParameter(2, (getNodeCount() * 2));
                q2.executeUpdate();

                Query q3 = em.createNativeQuery("DELETE FROM ticket_category WHERE category_id=?1");
                q3.setParameter(1, category.getTicketCategoryId());
                q3.executeUpdate();

                return null;
            }
        });
    }

    @Transactional(readOnly = false) // TODO: to chyba nie powinno byc oddzielnie?
    private void deleteChildNodes(final TicketCategory category) {
        final long nodeCount = getNodeCount();
        final long subtreeNodeCount = (category.getRight() - category.getLeft()) / 2;

        this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = em.createQuery(
                    "DELETE FROM TicketCategory c WHERE c.left > ?1 AND c.right < ?2");
                q.setParameter(1, category.getLeft());
                q.setParameter(2, category.getRight());
                q.executeUpdate();
                Query q2 = em.createQuery(
                    "UPDATE TicketCategory c SET c.left = c.left - ?1 " +
                    "WHERE c.left > ?2 AND c.left < ?3");
                q2.setParameter(1, subtreeNodeCount * 2);
                q2.setParameter(2, category.getLeft());
                q2.setParameter(3, nodeCount * 2);
                q2.executeUpdate();

                Query q3 = em.createQuery(
                    "UPDATE TicketCategory c SET c.right = c.right - ?1 " +
                    "WHERE c.right >= ?2 AND c.right <= ?3");
                q3.setParameter(1, subtreeNodeCount * 2);
                q3.setParameter(2, category.getRight());
                q3.setParameter(3, nodeCount * 2);
                q3.executeUpdate();

                return null;
            }
        });
    }

    private long getNodeCount() {
        return (Long) this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = em.createQuery("SELECT COUNT(tc) FROM TicketCategory tc");
                return q.getSingleResult();
            }
        });
    }
    
    public List<TicketCategory> getAllCategories() {
        return (List<TicketCategory>) this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = em.createNativeQuery(
                    "SELECT * FROM ticket_category WHERE category_id>0 ORDER BY t_left ASC",
                    TicketCategory.class);
                return q.getResultList();
            }
        });
    }

    public List<TicketCategory> getAllCategoriesForView() {
        return (List<TicketCategory>) this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = em.createNativeQuery(
                    "SELECT * FROM ticket_category " +
                    "WHERE is_active IS true AND category_id > 0 ORDER BY t_left ASC",
                    TicketCategory.class);
                return q.getResultList();
            }
        });
    }

    public TicketCategory getById(Long id) {
        return this.jpaTemplate.find(TicketCategory.class, id);
    }

    public TicketCategory getDefault() {
        return null;
    }

    @Transactional(readOnly = false)
    public void insertCategory(final TicketCategory category, final TicketCategory parent) {
        final long nodeCount = getNodeCount();
        this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q1 = em.createNativeQuery(
                    "UPDATE ticket_category SET t_right=t_right+2 WHERE t_right>=? AND t_right<=?");
                q1.setParameter(1, parent.getRight());
                q1.setParameter(2, nodeCount * 2);
                q1.executeUpdate();

                Query q2 = em.createNativeQuery(
                    "UPDATE ticket_category SET t_left=t_left+2 WHERE t_left>? AND t_left<?");
                q2.setParameter(1, parent.getRight());
                q2.setParameter(2, (nodeCount + 1) * 2);
                q2.executeUpdate();

                category.setLeft(parent.getRight());
                category.setRight(parent.getRight() + 1);
                category.setDepth(parent.getDepth() + 1);

                em.persist(category);
                return null;
            }
        });
    }
    
    @Transactional(readOnly = false)
    public void insertRootCategory(final TicketCategory rootCategory) {
        this.jpaTemplate.execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query getMaxTRightQuery =
                    em.createNativeQuery("SELECT max(t_right) FROM ticket_category", Long.class);
                final Long maxTRight = (Long) getMaxTRightQuery.getSingleResult();

                rootCategory.setLeft(new Long(maxTRight.longValue() + 1));
                rootCategory.setRight(new Long(maxTRight.longValue() + 2));
                rootCategory.setDepth(0);
                em.persist(rootCategory);
                return null;
            }
        });
    }

    @Transactional(readOnly = false)
    public void updateCategory(TicketCategory category) {
        this.jpaTemplate.merge(category);
    }
}
