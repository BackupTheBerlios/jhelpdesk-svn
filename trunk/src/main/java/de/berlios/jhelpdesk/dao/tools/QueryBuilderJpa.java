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
package de.berlios.jhelpdesk.dao.tools;

import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.web.form.ShowTicketsFilterForm;

/**
 *
 * @author jjhop
 */
public class QueryBuilderJpa {

    private ShowTicketsFilterForm filter;
    private EntityManager em;

    public QueryBuilderJpa() {
    }

    public QueryBuilderJpa(ShowTicketsFilterForm filterForm, EntityManager em) {
        this.filter = filterForm;
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

        if (!countOnly) {
            sb2.append("SELECT t FROM Ticket AS t WHERE ");
        } else {
            sb2.append("SELECT COUNT(t) FROM Ticket AS t WHERE ");
        }

        if ((filter.getStartDate() != null) && (filter.getStartDate().length() > 0)) {
            sb2.append(" t.createDate >= :startDate AND ");
            startDateExists = true;
        }

        if ((filter.getEndDate() != null) && (filter.getEndDate().length() > 0)) {
            sb2.append(" t.createDate <= :endDate AND ");
            endDateExists = true;
        }

        if ((filter.getCategories() != null) && (filter.getCategories().size() > 0)) {
            sb2.append(" t.ticketCategory IN (:ticketCategories) AND ");
            categoriesExists = true;
        }

        if ((filter.getNotifyiers() != null) && (filter.getNotifyiers().size() > 0)) {
            sb2.append(" t.notifier IN (:notifiers) AND ");
            notifiersExists = true;
        }

        if ((filter.getSaviours() != null) && (filter.getSaviours().size() > 0)) {
            sb2.append(" t.saviour IN (:saviours) AND ");
            savioursExists = true;
        }

        if ((filter.getPriorities() != null) && (filter.getPriorities().size() > 0)) {
            sb2.append(" t.ticketPriorityAsInt IN (:priorities) AND ");
            prioritiesExists = true;
        }

        if ((filter.getStatuses() != null) && (filter.getStatuses().size() > 0)) {
            sb2.append(" t.ticketStatusAsInt IN (:statuses) AND ");
            statusesExists = true;
        }

        sb2.append(" t.ticketId IS NOT NULL ");

        if (!countOnly) {
            sb2.append(" ORDER BY t.createDate DESC"); //  LIMIT ? OFFSET ?
        }
        Query q = em.createQuery(sb2.toString());
        DateFormat df = DateFormat.getDateInstance();
        try {
            if (startDateExists) {
                q.setParameter("startDate", df.parse(filter.getStartDate()), TemporalType.DATE);
            }
            if (endDateExists) {
                q.setParameter("endDate", df.parse(filter.getEndDate()), TemporalType.DATE);
            }
        } catch (ParseException pex) {
            throw new RuntimeException(pex);
        }
        if (categoriesExists) {
            q.setParameter("ticketCategories", filter.getCategories());
        }
        if (notifiersExists) {
            q.setParameter("notifiers", filter.getNotifyiers());
        }
        if (savioursExists) {
            q.setParameter("saviours", filter.getSaviours());
        }
        if (prioritiesExists) {
            List<Integer> ps = new ArrayList<Integer>();
            for (TicketPriority p : filter.getPriorities()) {
                ps.add(p.toInt());
            }
            q.setParameter("priorities", ps);
        }

        if (statusesExists) {
            List<Integer> statuses = new ArrayList<Integer>();
            for (TicketStatus s : filter.getStatuses()) {
                statuses.add(s.toInt());
            }
            q.setParameter("statuses", statuses);
        }
        return q;
    }

    public String getAllQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ticket_list_view ORDER BY b_create_date DESC");
        return sb.toString();
    }
}
