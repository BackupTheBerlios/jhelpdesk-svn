package de.berlios.jhelpdesk.web.charts;

import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.util.Rotation;

import de.laures.cewolf.ChartPostProcessor;

public class HDChartPostProcessor implements ChartPostProcessor {

	public void processChart( Object chart, Map args ) {
		JFreeChart jfreechart = (JFreeChart)chart;
		PiePlot3D pie3dplot = (PiePlot3D)jfreechart.getPlot();
		pie3dplot.setStartAngle(270D);
		pie3dplot.setDirection(Rotation.CLOCKWISE);
		pie3dplot.setForegroundAlpha(0.5F);
	}
}
