package de.berlios.jhelpdesk.pao.ifc;

import java.util.Map;

public interface IStatsPriority {
	Map<String, Long> getStatsForCurrentWeek();
	Map<String, Long> getStatsForCurrentMonth();
	Map<String, Long> getStatsForThreePreviousMonths();
	Map<String, Long> getStatsForLastYear();
	Map<String, Long> getStatsForAll();	
}
