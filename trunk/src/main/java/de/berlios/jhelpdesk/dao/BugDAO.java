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
import de.berlios.jhelpdesk.model.BugCategory;
import de.berlios.jhelpdesk.model.BugComment;
import de.berlios.jhelpdesk.model.BugPriority;
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.form.ShowBugsFilterForm;

/**
 * Definiuje zestaw metod do obsługi trwałości obiektów Bug.
 * 
 * @author jjhop
 */
public interface BugDAO {

    /**
     * Zwraca zgłoszenie o podanym identyfikatorze.
     *
     * @param bugId
     * @return
     */
    public Bug getBugById(Long bugId);

    /**
     * Zwraca ostatnio dodane zgłoszenie.
     *
     * @return
     */
    public Bug getLastAddedBug();

    /**
     * Zwraca wszystkie zgłoszenia z podanego dnia.
     *
     * @param date
     * @return
     */
    public List<Bug> getBugsByDate(Date date);

    /**
     * Zwraca wszystkie zgłoszenia o podanym statusie.
     *
     * @param bugStatus
     * @return
     */
    public List<Bug> getBugsByStatus(BugStatus bugStatus);

    /**
     *
     * @param bugStatus
     * @param howMuch
     * @return
     */
    public List<Bug> getBugsByStatus(BugStatus bugStatus, int howMuch);

    /**
     * Zwraca wszystkie zgłoszenia o podanej ważności.
     *
     * @param bugPriority
     * @return
     */
    public List<Bug> getBugsByPriority(BugPriority bugPriority);

    /**
     * Zwraca wszystkie zgłoszenia wybranej kategorii.
     *
     * @param bugCategory
     * @return
     */
    public List<Bug> getBugsByCategory(BugCategory bugCategory);

    /**
     *
     * @param user
     * @return
     */
    public List<Bug> getBugsNotifyiedByUser(User user);

    /**
     *
     * @param user
     * @return
     */
    public List<Bug> getBugsResolvedByUser(User user);

    /**
     * Usuwa wybrane zgłoszenie.
     *
     * @param bug2Del
     * @throws Exception
     */
    public void removeBug(Bug bug2Del) throws Exception;

    /**
     * Usuwa zgłoszenie o podanym identyfikatorze.
     *
     * @param bug2DelIId
     */
    public void remove(Long bug2DelIId);

    /**
     * Zapisuje podane zgłoszenie. Jesli jest to zgłoszenie istniejący uaktualnie związane z nim dane.
     *
     * @param bug2Save
     */
    public void save(Bug bug2Save) throws Exception;

    /**
     * Zwraca wszystkie błędy z bazy.
     *
     * @param bug2Save
     */
    public List<Bug> getAllBugs();

    /**
     * @param filterForm
     */
    public List<Bug> getBugsWithFilter(ShowBugsFilterForm filterForm, int limit, long offset);

    /**
     *
     * @param filterForm
     * @return
     */
    public Integer countBugsWithFilter(ShowBugsFilterForm filterForm);

    /**
     *
     * @param comm
     */
    public void addComment(BugComment comm);
}
