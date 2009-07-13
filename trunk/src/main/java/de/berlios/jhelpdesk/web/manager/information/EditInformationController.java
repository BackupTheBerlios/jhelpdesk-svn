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
package de.berlios.jhelpdesk.web.manager.information;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.InformationDAO;
import de.berlios.jhelpdesk.model.Information;
import de.berlios.jhelpdesk.web.tools.InformationValidator;

public class EditInformationController extends SimpleFormController {
	private static Log log = LogFactory.getLog(EditInformationController.class);

    @Autowired
	private InformationDAO informationDAO;

    @Autowired
    public EditInformationController(InformationValidator validator) {
        setValidator(validator);
    }

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		log.debug("formBackingObject");
		Information information = null;
		try {
			information = informationDAO.getById(
					Long.parseLong(request.getParameter("infoId")));
		} catch (Exception ex) {
			log.warn(ex);
			information = new Information();
		}
		return information;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, 
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		log.debug("onSubmit()");
		ModelAndView mav = null;
		try {
			informationDAO.save((Information) command);
			mav = new ModelAndView(
					new RedirectView("/manage/information/showAll.html", true));
		} catch (Exception ex) {
			log.error(ex);
			mav = new ModelAndView(getFormView());
		}
		return mav;
	}

}
