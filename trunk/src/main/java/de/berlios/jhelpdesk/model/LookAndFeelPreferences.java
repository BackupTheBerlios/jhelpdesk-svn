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
import java.util.Locale;

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
@Table(name = "laf_preferences")
@SequenceGenerator(name = "laf_preferences_sequence", 
                   sequenceName = "laf_preferences_id_seq", allocationSize = 1)
public class LookAndFeelPreferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="laf_preferences_sequence")
    @Column(name = "laf_preferences_id")
    private Long id;

    @OneToOne(mappedBy = "lafPreferences")
    private User user;

    @Column(name = "theme")
    private String theme;

    @Column(name = "welcome_page")
    private String welcomePage;

    @Column(name = "locale")
    private Locale preferredLocale;

    @Column(name = "filter_id")
    private Long filterId;

    @Column(name = "new_ticket_form_view")
    private String newTicketFormView;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Locale getPreferredLocale() {
        return preferredLocale;
    }

    public void setPreferredLocale(Locale preferredLocale) {
        this.preferredLocale = preferredLocale;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getWelcomePage() {
        return welcomePage;
    }

    public void setWelcomePage(String welcomePage) {
        this.welcomePage = welcomePage;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    public Long getFilterId() {
        return this.filterId;
    }

    public void setNewTicketFormView(String view) {
        this.newTicketFormView = view;
    }

    public String getNewTicketFormView() {
        return this.newTicketFormView;
    }

}
