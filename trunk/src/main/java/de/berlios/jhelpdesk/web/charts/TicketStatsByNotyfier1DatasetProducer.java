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
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.PieSectionLinkGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;
import de.laures.cewolf.tooltips.ToolTipGenerator;

import de.berlios.jhelpdesk.dao.DataAccessException;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.User;

@Component("pieChartTicketsByNotyfier1")
public class TicketStatsByNotyfier1DatasetProducer implements DatasetProducer, PieToolTipGenerator,
        PieSectionLinkGenerator, PieSectionLabelGenerator, ToolTipGenerator, Serializable {

    private static final long serialVersionUID = 2552236036081467352L;
    private static final Log log = LogFactory.getLog(TicketStatsByNotyfier1DatasetProducer.class);

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    @Qualifier("jdbc")
    private UserDAO userDAO;

    public Object produceDataset(@SuppressWarnings("unchecked") Map arg0) throws DatasetProduceException {
        DefaultPieDataset dataset = new DefaultPieDataset();

        List<User> listOfUsers = userDAO.getAllUser();
        try {
            for (User user : listOfUsers) {
                int numOfTickets = ticketDAO.getTicketsNotifyiedByUser(user).size();
                log.info("Ilosc bledow [" + user.getFullName() + "] => " + ticketDAO.getTicketsNotifyiedByUser(user).size());
                if (numOfTickets > 0) {
                    dataset.setValue(user.getFullName(), numOfTickets);
                }
            }
        } catch (DataAccessException ex) {
            throw new DatasetProduceException(ex.getMessage());
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
