package de.berlios.jhelpdesk.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.web.form.UserLoginForm;

public class LoginController extends SimpleFormController {

    private static Log log = LogFactory.getLog(LoginController.class);
    private UserLoginForm userForm;
    private UserDAO userDAO;

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
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
    protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object command, BindException errors) throws Exception {
        log.debug("onSubmit()->start");
        userForm = (UserLoginForm) command;
        if (userForm == null) {
            throw new RuntimeException("shit1!");
        }
        if (userDAO == null) {
            throw new RuntimeException("shit2!");
        }
        System.out.println("l => " + userForm.getLogin() + ", p => " + DigestUtils.shaHex(userForm.getPassw()));
        if (userDAO.checkLoginAndPassw(userForm.getLogin(), userForm.getPassw())) {
            //hdUserDAO.loginUser( userForm.getLogin(), new Date() );
            HttpSession sess = req.getSession();
            sess.setAttribute("user", userDAO.getByLogin(userForm.getLogin()));
            sess.setAttribute("logged", Boolean.TRUE);
            return new ModelAndView(new RedirectView(req.getContextPath().concat(getSuccessView())));
        }
        ModelAndView mav = new ModelAndView(getFormView());
        userForm.setPassw(null);
        mav.addObject("loginForm", userForm);
        mav.addObject("msg", "bla bla bla"); // getMessageSourceAccessor().getMessage( "loginPage.error" ) );
        return mav;
    }

    /**
     * @param hdUserDAO The userDAO to set.
     */
    public void setHdUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
