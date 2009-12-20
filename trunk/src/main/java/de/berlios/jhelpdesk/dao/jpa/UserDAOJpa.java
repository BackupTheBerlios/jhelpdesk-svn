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

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Repository
@Qualifier("jpa")
public class UserDAOJpa implements UserDAO {

    private JpaTemplate jpaTemplate;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.jpaTemplate = new JpaTemplate(emf);
    }

    public List<User> getAllUserWithLastNameStartsWithLetter(String letter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<User> getSavioursWithLastNameStartsWithLetter(String letter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<User> getAllUser() {
        return this.jpaTemplate.findByNamedQuery("User.allOrderByLastName");
    }

    public List<User> getByRole(Role role) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public User getById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public User getByLogin(String login) {
        List<User> users = this.jpaTemplate.findByNamedQuery("User.byLogin", login);
        return users.isEmpty() ? null : users.get(0);
    }

    public boolean authenticate(String login, String passw) {
        List<User> users = this.jpaTemplate.findByNamedQuery("User.byLoginAndHashedPassoword", login, passw);
        return users.isEmpty() ? false : true;
    }

    public void loginUser(String login, Date date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveOrUpdate(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
