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
package de.berlios.jhelpdesk.web.manager.users;

import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.RoleEditor;
import de.berlios.jhelpdesk.web.tools.UserValidator;

/**
 * Kontroler obsługujacy dodawanie nowych oraz edycję istniejących użytkowników systemu.
 * Został wydzielony z klasy UserController ze względu na konieczność dodatkowych
 * wywołań initBinder(...) i populateRoles() dla każdego żądania, co niepotrzebnie
 * obciążało wszystkie żądania (takżę te, które nie wymagały tych działań).
 *
 * @author jjhop
 */
@Controller
public class UserEditController {

    @Autowired
    @Qualifier("jdbc")
    private UserDAO userDAO;
    
    @Autowired
    private UserValidator validator;

    /**
     * Rejestruje edytory właściwości niezbędne podczas edycji danych
     * użytkownika.
     *
     * @param binder
     *
     * @see WebDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, new RoleEditor());
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class,
            NumberFormat.getNumberInstance(), true));
        binder.registerCustomEditor(Boolean.class, null, new CustomBooleanEditor(true));
    }

    /**
     * Dołącza do modelu widoku listę ról dostępnych w systemie.
     *
     * @return lista ról dostępnych w systemie
     *
     * @see Role
     */
    @ModelAttribute("roles")
    public List<Role> populateRoles() {
        return Role.getRoles();
    }

    /**
     * Przygotowuje formularz do dodania (lub edycji) użytkownika. Jeśli w żądaniu
     * znaduje się identyfikator istniejącego użytkownika to zostanie on przygotowany
     * do edycji, w przeciwnym wypadku do modelu zostanie dołączony nowy (pusty)
     * obiekt użytkownika.
     *
     * @param userId identyfikator użytkownika do edycji
     *               lub {@code false} jeśli to nowy użytkownik
     * @param map model widoku
     * @return identyfikator widoku
     */
    @RequestMapping(value = "/manage/users/edit.html", method = RequestMethod.GET)
    protected String prepareForm(
                     @RequestParam(value = "userId", required = false) Long userId,
                     ModelMap map) {

        if ((userId) == null) {
            map.addAttribute("user", new User());
        } else {
            map.addAttribute("user", userDAO.getById(userId));
        }
        return "manager/users/edit";
    }
    
    /**
     * Obsługuje wysłany formularz i zapisuje podany obiekt. Decyzję o tym czy jest
     * to update czy dodanie nowego podejmuje stosowny obiekt DAO.
     *
     * @param user obiekt użytkownika do zapisania
     * @param result
     * @param status
     * @return identyfikator widoku do wyświetlenia
     *
     * @see User
     * @see UserDAO
     * @see UserValidator
     *
     * @see BindingResult
     * @see SessionStatus
     */
    @RequestMapping(value = "/manage/users/edit.html", method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute("user") User user,
                     BindingResult result, SessionStatus status) {

        validator.validate(user, result);
        if (result.hasErrors()) {
            return "manager/users/edit";
        }
        userDAO.saveOrUpdate(user);
        status.setComplete();
        return "redirect:/manage/users/show.html?userId=" + user.getUserId();
    }

}
