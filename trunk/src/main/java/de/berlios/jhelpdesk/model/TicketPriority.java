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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jjhop
 */
public enum TicketPriority {

    /**
     *
     */
    NORMAL(1, "normalny"),

    /**
     *
     */
    IMPORTANT(2, "wazny"),

    /**
     *
     */
    MAJOR(3, "bardzo wazny"),

    /**
     *
     */
    CRITICAL(4, "krytyczny");

    private static final Log log = LogFactory.getLog(TicketPriority.class);

    private static List<TicketPriority> ps;

    static {
        ps = new ArrayList<TicketPriority>();
        ps.add(TicketPriority.NORMAL);
        ps.add(TicketPriority.IMPORTANT);
        ps.add(TicketPriority.MAJOR);
        ps.add(TicketPriority.CRITICAL);
    }

    /**
     *
     */
    private final int priorityId;

    /**
     *
     */
    private final String priorityName;

    /**
     * 
     * @param id
     * @param name
     */
    private TicketPriority(int id, String name) {
        this.priorityId = id;
        this.priorityName = name;
    }

    /**
     * 
     * @return
     */
    @Deprecated
    public int getPriorityId() {
        return this.priorityId;
    }

    /**
     *
     * @return
     */
    public String getPriorityName() {
        return this.priorityName;
    }

    /**
     *
     * @return
     */
    public int toInt() {
        return this.priorityId;
    }

    /**
     *
     * @param id
     * @return
     */
    public static TicketPriority fromInt(int id) {
        for (TicketPriority value : TicketPriority.values()) {
            if (value.getPriorityId() == id) {
                return value;
            }
        }
        throw new IllegalArgumentException("Wartosc spoza zakresu. Dostepne wartosci to: 1, 2, 3, 4");
    }

    public static List<TicketPriority> listFromString(String inputString) {
        List<TicketPriority> resultList = new ArrayList<TicketPriority>();
        if (inputString != null) {
            for (String priorityId : inputString.split(",")) {
                try {
                    resultList.add(TicketPriority.fromInt(Integer.parseInt(priorityId)));
                } catch(NumberFormatException nfe) {
                    log.debug(nfe.getMessage());
                }
            }
        }
        return resultList;
    }

    public static String listAsString(List<TicketPriority> inputList) {
        StringBuilder tpBuf = new StringBuilder("");
        if (inputList != null) {
            for (Iterator<TicketPriority> it = inputList.iterator(); it.hasNext();) {
                tpBuf.append(it.next().toInt());
                if (it.hasNext()) {
                    tpBuf.append(",");
                }
            }
        }
        return tpBuf.toString();
    }

    public static List<TicketPriority> getPriorities() {
        return ps;
    }

    @Override
    public String toString() {
        return this.priorityName;
    }
}
