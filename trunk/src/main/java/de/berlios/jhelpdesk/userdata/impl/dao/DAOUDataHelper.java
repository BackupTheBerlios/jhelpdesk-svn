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
package de.berlios.jhelpdesk.userdata.impl.dao;

import java.util.Date;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.userdata.ifc.IUDataHelpder;

public class DAOUDataHelper implements IUDataHelpder {

    private UserDAO userDAO;

    public boolean loginUser(String name, String passw) {
        if (userDAO.checkLoginAndPassw(name, passw)) {
            userDAO.loginUser(name, new Date(System.currentTimeMillis()));
            return true;
        }
        return false;
    }

    /**
     * @param userDAO The userDAO to set.
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
