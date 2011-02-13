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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.web.tools.TicketCategoryValidator;

// TODO: dorobić widok metoda dla save.html w odróżnieniu od update
@Controller
public class TicketCategoryEditController {

    private final static String MANAGE_TICKET_CATEGORY_EDIT = "manager/category/edit";

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

    @RequestMapping(value = "/manage/category/update.html", method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("category") TicketCategory category,
                                BindingResult result, SessionStatus status, ModelMap map) {
        validator.validate(category, result);
        if (result.hasErrors()) {
            return MANAGE_TICKET_CATEGORY_EDIT;
        }

        // jeśli walidacja się powiedzie to można przystąpić do zapisania kategorii
        if (category.getTicketCategoryId() != null) {
            categoryDAO.updateCategory(category);
        } else {
            if (category.getParentCategory() != null) {
                TicketCategory parent = categoryDAO.getById(category.getParentCategory()); // TODO: parametr w URLu
                categoryDAO.insertCategory(category, parent);
            } else {
                categoryDAO.insertRootCategory(category);
            }
        }
        status.setComplete();
        return "redirect:/manage/category/" + category.getTicketCategoryId() + "/show.html";
    }

    @RequestMapping(value = "/manage/category/{id}/edit.html", method = RequestMethod.GET)
    public String prepareForm(@PathVariable("id") Long catId, ModelMap map) {
        map.addAttribute("category", categoryDAO.getById(catId));
        return MANAGE_TICKET_CATEGORY_EDIT;
    }

    @RequestMapping(value = "/manage/category/new.html", method = RequestMethod.GET)
    public String prepareFormForNew(ModelMap map) {
        map.addAttribute("category", new TicketCategory());
        return MANAGE_TICKET_CATEGORY_EDIT; // todo: może tutaj jakiś widok inny? -> new?
    }

    @RequestMapping(value = "/manage/category/{parent}/new.html", method = RequestMethod.GET)
    public String prepareFormForNew(@PathVariable("parent") Long parent, ModelMap map) {
        map.addAttribute("category", new TicketCategory());
        return MANAGE_TICKET_CATEGORY_EDIT; // todo: może tutaj jakiś widok inny? -> new?
    }
}
