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
package de.berlios.jhelpdesk.web.stats.saviour;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.pao.StatsSaviourPAO;

public class StatsSaviourViewCtrl implements Controller {
	
	private StatsSaviourPAO statsPAO;

	public ModelAndView handleRequest(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("stats/saviour/saviourStats");
		statsPAO.setSaviourId(request.getParameter("saviour"));
		mav.addObject("stats", statsPAO.getFullStats());
		return mav;
	}

	/**
	 * @param statsPAO The statsPAO to set.
	 */
	public void setStatsPAO(StatsSaviourPAO statsPAO) {
		this.statsPAO = statsPAO;
	}

}
