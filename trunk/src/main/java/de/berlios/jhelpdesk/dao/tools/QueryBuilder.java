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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.form.ShowTicketsFilterForm;


/**
 *
 * @author jjhop
 */
public class QueryBuilder {

    private static Log log = LogFactory.getLog(QueryBuilder.class);

    private ShowTicketsFilterForm filter;

    public QueryBuilder() {
    }

    public QueryBuilder(ShowTicketsFilterForm filterForm) {
        this.filter = filterForm;
    }

    public String getFilteredQuery(boolean countOnly) {

        StringBuffer sb = new StringBuffer();

        StringBuffer sb2 = new StringBuffer();
        
        if (!countOnly) {
            sb.append("SELECT * FROM ticket_list_view WHERE ");
            sb2.append("SELECT t FROM Ticket AS t WHERE ");
        } else {
            sb.append("SELECT COUNT(*) FROM ticket_list_view WHERE ");
            sb2.append("SELECT COUNT(t) FROM Ticket AS t WHERE ");
        }

        if ((filter.getStartDate() != null) && (filter.getStartDate().length() > 0)) {
            sb.append(" b_create_date >= '" + filter.getStartDate() + "' AND ");
            sb2.append(" t.createDate >= :startDate AND ");
        }

        if ((filter.getEndDate() != null) && (filter.getEndDate().length() > 0)) {
            sb.append(" b_create_date <= '" + filter.getEndDate() + "' AND ");
            sb2.append(" t.createDate <= :endDate AND ");
        }

        if ((filter.getCategories() != null) && (filter.getCategories().size() > 0)) {
            String cat_ = "";
            for (TicketCategory cat : filter.getCategories()) {
                cat_ += cat.getTicketCategoryId().intValue() + ",";
            }
            cat_ += "-1";
            sb.append(" c_id IN(" + cat_ + ") AND ");
            sb2.append(" t.ticketCategory IN (:ticketCategories) AND ");
        }

        if ((filter.getNotifyiers() != null) && (filter.getNotifyiers().size() > 0)) {
            String not_ = "";
            for (User user : filter.getNotifyiers()) {
                not_ += user.getUserId().intValue() + ",";
            }
            not_ += "-1";
            sb.append(" n_id IN(" + not_ + ") AND ");
            sb2.append(" t.notifier IN (:notifiers) AND ");
        }

        if ((filter.getSaviours() != null) && (filter.getSaviours().size() > 0)) {
            String sav_ = "";
            for (User user : filter.getSaviours()) {
                sav_ += user.getUserId().intValue() + ",";
            }
            sav_ += "-1";
            sb.append(" s_id IN(" + sav_ + ") AND ");
            sb2.append(" t.saviour IN (:saviours) AND ");
        }

        if ((filter.getPriorities() != null) && (filter.getPriorities().size() > 0)) {
            String priors_ = "";
            for (TicketPriority pr : filter.getPriorities()) {
                priors_ += pr.toInt() + ",";
            }
            priors_ += "-1";
            sb.append(" p_id IN(" + priors_ + ") AND ");
            sb2.append(" t.ticketPriorityAsInt IN (" + priors_ + ") AND ");
        }

        if ((filter.getStatuses() != null) && (filter.getStatuses().size() > 0)) {
            String stat_ = "";
            for (TicketStatus stat : filter.getStatuses()) {
                stat_ += stat.getStatusId() + ",";
            }
            stat_ += "-1";
            sb.append(" b_status IN(" + stat_ + ") AND ");
            sb2.append(" t.ticketStatusAsInt IN (" + stat_ + ") AND ");
        }

        sb.append(" true ");
        sb2.append(" true ");
        if (!countOnly) {
            sb.append("ORDER BY b_create_date DESC LIMIT ? OFFSET ?");
        }

        log.debug(sb.toString());
        return sb.toString();
    }

    public String getAllQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ticket_list_view ORDER BY b_create_date DESC");
        return sb.toString();
    }
}
