package de.berlios.jhelpdesk.web.manager.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.UserDAO;

public class ShowUserController implements Controller {

    private static Log log = LogFactory.getLog(ShowUserController.class);
    private UserDAO userDAO;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manager/users/show");
        mav.addObject("user", userDAO.getById(Long.parseLong(request.getParameter("userId"))));
        return mav;
    }

    /** @param userDAO the userDAO to set */
    public void setUserDAO(UserDAO userDAO) {
        log.debug("setUserDAO( IHDUserDAO userDAO )");
        this.userDAO = userDAO;
    }
}
