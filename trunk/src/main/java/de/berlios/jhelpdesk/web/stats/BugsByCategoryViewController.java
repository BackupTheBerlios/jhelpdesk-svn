package de.berlios.jhelpdesk.web.stats;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.pao.StatsCategoryPAO;
import de.berlios.jhelpdesk.web.charts.BugStatsByCategoryDatasetProducer;
import de.berlios.jhelpdesk.web.charts.BugsStatsByCategoryFullDatasetProducer;
import de.berlios.jhelpdesk.web.charts.category.Vertical3DChartCategoryDatasetProducer;

public class BugsByCategoryViewController implements Controller {
	
	private static Log log = LogFactory.getLog(BugsByCategoryViewController.class);
	private StatsCategoryPAO statsPAO;

	public void setStatsPAO(StatsCategoryPAO statsPAO) {
		this.statsPAO = statsPAO;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("handleRequest()");
		ModelAndView mav = new ModelAndView("stats/bugsByCategory");

		TreeMap<String, Long> currentWeekMap = new TreeMap<String, Long>(statsPAO.getStatsForCurrentWeek());
		TreeMap<String, Long> currentMonthMap = new TreeMap<String, Long>(statsPAO.getStatsForCurrentMonth());
		TreeMap<String, Long> threeMonthsMap = new TreeMap<String, Long>(statsPAO.getStatsForThreePreviousMonths());
		Map<String, Long> allMap = rebuildMap(statsPAO.getStatsForAll(), 9);

		mav.addObject("currentWeekData", currentWeekMap);
		mav.addObject("currentMonthData", currentMonthMap);
		mav.addObject("threeMonthsData", threeMonthsMap);
		mav.addObject("allData", allMap);

		mav.addObject("currentWeek", new BugStatsByCategoryDatasetProducer(currentWeekMap));
		mav.addObject("currentWeekDayByDayData", new Vertical3DChartCategoryDatasetProducer(currentWeekMap));

		mav.addObject("currentMonth", new BugStatsByCategoryDatasetProducer(currentMonthMap));
		mav.addObject("currentMonthWeekByWeekData", new Vertical3DChartCategoryDatasetProducer(currentWeekMap));

		mav.addObject("threeMonths", new BugStatsByCategoryDatasetProducer(threeMonthsMap));
		mav.addObject("threeMonthsMonthByMonth", new Vertical3DChartCategoryDatasetProducer(threeMonthsMap));

		mav.addObject("allTime", new BugStatsByCategoryDatasetProducer(allMap));
		mav.addObject("allTimeFull", new BugsStatsByCategoryFullDatasetProducer(allMap));
		return mav;
	}

	private static Map<String, Long> rebuildMap(Map<String, Long> entryMap, int maxSize) {
		Map<String, Long> newMapToReturn = new LinkedHashMap<String, Long>();

		SortedSet<Entry<String, Long>> ss = new TreeSet<Entry<String, Long>>(new CustomComparator());
		ss.addAll(entryMap.entrySet());
		int count = 0;
		long otherSum = 0;

		for (Iterator<Entry<String, Long>> it = ss.iterator(); it.hasNext(); count++) {
			Entry<String, Long> entry = it.next();
			if (count < maxSize)
				newMapToReturn.put(entry.getKey(), entry.getValue());
			else
				otherSum += entry.getValue();
		}
		if (count > maxSize)
			newMapToReturn.put("Pozostałe...", otherSum);

		return newMapToReturn;
	}

	private static class CustomComparator implements Comparator<Entry<String, Long>> {
		public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
			return o2.getValue().compareTo(o1.getValue());
		}
	}
}
