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
package de.berlios.jhelpdesk.dao.jdbc;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.BugPriorityDAO;
import de.berlios.jhelpdesk.model.BugPriority;

@Repository("bugPriorityDAO")
public class BugPriorityDAOJdbc implements BugPriorityDAO {

	public List<BugPriority> getAllPriorities() {
		final List<BugPriority> toReturn = new LinkedList<BugPriority>();
		toReturn.add( BugPriority.NORMAL );
		toReturn.add( BugPriority.IMPORTANT );
		toReturn.add( BugPriority.MAJOR );
		toReturn.add( BugPriority.CRITICAL );
		return toReturn;
	}

	public BugPriority getById( Long bugCategoryId ) {
		return getById( bugCategoryId.intValue() );
	}

	public BugPriority getById( int id ) {
		return BugPriority.fromInt( id );
	}
}
