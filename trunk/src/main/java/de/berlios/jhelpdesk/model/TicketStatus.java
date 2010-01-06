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

/**
 * TODO: dodać jakąą maszynę stanów, która kontrolowałaby w logiczny
 *       sposób przejścia pomiędzy różnymi statusami.
 * @author jjhop
 */
public enum TicketStatus {

    /**
     *
     */
    NOTIFIED(1, "ZGŁOSZONY", "desc", "FF4040", true),
    
    /**
     * 
     */
    ASSIGNED(2, "PRZYPISANY", "desc", "FFC843", true),
    
    /**
     * 
     */
    REJECTED(3, "ODRZUCONY", "desc", "4D61A9", true),
    
    /**
     * 
     */
    RESOLVED(4, "ROZWIĄZANY", "desc", "228664", true),
    
    /**
     * 
     */
    CLOSED(5, "ZAMKNIĘTY", "desc", "228664", true),
    
    /**
     * 
     */
    UNKNOWN(6, "NIEZNANY", "desc", "228664", true); // TODO: tymczasowe...

    /**
     *
     */
    private final int statusId;

    /**
     *
     */
    private final String statusName;

    /**
     *
     */
    private final String statusDesc;

    /**
     *
     */
    private final String bgColor;

    /**
     *
     */
    private final boolean isActive;

    /**
     *
     */
    private static TicketStatus[] ALL_STATUSES = {
        NOTIFIED,
        ASSIGNED,
        REJECTED,
        RESOLVED,
        CLOSED
    };

    /**
     * TODO: dlaczego tutaj jest NONOPEN skoro NOTIFIED to OPEN??
     */
    private static TicketStatus[] NONOPEN_STATUSES = {
        NOTIFIED,
        ASSIGNED
    };

    /**
     *
     * @param id
     * @param name
     * @param desc
     * @param bgColor
     * @param active
     */
    private TicketStatus(int id, String name, String desc, String bgColor, boolean active) {
        this.isActive = active;
        this.statusDesc = desc;
        this.statusId = id;
        this.statusName = name;
        this.bgColor = bgColor;
    }

    /**
     *
     * @return
     */
    public int toInt() {
        return statusId;
    }

    /**
     *
     * @param code
     * @return
     */
    public static TicketStatus fromInt(int code) {
        switch (code) {
            case 1:
                return NOTIFIED;
            case 2:
                return ASSIGNED;
            case 3:
                return REJECTED;
            case 4:
                return RESOLVED;
            case 5:
                return CLOSED;
            //default: throw new RuntimeException("Nieznany status.");
            default:
                return UNKNOWN;
        }
    }

    /**
     *
     * @return
     */
    public static TicketStatus[] getAllStatuses() {
        return ALL_STATUSES;
    }

    /**
     *
     * @return
     */
    public static TicketStatus[] getNonOpenedStatuses() {
        return NONOPEN_STATUSES;
    }

    /**
     *
     * @return
     */
    public boolean getActive() {
        return isActive;
    }

    /**
     *
     * @return
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     *
     * @return
     */
    public String getStatusDesc() {
        return statusDesc;
    }

    /**
     *
     * @return
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     *
     * @return
     */
    public String getBgColor() {
        return bgColor;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return statusName;
    }

}
