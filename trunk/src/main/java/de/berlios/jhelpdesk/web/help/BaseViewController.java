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
package de.berlios.jhelpdesk.web.help;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;

@Scope("prototype")
@Controller("helpBaseViewController")
public class BaseViewController {

    @Autowired
    private KnowledgeDAO knowledgeDAO;
    @Autowired
    private KnowledgeSectionDAO knowledgeSectionDAO;

    @RequestMapping
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        String key = request.getParameter("key");
        String id = request.getParameter("id");
        ModelAndView mav = null;
        if ((key != null) && key.equalsIgnoreCase("details")) {
            mav = new ModelAndView("help/base/one");
            mav.addObject("article", knowledgeDAO.getById(Long.parseLong(id)));
        } else {
            mav = new ModelAndView("help/base");
            mav.addObject("sections", knowledgeSectionDAO.getAllSections());
        }
        return mav;
    }
}