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

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.BugStatusDAO;
import de.berlios.jhelpdesk.model.BugStatus;

@Repository("bugStatusDAO")
public class BugStatusDAOJdbc implements BugStatusDAO {

    public List<BugStatus> getAllStatuses() {
        List<BugStatus> list2Return = new ArrayList<BugStatus>(4);
        list2Return.add(BugStatus.NOTIFIED);
        list2Return.add(BugStatus.ASSIGNED);
        list2Return.add(BugStatus.REJECTED);
        list2Return.add(BugStatus.RESOLVED);
        list2Return.add(BugStatus.CLOSED);
        return list2Return;
    }

    public List<BugStatus> getNonOpenedStatuses() {
        List<BugStatus> list2Return = new ArrayList<BugStatus>(2);
        list2Return.add(BugStatus.NOTIFIED);
        list2Return.add(BugStatus.ASSIGNED);
        return list2Return;
    }

    public BugStatus getById(Long id) {
        return getById(id.intValue());
    }

    public BugStatus getById(int id) {
        return BugStatus.fromInt(id);
    }
}
