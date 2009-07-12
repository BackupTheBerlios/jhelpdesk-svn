package de.berlios.jhelpdesk.web.charts;

import java.io.Serializable;
import java.text.AttributedString;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.PieSectionLinkGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;
import de.laures.cewolf.tooltips.ToolTipGenerator;

public class BugStatsByCategoryDatasetProducer implements DatasetProducer, PieToolTipGenerator,
		PieSectionLinkGenerator, PieSectionLabelGenerator, ToolTipGenerator, Serializable {

	private static final long serialVersionUID = -5257467386158073984L;
	private static final Log log = LogFactory.getLog(BugStatsByCategoryDatasetProducer.class);
	// private final static String[] seriesNames = {"cewolfset.jsp", "tutorial.jsp", "testpage.jsp",
	// "performancetest.jsp"};

	private Map<String, Long> chartData;

	public BugStatsByCategoryDatasetProducer(Map<String, Long> chartData) {
		log.debug("");
		this.chartData = chartData;
	}

	public Object produceDataset(@SuppressWarnings("unchecked") Map params) throws DatasetProduceException {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (String key : chartData.keySet()) {
			dataset.setValue(key, chartData.get(key));
		}
		return dataset;
	}

	public boolean hasExpired(@SuppressWarnings("unchecked") Map params, Date since) {
		return (System.currentTimeMillis() - since.getTime()) > 5000;
	}

	public String getProducerId() {
		return "BugStatsByCategoryDatasetProducer DatasetProducer";
	}

	public String generateToolTip(CategoryDataset arg0, int series, int arg2) {
		return "";// seriesNames[series];
	}

	public String generateLink(Object arg0, int series, Object arg2) {
		return "";// seriesNames[series];
	}

	public String generateToolTip(PieDataset dataset, @SuppressWarnings("unchecked") Comparable key, int pieIndex) {
		return "jop";
	}

	public String generateLink(Object dataset, Object category) {
		return "showBugsByCategory.html?categoryId=" + category.toString();
	}

	public String generateSectionLabel(PieDataset arg0, @SuppressWarnings("unchecked") Comparable arg1) {
		return "section label";
	}

	public AttributedString generateAttributedSectionLabel(PieDataset arg0,
			@SuppressWarnings("unchecked") Comparable arg1) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
