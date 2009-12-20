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
package de.berlios.jhelpdesk.web.charts;

import java.io.Serializable;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.PieSectionLinkGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;
import de.laures.cewolf.tooltips.ToolTipGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import de.berlios.jhelpdesk.dao.DataAccessException;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;

@Component("pieChartTicketsByNotyfier2")
public class TicketStatsByNotyfier2DatasetProducer implements DatasetProducer, PieToolTipGenerator,
        PieSectionLinkGenerator, PieSectionLabelGenerator, ToolTipGenerator, Serializable {

    private static final long serialVersionUID = -6385829065111292806L;
    private static final Log log = LogFactory.getLog(TicketStatsByNotyfier2DatasetProducer.class);

    @Autowired
    @Qualifier("jdbc")
    private UserDAO userDAO;
    
    @Autowired
    private TicketDAO ticketDAO;

    public Object produceDataset(@SuppressWarnings("unchecked") Map arg0) throws DatasetProduceException {
        DefaultPieDataset dataset = new DefaultPieDataset();

        TicketStatus[] listOfStatuses = TicketStatus.getNonOpenedStatuses();
        List<Ticket> listOfTickets = new ArrayList<Ticket>();
        try {
            for (TicketStatus status : listOfStatuses) {
                listOfTickets.addAll(ticketDAO.getTicketsByStatus(status));
                log.debug("Nowy rozmiar listy => " + listOfTickets.size());
            }
        } catch (DataAccessException ex) {
            // TODO: moze cos z tym lepszego zrobic?
            throw new DatasetProduceException(ex.getMessage());
        }

        List<User> listOfUsers = userDAO.getAllUser();
        log.debug("Ilosc uzytkownikow w liscie -> " + listOfUsers.size());

        for (User user : listOfUsers) {
            int numOfTickets = 0;
            for (Ticket ticket : listOfTickets) {
                if (ticket.getNotifier().getUserId().longValue() == user.getUserId().longValue()) {
                    numOfTickets++;
                }
            }
            log.debug("Ilosc bledow [" + user.getFullName() + "] => " + numOfTickets);
            if (numOfTickets > 0) {
                dataset.setValue(user.getFullName(), numOfTickets);
            }
        }
        return dataset;
    }

    public boolean hasExpired(@SuppressWarnings("unchecked") Map arg0, Date arg1) {
        return false;
    }

    public String getProducerId() {
        return null;
    }

    public String generateToolTip(PieDataset arg0, @SuppressWarnings("unchecked") Comparable arg1, int arg2) {
        return null;
    }

    public String generateLink(Object arg0, Object arg1) {
        return null;
    }

    public String generateSectionLabel(PieDataset arg0, @SuppressWarnings("unchecked") Comparable arg1) {
        return null;
    }

    public AttributedString generateAttributedSectionLabel(PieDataset arg0,
            @SuppressWarnings("unchecked") Comparable arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
