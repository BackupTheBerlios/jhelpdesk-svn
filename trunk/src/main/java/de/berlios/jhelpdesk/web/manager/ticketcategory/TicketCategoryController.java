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
package de.berlios.jhelpdesk.web.manager.ticketcategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;

/**
 *
 * @author jjhop
 */
@Controller
public class TicketCategoryController {

    @Autowired
    @Qualifier("jpa")
    private TicketCategoryDAO categoryDAO;

    /**
     * 
     * @return
     */
    @RequestMapping("/manage/category/showAll.html")
    public String showAllCategories(ModelMap map) {
        map.addAttribute("categories", categoryDAO.getAllCategories());
        return "manager/category/showAll";
    }

    /**
     * 
     * @param categoryId
     * @param map
     * @return
     */
    @RequestMapping("/manager/category/show.html")
    public String showOneCategory(
                  @RequestParam("catId") Long categoryId,
                  ModelMap map) {
        map.addAttribute("category", categoryDAO.getById(categoryId));
        return null;
    }

    /**
     * 
     * @param categoryId
     * @return
     */
    @RequestMapping("/manage/category/remove.html")
    public String removeCategory(@RequestParam("catId") Long categoryId) {
        categoryDAO.deleteCategory(categoryDAO.getById(categoryId));
        return "redirect:/manage/category/showAll.html";
    }

    /**
     * 
     * @param categoryId
     * @return
     */
    @RequestMapping("/manage/category/up.html")
    public String moveUp(@RequestParam("categoryId") Long categoryId) {
        // TODO: zaimplementować
        return "redirect:/manage/category/showAll.html";
    }

    /**
     * 
     * @param categoryId
     * @return
     */
    @RequestMapping("/manage/category/down.html")
    public String moveDown(@RequestParam("categoryId") Long categoryId) {
        // TODO: zaimplementować
        return "redirect:/manage/category/showAll.html";
    }
}
