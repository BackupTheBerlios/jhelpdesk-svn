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

import java.text.NumberFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.web.tools.TicketCategoryValidator;

@Controller
public class TicketCategoryEditController {

    @Autowired
    private TicketCategoryDAO categoryDAO;
    
    @Autowired
    private TicketCategoryValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Long.class, null,
            new CustomNumberEditor(Long.class, NumberFormat.getNumberInstance(), true));
        binder.registerCustomEditor(Boolean.class, null, new CustomBooleanEditor(true));
    }

    @RequestMapping(value = "/manage/category/edit.html", method = RequestMethod.POST)
    public String processSubmit(
                  @ModelAttribute("category") TicketCategory category,
                  BindingResult result, SessionStatus status,
                  ModelMap map) {

        validator.validate(category, result);
        if (result.hasErrors()) {
            return "manager/category/edit";
        }

        // jeśli walidacja się powiedzie to można przystąpić do zapisania kategorii
        if (category.getTicketCategoryId() != null) {
            categoryDAO.updateCategory(category);
        } else {
            if (category.getParentCategory() != null) {
                TicketCategory parent = new TicketCategory();
                parent.setTicketCategoryId(category.getParentCategory());
                categoryDAO.insertCategory(category, parent);
            } else {
                categoryDAO.insertRootCategory(category);
            }
        }
        status.setComplete();
        return "redirect:/manage/category/show.html?catId=" + category.getTicketCategoryId();
    }

    @RequestMapping(value = "/manage/category/edit.html", method = RequestMethod.GET)
    public String prepareForm(
                  @RequestParam(value = "catId", required = false) Long catId,
                  ModelMap map) {
        if (catId == null) {
            map.addAttribute("category", new TicketCategory());
        } else {
            map.addAttribute("category", categoryDAO.getById(catId));
        }
        return "manager/category/edit";
    }
}
