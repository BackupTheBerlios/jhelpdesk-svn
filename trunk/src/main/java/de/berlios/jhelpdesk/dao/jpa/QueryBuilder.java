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
package de.berlios.jhelpdesk.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import de.berlios.jhelpdesk.model.TicketFilter;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;

/**
 *
 * @author jjhop
 */
class QueryBuilder {

    private TicketFilter ticketFilter;
    private EntityManager em;

    public QueryBuilder(TicketFilter filter, EntityManager em) {
        this.ticketFilter = filter;
        this.em = em;
    }

    public Query getFilteredQuery(boolean countOnly) {
        boolean startDateExists = false;
        boolean endDateExists = false;
        boolean categoriesExists = false;
        boolean notifiersExists = false;
        boolean savioursExists = false;
        boolean prioritiesExists = false;
        boolean statusesExists = false;

        StringBuffer sb2 = new StringBuffer();

        sb2.append(
            countOnly
                ? "SELECT COUNT(t) FROM Ticket AS t WHERE "
                : "SELECT t FROM Ticket AS t WHERE "
        );

        if (ticketFilter.getBeginDate() != null) {
            sb2.append(" t.createDate >= :startDate AND ");
            startDateExists = true;
        }

        if (ticketFilter.getEndDate() != null) {
            sb2.append(" t.createDate <= :endDate AND ");
            endDateExists = true;
        }

        if (ticketFilter.getTicketCategories() != null
                && ticketFilter.getTicketCategories().size() > 0) {
            sb2.append(" t.ticketCategory IN (:ticketCategories) AND ");
            categoriesExists = true;
        }

        if (ticketFilter.getNotifiers() != null
                && ticketFilter.getNotifiers().size() > 0) {
            sb2.append(" t.notifier IN (:notifiers) AND ");
            notifiersExists = true;
        }

        if (ticketFilter.getSaviours() != null
                && ticketFilter.getSaviours().size() > 0) {
            sb2.append(" t.saviour IN (:saviours) AND ");
            savioursExists = true;
        }

        if (ticketFilter.getTicketPriorities() != null
                && ticketFilter.getTicketPriorities().size() > 0) {
            sb2.append(" t.ticketPriorityAsInt IN (:priorities) AND ");
            prioritiesExists = true;
        }

        if (ticketFilter.getTicketStatuses() != null
                && ticketFilter.getTicketStatuses().size() > 0) {
            sb2.append(" t.ticketStatusAsInt IN (:statuses) AND ");
            statusesExists = true;
        }

        sb2.append(" t.ticketId IS NOT NULL ");

        if (!countOnly) {
            sb2.append(" ORDER BY t.createDate DESC");
        }

        Query q = em.createQuery(sb2.toString());

        if (startDateExists) {
            q.setParameter("startDate", ticketFilter.getBeginDate(), TemporalType.DATE);
        }
        if (endDateExists) {
            q.setParameter("endDate", ticketFilter.getEndDate(), TemporalType.DATE);
        }

        if (categoriesExists) {
            q.setParameter("ticketCategories", ticketFilter.getTicketCategories());
        }

        if (notifiersExists) {
            q.setParameter("notifiers", ticketFilter.getNotifiers());
        }

        if (savioursExists) {
            q.setParameter("saviours", ticketFilter.getSaviours());
        }
        if (prioritiesExists) {
            List<Integer> ps = new ArrayList<Integer>();
            for (TicketPriority p : ticketFilter.getTicketPriorities()) {
                ps.add(p.toInt());
            }
            q.setParameter("priorities", ps);
        }

        if (statusesExists) {
            List<Integer> statuses = new ArrayList<Integer>();
            for (TicketStatus s : ticketFilter.getTicketStatuses()) {
                statuses.add(s.toInt());
            }
            q.setParameter("statuses", statuses);
        }
        return q;
    }
}
