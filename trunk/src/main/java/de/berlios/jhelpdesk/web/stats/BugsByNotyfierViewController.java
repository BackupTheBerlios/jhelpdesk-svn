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
package de.berlios.jhelpdesk.web.stats;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.UserDAO;

public class BugsByNotyfierViewController implements Controller {

    private static Log log = LogFactory.getLog(BugsByNotyfierViewController.class);
    private UserDAO hdUserDAO;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("BugsByNotyfierViewController.handleRequest()");
        ModelAndView mav = new ModelAndView();
        if (request.getParameter("stats") != null) {
            // przegladanie statystyk
            mav.setViewName("stats/bugsByNotyfierStats");

            return mav;
        }

        if (request.getParameter("letter") == null) {
            response.sendRedirect(request.getRequestURI() + "?letter=A");
            return null;
        }
        log.info("Parametr letter=" + request.getParameter("letter"));
        mav.setViewName("stats/bugsByNotyfierList"); // lista uzytkownikow
        mav.addObject(
            "users",
            hdUserDAO.getAllUserWithLastNameStartsWithLetter(request.getParameter("letter")));

        return mav;
    }

    /**
     * @param hdUserDAO The hdUserDAO to set.
     */
    public void setHdUserDAO(UserDAO hdUserDAO) {
        this.hdUserDAO = hdUserDAO;
    }
}