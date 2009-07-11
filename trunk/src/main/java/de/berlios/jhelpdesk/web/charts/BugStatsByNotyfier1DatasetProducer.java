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


import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.User;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.PieSectionLinkGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;
import de.laures.cewolf.tooltips.ToolTipGenerator;

public class BugStatsByNotyfier1DatasetProducer  implements DatasetProducer, PieToolTipGenerator,
		PieSectionLinkGenerator, PieSectionLabelGenerator, ToolTipGenerator, Serializable {

	private static final long serialVersionUID = 2552236036081467352L;
	private static final Log log = LogFactory.getLog(BugStatsByNotyfier1DatasetProducer.class);
    private BugDAO bugDAO;
    private UserDAO userDAO;

	/**
	 * @param userDAO The userDAO to set.
	 */
	public void setUserDAO( UserDAO userDAO ) {
		log.info( "Ustawiam userDAO w 1..." );
		this.userDAO = userDAO;
		log.info( "...ustawione." );
	}

	/**
	 * @param bugDAO The bugDAO to set.
	 */
	public void setBugDAO(BugDAO bugDAO) {
		log.info( "Ustawiam bugDAO w 1..." );
		this.bugDAO = bugDAO;
		log.info( "...ustawione." );
	}

	public Object produceDataset(Map arg0) throws DatasetProduceException {
		DefaultPieDataset dataset = new DefaultPieDataset();

		List<User> listOfUsers = userDAO.getAllUser();
		
		for( User user : listOfUsers ) {
			int numOfBugs = bugDAO.getBugsNotifyiedByUser( user ).size();
			log.info( "Ilosc bledow [" + user.getFullName() + "] => " + bugDAO.getBugsNotifyiedByUser( user ).size() );
			if( numOfBugs > 0 ) {
				dataset.setValue( user.getFullName(), (double)numOfBugs );
			}
		}
		return dataset;
	}

	public boolean hasExpired(Map arg0, Date arg1) {
		return false;
	}

	public String getProducerId() {
		return null;
	}

	public String generateToolTip(PieDataset arg0, Comparable arg1, int arg2) {
		return null;
	}

	public String generateLink(Object arg0, Object arg1) {
		return null;
	}

	public String generateSectionLabel(PieDataset arg0, Comparable arg1) {
		return null;
	}

    public AttributedString generateAttributedSectionLabel(PieDataset arg0, Comparable arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
