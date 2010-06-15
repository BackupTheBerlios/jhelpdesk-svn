package de.berlios.jhelpdesk.web.tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.theme.AbstractThemeResolver;

import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
public class ThemeCustomResolever extends AbstractThemeResolver {

    // Tutaj jeszcze powinna byc lista dostępnych tematów
    private String defaultThemeName = "blue";

    public String resolveThemeName(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null) {
            return currentUser.getPreferedTheme();
        }
        for (Cookie cookie : request.getCookies()) {
            if ("theme".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return defaultThemeName;
    }

    public void setThemeName(HttpServletRequest req, HttpServletResponse res, String themeName) {
        System.out.println(" ************* setThemeName(" + themeName + ")");
        this.defaultThemeName = themeName;
    }
}
