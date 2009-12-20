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

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.berlios.jhelpdesk.dao.ArticleCategoryDAO;
import de.berlios.jhelpdesk.model.ArticleCategory;

/**
 *
 * @author jjhop
 */
@Repository
@Qualifier("jpa")
@Transactional(readOnly = true)
public class ArticleCategoryDAOJpa implements ArticleCategoryDAO {

    private static final Log log = LogFactory.getLog(ArticleCategoryDAOJpa.class);
    private JpaTemplate jpaTemplate;

    @Autowired
    public ArticleCategoryDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    @Transactional(readOnly = false)
    public void delete(final Long categoryId) {
        try {
            this.jpaTemplate.execute(new JpaCallback() {
                public Object doInJpa(EntityManager em) throws PersistenceException {
                    // TODO: do przestawienia jescze kolejność pozostałych kategorii
                    Query q = em.createNativeQuery("DELETE FROM article_category WHERE article_category_id=?");
                    q.setParameter(1, categoryId);
                    q.executeUpdate();
                    return null;
                }
            });
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public List<ArticleCategory> getAllCategories() {
        return this.jpaTemplate.findByNamedQuery("ArticleCategory.getAllOrderByPositionASC");
    }

    public List<ArticleCategory> getAllShortSections() {
        throw new UnsupportedOperationException("Metoda jest przestarzała. Użyj #getAllCategories()");
    }

    public ArticleCategory getById(Long categoryId) {
        return this.jpaTemplate.find(ArticleCategory.class, categoryId);
    }

    public void moveDown(Long categoryId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void moveUp(Long categoryId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional(readOnly = false)
    public void saveOrUpdate(ArticleCategory category) {
        if (category.getArticleCategoryId() == null) {
            this.jpaTemplate.persist(category);
        } else {
            this.jpaTemplate.merge(category);
        }
    }
}
