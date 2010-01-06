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
package de.berlios.jhelpdesk.model;

import java.util.Locale;

/**
 *
 * @author jjhop
 */
public class UserPreferences {

    /**
     * Look & Feel
     */
    private String theme;

    /**
     *
     */
    private String startingPage;

    /**
     *
     */
    private String lang;

//    private String ticketAssign; // I, D, W, M, N - Immediatly, Daily, Weekly, Monthly, Never

    /**
     * 
     * @return
     */
    public String getLang() {
        return lang;
    }

    /**
     *
     * @param lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     *
     * @return
     */
    public String getStartingPage() {
        return startingPage;
    }

    /**
     *
     * @param startingPage
     */
    public void setStartingPage(String startingPage) {
        this.startingPage = startingPage;
    }

    /**
     *
     * @return
     */
    public String getTheme() {
        return theme;
    }

    /**
     *
     * @param theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     *
     * @return
     */
    public Locale getLocale() {
        return (!lang.isEmpty()) ? new Locale(lang) : Locale.getDefault();
    }
}
