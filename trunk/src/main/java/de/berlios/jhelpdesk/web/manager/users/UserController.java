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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.berlios.jhelpdesk.dao.UserDAO;

/**
 * Kontroler częściowo obsługujący zarządzanie użytkownikami systemu. Obsługuje tylko wyświetlanie listy
 * użytkowników, szczegółów wybranego użytkownika oraz usuwanie (patrz uwagi {@link #removeUser(java.lang.Long))
 * użytkownika. Dodawanie i edycja zostały przeniesione do oddzielnego kontrolera.
 *
 * @author jjhop
 *
 * @see UserEditController
 */
@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    /**
     * Wyświetla dane użytkownika na podstawie dostarczonego identyfikatora.
     *
     * @param userId identyfikator użytkownika
     * @param map modelu widoku
     * @return identyfikator widoku prezentującego użytkownika
     */
    @RequestMapping("/manage/users/{userId}/show.html")
    public ModelAndView showUser(@PathVariable("userId") Long userId) {
        ModelAndView mav = new ModelAndView("manager/users/show");
        mav.addObject("user", userDAO.getById(userId));
        return mav;
    }

    /**
     * Wyświetla pełną listę użytkowników.
     * TODO: stronicowanie
     *
     * @param map model widoku
     * @return identyfikator widoku prezentującego listę użytkowników
     */
    @RequestMapping("/manage/users/list.html")
    public ModelAndView showAllUsers() {
        ModelAndView mav = new ModelAndView("manager/users/showAll");
        mav.addObject("users", userDAO.getAllUsers());
        return mav;
    }

    /**
     * Usuwa użytkownika z bazy danych systemu. (Lub po prostu oznacza go jako nieaktywnego).
     * 
     * TODO: przemysleć sens i ewentualnie sposób w jaki będziemy usuwać użytkowników
     *
     * @param userId identyfikator uzytkownika do usunięcia
     * @return identyfikator widoku do wyświetlenia po usunięciu użytkownika
     */
    @RequestMapping("/manage/users/{userId}/remove.html")
    public String removeUser(@PathVariable("userId") Long userId) {
//        userDAO.remove(userId);
        return "redirect:/manage/users/showAll.html";
    }
}
