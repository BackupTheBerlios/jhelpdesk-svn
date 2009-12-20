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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.AnnouncementDAO;
import de.berlios.jhelpdesk.model.Announcement;

@Repository("announcementDAO")
@Qualifier("jdbc")
public class AnnouncementDAOJdbc extends AbstractJdbcTemplateSupport implements AnnouncementDAO {

    private static Log log = LogFactory.getLog(AnnouncementDAOJdbc.class);
    private static final RowMapper ANNOUNCEMENT_ROW_MAPPER = new RowMapper() {

        public Object mapRow(ResultSet rs, int row) throws SQLException {
            Announcement announcement = new Announcement();
            announcement.setAnnouncementId(rs.getLong("announcement_id"));
            announcement.setCreateDate(rs.getDate("create_date"));
            announcement.setTitle(rs.getString("title"));
            announcement.setLead(rs.getString("lead"));
            announcement.setBody(rs.getString("body"));
            return announcement;
        }
    };

    @Autowired
    public AnnouncementDAOJdbc(DataSource dataSource) {
        super(dataSource);
    }

    public void delete(Long announcementId) {
        // usuwamy tylko dane z tabeli infomation,
        // ewentualne dane z tabeli announcement_body
        // usuwane sa za pomoca odpowiedniego triggera
        // funkcja SQL -> drop_announcement_body()
        // trigger     -> drop_announcement_body_trg
        getJdbcTemplate().update(
                "DELETE FROM announcement WHERE announcement_id=?",
                new Object[]{
                    announcementId
                });
    }

    public void delete(Announcement announcement) {
        // usuwamy tylko dane z tabeli infomation,
        // ewentualne dane z tabeli announcement_body
        // usuwane sa za pomoca odpowiedniego triggera
        // funkcja SQL -> drop_announcement_body()
        // trigger     -> drop_announcement_body_trg
        getJdbcTemplate().update(
                "DELETE FROM announcement WHERE announcement_id=?",
                new Object[]{
                    announcement.getAnnouncementId()
                });
    }

    @SuppressWarnings("unchecked")
    public List<Announcement> getAll() {
        return getJdbcTemplate().query(
                "SELECT * FROM announcement_view ORDER BY create_date DESC",
                ANNOUNCEMENT_ROW_MAPPER);
    }

    public Announcement getById(Long announcementId) {
        return (Announcement) getJdbcTemplate().queryForObject(
                "SELECT * FROM announcement_view WHERE announcement_id=?",
                new Object[]{
                    announcementId
                },
                ANNOUNCEMENT_ROW_MAPPER);
    }

    @SuppressWarnings("unchecked")
    public List<Announcement> getLastFew(int howMuch) {
        return getJdbcTemplate().query(
                "SELECT * FROM announcement_view ORDER BY create_date DESC LIMIT ?",
                new Object[]{
                    howMuch
                },
                ANNOUNCEMENT_ROW_MAPPER);
    }

    public void save(final Announcement announcement) {
        if (announcement.getAnnouncementId() != null) {
            getJdbcTemplate().execute(
                    new ConnectionCallback() {

                        public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
                            conn.setAutoCommit(false);
                            try {
                                PreparedStatement pstmt =
                                        conn.prepareStatement(
                                        "UPDATE announcement SET title=?, lead=? WHERE announcement_id=?");
                                pstmt.setString(1, announcement.getTitle());
                                pstmt.setString(2, announcement.getLead());
                                pstmt.setLong(3, announcement.getAnnouncementId());
                                pstmt.executeUpdate();
                                if (announcement.getBody() != null) {
                                    pstmt =
                                            conn.prepareStatement("DELETE FROM announcement_body WHERE announcement_id=?");
                                    pstmt.setLong(1, announcement.getAnnouncementId());
                                    pstmt.executeUpdate();
                                    pstmt =
                                            conn.prepareStatement("INSERT INTO announcement_body(announcement_id,body) VALUES(?,?)");
                                    pstmt.setLong(1, announcement.getAnnouncementId());
                                    pstmt.setString(2, announcement.getBody());
                                    pstmt.executeUpdate();
                                }
                                conn.commit();
                            } catch (Exception ex) {
                                log.error(ex);
                                conn.rollback();
                            }
                            return null;
                        }
                    });
        } else {
            getJdbcTemplate().execute(
                    new ConnectionCallback() {

                        public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
                            conn.setAutoCommit(false);
                            try {
                                PreparedStatement pstmt =
                                        conn.prepareStatement(
                                        "INSERT INTO announcement(announcement_id,create_date,title,lead) "
                                        + "VALUES(nextval('announcement_id_seq'),now(),?,?)");
                                pstmt.setString(1, announcement.getTitle());
                                pstmt.setString(2, announcement.getLead());
                                pstmt.executeUpdate();

                                Statement stmt =
                                        conn.createStatement(
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs = stmt.executeQuery("SELECT currval('announcement_id_seq')");
                                if (rs.first()) {
                                    announcement.setAnnouncementId(rs.getLong(1));
                                }

                                if ((announcement.getBody() != null) && (announcement.getBody().length() > 0)) {
                                    PreparedStatement pstmt3 =
                                            conn.prepareStatement("INSERT INTO announcement_body(announcement_id,body) VALUES(?,?)");
                                    pstmt3.setLong(1, announcement.getAnnouncementId());
                                    pstmt3.setString(2, announcement.getBody());
                                    pstmt3.executeUpdate();
                                }
                                conn.commit();
                            } catch (Exception ex) {
                                log.error(ex);
                                conn.rollback();
                                announcement.setAnnouncementId(null);
                            }
                            return null;
                        }
                    });
        }
    }
}
