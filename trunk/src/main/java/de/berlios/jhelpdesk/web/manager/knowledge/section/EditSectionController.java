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
package de.berlios.jhelpdesk.web.manager.knowledge.section;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;
import de.berlios.jhelpdesk.model.KnowledgeSection;

public class EditSectionController extends SimpleFormController {

    private static Log log = LogFactory.getLog(EditSectionController.class);

    @Autowired
    private KnowledgeSectionDAO sectionDAO;

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        KnowledgeSection section = null;
        try {
            section = sectionDAO.getById(
                Long.parseLong(request.getParameter("sectionId")));
        } catch (Exception ex) {
            log.warn(ex);
            section = new KnowledgeSection();
        }
        return section;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
        HttpServletResponse response, Object command, BindException errors)
        throws Exception {
        ModelAndView mav = new ModelAndView(
            new RedirectView("/manage/knowledge/section/showAll.html", true));
        KnowledgeSection section = (KnowledgeSection) command;
        sectionDAO.saveOrUpdate(section);
        return mav;
    }

}
