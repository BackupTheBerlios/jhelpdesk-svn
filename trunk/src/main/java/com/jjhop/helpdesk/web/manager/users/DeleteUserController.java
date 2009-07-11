package com.jjhop.helpdesk.web.manager.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.jjhop.helpdesk.dao.UserDAO;

public class DeleteUserController implements Controller {

    private static Log log = LogFactory.getLog(DeleteUserController.class);
    private UserDAO userDAO;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("handleRequest( HttpServletRequest request, HttpServletResponse response )");
        /*
         * try {
         * 		userDAO.remove( request.getParameter( "userId") );
         * } catch( Exception ex ) {
         *
         * }
         */
        return new ModelAndView(new RedirectView("showAll.html", true));
    }

    /** @param userDAO the userDAO to set */
    public void setUserDAO(UserDAO userDAO) {
        log.debug("setUserDAO( IHDUserDAO userDAO )");
        this.userDAO = userDAO;
    }
}
