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
package de.berlios.jhelpdesk.web.tools;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.berlios.jhelpdesk.model.Ticket;

@Component("ticketValidator")
public class TicketValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return Ticket.class.equals(clazz);
    }

    public void validate(Object command, Errors errors) {
        Ticket ticketToValidate = (Ticket)command;
        validateSubject(ticketToValidate, errors);
        validateDescription(ticketToValidate, errors);
        validateNotifier(ticketToValidate, errors);
    }

    private void validateSubject(Ticket ticketToValidate, Errors errors) {
        if (ticketToValidate.getSubject() == null
                || ticketToValidate.getSubject().trim().isEmpty()) { // isEmpty jest z jdk 1.6
            errors.rejectValue("subject", "ticket.subject.error");
        }
    }

    private void validateDescription(Ticket ticketToValidate, Errors errors) {
        if (ticketToValidate.getDescription() == null
                || ticketToValidate.getDescription().trim().isEmpty()) {
            errors.rejectValue("description", "ticket.description.error");
        }
    }

    private void validateNotifier(Ticket ticketToValidate, Errors errors) {
        if (ticketToValidate.getNotifier() == null) {
            errors.rejectValue("notifier", "ticket.notifier.error");
        }
    }
}
