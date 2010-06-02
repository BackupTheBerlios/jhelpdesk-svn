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

import java.text.NumberFormat;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
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

import de.berlios.jhelpdesk.dao.ArticleCategoryDAO;
import de.berlios.jhelpdesk.dao.ArticleDAO;
import de.berlios.jhelpdesk.model.Article;
import de.berlios.jhelpdesk.model.ArticleCategory;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.ArticleCategoryEditor;
import de.berlios.jhelpdesk.web.tools.UserEditor;

/**
 *
 * TODO: walidator do artykułu
 * TODO: dokumentacja
 *
 * @author jjhop
 */
@Controller
public class ArticleController {

    private static Log log = LogFactory.getLog(ArticleController.class);
    
    @Autowired
    private ArticleDAO articleDAOJpa;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Autowired
    private ArticleCategoryEditor articleCategoryEditor;

    @Autowired
    private UserEditor userEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ArticleCategory.class, "category", articleCategoryEditor);
        binder.registerCustomEditor(User.class, "author", userEditor);
        NumberFormat nf = NumberFormat.getNumberInstance();
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, nf, true));
    }

    /**
     * TODO: usunąć {@code required=false} z parametru.
     * 
     * @param categoryId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping("/manage/knowledge/article/showAll.html")
    public String showAllArticles(
                  @RequestParam(value = "categoryId", required = false) Long categoryId,
                  ModelMap map) {

        try {
            map.addAttribute("articles", articleDAOJpa.getForSection(categoryId));
            map.addAttribute("categoryId", categoryId);
        } catch (Exception ex) {
            log.error(ex);
            return "redirect:/manage/knowledge/category/showAll.html";
        }
        return "manage/knowledge/article/showAll";
    }

    @RequestMapping("/manage/knowledge/article/show.html")
    public String showArticle(
                  @RequestParam(value = "articleId") Long articleId,
                  ModelMap map) {

        try {
            map.addAttribute("article", articleDAOJpa.getById(articleId));
        } catch (Exception ex) {
            log.error(ex);
            return "redirect:/manage/knowledge/category/showAll.html";
        }
        return "manage/knowledge/article/show";
    }

    @RequestMapping("/manage/knowledge/article/remove.html")
    public String remove(@RequestParam("articleId") Long articleId) {
        try {
            articleDAOJpa.delete(articleId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return "/manage/knowledge/article/show.html?articledId=" + articleId;
    }

    @RequestMapping(value = "/manage/knowledge/article/edit.html", method = RequestMethod.GET)  
    public String prepareForm(
                  @RequestParam(value = "articleId", required = false) Long articleId,
                  @RequestParam(value = "categoryId", required = false) Long categoryId,
                  HttpSession session, ModelMap map) {
        
        Article article = null;
        if (articleId == null) {
            article = new Article();
            article.setCategory(articleCategoryDAO.getById(categoryId));
            article.setAuthor((User) session.getAttribute("user"));
        } else {
            article = articleDAOJpa.getById(articleId);
        }

        map.addAttribute("article", article);
        return "manage/knowledge/article/edit";
    }

    @RequestMapping(value = "/manage/knowledge/article/edit.html", method = RequestMethod.POST)
    public String processSubmit(
                  @ModelAttribute("article") Article article,
                  BindingResult result, HttpSession session) {

        if (result.hasErrors()) {
            return "manage/knowledge/article/edit";
        }
        articleDAOJpa.saveOrUpdate(article);
        return "redirect:showAll.html";
    }
}
