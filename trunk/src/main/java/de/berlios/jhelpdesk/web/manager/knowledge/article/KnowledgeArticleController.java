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
package de.berlios.jhelpdesk.web.manager.knowledge.article;

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

import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.model.Knowledge;
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
public class KnowledgeArticleController {

    private static Log log = LogFactory.getLog(KnowledgeArticleController.class);
    
    @Autowired
    private KnowledgeDAO knowledgeDAO;

    /**
     * TODO: usunąć {@code required=false} z parametru.
     * 
     * @param sectionId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping("/manage/knowledge/article/showAll.html")
    public ModelAndView showAllArticles(@RequestParam(value = "sectionId", required = false) Long sectionId) {
        ModelAndView mav = new ModelAndView("manage/knowledge/article/showAll");
        try {
            mav.addObject("articles", knowledgeDAO.getForSection(sectionId));
        } catch (Exception ex) {
            log.error(ex);
            mav.setView(new RedirectView("/manage/knowledge/section/showAll.html", true));
        }
        return mav;
    }

    @RequestMapping("/manage/knowledge/article/show.html")
    public String showArticle(@RequestParam(value = "articleId") Long articleId, ModelMap map) {

        try {
            map.addAttribute("article", knowledgeDAO.getById(articleId));
        } catch (Exception ex) {
            log.error(ex);
            return "redirect:/manage/knowledge/section/showAll.html";
        }
        return "manage/knowledge/article/show";
    }

    @RequestMapping("/manage/knowledge/article/remove.html")
    public String remove(@RequestParam("articleId") Long articleId) {
        try {
            knowledgeDAO.delete(articleId);
        } catch (Exception ex) {
            log.error(ex);
        }
        return "/manage/knowledge/article/show.html?articledId=" + articleId;
    }

    @RequestMapping(value = "/manage/knowledge/article/edit.html", method = RequestMethod.GET)  
    protected String prepareForm(
                     @RequestParam(value = "articleId", required = false) Long articleId,
                     @RequestParam(value = "sectionId", required = false) Long sectionId,
                     @ModelAttribute("user") User user,
                     ModelMap map) {

        Knowledge article = (articleId == null) 
            ? new Knowledge()
            : knowledgeDAO.getById(articleId);
        article.setKnowledgeSectionId(sectionId);
        article.setAuthor(user);
        map.addAttribute("article", article);
        System.out.println(user + "");

        return "manage/knowledge/article/edit";
    }

    @RequestMapping(value = "/manage/knowledge/article/edit.html", method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute("article") Knowledge article,
                     BindingResult result, SessionStatus status) {

//        validator.validate(article, result);
        if (result.hasErrors()) {
            return "manage/knowledge/article/edit";
        }
        knowledgeDAO.saveOrUpdate(article);
        status.setComplete();
        return "redirect:showAll.html";
    }
}
