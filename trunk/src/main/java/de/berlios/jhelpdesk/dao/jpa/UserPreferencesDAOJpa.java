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

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.berlios.jhelpdesk.dao.UserPreferencesDAO;
import de.berlios.jhelpdesk.model.DisplayListsPreferences;
import de.berlios.jhelpdesk.model.LookAndFeelPreferences;

/**
 *
 * @author jjhop
 */
@Repository
@Transactional(readOnly = true)
public class UserPreferencesDAOJpa implements UserPreferencesDAO {

    private final JpaTemplate jpaTemplate;

    @Autowired
    public UserPreferencesDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    @Transactional(readOnly = false)
    public void save(LookAndFeelPreferences lafPrefs) {
        if (lafPrefs.getId() != null) {
            this.jpaTemplate.merge(lafPrefs);
        } else {
            this.jpaTemplate.persist(lafPrefs);
        }
    }

    @Transactional(readOnly = false)
    public void save(DisplayListsPreferences dlPrefs) {
        if (dlPrefs.getId() != null) {
            this.jpaTemplate.merge(dlPrefs);
        } else {
            this.jpaTemplate.persist(dlPrefs);
        }
    }
}
