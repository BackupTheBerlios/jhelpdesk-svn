package de.berlios.jhelpdesk.pao;

import java.util.Map;

public interface StatsSaviourPAO {
	void setSaviourId( String sav );
	Map getStatsForCurrentWeek();
	Map getStatsForCurrentMonth();
	Map getStatsForThreePreviousMonths();
	Map getStatsForLastYear();
	Map getStatsForAll();
	
	Map getFullStats();
}
