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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.berlios.jhelpdesk.dao.ArticleCategoryDAO;
import de.berlios.jhelpdesk.dao.ArticleDAO;
import de.berlios.jhelpdesk.utils.LuceneIndexer;

/**
 * Obsługa funkcji znajdujących się w menu "Pomoc" programu (w tym obsługa
 * przeglądania bazy wiedzy dzialu wsparcia).
 * 
 * @author jjhop
 */
@Controller
public class HelpViewController {

    private final static int NUM_OF_LAST_ADDED_ARTICLES = 10;

    private final static String HELP_INDEX = "help/index";
    private final static String HELP_ABOUT = "help/about";
    private final static String HELP_KB_INDEX = "help/base"; // zamienić na help/kb/index
    private final static String HELP_KB_ARTICLE = "help/base/one";
    private final static String HELP_KB_SEARCH_RESULT = "help/kb/searchResult";

    @Autowired
    private ArticleDAO articleDAO;
    
    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Autowired
    private LuceneIndexer luceneIndexer;

    /**
     * Wyświetla pomoc do programu.
     * 
     * @return identyfikator widoku pomocy
     */
    @RequestMapping("/help/index.html")
    public String indexView() {
        return HELP_INDEX;
    }

    /**
     * Wyświetla stronę z informacjami o programie.
     *
     * @return identyfikator widoku z informacjami o programie
     */
    @RequestMapping("/help/about.html")
    public String aboutView() {
        return HELP_ABOUT;
    }

    /**
     * Obsługuje przeglądanie bazy wiedzy działu wsparcia.
     *
     * @param map model widoku
     * @return identyfikator widoku
     */
    @RequestMapping(value = "/help/kb/index.html", method = RequestMethod.GET)
    public String knowledgeBaseView(ModelMap map) {
        map.addAttribute("categories", articleCategoryDAO.getAllCategories());
        map.addAttribute("latest", articleDAO.getLastAddedArticles(NUM_OF_LAST_ADDED_ARTICLES));
        return HELP_KB_INDEX;
    }

    @RequestMapping(value = "/help/kb/search.html", method = RequestMethod.GET)
    public String knowledgeBaseSearch(@RequestParam("query") String query, ModelMap map) {
        map.addAttribute("result", luceneIndexer.search(query));
        return HELP_KB_SEARCH_RESULT;
    }

    /**
     * Wyświetla artykuł oznaczony w bazie wiedzy identyfikatorem {@code id}
     * 
     * @param id identyfikator artykułu do wyświetlenia
     * @param map model widoku
     * @return identyfikator widoku
     */
    @RequestMapping(value = "/help/base/showOne.html", method = RequestMethod.GET)
    public String knowledgeBaseItemView(@RequestParam("id") Long id, ModelMap map) {
        map.addAttribute("article", articleDAO.getById(id));
        return HELP_KB_ARTICLE;
    }
}
