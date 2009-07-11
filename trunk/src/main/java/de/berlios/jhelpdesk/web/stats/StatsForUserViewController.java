package de.berlios.jhelpdesk.web.stats;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.UserDAO;

public class StatsForUserViewController implements Controller {

    private UserDAO hdUserDAO;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        return new ModelAndView("stats/myStats");
    }

    /** @return the hdUserDAO */
    public UserDAO getHdUserDAO() {
        return hdUserDAO;
    }

    /** @param hdUserDAO the hdUserDAO to set */
    public void setHdUserDAO(UserDAO hdUserDAO) {
        this.hdUserDAO = hdUserDAO;
    }
}
