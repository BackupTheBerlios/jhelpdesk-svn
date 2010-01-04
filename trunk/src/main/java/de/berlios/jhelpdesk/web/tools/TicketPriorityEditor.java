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

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;

import de.berlios.jhelpdesk.model.TicketPriority;

@Component
public class TicketPriorityEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        if (getValue() != null) {
            return String.valueOf(((TicketPriority)getValue()).toInt());
        } else {
            return null;
        }
    }

    @Override
    public void setAsText(String text) {
        int ticketPriorityId = Integer.valueOf(text);
        TicketPriority.fromInt(ticketPriorityId);
        setValue(TicketPriority.fromInt(ticketPriorityId));
    }
}
