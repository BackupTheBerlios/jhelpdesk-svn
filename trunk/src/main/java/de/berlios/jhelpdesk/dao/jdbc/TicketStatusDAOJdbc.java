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
package de.berlios.jhelpdesk.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.TicketStatusDAO;
import de.berlios.jhelpdesk.model.TicketStatus;

@Repository("ticketStatusDAO") // TODO: status jest enumem, do kosza z tym
public class TicketStatusDAOJdbc implements TicketStatusDAO {

    public List<TicketStatus> getAllStatuses() {
        List<TicketStatus> list2Return = new ArrayList<TicketStatus>(4);
        list2Return.add(TicketStatus.NOTIFIED);
        list2Return.add(TicketStatus.ASSIGNED);
        list2Return.add(TicketStatus.REJECTED);
        list2Return.add(TicketStatus.RESOLVED);
        list2Return.add(TicketStatus.CLOSED);
        return list2Return;
    }

    public List<TicketStatus> getNonOpenedStatuses() {
        List<TicketStatus> list2Return = new ArrayList<TicketStatus>(2);
        list2Return.add(TicketStatus.NOTIFIED);
        list2Return.add(TicketStatus.ASSIGNED);
        return list2Return;
    }

    public TicketStatus getById(Long id) {
        return getById(id.intValue());
    }

    public TicketStatus getById(int id) {
        return TicketStatus.fromInt(id);
    }
}
