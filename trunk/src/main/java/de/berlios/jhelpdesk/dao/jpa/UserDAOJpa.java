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

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Repository("userDAO")
@Qualifier("jpa")
@Transactional(readOnly = true)
public class UserDAOJpa implements UserDAO {

    private final JpaTemplate jpaTemplate;

    @Autowired
    public UserDAOJpa(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    public List<User> getAllUsers() {
        return this.jpaTemplate.findByNamedQuery("User.allOrderByLastName");
    }

    public List<User> getByRole(Role role) {
        return this.jpaTemplate.findByNamedQuery("User.allByRoleOrderByLastName", role.toInt());
    }

    public User getById(Long id) {
        return this.jpaTemplate.find(User.class, id);
    }

    public User getByLoginFetchFilters(String login) {
        List<User> users = this.jpaTemplate.findByNamedQuery("User.byLoginFetchFilters", login);
        return users.isEmpty() ? null : users.get(0);
    }

    public User getByLogin(String login) {
        List<User> users = this.jpaTemplate.findByNamedQuery("User.byLogin", login);
        return users.isEmpty() ? null : users.get(0);
    }

    public boolean authenticate(String login, String passw) {
        List<User> users = this.jpaTemplate.findByNamedQuery("User.byLoginAndHashedPassoword", login, passw);
        return users.isEmpty() ? false : true;
    }

    @Transactional(readOnly = false)
    public void loginUser(String login, Date date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional(readOnly = false)
    public void saveOrUpdate(User user) {
        if (user.getUserId() == null) {
            this.jpaTemplate.persist(user);
        } else {
            this.jpaTemplate.merge(user);
        }
    }

    public void refresh(User user) {
        this.jpaTemplate.refresh(user);
    }

}
