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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import de.berlios.jhelpdesk.pao.StatsPriorityPAO;
import de.berlios.jhelpdesk.web.charts.BugStatsByPriorityDatasetProducer;

@Controller
public class BugsByPriorityViewController {

    private static Log log = LogFactory.getLog(BugsByPriorityViewController.class);

    @Autowired
    private StatsPriorityPAO statsPAO;

    @SuppressWarnings("unchecked")
    @RequestMapping("/stats/bugs/priority.html")
    public String handleRequest(ModelMap mav) {
        log.info("handleRequest()");

        TreeMap currentWeekMap = new TreeMap(statsPAO.getStatsForCurrentWeek());
        TreeMap currentMonthMap = new TreeMap(statsPAO.getStatsForCurrentMonth());
        TreeMap threeMonthsMap = new TreeMap(statsPAO.getStatsForThreePreviousMonths());
        TreeMap allMap = new TreeMap(statsPAO.getStatsForAll());

        log.info("currentWeekMap[data] -> ilosc = " + currentWeekMap.size());

        mav.addAttribute("currentWeekData", currentWeekMap);
        mav.addAttribute("currentMonthData", currentMonthMap);
        mav.addAttribute("threeMonthsData", threeMonthsMap);
        mav.addAttribute("allData", allMap);

        mav.addAttribute("currentWeek", new BugStatsByPriorityDatasetProducer(currentWeekMap));
        mav.addAttribute("currentMonth", new BugStatsByPriorityDatasetProducer(currentMonthMap));
        mav.addAttribute("threeMonth", new BugStatsByPriorityDatasetProducer(threeMonthsMap));
        mav.addAttribute("allTime", new BugStatsByPriorityDatasetProducer(allMap));

        return "stats/bugsByPriority";
    }
}