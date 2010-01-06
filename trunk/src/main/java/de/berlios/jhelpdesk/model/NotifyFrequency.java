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
 *
 * @author jjhop
 */
public enum NotifyFrequency {

    IMMEDIATELY(1, "IMMEDIATELY"),
    DAILY(2, "DAILY"),
    WEEKLY(3, "WEEKLY"),
    MONTHLY(4, "MONTHLY"),
    NEVER(5, "NEVER");
    
    private final int id;
    private final String name;

    private NotifyFrequency(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static final NotifyFrequency fromInt(int i) {
        switch(i) {
            case 1:
                return IMMEDIATELY;
            case 2:
                return DAILY;
            case 3:
                return WEEKLY;
            case 4:
                return MONTHLY;
            case 5:
                return NEVER;
            default:
                throw new RuntimeException("Nieznana wartość int dla NotifyFrequency");
        }
    }
}
