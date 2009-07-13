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
package de.berlios.jhelpdesk.web.stats;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.pao.StatsPriorityPAO;
import de.berlios.jhelpdesk.web.charts.BugStatsByPriorityDatasetProducer;

public class BugsByPriorityViewController implements Controller {
	
	private static Log log = LogFactory.getLog(BugsByPriorityViewController.class);

    @Autowired
	private StatsPriorityPAO statsPAO;
	
	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.info("handleRequest()");
		ModelAndView mav = new ModelAndView("stats/bugsByPriority");
		
		TreeMap currentWeekMap = new TreeMap(statsPAO.getStatsForCurrentWeek());
		TreeMap currentMonthMap = new TreeMap(statsPAO.getStatsForCurrentMonth());
		TreeMap threeMonthsMap = new TreeMap(statsPAO.getStatsForThreePreviousMonths());
		TreeMap allMap = new TreeMap(statsPAO.getStatsForAll());
		log.info("currentWeekMap[data] -> ilosc = " + currentWeekMap.size());
		
		mav.addObject("currentWeekData", currentWeekMap);
		mav.addObject("currentMonthData", currentMonthMap);
		mav.addObject("threeMonthsData", threeMonthsMap);
		mav.addObject("allData", allMap);
		
		mav.addObject("currentWeek", new BugStatsByPriorityDatasetProducer(currentWeekMap));
		mav.addObject("currentMonth", new BugStatsByPriorityDatasetProducer(currentMonthMap));
		mav.addObject("threeMonth", new BugStatsByPriorityDatasetProducer(threeMonthsMap));
		mav.addObject("allTime", new BugStatsByPriorityDatasetProducer(allMap));
		
		return mav;
	}
}