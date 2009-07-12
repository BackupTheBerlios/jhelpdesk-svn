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

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.UserDAO;

public class ShowSavioursViewCtrl implements Controller {
	private static Log log = LogFactory.getLog( ShowSavioursViewCtrl.class );
	private final static String[] letters =
		new String[] {
			"A", "B", "C", "Ć", "D", "E",
			"F", "G", "H", "I", "J", "K", 
			"L", "Ł", "M", "N", "O", "P", 
			"Q", "R", "S", "Ś", "T", "U", "V", 
			"W", "X", "Y", "Z", "Ź", "Ż"
	};
	private UserDAO hdUserDAO;
	
	public ModelAndView handleRequest(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("stats/saviour/savioursList");
		if (request.getParameter("letter") == null) {
			response.sendRedirect(request.getRequestURI() + "?letter=0-5");
			return null;
		}
		log.debug("Parametr letter=" + URLDecoder.decode(request.getParameter("letter"), "utf-8"));
		mav.addObject("users", hdUserDAO.getSavioursWithLastNameStartsWithLetter(
				getLetters(request.getParameter("letter"))));
		return mav;
	}
	
	private String getLetters(String param) {
		String[] params = param.split("-");
		int begin = Integer.parseInt(params[0]);
		int end = Integer.parseInt(params[1]);

		String[] _letters = new String[end - begin + 1];

		for (int current = 0, i = begin; i < end + 1; ++i) {
			_letters[current++] = letters[i];
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < _letters.length; ++i)
			sb.append(_letters[i]);
		return sb.toString();
	}
	
	/**
	 * @param hdUserDAO The hdUserDAO to set.
	 */
	public void setHdUserDAO(UserDAO hdUserDAO) {
		this.hdUserDAO = hdUserDAO;
	}
}
