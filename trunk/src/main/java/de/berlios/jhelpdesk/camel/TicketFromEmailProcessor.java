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
package de.berlios.jhelpdesk.camel;

import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.mail.MailMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.AdditionalFile;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Component("ticketFromEmailProcessor")
public class TicketFromEmailProcessor implements Processor {

    private static final Log log = LogFactory.getLog(TicketFromEmailProcessor.class);

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TicketCategoryDAO ticketCategoryDAO;
    
    @Autowired
    private UserDAO userDAO;

    public void process(Exchange exchange) throws Exception {
        MailMessage in = exchange.getIn(MailMessage.class);
        User emailAuthor = null;
        TicketPriority priority = TicketPriority.LOW; // TODO: jakiś default powinien być
        for (Map.Entry<String, Object> e : in.getHeaders().entrySet()) {
            if (e.getKey().startsWith("From")) {
                emailAuthor = extractUserEmail(e.getValue());
            } else if (e.getKey().startsWith("X-Priority")) {
                priority = extractTicketPriority(e.getValue());
            }
        }
        if (existsAndActive(emailAuthor)) {
            // TODO: kategoria powinna być albo na podstawie zawartości maila, albo
            //       ticketCategoryDAO.getDefault() jeśli to co zostanie zwrócone nie jest null
            Ticket ticket = Ticket.create(priority, ticketCategoryDAO.getById(1L),
                    in.getMessage().getSubject(), (String) in.getMessage().getContent(),
                    in.hasAttachments() ? extractAttachments(in.getAttachments()) : null,
                    emailAuthor);
            ticketDAO.save(ticket);
        }
    }

    private boolean existsAndActive(User user) {
        return user != null && user.isActive();
    }

    // TODO: przechwycić załączniki i zapisać razem z Ticketem
    private List<AdditionalFile> extractAttachments(Map<String, DataHandler> attachmentsMap) {
        for (String attachmentName : attachmentsMap.keySet()) {
            System.out.println("attachment: " + attachmentName);
        }
        return null;
    }

    private TicketPriority extractTicketPriority(Object from) {
        try {
            String priorityAsString = ((String) from).substring(0, 1);
            int priorityAsInt = Integer.parseInt(priorityAsString);
            return TicketPriority.fromInt(priorityAsInt);
        } catch(Exception ex) {
            log.debug("Nieznany znaczniki priorytetu. Ustawiam [TicketPriority.NORMAL]", ex);
        }
        return TicketPriority.NORMAL;
    }

    private User extractUserEmail(Object from) {
        String fromAsString = (String) from;
        int begin = fromAsString.indexOf("<") + 1;
        int end   = fromAsString.indexOf(">", begin);
        String email = fromAsString.substring(begin, end);
        return userDAO.getByEmail(email);
    }
}
