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

import java.util.Date;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.mail.MailMessage;

import org.springframework.stereotype.Component;

/**
 *
 * @author jjhop
 */
@Component("ticketFromEmailProcessor")
public class TicketFromEmailProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        MailMessage in = exchange.getIn(MailMessage.class);
        System.out.println("Przyszła wiadomość:");
        for (Map.Entry<String, Object> e : in.getHeaders().entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }
        System.out.println("-------");
        System.out.println("b:" + in.getMessage());
        System.out.println("bClass:" + in.getMessage().getClass().getCanonicalName());
        
        if (in.hasAttachments()) {
            for (String attachmentName : in.getAttachmentNames()) {
                System.out.println("attachment: " + attachmentName);
            }
        }
        System.out.println("b:" + in.getBody());
        System.out.println("--END--");
        System.out.println(new Date() + " [[[" + exchange.getIn().toString() + "]]]");

    }
}
