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
package de.berlios.jhelpdesk.web.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author jjhop
 */
public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(
                "stampsList", new ConcurrentHashMap<String, Boolean>());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        Map<String, Boolean> stampsList =
                (Map<String, Boolean>) se.getSession().getAttribute("stampsList");
        for (String stamp : stampsList.keySet()) {
            System.out.println("do czyszczenia: " + stamp);
        }
    }
}
