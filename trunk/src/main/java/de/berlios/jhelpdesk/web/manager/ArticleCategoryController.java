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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import de.berlios.jhelpdesk.dao.ArticleCategoryDAO;
import de.berlios.jhelpdesk.model.ArticleCategory;
import de.berlios.jhelpdesk.web.tools.ArticleCategoryValidator;

@Controller
public class ArticleCategoryController {

    private static final Log log = LogFactory.getLog(ArticleCategoryController.class);

    @Autowired
    private ArticleCategoryValidator validator;

    @Autowired
    private ArticleCategoryDAO categoryDAO;

    @RequestMapping("/manage/knowledge/category/showAll.html")
    public String showAll(ModelMap map) throws Exception {
        map.addAttribute("categories", categoryDAO.getAllShortSections());
        return "manager/knowledge/category/showAll";
    }

    @RequestMapping("/manage/knowledge/category/show.html")
    public String showOne(@RequestParam("categoryId") Long categoryId, ModelMap map) {
        try {
            map.addAttribute("category", categoryDAO.getById(categoryId));
        } catch (Exception ex) {
            return "redirect:/manager/knowledge/category/showAll";
        }
        return "manager/knowledge/category/show";
    }

    @RequestMapping("/manage/knowledge/category/remove.html")
    public String processRemove(@RequestParam("categoryId") Long categoryId) {
        try {
            categoryDAO.delete(categoryId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return "redirect:/manage/knowledge/category/showAll.html";
    }

    @RequestMapping("/manage/knowledge/category/up.html")
    public String moveUp(@RequestParam("categoryId") Long categoryId) {
        try {
            categoryDAO.moveUp(categoryId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return "redirect:/manage/knowledge/category/showAll.html";
    }

    @RequestMapping("/manage/knowledge/category/down.html")
    public String moveDown(@RequestParam("categoryId") Long categoryId) {
        try {
            categoryDAO.moveDown(categoryId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return "redirect:/manage/knowledge/category/showAll.html";
    }

    @RequestMapping(value = "/manage/knowledge/category/edit.html", method = RequestMethod.GET)
    public String prepareForm(
                  @RequestParam(value = "categoryId", required = false) Long categoryId,
                  ModelMap map) throws Exception {
        
        if (categoryId == null) {
            map.addAttribute("category", new ArticleCategory());
        } else {
            map.addAttribute("category", categoryDAO.getById(categoryId));
        }
        return "manager/knowledge/category/edit";
    }

    @RequestMapping(value = "/manage/knowledge/category/edit.html", method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("category")  ArticleCategory category,
                  BindingResult result, SessionStatus status) throws Exception {

        validator.validate(category, result);
        if (result.hasErrors()) {
            return "manager/knowledge/category/edit";
        }
        categoryDAO.saveOrUpdate(category);
        status.setComplete();
        return "redirect:/manage/knowledge/category/showAll.html";
    }
}
