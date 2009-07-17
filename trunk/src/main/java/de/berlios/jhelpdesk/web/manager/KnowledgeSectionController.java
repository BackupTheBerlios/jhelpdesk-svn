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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;
import de.berlios.jhelpdesk.model.KnowledgeSection;
import de.berlios.jhelpdesk.web.tools.KnowledgeSectionValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class KnowledgeSectionController {

    private static final Log log = LogFactory.getLog(KnowledgeSectionController.class);

    @Autowired
    private KnowledgeSectionValidator validator;

    @Autowired
    private KnowledgeSectionDAO sectionDAO;

    @RequestMapping("/manage/knowledge/section/showAll.html")
    public String showAll(ModelMap map) throws Exception {
        map.addAttribute("sections", sectionDAO.getAllShortSections());
        return "manager/knowledge/section/showAll";
    }

    @RequestMapping("/manage/knowledge/section/show.html")
    public String showOne(@RequestParam("sectionId") Long sectionId, ModelMap map) {
        try {
            map.addAttribute("section", sectionDAO.getById(sectionId));
        } catch (Exception ex) {
            return "redirect:/manager/knowledge/section/showAll";
        }
        return "manager/knowledge/section/show";
    }

    @RequestMapping("/manage/knowledge/section/remove.html")
    public String processRemove(@RequestParam("sectionId") Long sectionId) {
        try {
            sectionDAO.delete(sectionId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return "redirect:/manage/knowledge/section/showAll.html";
    }

    @RequestMapping("/manage/knowledge/section/up.html")
    public String moveUp(@RequestParam("sectionId") Long sectionId) {
        try {
            sectionDAO.moveUp(sectionId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return "redirect:/manage/knowledge/section/showAll.html";
    }

    @RequestMapping("/manage/knowledge/section/down.html")
    public String moveDown(@RequestParam("sectionId") Long sectionId) {
        try {
            sectionDAO.moveDown(sectionId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return "redirect:/manage/knowledge/section/showAll.html";
    }

    @RequestMapping(value = "/manage/knowledge/section/edit.html", method = RequestMethod.GET)
    public String prepareForm(
                     @RequestParam(value = "sectionId", required = false) Long sectionId,
                     ModelMap map) throws Exception {
        
        if (sectionId == null) {
            map.addAttribute("section", new KnowledgeSection());
        } else {
            map.addAttribute("section", sectionDAO.getById(sectionId));
        }
        return "manager/knowledge/section/edit";
    }

    @RequestMapping(value = "/manage/knowledge/section/edit.html", method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("section")  KnowledgeSection section,
                  BindingResult result, SessionStatus status) throws Exception {

        validator.validate(section, result);
        if (result.hasErrors()) {
            return "manager/information/edit";
        }
        sectionDAO.saveOrUpdate(section);
        status.setComplete();
        return "redirect:/manage/knowledge/section/showAll.html";
    }
}
