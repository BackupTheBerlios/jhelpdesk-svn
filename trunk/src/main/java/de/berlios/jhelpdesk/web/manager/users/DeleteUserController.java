package de.berlios.jhelpdesk.web.manager.users;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.UserDAO;

@Controller("managerUserDelCtrl")
public class DeleteUserController {

    private static Log log = LogFactory.getLog(DeleteUserController.class);

    @Autowired
    private UserDAO userDAO;

    @RequestMapping
	public ModelAndView handleRequest(@RequestParam("userId") Long userId) throws Exception {
		log.debug("handleRequest(HttpServletRequest, HttpServletResponse)");
        /*
         * try {
         * 		userDAO.remove( request.getParameter( "userId") );
         * } catch( Exception ex ) {
         *
         * }
         */
        return new ModelAndView(new RedirectView("showAll.html", true));
    }

}
