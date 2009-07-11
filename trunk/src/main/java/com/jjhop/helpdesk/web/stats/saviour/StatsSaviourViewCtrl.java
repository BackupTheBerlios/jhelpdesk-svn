package com.jjhop.helpdesk.web.stats.saviour;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jjhop.helpdesk.pao.ifc.IStatsSaviour;

public class StatsSaviourViewCtrl implements Controller {
	private IStatsSaviour statsPAO;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response ) throws Exception {
		ModelAndView mav = new ModelAndView( "stats/saviour/saviourStats" );
		statsPAO.setSaviourId( request.getParameter( "saviour" ) );
		mav.addObject( "stats", statsPAO.getFullStats() );
		return mav;
	}

	/**
	 * @param statsPAO The statsPAO to set.
	 */
	public void setStatsPAO(IStatsSaviour statsPAO) {
		this.statsPAO = statsPAO;
	}
	
}
