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
package de.berlios.jhelpdesk.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.web.form.UserLoginForm;

public class LoginController extends SimpleFormController {

    private static Log log = LogFactory.getLog(LoginController.class);
    private UserLoginForm userForm;

    @Autowired
    private UserDAO userDAO;

    @Override
    protected Object formBackingObject(HttpServletRequest request) 
    		throws ServletException {
        log.debug("formBackingObject()->start");
        if (userForm == null) {
            userForm = new UserLoginForm();
        }
        return userForm;
    }

    @Override
    protected void onBind(HttpServletRequest req, Object command) {
        log.debug("onBind()->start");
        if (userForm != null) {
            log.debug("Formularz istnieje. Przepisuje do obiektu login.");
            userForm.setLogin(((UserLoginForm) command).getLogin());
        }
        log.debug("onBind()->end");
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest req, 
    		HttpServletResponse res, Object command, BindException errors) 
    		throws Exception {
        log.debug("onSubmit()->start");
        userForm = (UserLoginForm) command;
        if (userForm == null) {
            throw new RuntimeException("shit1!");
        }
        if (userDAO == null) {
            throw new RuntimeException("shit2!");
        }
        System.out.println("l => " + userForm.getLogin() + ", p => " 
        		+ DigestUtils.shaHex(userForm.getPassw()));
        if (userDAO.checkLoginAndPassw(userForm.getLogin(), userForm.getPassw())) {
            //hdUserDAO.loginUser( userForm.getLogin(), new Date() );
            HttpSession sess = req.getSession();
            sess.setAttribute("user", userDAO.getByLogin(userForm.getLogin()));
            sess.setAttribute("logged", Boolean.TRUE);
            return new ModelAndView(new RedirectView(
            		req.getContextPath().concat(getSuccessView())));
        }
        ModelAndView mav = new ModelAndView(getFormView());
        userForm.setPassw(null);
        mav.addObject("loginForm", userForm);
        mav.addObject("msg", "bla bla bla"); // getMessageSourceAccessor().getMessage( "loginPage.error" ) );
        return mav;
    }

}
