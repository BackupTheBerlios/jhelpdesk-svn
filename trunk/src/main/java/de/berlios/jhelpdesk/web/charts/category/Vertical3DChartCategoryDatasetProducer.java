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
package de.berlios.jhelpdesk.web.charts.category;

import java.util.Date;
import java.util.Map;

import org.jfree.data.category.DefaultIntervalCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

public class Vertical3DChartCategoryDatasetProducer implements DatasetProducer {

	private static final long serialVersionUID = 2099638445614051852L;

	private Map<String, Long> data;

	public Vertical3DChartCategoryDatasetProducer() {}

	public Vertical3DChartCategoryDatasetProducer(Map<String, Long> data) {
		this.data = data;
	}

	public Object produceDataset(@SuppressWarnings("unchecked") Map params)
			throws DatasetProduceException {
		// TODO: zlokalizować nazwy dni tygodnia
		final String[] weekDays = { "Pon.", "Wt.", "Śr.", "Czw.", "Pią.", "Sob.", "Ndz." };
		final String[] categoryNames = { "Peter", "Helga", "Franz", "Olga" };
		final Integer[][] startValues = new Integer[categoryNames.length][weekDays.length];
		final Integer[][] endValues = new Integer[categoryNames.length][weekDays.length];
		for (int series = 0; series < categoryNames.length; series++) {
			for (int i = 0; i < weekDays.length; i++) {
				int y = (int) (Math.random() * 10 + 1);
				startValues[series][i] = new Integer(y);
				endValues[series][i] = new Integer(y + (int) (Math.random() * 10));
			}
		}
		DefaultIntervalCategoryDataset ds = 
			new DefaultIntervalCategoryDataset(categoryNames, weekDays, 
					startValues, endValues);
		return ds;
	}

	public boolean hasExpired(@SuppressWarnings("unchecked") Map params, Date date) {
		return false;
	}

	public String getProducerId() {
		return "" + serialVersionUID;
	}
}
