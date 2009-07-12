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
package de.berlios.jhelpdesk.dao;

import java.util.Date;
import java.util.List;

import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;

/**
 * Podstawowe DAO dla obiektów User.
 * @author jjhop
*/
public interface UserDAO {
	List<User> getAllUserWithLastNameStartsWithLetter( String letter );
	List<User> getSavioursWithLastNameStartsWithLetter( String letter );
	List<User> getAllUser();
	List<User> getByRole( Role role );
	List<User> getSaviours();
	User getById( Long id );
	User getById( int id );
	User getByLogin( String login );
	boolean checkLoginAndPassw(String login, String passw);
	void loginUser(String login, Date date);
	void save( User user );
}
