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
package de.berlios.jhelpdesk.dao;

import java.util.Date;
import java.util.List;

import de.berlios.jhelpdesk.model.Bug;
import de.berlios.jhelpdesk.model.BugEvent;
import de.berlios.jhelpdesk.model.EventType;
import de.berlios.jhelpdesk.model.User;

/**
 * Definiuje zestaw metod do obsługi trwałości obiektów BugEvent.
 * 
 * @author jjhop
 */
public interface BugEventDAO {

    /**
     *
     * @param eventId
     * @return
     */
    public BugEvent getById(Long eventId);

    /**
     *
     * @param user
     * @return
     */
    public List<BugEvent> getByUser(User user);

    /**
     *
     * @param userId
     * @return
     */
    public List<BugEvent> getByUser(Long userId);

    /**
     *
     * @param bug
     * @return
     */
    public List<BugEvent> getByBug(Bug bug);

    /**
     *
     * @param bugId
     * @return
     */
    public List<BugEvent> getByBug(Long bugId);

    /**
     *
     * @param type
     * @return
     */
    public List<EventType> getByType(EventType type);

    /**
     *
     * @param date
     * @return
     */
    public List<BugEvent> getByDate(Date date);

    /**
     *
     * @param from
     * @param to
     * @return
     */
    public List<BugEvent> getByDate(Date from, Date to);

    /**
     *
     * @param howMuch
     * @return
     */
    public List<BugEvent> getLastFewEvents(int howMuch);

    /**
     *
     * @param event
     */
    public void save(BugEvent event);
}
