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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;

/**
 * Obsługa funkcji znajdujących się w menu "Pomoc" programu (w tym obsługa
 * przeglądania bazy wiedzy dzialu wsparcia).
 * 
 * @author jjhop
 */
@Controller
public class HelpViewController {

    @Autowired
    private KnowledgeDAO knowledgeDAO;
    
    @Autowired
    private KnowledgeSectionDAO knowledgeSectionDAO;

    /**
     * Wyświetla pomoc do programu.
     * 
     * @return identyfikator widoku pomocy
     */
    @RequestMapping("/help/index.html")
    public String indexView() {
        return "help/index";
    }

    /**
     * Wyświetla stronę z informacjami o programie.
     *
     * @return identyfikator widoku z informacjami o programie
     */
    @RequestMapping("/help/about.html")
    public String aboutView() {
        return "help/about";
    }

    /**
     * Obsługuje przeglądanie bazy wiedzy działu wsparcia.
     *
     * @param id identyfikator artykułu z bazy wiedzy, może mieć wartość {@code null}
     * @param key
     * @param mav model widoku
     * @return identyfikator widoku właściwego dla zakresu żądania
     */
    // TODO: metoda do przejrzenia, być może trzeba będzie ją rozbić na dwie lub więcej
    @RequestMapping("/help/base.html")
    public String knowledgeBaseView(
                  @RequestParam(value = "id", required = false) Long id,
                  @RequestParam(value = "key", required = false) String key,
                  ModelMap mav) {

        if ((key != null) && key.equalsIgnoreCase("details")) {
            mav.addAttribute("article", knowledgeDAO.getById(id));
            return "help/base/one";
        } else {
            mav.addAttribute("sections", knowledgeSectionDAO.getAllSections());
            return "help/base";
        }
    }
}