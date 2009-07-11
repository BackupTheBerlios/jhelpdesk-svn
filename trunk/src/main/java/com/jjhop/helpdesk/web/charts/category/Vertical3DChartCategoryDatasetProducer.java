package com.jjhop.helpdesk.web.charts.category;

import java.util.Date;
import java.util.Map;

import org.jfree.data.category.DefaultIntervalCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

public class Vertical3DChartCategoryDatasetProducer implements DatasetProducer {

	private static final long serialVersionUID = 2099638445614051852L;
	
	private Map data;
	
	public Vertical3DChartCategoryDatasetProducer() {}
	
	public Vertical3DChartCategoryDatasetProducer( Map data ) {
		this.data = data;
	}

	public Object produceDataset( Map params ) throws DatasetProduceException {
		// TODO: zlokaizować nazwy dni tygodnia
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
			new DefaultIntervalCategoryDataset( categoryNames, weekDays, startValues, endValues );
		return ds;
	}

	public boolean hasExpired( Map params, Date date ) {
		return false;
	}

	public String getProducerId() {
		return "" + serialVersionUID;
	}
}
