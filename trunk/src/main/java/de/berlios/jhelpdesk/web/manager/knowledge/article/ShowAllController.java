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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.KnowledgeDAO;

@Controller("managerKnowledgeArticleShowAll")
public class ShowAllController {

    private static Log log = LogFactory.getLog(ShowAllController.class);
    
    @Autowired
    private KnowledgeDAO knowledgeDAO;

    /**
     * TODO: usunąć {@code required=false} z parametru.
     * 
     * @param sectionId
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping
    public ModelAndView handleRequest(@RequestParam(value = "sectionId", required = false) Long sectionId)
        throws Exception {
        ModelAndView mav = new ModelAndView("manage/knowledge/article/showAll");
        try {
            mav.addObject("articles", knowledgeDAO.getForSection(sectionId));
        } catch (Exception ex) {
            log.error(ex);
            mav.setView(new RedirectView("/manage/knowledge/section/showAll.html", true));
        }
        return mav;
    }
}
