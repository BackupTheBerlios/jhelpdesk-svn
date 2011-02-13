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
import org.springframework.web.bind.annotation.PathVariable;
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

    private final static String MANAGE_KB_CATEGORY_LIST_RDR = "redirect:/manage/kb/categories/all.html";
    private final static String MANAGE_KB_CATEGORY_LIST = "manager/knowledge/category/showAll";
    private final static String MANAGE_KB_CATEGORY_SHOW = "manager/knowledge/category/show";
    private final static String MANAGE_KB_CATEGORY_EDIT = "manager/knowledge/category/edit";

    @Autowired
    private ArticleCategoryValidator validator;

    @Autowired
    private ArticleCategoryDAO categoryDAO;

    @RequestMapping("/manage/kb/categories/all.html")
    public String showAll(ModelMap map) throws Exception {
        map.addAttribute("categories", categoryDAO.getAllCategories());
        return MANAGE_KB_CATEGORY_LIST;
    }

    @RequestMapping("/manage/kb/category/{id}/show.html")
    public String showOne(@PathVariable("id") Long categoryId, ModelMap map) {
        try {
            map.addAttribute("category", categoryDAO.getById(categoryId));
        } catch (Exception ex) {
            return MANAGE_KB_CATEGORY_LIST_RDR;
        }
        return MANAGE_KB_CATEGORY_SHOW;
    }

    @RequestMapping("/manage/kb/category/{id}/remove.html")
    public String processRemove(@PathVariable("id") Long categoryId, ModelMap map) {
        try {
            ArticleCategory category = categoryDAO.getById(categoryId);
            if (category.getArticlesCount() > 0) {
                // todo: i18n dla komunikatu
                map.addAttribute("message", "Nie można usunąć kategorii, w której sa artykuły");
                // tutaj warto przejść na jakis widok z komunikatem
            } else {
                categoryDAO.delete(categoryId);
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return MANAGE_KB_CATEGORY_LIST_RDR;
    }

    @RequestMapping("/manage/kb/category/up.html")
    public String moveUp(@RequestParam("categoryId") Long categoryId) {
        try {
            categoryDAO.moveUp(categoryId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return MANAGE_KB_CATEGORY_LIST_RDR;
    }

    @RequestMapping("/manage/kb/category/down.html")
    public String moveDown(@RequestParam("categoryId") Long categoryId) {
        try {
            categoryDAO.moveDown(categoryId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return MANAGE_KB_CATEGORY_LIST_RDR;
    }

    @RequestMapping(value = "/manage/kb/category/edit.html", method = RequestMethod.GET)
    public String prepareForm(@RequestParam(value = "categoryId", required = false) Long categoryId,
                              ModelMap map) throws Exception {
        if (categoryId == null) {
            map.addAttribute("category", new ArticleCategory());
        } else {
            map.addAttribute("category", categoryDAO.getById(categoryId));
        }
        return MANAGE_KB_CATEGORY_EDIT;
    }

    @RequestMapping(value = "/manage/kb/category/edit.html", method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("category")  ArticleCategory category,
                                BindingResult result, SessionStatus status) throws Exception {
        validator.validate(category, result);
        if (result.hasErrors()) {
            return MANAGE_KB_CATEGORY_EDIT;
        }
        categoryDAO.saveOrUpdate(category);
        status.setComplete();
        return MANAGE_KB_CATEGORY_LIST_RDR;
    }
}
