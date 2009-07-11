package com.jjhop.helpdesk.userdata.impl.dao;

import java.util.Date;

import com.jjhop.helpdesk.dao.UserDAO;
import com.jjhop.helpdesk.userdata.ifc.IUDataHelpder;

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
