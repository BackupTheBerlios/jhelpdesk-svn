package com.jjhop.helpdesk.pao.ifc;

import java.util.Map;

public interface IStatsPriority {
	Map getStatsForCurrentWeek();
	Map getStatsForCurrentMonth();
	Map getStatsForThreePreviousMonths();
	Map getStatsForLastYear();
	Map getStatsForAll();	
}
