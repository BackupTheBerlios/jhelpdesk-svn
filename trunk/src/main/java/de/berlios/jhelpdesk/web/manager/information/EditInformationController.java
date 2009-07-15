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
package de.berlios.jhelpdesk.web.manager.information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import de.berlios.jhelpdesk.dao.InformationDAO;
import de.berlios.jhelpdesk.model.Information;
import de.berlios.jhelpdesk.web.tools.InformationValidator;

/**
 * Kontroler obsługujący dodawanie oraz edycję ogłoszeń z działu wsparcia.
 *
 * @author jjhop
 *
 * @see Information
 */
@Controller("managerEditInformationCtrl")
public class EditInformationController {

    @Autowired
    private InformationDAO informationDAO;

    @Autowired
    private InformationValidator validator;

    /**
     * Przygotowuje formularz do dodania (lub edycji) ogłoszenia. Jeśli w żądaniu
     * znaduje się identyfikator ogłoszenia to zostanie ono przygotowane do edycji,
     * w przeciwnym wypadku do modelu zostanie dołączony nowy (pusty) obiekt ogłoszenia.
     * 
     * @param infoId identyfikator ogłoszenia do edycji
     *               lub {@code false} jeśli to nowe ogłoszenie
     * @param map model widoku
     * @return identyfikato widoku
     *
     * @see Information
     * @see InformationDAO
     * 
     * @see ModelMap
     */
    @RequestMapping(method = RequestMethod.GET)
    protected String prepareForm(
                     @RequestParam(value = "infoId", required = false) Long infoId,
                     ModelMap map) {
        
        if (infoId == null) {
            map.addAttribute("information", new Information());
        } else {
            map.addAttribute("information", informationDAO.getById(infoId));
        }
        return "manager/information/edit";
    }

    /**
     * Obsługuje wysłany formularz i zapisuje podany obiekt. Decyzję o tym czy jest
     * to update czy dodanie nowego podejmuje stosowny obiekt DAO.
     * 
     * @param information obiekt ogłoszenia do zapisania (po poprawnym zwalidowaniu)
     * @param result
     * @param status
     * @return identyfikator widoku do wyświetlenia
     *
     * @see Information
     * @see InformationDAO
     * @see InformationValidator
     *
     * @see BindingResult
     * @see SessionStatus
     */
    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(
                     @ModelAttribute("information") Information information,
                     BindingResult result, SessionStatus status) {

        validator.validate(information, result);

        if (result.hasErrors()) {
            return "manager/information/edit";
        }
        informationDAO.save(information);
        status.setComplete();
        return "redirect:showAll.html";
    }
}
