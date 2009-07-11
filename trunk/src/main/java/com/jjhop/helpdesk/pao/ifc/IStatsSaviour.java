package com.jjhop.helpdesk.pao.ifc;

import java.util.Map;

public interface IStatsSaviour {
	void setSaviourId( String sav );
	Map getStatsForCurrentWeek();
	Map getStatsForCurrentMonth();
	Map getStatsForThreePreviousMonths();
	Map getStatsForLastYear();
	Map getStatsForAll();
	
	Map getFullStats();
}
