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

import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.util.Rotation;

import de.laures.cewolf.ChartPostProcessor;

public class HDChartPostProcessor implements ChartPostProcessor {

	public void processChart(Object chart, @SuppressWarnings("unchecked") Map args) {
		JFreeChart jfreechart = (JFreeChart) chart;
		PiePlot3D pie3dplot = (PiePlot3D) jfreechart.getPlot();
		pie3dplot.setStartAngle(270D);
		pie3dplot.setDirection(Rotation.CLOCKWISE);
		pie3dplot.setForegroundAlpha(0.5F);
	}
}
