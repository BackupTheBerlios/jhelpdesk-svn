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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;

public class MoveUpDownSectionController extends MultiActionController {
	
	private Log log = LogFactory.getLog( MoveUpDownSectionController.class );
	private KnowledgeSectionDAO sectionDAO;
	private ModelAndView mav;
	
	public MoveUpDownSectionController() {
		mav = new ModelAndView(
			new RedirectView("/manage/knowledge/section/showAll.html", true));
	}
	
	public ModelAndView moveUp(HttpServletRequest request, HttpServletResponse response) {
		try {
			sectionDAO.moveUp(Long.parseLong(request.getParameter("sectionId")));
		} catch (Exception ex) {
			log.error(ex);
		}
		return mav;
	}

	public ModelAndView moveDown(HttpServletRequest request, HttpServletResponse response) {
		try {
			sectionDAO.moveDown(Long.parseLong(request.getParameter("sectionId")));
		} catch (Exception ex) {
			log.error(ex);
		}
		return mav;
	}

	/**
	 * @param sectionDAO the sectionDAO to set
	 */
	public void setSectionDAO(KnowledgeSectionDAO sectionDAO) {
		this.sectionDAO = sectionDAO;
	}
}
