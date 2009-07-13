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
package de.berlios.jhelpdesk.web.manager.bugcategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;

public class MoveUpDownCategoryController extends MultiActionController {

    private static Log log = LogFactory.getLog(MoveUpDownCategoryController.class);
    
    @Autowired
    private BugCategoryDAO categoryDAO;

    public ModelAndView moveUp(HttpServletRequest request, HttpServletResponse response) {
        log.debug("moveUp( HttpServletRequest request, HttpServletResponse response )");
        if (categoryDAO != null) {
        }
        return null;
    }

    public ModelAndView moveDown(HttpServletRequest request, HttpServletResponse response) {
        log.debug("moveDown( HttpServletRequest request, HttpServletResponse response )");
        return null;
    }
}
