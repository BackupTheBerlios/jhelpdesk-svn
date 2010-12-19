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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jjhop
 */
@Entity
@Table(name = "dl_preferences")
@SequenceGenerator(name = "dl_preferences_sequence",
                   sequenceName = "dl_preferences_id_seq", allocationSize = 1)
public class DisplayListsPreferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dl_preferences_sequence")
    @Column(name = "dl_preferences_id")
    private Long id;

    @OneToOne(mappedBy = "dlPreferences")
    private User user;

    @Column(name = "tickest_list_size")
    private Integer ticketsListSize;

    @Column(name = "announcements_list_size")
    private Integer announcementsListSize;

    @Column(name = "users_list_size")
    private Integer usersListSize;

    @Column(name = "filters_list_size")
    private Integer filtersListSize;

    public Integer getAnnouncementsListSize() {
        return announcementsListSize;
    }

    public void setAnnouncementsListSize(Integer announcementsListSize) {
        this.announcementsListSize = announcementsListSize;
    }

    public Integer getFiltersListSize() {
        return filtersListSize;
    }

    public void setFiltersListSize(Integer filtersListSize) {
        this.filtersListSize = filtersListSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTicketsListSize() {
        return ticketsListSize;
    }

    public void setTicketsListSize(Integer ticketsListSize) {
        this.ticketsListSize = ticketsListSize;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUsersListSize() {
        return usersListSize;
    }

    public void setUsersListSize(Integer usersListSize) {
        this.usersListSize = usersListSize;
    }

}
