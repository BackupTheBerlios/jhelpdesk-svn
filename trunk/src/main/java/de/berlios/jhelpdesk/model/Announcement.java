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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author jjhop
 */
@Entity
@Table(name = "announcement")
@SequenceGenerator(name="announcement_sequence", sequenceName="announcement_id_seq")
@SecondaryTable(name="announcement_body",
    pkJoinColumns=@PrimaryKeyJoinColumn(name="announcement_id", referencedColumnName="announcement_id"))
@NamedQueries({
    @NamedQuery(name = "Announcement.allOrderByCreateDateDesc", query = "SELECT a FROM Announcement a ORDER BY a.createDate DESC"),
    @NamedQuery(name = "Announcement.byId", query = "SELECT a FROM Announcement a WHERE a.announcementId=?1")
})
public class Announcement implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="announcement_sequence")
    @Column(name = "announcement_id")
    private Long announcementId;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    /**
     *
     */
    @Column(name = "title")
    private String title;

    /**
     *
     */
    @Column(name = "lead")
    private String lead;

    /**
     *
     */
    @Column(name = "body", table = "announcement_body", nullable = true)
    private String body;

    public Announcement() {
        this.createDate = new Date();
    }


    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the announcementId
     */
    public Long getAnnouncementId() {
        return announcementId;
    }

    /**
     * @param announcementId the announcementId to set
     */
    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    /**
     * @return the lead
     */
    public String getLead() {
        return lead;
    }

    /**
     * @param lead the lead to set
     */
    public void setLead(String lead) {
        this.lead = lead;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
