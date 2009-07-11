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
