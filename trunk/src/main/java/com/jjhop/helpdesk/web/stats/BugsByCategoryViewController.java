package com.jjhop.helpdesk.web.stats;

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

import com.jjhop.helpdesk.pao.ifc.IStatsCategory;
import com.jjhop.helpdesk.web.charts.BugStatsByCategoryDatasetProducer;
import com.jjhop.helpdesk.web.charts.BugsStatsByCategoryFullDatasetProducer;
import com.jjhop.helpdesk.web.charts.category.Vertical3DChartCategoryDatasetProducer;
       
public class BugsByCategoryViewController implements Controller {
	private static Log log = LogFactory.getLog( BugsByCategoryViewController.class );
	private IStatsCategory statsPAO;

	public void setStatsPAO( IStatsCategory statsPAO ) {
		this.statsPAO = statsPAO;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		log.debug("handleRequest()");
		ModelAndView mav = new ModelAndView("stats/bugsByCategory");
		
		TreeMap currentWeekMap  = new TreeMap( statsPAO.getStatsForCurrentWeek() );
		TreeMap currentMonthMap = new TreeMap( statsPAO.getStatsForCurrentMonth() );
		TreeMap threeMonthsMap  = new TreeMap( statsPAO.getStatsForThreePreviousMonths() );
		Map allMap = rebuildMap( statsPAO.getStatsForAll(), 9 );
			
		mav.addObject( "currentWeekData", currentWeekMap );
		mav.addObject( "currentMonthData", currentMonthMap );
		mav.addObject( "threeMonthsData", threeMonthsMap );
		mav.addObject( "allData", allMap );
				
		mav.addObject( "currentWeek", new BugStatsByCategoryDatasetProducer( currentWeekMap ) );
		mav.addObject( "currentWeekDayByDayData", new Vertical3DChartCategoryDatasetProducer( currentWeekMap ) );
		
		mav.addObject( "currentMonth", new BugStatsByCategoryDatasetProducer( currentMonthMap ) );
		mav.addObject( "currentMonthWeekByWeekData", new Vertical3DChartCategoryDatasetProducer( currentWeekMap ) );
		
		mav.addObject( "threeMonths", new BugStatsByCategoryDatasetProducer( threeMonthsMap ) );
		mav.addObject( "threeMonthsMonthByMonth", new Vertical3DChartCategoryDatasetProducer( threeMonthsMap ) );
		
		mav.addObject( "allTime", new BugStatsByCategoryDatasetProducer( allMap ) );
		mav.addObject( "allTimeFull", new BugsStatsByCategoryFullDatasetProducer( allMap) );
		return mav;
	}
	
	private static Map rebuildMap( Map<String, Long> entryMap, int maxSize ) {
		Map<String, Long> newMapToReturn = new LinkedHashMap<String, Long>();
		
		SortedSet<Entry<String,Long>> ss = new TreeSet<Entry<String,Long>>( new CustomComparator() );
		ss.addAll( entryMap.entrySet() );
		int count = 0;
		long otherSum = 0;
		
		for( Iterator<Entry<String,Long>> it = ss.iterator(); it.hasNext(); count++ ) {
			Entry<String,Long> entry = it.next();
			if( count < maxSize )
				newMapToReturn.put( entry.getKey(), entry.getValue() );
			else
				otherSum += entry.getValue();
		}
		if( count > maxSize )
			newMapToReturn.put( "Pozostałe...", otherSum );
		
		return newMapToReturn;
	}
	
	private static class CustomComparator implements Comparator<Entry<String, Long>> {
		public int compare( Entry<String, Long> o1, Entry<String, Long> o2 ) {
			return o2.getValue().compareTo( o1.getValue() );
		}
	}
}
