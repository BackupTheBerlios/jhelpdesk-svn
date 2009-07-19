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

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.jfree.data.category.DefaultCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

public class TicketsStatsByCategoryFullDatasetProducer implements DatasetProducer {

	private static final long serialVersionUID = -5846059145088380422L;
	// private Map<String,Long> chartData;
	private SortedSet<Entry<String, Long>> ss;

	public TicketsStatsByCategoryFullDatasetProducer(Map<String, Long> chartData) {
		// this.chartData = chartData;
		ss = new TreeSet<Entry<String, Long>>(new Comparator<Entry<String, Long>>() {
			public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		ss.addAll(chartData.entrySet());
	}

	public String getProducerId() {
		return String.valueOf(serialVersionUID);
	}

	public boolean hasExpired(@SuppressWarnings("unchecked") Map arg0, Date date) {
		// TODO: należy zaimplemetowac wygasanie
		return true;
	}

	public Object produceDataset(@SuppressWarnings("unchecked") Map arg0) throws DatasetProduceException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int count = 0;
		long otherSum = 0;
		for (Iterator<Entry<String, Long>> it = ss.iterator(); it.hasNext(); count++) {
			Entry<String, Long> entry = it.next();
			if (count < 10)
				dataset.addValue(entry.getValue(), "", entry.getKey());
			else
				otherSum += entry.getValue();
		}
		if (count > 10)
			dataset.addValue(otherSum, "", "Pozostałe...");
		return dataset;
	}
}
