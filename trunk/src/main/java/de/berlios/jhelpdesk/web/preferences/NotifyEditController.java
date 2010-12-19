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
package de.berlios.jhelpdesk.web.preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.berlios.jhelpdesk.model.NotifyFrequency;
import de.berlios.jhelpdesk.model.EventNotifyPreferences;
import de.berlios.jhelpdesk.web.tools.NotifyFrequencyEditor;

/**
 * 
 * @author jjhop
 */
@Controller
public class NotifyEditController {

    private final static Log log = LogFactory.getLog(NotifyEditController.class);

    @Autowired
    private NotifyFrequencyEditor notifyFrequencyEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(NotifyFrequency.class, notifyFrequencyEditor);
    }

    @RequestMapping(value = "/preferences/eventNotify.html", method = RequestMethod.GET)
    public String prepareForm(ModelMap map) {
        map.addAttribute("preferences", new EventNotifyPreferences());
        return "preferences/eventNotify";
    }

    // TODO: może warto po zapisaniu z sukcesem wrocic na podstawowy widok i wyswietlic
    // na krótką chwilkę komunikat (na zielonym tle - a jakże), że się udało???
    @RequestMapping(value = "/preferences/eventNotify.html", method = RequestMethod.POST)
    public String processForm(@ModelAttribute("preferences") EventNotifyPreferences form,
                              ModelMap map) {
        if (log.isDebugEnabled()) {
            log.debug("START ---------------------------------------");
            log.debug("ticketAssign          -> " + form.getTicketAssign().getName());
            log.debug("ticketAssignOther     -> " + form.getTicketAssignOther().getName());
            log.debug("ticketCategoryChange  -> " + form.getTicketCategoryChange().getName());
            log.debug("ticketClose           -> " + form.getTicketClose().getName());
            log.debug("ticketCommentAdd      -> " + form.getTicketCommentAdd().getName());
            log.debug("ticketFileAddOrRemove -> " + form.getTicketFileAddOrRemove().getName());
            log.debug("ticketPriorityChange  -> " + form.getTicketPriorityChange().getName());
            log.debug("ticketReassign        -> " + form.getTicketReassign().getName());
            log.debug("ticketReject          -> " + form.getTicketReject().getName());
            log.debug("ticketResolve         -> " + form.getTicketResolve().getName());
            log.debug("addDelTicketCategory  -> " + form.getAddDelTicketCategory());
            log.debug("END ----------------------------------------");
        }
        map.addAttribute("preferences", form);
        return "preferences/eventNotify";
    }
}
