package com.jjhop.helpdesk.web.charts;

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

public class BugStatsByPriorityDatasetProducer implements DatasetProducer,
		PieToolTipGenerator, PieSectionLinkGenerator, PieSectionLabelGenerator,
		ToolTipGenerator, Serializable {

	private static final long serialVersionUID = 5634162045009465748L;
	
	private Map<String,Long> chartData;
	
	 @SuppressWarnings("unchecked")
	public BugStatsByPriorityDatasetProducer( Map chartData ) {
    	this.chartData = chartData;
    }

	public Object produceDataset(Map arg0) throws DatasetProduceException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for( String key : chartData.keySet() ) {
			dataset.setValue(  key, chartData.get( key ) );
		}
		return dataset;
	}

	public boolean hasExpired(Map arg0, Date arg1) {
		return false;
	}

	public String getProducerId() {
		return "BugStatsByPriorityDatasetProducer DatasetProducer";
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