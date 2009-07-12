package de.berlios.jhelpdesk.pao;

import java.util.Map;

public interface StatsPriorityPAO {
	Map<String, Long> getStatsForCurrentWeek();
	Map<String, Long> getStatsForCurrentMonth();
	Map<String, Long> getStatsForThreePreviousMonths();
	Map<String, Long> getStatsForLastYear();
	Map<String, Long> getStatsForAll();	
}
