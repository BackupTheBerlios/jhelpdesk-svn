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

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.berlios.jhelpdesk.dao.AnnouncementDAO;
import de.berlios.jhelpdesk.model.Announcement;

/**
 *
 * @author jjhop
 */
@Repository
@Transactional(readOnly = true)
public class AnnouncementDAOJpa implements AnnouncementDAO {

    private static final Log log = LogFactory.getLog(AnnouncementDAOJpa.class);

    private final JpaTemplate jpaTemplate;

    @Autowired
    public AnnouncementDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    public Announcement getById(Long announcementId) {
        try {
            return this.jpaTemplate.find(Announcement.class, announcementId);
        } catch(Exception ex) {
            log.error(ex);
            return null;
        }
    }

    public List<Announcement> getAll() {
        return this.jpaTemplate.findByNamedQuery("Announcement.allOrderByCreateDateDesc");
    }

    public List<Announcement> getLastAnnouncements(final int howMuch) {
        try {
             return (List<Announcement>)this.jpaTemplate.executeFind(new JpaCallback() {
                public Object doInJpa(EntityManager em) throws PersistenceException {
                    Query q = em.createNamedQuery("Announcement.allOrderByCreateDateDesc");
                    q.setMaxResults(howMuch);
                    return q.getResultList();
                }
            });
         } catch(Exception ex) {
            log.error("wystapil problem..", ex);
         }
        return Collections.<Announcement>emptyList();
    }

    @Transactional(readOnly = false)
    public void save(Announcement announcement) {
        if (announcement.getAnnouncementId() == null) {
            this.jpaTemplate.persist(announcement);
        } else {
            this.jpaTemplate.merge(announcement);
        }
    }

    @Transactional(readOnly = false)
    public void delete(final Long announcementId) {
        try {
            Announcement toDelete = this.jpaTemplate.find(Announcement.class, announcementId);
            this.jpaTemplate.remove(toDelete);
        } catch(Exception ex) {
            log.error(ex);
        }
    }

    @Transactional(readOnly = false)
    public void delete(Announcement announcement) {
        this.jpaTemplate.remove(announcement);
    }
}
