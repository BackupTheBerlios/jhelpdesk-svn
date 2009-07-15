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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import de.berlios.jhelpdesk.pao.StatsSaviourPAO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Scope("prototype")
@Controller("statsSaviourViewCtrl")
public class StatsSaviourViewCtrl {

    @Autowired
    private StatsSaviourPAO statsPAO;

    @RequestMapping
    public ModelAndView handleRequest(@RequestParam("saviour") String saviour) {
        ModelAndView mav = new ModelAndView("stats/saviour/saviourStats");
        statsPAO.setSaviourId(saviour);
        mav.addObject("stats", statsPAO.getFullStats());
        return mav;
    }
}
