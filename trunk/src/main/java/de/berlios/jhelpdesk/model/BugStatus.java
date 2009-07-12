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

public class BugStatus {
	
	private final int statusId;
	private final String statusName;
	private final String statusDesc;
	private final String bgColor;
	private final boolean isActive;
	
	public static final BugStatus NOTIFIED = new BugStatus( 1, "ZGŁOSZONY", "desc", "FF4040", true );
	public static final BugStatus ATTACHED = new BugStatus( 2, "PRZYPISANY", "desc", "FFC843", true );
	public static final BugStatus REJECTED = new BugStatus( 3, "ODRZUCONY", "desc", "4D61A9", true );
	public static final BugStatus RESOLVED = new BugStatus( 4, "ROZWIĄZANY", "desc", "228664", true );
	public static final BugStatus CLOSED   = new BugStatus( 5, "ZAMKNIĘTY", "desc", "228664", true );
	
	// TODO: tymczasowe rozwiazanie...
	public static final BugStatus UNKNOWN  = new BugStatus( 6, "NIEZNANY", "desc", "228664", true );
	
	public BugStatus( int id, String name, String desc, String bgColor, boolean active ) {
		this.isActive = active;
		this.statusDesc = desc;
		this.statusId = id;
		this.statusName = name;
		this.bgColor = bgColor;
	}
	
	public int toInt( ) {
		return statusId;
	}
	
	public static BugStatus fromInt( int code ) {
		switch ( code ) {
			case 1: return NOTIFIED;
			case 2: return ATTACHED;
			case 3: return REJECTED;
			case 4: return RESOLVED;
			case 5: return CLOSED;
			//default: throw new RuntimeException( "Nieznany status." );
			default: return UNKNOWN;
		}
	}
	
	public boolean getActive() {
		return isActive;
	}

	public int getStatusId() {
		return statusId;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public String getStatusName() {
		return statusName;
	}
	
	public String toString() {
		return statusName;
	}

	public String getBgColor() {
		return bgColor;
	}
}
