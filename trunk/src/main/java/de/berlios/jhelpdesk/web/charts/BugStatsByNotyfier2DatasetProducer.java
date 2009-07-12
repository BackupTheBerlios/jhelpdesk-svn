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

import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.dao.BugStatusDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Bug;
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.model.User;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.PieSectionLinkGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;
import de.laures.cewolf.tooltips.ToolTipGenerator;

public class BugStatsByNotyfier2DatasetProducer implements DatasetProducer, PieToolTipGenerator,
		PieSectionLinkGenerator, PieSectionLabelGenerator, ToolTipGenerator, Serializable {

	private static final long serialVersionUID = -6385829065111292806L;
	private static final Log log = LogFactory.getLog(BugStatsByNotyfier2DatasetProducer.class);

	private UserDAO userDAO;
	private BugStatusDAO statusDAO;
	private BugDAO bugDAO;

	/**
	 * @param statusDAO The statusDAO to set.
	 */
	public void setStatusDAO(BugStatusDAO statusDAO) {
		log.debug("Ustawiam statusDAO w 2...");
		this.statusDAO = statusDAO;
		log.debug("...ustawione.");
	}

	/**
	 * @param userDAO The userDAO to set.
	 */
	public void setUserDAO(UserDAO userDAO) {
		log.debug("Ustawiam userDAO w 2...");
		this.userDAO = userDAO;
		log.debug("...ustawione.");
	}

	/**
	 * @param bugDAO The bugDAO to set.
	 */
	public void setBugDAO(BugDAO bugDAO) {
		log.debug("Ustawiam bugDAO w 2...");
		this.bugDAO = bugDAO;
		log.debug("...ustawione.");
	}

	public Object produceDataset(@SuppressWarnings("unchecked") Map arg0) throws DatasetProduceException {
		DefaultPieDataset dataset = new DefaultPieDataset();

		List<BugStatus> listOfStatuses = statusDAO.getNonOpenedStatuses();
		List<Bug> listOfBugs = new ArrayList<Bug>();
		for (BugStatus status : listOfStatuses) {
			listOfBugs.addAll(bugDAO.getBugsByStatus(status));
			log.debug("Nowy rozmiar listy => " + listOfBugs.size());
		}

		List<User> listOfUsers = userDAO.getAllUser();
		log.debug("Ilosc uzytkownikow w liscie -> " + listOfUsers.size());

		for (User user : listOfUsers) {
			int numOfBugs = 0;
			for (Bug bug : listOfBugs)
				if (bug.getNotifier().getUserId().longValue() == user.getUserId().longValue())
					numOfBugs++;
			log.debug("Ilosc bledow [" + user.getFullName() + "] => " + numOfBugs);
			if (numOfBugs > 0)
				dataset.setValue(user.getFullName(), numOfBugs);
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
