package com.jjhop.helpdesk.web.stats;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jjhop.helpdesk.pao.ifc.IStatsPriority;
import com.jjhop.helpdesk.web.charts.BugStatsByPriorityDatasetProducer;

public class BugsByPriorityViewController implements Controller {
	private static Log log = LogFactory.getLog( BugsByPriorityViewController.class );
	private IStatsPriority statsPAO;

	public void setStatsPAO( IStatsPriority statsPAO ) {
		this.statsPAO = statsPAO;
	}	
	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("handleRequest()");
		ModelAndView mav = new ModelAndView( "stats/bugsByPriority" );
		
		TreeMap currentWeekMap  = new TreeMap( statsPAO.getStatsForCurrentWeek() );
		TreeMap currentMonthMap = new TreeMap( statsPAO.getStatsForCurrentMonth() );
		TreeMap threeMonthsMap  = new TreeMap( statsPAO.getStatsForThreePreviousMonths() );
		TreeMap allMap          = new TreeMap( statsPAO.getStatsForAll() );
		log.info( "currentWeekMap[data] -> ilosc = " + currentWeekMap.size() );
		
		mav.addObject( "currentWeekData", currentWeekMap );
		mav.addObject( "currentMonthData", currentMonthMap );
		mav.addObject( "threeMonthsData", threeMonthsMap );
		mav.addObject( "allData", allMap );
		
		mav.addObject( "currentWeek", new BugStatsByPriorityDatasetProducer( currentWeekMap ) );
		mav.addObject( "currentMonth", new BugStatsByPriorityDatasetProducer( currentMonthMap ) );
		mav.addObject( "threeMonth", new BugStatsByPriorityDatasetProducer( threeMonthsMap ) );
		mav.addObject( "allTime", new BugStatsByPriorityDatasetProducer( allMap ) );
		
		return mav;
	}
}