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

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.berlios.jhelpdesk.model.Ticket;

public class TicketValidator implements Validator {
	
	// implementujemy Validator.supports(Class), dlatego SuppressWarnings
	public boolean supports(@SuppressWarnings("unchecked") Class clazz) { 
		return Ticket.class.equals(clazz);
	}

	public void validate(Object command, Errors errors) {
		Ticket ticket = (Ticket) command;
		if (ticket.getSubject().trim().isEmpty())
			errors.reject("pofakany.subject"); // TODO: to chyba do wymiany, nie?
		if (ticket.getTicketPriority() == null)
			errors.reject("pofakany.priorytet");
		if (ticket.getTicketCategory() == null)
			errors.reject("pofakana.kategoria");
	}
}
