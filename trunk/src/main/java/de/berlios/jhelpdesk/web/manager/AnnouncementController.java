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
package de.berlios.jhelpdesk.web.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import de.berlios.jhelpdesk.dao.AnnouncementDAO;
import de.berlios.jhelpdesk.model.Announcement;
import de.berlios.jhelpdesk.web.tools.AnnouncementValidator;

/**
 * Kontroler obsługujący zarządzanie ogłoszeniami z działu wsparcia.
 * 
 * @author jjhop
 */
@Controller
public class AnnouncementController {

    private static Log log = LogFactory.getLog(AnnouncementController.class);

    @Autowired
    private AnnouncementValidator validator;

    @Autowired
    @Qualifier("jpa")
    private AnnouncementDAO announcementDAO;

    /**
     * Wyświetla ogłoszenie na podstawie dostarczonego identyfikatora.
     *
     * @param annId identyfikator ogłoszenia
     * @param map modelu widoku
     * @return identyfikator widoku prezentującego ogłoszenia
     */
    @RequestMapping("/announcement/show.html")
    public String showAnnouncement(@RequestParam("infoId") Long annId, ModelMap map) {
        try {
            map.addAttribute("announcement", announcementDAO.getById(annId));
        } catch (Exception e) {
            log.error(e);
            map.addAttribute("errorInfo", e.getMessage());
        }
        return "announcement/show";
    }

    /**
     * Wyświetla pełną listę ogłoszeń.
     * TODO: stronicowanie
     *
     * @param map model widoku
     * @return identyfikator widoku prezentującego listę ogłoszeń
     */
    @RequestMapping("/announcement/showAll.html")
    public String showAllAnnouncements(ModelMap map) {
        map.addAttribute("announcements", announcementDAO.getAll());
        return "announcement/showAll";
    }

    /**
     * Usuwa ogłoszenie na podstawie dostarczonego identyfikatora.
     *
     * @param annId identyfikator ogłoszenia do usunięcia
     * @return widok do wyświetlania po usunięciu ogłoszenia
     */
    @RequestMapping("/announcement/remove.html")
    public String removeAnnouncement(@RequestParam("infoId") Long annId) {
        try {
            announcementDAO.delete(annId);
        } catch (Exception e) {
            log.error(e);
        }
        return "redirect:/announcement/showAll.html";
    }

    /**
     * Przygotowuje formularz do dodania (lub edycji) ogłoszenia. Jeśli w
     * żądaniu znaduje się identyfikator ogłoszenia to zostanie ono przygotowane
     * do edycji, w przeciwnym wypadku do modelu zostanie dołączony nowy (pusty)
     * obiekt ogłoszenia.
     * 
     * @param annId identyfikator ogłoszenia do edycji lub {@code false} jeśli
     * to nowe ogłoszenie
     * @param map model widoku
     * @return identyfikato widoku
     * 
     * @see Announcement
     * @see AnnouncementDAO
     * 
     * @see ModelMap
     */
    @RequestMapping(value = "/announcement/edit.html", method = RequestMethod.GET)
    protected String prepareForm(
                     @RequestParam(value = "infoId", required = false) Long annId,
                     ModelMap map) {

        if (annId == null) {
            map.addAttribute("announcement", new Announcement());
        } else {
            map.addAttribute("announcement", announcementDAO.getById(annId));
        }
        return "announcement/edit";
    }

    /**
     * Obsługuje wysłany formularz i zapisuje podany obiekt. Decyzję o tym czy
     * jest to update czy dodanie nowego podejmuje stosowny obiekt DAO.
     * 
     * @param announcement obiekt ogłoszenia do zapisania (po poprawnym
     * zwalidowaniu)
     * @param result
     * @param status
     * @return identyfikator widoku do wyświetlenia
     * 
     * @see Announcement
     * @see AnnouncementDAO
     * @see AnnouncementValidator
     * 
     * @see BindingResult
     * @see SessionStatus
     */
    @RequestMapping(value = "/announcement/edit.html", method = RequestMethod.POST)
    protected String processSubmit(
                     @ModelAttribute("announcement") Announcement announcement,
                     BindingResult result, SessionStatus status) {

        validator.validate(announcement, result);
        if (result.hasErrors()) {
            return "announcement/edit";
        }
        announcementDAO.save(announcement);
        status.setComplete();
        return "redirect:showAll.html";
    }
}
