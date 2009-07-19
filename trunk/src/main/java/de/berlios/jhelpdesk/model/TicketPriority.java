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

public enum TicketPriority {
	
	NORMAL(1, "normalny"),
	IMPORTANT(2, "wazny"),
	MAJOR(3, "bardzo wazny"),
	CRITICAL(4, "krytyczny");
	
	private final int priorityId;
	private final String priorityName;

	private TicketPriority(int id, String name) {
		this.priorityId = id;
		this.priorityName = name;
	}

	public int getPriorityId() {
		return priorityId;
	}

	public String getPriorityName() {
		return priorityName;
	}
	
	public static TicketPriority fromInt(int id) {
		for (TicketPriority value : TicketPriority.values()) {
			if (value.getPriorityId() == id)
				return value;
		}
		throw new IllegalArgumentException("Wartosc spoza zakresu. Dostepne wartosci to: 1, 2, 3, 4" );
	}
	
	@Override
	public String toString() {
		return priorityName;
	}

}
