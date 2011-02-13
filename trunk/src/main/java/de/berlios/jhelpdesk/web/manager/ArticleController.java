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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.berlios.jhelpdesk.dao.ArticleCategoryDAO;
import de.berlios.jhelpdesk.dao.ArticleDAO;
import de.berlios.jhelpdesk.model.Article;
import de.berlios.jhelpdesk.model.ArticleCategory;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.utils.LuceneIndexer;
import de.berlios.jhelpdesk.web.tools.ArticleCategoryEditor;
import de.berlios.jhelpdesk.web.tools.ArticleValidator;
import de.berlios.jhelpdesk.web.tools.UserEditor;

/**
 * TODO: dokumentacja
 *
 * @author jjhop
 */
@Controller
public class ArticleController {

    private final static String MANAGE_KB_ARTICLE_SHOW = "manage/knowledge/article/show";
    private final static String MANAGE_KB_ARTICLE_EDIT = "manage/knowledge/article/edit";
    private final static String MANAGE_KB_ARTICLE_LIST = "manage/knowledge/article/showAll";

    @Autowired
    private ArticleDAO articleDAOJpa;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Autowired
    private ArticleValidator articleValidator;

    @Autowired
    private LuceneIndexer luceneIndexer;

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

    @RequestMapping("/manage/kb/category/{id}/articles.html")
    public String showAllArticles(@PathVariable("id") Long categoryId, ModelMap map) {
        map.addAttribute("articles", articleDAOJpa.getForSection(categoryId));
        map.addAttribute("categoryId", categoryId);
        return MANAGE_KB_ARTICLE_LIST;
    }

    @RequestMapping("/manage/kb/category/{cId}/articles/{aId}/show.html")
    public String showArticle(@PathVariable("aId") Long articleId, ModelMap map) {
        map.addAttribute("article", articleDAOJpa.getById(articleId));
        return MANAGE_KB_ARTICLE_SHOW;
    }

    @RequestMapping("/manage/kb/category/{cId}/articles/{aId}/remove.html")
    public String remove(@PathVariable("cId") Long categoryId,
                         @PathVariable("aId") Long articleId) {
        articleDAOJpa.delete(articleId);
        luceneIndexer.removeIndexedArticle(articleId);
        return "redirect:/manage/kb/category/" + categoryId + "/articles.html";
    }

    @RequestMapping(value = "/manage/kb/category/{cId}/articles/{aId}/edit.html", method = RequestMethod.GET)
    public String prepareFormForEdit(@PathVariable("cId") Long categoryId,
                                     @PathVariable("aId") Long articleId,
                                     HttpSession session, ModelMap map) throws Exception {
        Article article = null;
        if (articleId == null) {
            article = new Article();
            article.setCategory(articleCategoryDAO.getById(categoryId));
            article.setAuthor((User) session.getAttribute("user"));
        } else {
            article = articleDAOJpa.getById(articleId);
        }
        map.addAttribute("article", article);
        map.addAttribute("formAction", "edit");
        return MANAGE_KB_ARTICLE_EDIT;
    }

    /**
     * Przygotowuje formularz tworzenia nowego artykułu.
     */
    @RequestMapping(value = "/manage/kb/category/{cId}/articles/new.html", method = RequestMethod.GET)
    public String prepareFormForNew(@PathVariable("cId") Long categoryId,
                                    HttpSession session, ModelMap map) {
        Article article = new Article();
        article.setCategory(articleCategoryDAO.getById(categoryId));
        article.setAuthor((User) session.getAttribute("user"));
        map.addAttribute("article", article);
        map.addAttribute("formAction", "save");
        return MANAGE_KB_ARTICLE_EDIT;
    }

    @RequestMapping(value = "/manage/kb/articles/save.html", method = RequestMethod.POST)
    public String processSubmitNew(@ModelAttribute("article") Article article,
                                   BindingResult result, ModelMap map) {
        articleValidator.validate(article, result);
        if (result.hasErrors()) {
            map.addAttribute("formAction", "save");
            return MANAGE_KB_ARTICLE_EDIT;
        }
        articleDAOJpa.saveOrUpdate(article);
        luceneIndexer.addToIndex(article);
        System.out.println("artykul [" + article.getArticleId() + "] dodany do indeksu");
        return "redirect:/manage/kb/category/" + article.getCategory().getArticleCategoryId() +
                "/articles/" + article.getArticleId() + "/show.html";
    }
}
