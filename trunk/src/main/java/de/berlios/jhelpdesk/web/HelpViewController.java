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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelpViewController {

    @Autowired
    private KnowledgeDAO knowledgeDAO;
    @Autowired
    private KnowledgeSectionDAO knowledgeSectionDAO;

    @RequestMapping("/help/index.html")
    public ModelAndView indexView() throws Exception {
        return new ModelAndView("help/index");
    }

    @RequestMapping("/help/about.html")
    public ModelAndView aboutView() throws Exception {
        return new ModelAndView("help/about");
    }

    @RequestMapping("/help/base.html")
    public ModelAndView knowledgeBaseView(
                        @RequestParam(value = "id", required = false) Long id,
                        @RequestParam(value ="key", required = false) String key) {

        ModelAndView mav = null;
        if ((key != null) && key.equalsIgnoreCase("details")) {
            mav = new ModelAndView("help/base/one");
            mav.addObject("article", knowledgeDAO.getById(id));
        } else {
            mav = new ModelAndView("help/base");
            mav.addObject("sections", knowledgeSectionDAO.getAllSections());
        }
        return mav;
    }
}
