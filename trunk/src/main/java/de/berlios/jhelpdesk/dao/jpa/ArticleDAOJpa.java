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

import de.berlios.jhelpdesk.dao.ArticleDAO;
import de.berlios.jhelpdesk.model.Article;


/**
 *
 * @author jjhop
 */
@Repository
@Qualifier("jpa")
@Transactional(readOnly = true)
public class ArticleDAOJpa implements ArticleDAO {

    private static final Log log = LogFactory.getLog(AnnouncementDAOJpa.class);
    
    private JpaTemplate jpaTemplate;

    @Autowired
    public ArticleDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    public void delete(Article article) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delete(Long articleId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Article getById(Long articleId) {
        try {
            return this.jpaTemplate.find(Article.class, articleId);
        } catch (Exception ex) {
            log.error(ex);
            return null;
        }
    }

    public List<Article> getForSection(Long categoryId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Article> getLastAddedArticles(final int howMuch) {
        try {
             return (List<Article>)this.jpaTemplate.executeFind(new JpaCallback() {
                public Object doInJpa(EntityManager em) throws PersistenceException {
                    Query q = em.createNamedQuery("Article.lastAdded");
                    q.setMaxResults(howMuch);
                    return q.getResultList();
                }
            });
         } catch(Exception ex) {
            log.error("wystapil problem..", ex);
         }
        return null;
    }

    public void saveOrUpdate(Article article) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
