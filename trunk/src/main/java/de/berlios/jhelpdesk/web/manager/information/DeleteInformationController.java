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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.InformationDAO;

@Controller("managerDeleteInformationCtrl")
public class DeleteInformationController {

    private static Log log = LogFactory.getLog(DeleteInformationController.class);
    
    @Autowired
    private InformationDAO informationDAO;

    @RequestMapping
    public ModelAndView handleRequest(@RequestParam("infoId") Long infoId) throws Exception {
        ModelAndView mav = new ModelAndView(
            new RedirectView("/manage/information/showAll.html", true));
        try {
            informationDAO.delete(infoId);
        } catch (Exception e) {
            mav.addObject("errorInfo", e.getMessage());
            log.error(e);
        }
        return mav;
    }
}
