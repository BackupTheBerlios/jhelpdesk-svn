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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.ArticleDAO;
import de.berlios.jhelpdesk.model.Article;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * TODO: walidator do artykułu
 * TODO: dokumentacja
 *
 * @author jjhop
 */
@Controller
@SessionAttributes("user")
public class ArticleController {

    private static Log log = LogFactory.getLog(ArticleController.class);
    
    @Autowired
    private ArticleDAO articleDAO;

    /**
     * TODO: usunąć {@code required=false} z parametru.
     * 
     * @param categoryId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping("/manage/knowledge/article/showAll.html")
    public ModelAndView showAllArticles(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        ModelAndView mav = new ModelAndView("manage/knowledge/article/showAll");
        try {
            mav.addObject("articles", articleDAO.getForSection(categoryId));
        } catch (Exception ex) {
            log.error(ex);
            mav.setView(new RedirectView("/manage/knowledge/category/showAll.html", true));
        }
        return mav;
    }

    @RequestMapping("/manage/knowledge/article/show.html")
    public String showArticle(@RequestParam(value = "articleId") Long articleId, ModelMap map) {

        try {
            map.addAttribute("article", articleDAO.getById(articleId));
        } catch (Exception ex) {
            log.error(ex);
            return "redirect:/manage/knowledge/category/showAll.html";
        }
        return "manage/knowledge/article/show";
    }

    @RequestMapping("/manage/knowledge/article/remove.html")
    public String remove(@RequestParam("articleId") Long articleId) {
        try {
            articleDAO.delete(articleId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return "/manage/knowledge/article/show.html?articledId=" + articleId;
    }

    @RequestMapping(value = "/manage/knowledge/article/edit.html", method = RequestMethod.GET)  
    protected String prepareForm(
                     @RequestParam(value = "articleId", required = false) Long articleId,
                     @RequestParam(value = "categoryId", required = false) Long categoryId,
                     @ModelAttribute("user") User user,
                     ModelMap map) {

        Article article = (articleId == null) 
            ? new Article()
            : articleDAO.getById(articleId);
        article.setArticleSectionId(categoryId);
        article.setAuthor(user);
        map.addAttribute("article", article);
        return "manage/knowledge/article/edit";
    }

    @RequestMapping(value = "/manage/knowledge/article/edit.html", method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute("article") Article article,
                     BindingResult result, SessionStatus status) {

//        validator.validate(article, result);
        if (result.hasErrors()) {
            return "manage/knowledge/article/edit";
        }
        articleDAO.saveOrUpdate(article);
        status.setComplete();
        return "redirect:showAll.html";
    }
}
