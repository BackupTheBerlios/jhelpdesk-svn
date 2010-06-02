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
