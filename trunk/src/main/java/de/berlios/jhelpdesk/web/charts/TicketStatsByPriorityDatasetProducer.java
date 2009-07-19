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
import java.util.Map;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.PieSectionLinkGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;
import de.laures.cewolf.tooltips.ToolTipGenerator;

public class TicketStatsByPriorityDatasetProducer implements DatasetProducer, PieToolTipGenerator,
		PieSectionLinkGenerator, PieSectionLabelGenerator, ToolTipGenerator, Serializable {

	private static final long serialVersionUID = 5634162045009465748L;

	private Map<String, Long> chartData;

	@SuppressWarnings("unchecked")
	public TicketStatsByPriorityDatasetProducer(Map chartData) {
		this.chartData = chartData;
	}

	public Object produceDataset(@SuppressWarnings("unchecked") Map arg0) throws DatasetProduceException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String key : chartData.keySet()) {
			dataset.setValue(key, chartData.get(key));
		}
		return dataset;
	}

	public boolean hasExpired(@SuppressWarnings("unchecked") Map arg0, Date arg1) {
		return false;
	}

	public String getProducerId() {
		return "TicketStatsByPriorityDatasetProducer DatasetProducer";
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