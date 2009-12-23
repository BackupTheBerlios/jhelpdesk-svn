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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.DataAccessException;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.model.EventType;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketComment;
import de.berlios.jhelpdesk.model.TicketEvent;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.form.ShowTicketsFilterForm;

@Repository("ticketDAO")
@Qualifier("jdbc")
public class TicketDAOJdbc extends AbstractJdbcTemplateSupport implements TicketDAO {

    private static Log log = LogFactory.getLog(TicketDAOJdbc.class);

    @Autowired
    public TicketDAOJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @SuppressWarnings("unchecked")
    public Ticket getTicketById(final Long ticketId) throws DataAccessException {
        log.debug("getTicketById(final Long ticketId) => " + ticketId);
        try {
            Ticket ticket = (Ticket) getJdbcTemplate().queryForObject(
                "SELECT ticket.*, ticket_category.* " +
                "FROM ticket " +
                "LEFT OUTER JOIN ticket_category ON ticket.ticket_category=ticket_category.category_id " +
                "WHERE ticket.ticket_id=?",
                new Object[]{ticketId},
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int row) throws SQLException {
                        Ticket ticket = new Ticket();
                        ticket.setTicketId(rs.getLong("ticket_id"));
                        ticket.setSubject(rs.getString("subject"));
                        ticket.setCreateDate(rs.getTimestamp("create_date"));
                        ticket.setInputer(new User(rs.getLong("inputer"), null, null, null));
                        ticket.setNotifier(new User(rs.getLong("notifyier"), null, null, null));
                        if (rs.getLong("saviour") != 0) {
                            ticket.setSaviour(new User(rs.getLong("saviour"), null, null, null));
                        }
                        ticket.setDescription(rs.getString("description"));
                        ticket.setStepByStep(rs.getString("step_by_step"));
                        TicketCategory cat = new TicketCategory(rs.getInt("category_id"),
                                rs.getString("category_name"));
                        ticket.setTicketCategory(cat);
                        ticket.setTicketPriority(TicketPriority.fromInt(rs.getInt("ticket_priority")));
                        ticket.setTicketStatus(TicketStatus.fromInt(rs.getInt("ticket_status")));
                        return ticket;
                    }
                }
            );
            ticket.setNotifier(
                (User) getJdbcTemplate().queryForObject(
                    "SELECT user_id,first_name,last_name,login,email,mobile,phone FROM users WHERE user_id=?",
                    new Object[]{ticket.getNotifier().getUserId()},
                    new RowMapper() {
                        public Object mapRow(ResultSet rs, int row) throws SQLException {
                            User user = new User();
                            user.setUserId(rs.getLong("user_id"));
                            user.setFirstName(rs.getString("first_name"));
                            user.setLastName(rs.getString("last_name"));
                            user.setLogin(rs.getString("login"));
                            user.setEmail(rs.getString("email"));
                            user.setPhone(rs.getString("phone"));
                            user.setMobile(rs.getString("mobile"));
                            return user;
                        }
                    }
                )
            );

            ticket.setInputer(
                (User) getJdbcTemplate().queryForObject(
                    "SELECT user_id,first_name,last_name,login,email,mobile,phone FROM users WHERE user_id=?",
                    new Object[]{ticket.getInputer().getUserId()},
                    new RowMapper() {
                        public Object mapRow(ResultSet rs, int row) throws SQLException {
                            User user = new User();
                            user.setUserId(rs.getLong("user_id"));
                            user.setFirstName(rs.getString("first_name"));
                            user.setLastName(rs.getString("last_name"));
                            user.setLogin(rs.getString("login"));
                            user.setEmail(rs.getString("email"));
                            user.setPhone(rs.getString("phone"));
                            user.setMobile(rs.getString("mobile"));
                            return user;
                        }
                    }
                )
            );

            if (ticket.getSaviour() != null) {
                ticket.setSaviour(
                    (User) getJdbcTemplate().queryForObject(
                        "SELECT user_id,first_name,last_name,login,email,mobile,phone FROM users WHERE user_id=?",
                        new Object[]{ticket.getSaviour().getUserId()},
                        new RowMapper() {
                            public Object mapRow(ResultSet rs, int row) throws SQLException {
                                User user = new User();
                                user.setUserId(rs.getLong("user_id"));
                                user.setFirstName(rs.getString("first_name"));
                                user.setLastName(rs.getString("last_name"));
                                user.setLogin(rs.getString("login"));
                                user.setEmail(rs.getString("email"));
                                user.setPhone(rs.getString("phone"));
                                user.setMobile(rs.getString("mobile"));
                                return user;
                            }
                        }
                    )
                );
            }

            ticket.setEvents(
                new HashSet(
                    getJdbcTemplate().query(
                        // TODO: dorzucic wyswietlania (autora zdarzenia)
                        "SELECT * FROM ticket_event WHERE ticket_id=?",
                        new Object[]{ticketId},
                        new RowMapper() {
                            public Object mapRow(ResultSet rs, int row) throws SQLException {
                                TicketEvent evt = new TicketEvent();
                                evt.setTicketEventId(rs.getLong("event_id"));
                                evt.setTicketId(ticketId);
                                evt.setEvtSubject(rs.getString("event_subject"));
                                evt.setEventType(EventType.fromInt(rs.getInt("event_type")));
                                evt.setEvtDate(rs.getTimestamp("event_date"));
                                return evt;
                            }
                        }
                    )
                )
            );
            ticket.setComments(
                new HashSet(
                    getJdbcTemplate().query(
                        //TODO: dorzucic wyswietlania autora komentarza
                        "SELECT ticket_comment.* ,users.first_name,users.last_name,users.user_id,users.login " +
                        "FROM ticket_comment " +
                        "LEFT OUTER JOIN users " +
                        "ON ticket_comment.comment_author=users.user_id " +
                        "WHERE ticket_id=?",
                        new Object[]{ticketId},
                        new RowMapper() {
                            public Object mapRow(ResultSet rs, int row) throws SQLException {
                                TicketComment comment = new TicketComment();
                                comment.setTicketId(ticketId);
                                comment.setTicketCommentId(rs.getLong("comment_id"));
                                comment.setCommentDate(rs.getTimestamp("comment_date"));
                                comment.setCommentText(rs.getString("comment_text"));
                                comment.setNotForPlainUser(rs.getBoolean("not_for_plain_user"));
                                comment.setCommentAuthor(
                                    new User(
                                        rs.getLong("user_id"), rs.getString("login"),
                                        rs.getString("first_name"), rs.getString("last_name")
                                    )
                                );
                                return comment;
                            }
                        }
                    )
                )
            );
            return ticket;
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketsByStatus(TicketStatus ticketStatus) throws DataAccessException {
        try {
            return getJdbcTemplate().query(
                "SELECT * FROM ticket WHERE ticket_status=? " +
                "ORDER BY create_date DESC",
                new Object[]{ticketStatus.toInt()},
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int row) throws SQLException {
                        Ticket ticket = new Ticket();
                        ticket.setTicketId(rs.getLong("ticket_id"));
                        ticket.setSubject(rs.getString("subject"));
                        ticket.setCreateDate(rs.getTimestamp("create_date"));
                        return ticket;
                    }
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketsByStatus(TicketStatus ticketStatus, int howMuch) throws DataAccessException {
        try {
            return getJdbcTemplate().query(
                "SELECT * FROM ticket_list_view WHERE b_status=? " +
                "ORDER BY b_create_date DESC LIMIT ?",
                new Object[]{ticketStatus.toInt(), howMuch},
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int row) throws SQLException {
                        Ticket ticket = new Ticket();
                        ticket.setTicketId(rs.getLong("b_id"));
                        ticket.setSubject(rs.getString("b_subject"));
                        /* zgłaszający */
                        ticket.setNotifier(
                                new User(
                                    rs.getLong("n_id"),
                                    rs.getString("n_login"),
                                    rs.getString("n_first_name"),
                                    rs.getString("n_last_name")));
                        /* wprowadzajacy */
                        ticket.setInputer(
                                new User(
                                    rs.getLong("i_id"),
                                    rs.getString("i_login"),
                                    rs.getString("i_first_name"),
                                    rs.getString("i_last_name")));
                        /* rozwiazujacy */
                        ticket.setSaviour(
                                new User(
                                    rs.getLong("s_id"),
                                    rs.getString("s_login"),
                                    rs.getString("s_first_name"),
                                    rs.getString("s_last_name")));
                        /* ticketCategory */
                        TicketCategory category = new TicketCategory();
                        category.setTicketCategoryId(rs.getLong("c_id"));
                        category.setCategoryName(rs.getString("c_name"));
                        ticket.setTicketCategory(category);
                        /* ticketPriority */
                        ticket.setTicketPriority(TicketPriority.fromInt(rs.getInt("p_id")));
                        ticket.setCreateDate(rs.getTimestamp("b_create_date"));
                        return ticket;
                    }
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketsByPriority(TicketPriority ticketPriority)
            throws DataAccessException {
        try {
            return getJdbcTemplate().query(
                "SELECT * FROM ticket WHERE ticket_priority=? " +
                "ORDER BY create_date DESC",
                new Object[]{ticketPriority.toInt()},
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int row) throws SQLException {
                        Ticket ticket = new Ticket();
                        ticket.setTicketId(rs.getLong("ticket_id"));
                        ticket.setSubject(rs.getString("subject"));
                        return ticket;
                    }
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketsByCategory(TicketCategory ticketCategory)
            throws DataAccessException {
        try {
            return getJdbcTemplate().query(
                "SELECT * FROM ticket WHERE ticket_category=? " +
                "ORDER BY create_date DESC",
                new Object[]{ticketCategory.getTicketCategoryId()},
                new RowMapper() {

                    public Object mapRow(ResultSet rs, int row) throws SQLException {
                        Ticket ticket = new Ticket();
                        ticket.setTicketId(rs.getLong("ticket_id"));
                        ticket.setSubject(rs.getString("subject"));
                        return ticket;
                    }
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketsNotifyiedByUser(User user) throws DataAccessException {
        try {
            return getJdbcTemplate().query(
                "SELECT * FROM ticket WHERE notifyier=? " +
                "ORDER BY create_date DESC",
                new Object[]{user.getUserId()},
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int row) throws SQLException {
                        Ticket ticket = new Ticket();
                        ticket.setTicketId(rs.getLong("ticket_id"));
                        ticket.setSubject(rs.getString("subject"));
                        return ticket;
                    }
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketsResolvedByUser(User user) throws DataAccessException {
        log.debug("getTicketsResolvedByUser(HDUser user) => " + user.getUserId());
        try {
            // TODO: trzeba to zaimplementowac zeby kontroler dzialal
            return getJdbcTemplate().query(
                "SELECT * FROM ticket WHERE",
                new Object[]{user.getUserId()},
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int row) throws SQLException {
                        Ticket ticket = new Ticket();
                        ticket.setTicketId(rs.getLong("ticket_id"));
                        ticket.setSubject(rs.getString("subject"));
                        return ticket;
                    }
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void removeTicket(Ticket ticket) throws DataAccessException {
        remove(ticket.getTicketId());
    }

    public void remove(Long ticketId) throws DataAccessException {
        try {
            getJdbcTemplate().update(
                "DELETE FROM ticket WHERE ticket_id=?",
                new Object[]{ticketId}
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void save(final Ticket ticketToSave) throws DataAccessException {
        try {
            getJdbcTemplate().execute(
                new ConnectionCallback() {
                    public Object doInConnection(Connection conn) throws SQLException {
                        conn.setAutoCommit(false);
                        PreparedStatement pstmt =
                            conn.prepareStatement(
                                "INSERT INTO ticket(ticket_id,add_phone,"
                                + "ticket_category,ticket_priority,ticket_status,"
                                + "saviour,notifyier,inputer,create_date,"
                                + "description,step_by_step,subject) "
                                + "VALUES(nextval('ticket_id_seq'),?,?,?,?,?,?,?,?,?,?,?)");
                        pstmt.setString(1, ticketToSave.getAddPhone());
                        pstmt.setInt(2, 0); //(2, ticketToSave.getTicketCategory().getTicketCategoryId());
                        pstmt.setLong(3, ticketToSave.getTicketPriority().getPriorityId());
                        pstmt.setLong(4, ticketToSave.getTicketStatus().getStatusId());

                        if (ticketToSave.getSaviour() != null) {
                            pstmt.setLong(5, ticketToSave.getSaviour().getUserId());
                        } else {
                            pstmt.setNull(5, Types.INTEGER);
                        }

                        pstmt.setLong(6, ticketToSave.getNotifier().getUserId());
                        pstmt.setLong(7, ticketToSave.getInputer().getUserId());

                        pstmt.setTimestamp(8, new Timestamp(ticketToSave.getCreateDate().getTime()));
                        if (ticketToSave.getDescription() != null) {
                            pstmt.setString(9, ticketToSave.getDescription());
                        } else {
                            pstmt.setNull(9, Types.VARCHAR);
                        }

                        if (ticketToSave.getStepByStep() != null) {
                            pstmt.setString(10, ticketToSave.getStepByStep());
                        } else {
                            pstmt.setNull(10, Types.VARCHAR);
                        }
                        if (ticketToSave.getSubject() != null) {
                            pstmt.setString(11, ticketToSave.getSubject());
                        } else {
                            pstmt.setNull(11, Types.VARCHAR);
                        }
                        pstmt.executeUpdate();

                        Statement stmt =
                                conn.createStatement(
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs = stmt.executeQuery("SELECT currval('ticket_id_seq')");
                        if (rs.first()) {
                            ticketToSave.setTicketId(rs.getLong(1));
                        }
                        conn.commit();
                        return null;
                    }
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Ticket> getAllTickets() throws DataAccessException {
        try {
            return getJdbcTemplate().query(
                new QueryBuilder().getAllQuery(),
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int row) throws SQLException {
                        Ticket ticket = new Ticket();
                        ticket.setTicketId(rs.getLong("b_id"));
                        ticket.setSubject(rs.getString("b_subject"));
                        ticket.setDescription(rs.getString("b_description"));
                        ticket.setCreateDate(rs.getTimestamp("b_create_date"));
                        ticket.setTicketStatus(TicketStatus.fromInt(rs.getInt("s_id")));
                        ticket.setTicketCategory(
                            new TicketCategory(-1,
                            rs.getString("c_name"))
                        );
                        return ticket;
                    }
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketsWithFilter(ShowTicketsFilterForm filterForm, int limit, long offset)
            throws DataAccessException {
        try {
            return getJdbcTemplate().query(
                new QueryBuilder(filterForm).getFilteredQuery(false),
                new Object[]{limit, offset},
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int row) throws SQLException {
                        Ticket ticket = new Ticket();
                        ticket.setTicketId(rs.getLong("b_id"));
                        ticket.setSubject(rs.getString("b_subject"));
                        ticket.setDescription(rs.getString("b_description"));
                        ticket.setCreateDate(rs.getTimestamp("b_create_date"));
                        ticket.setTicketStatus(TicketStatus.fromInt(rs.getInt("b_status")));
                        ticket.setTicketCategory(
                            new TicketCategory(
                                rs.getInt("c_id"),
                                rs.getString("c_name")));
                        ticket.setTicketPriority(TicketPriority.fromInt(rs.getInt("p_id")));
                        /* zgłaszający */
                        ticket.setNotifier(
                            new User(
                                rs.getLong("n_id"),
                                rs.getString("n_login"),
                                rs.getString("n_first_name"),
                                rs.getString("n_last_name")));
                        /* wprowadzajacy */
                        ticket.setInputer(
                            new User(
                                rs.getLong("i_id"),
                                rs.getString("i_login"),
                                rs.getString("i_first_name"),
                                rs.getString("i_last_name")));
                        /* rozwiazujacy */
                        ticket.setSaviour(
                            new User(
                                rs.getLong("s_id"),
                                rs.getString("s_login"),
                                rs.getString("s_first_name"),
                                rs.getString("s_last_name")));
                        return ticket;
                    }
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public Integer countTicketsWithFilter(ShowTicketsFilterForm filterForm) throws DataAccessException {
        try {
            return getJdbcTemplate().queryForInt(
                new QueryBuilder(filterForm).getFilteredQuery(true));
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void addComment(TicketComment comm) throws DataAccessException {
        try {
            getJdbcTemplate().update(
                "INSERT INTO ticket_comment(comment_id, ticket_id, " +
                "comment_author, comment_date, comment_text, not_for_plain_user) " +
                "VALUES (nextval('ticket_comment_id_seq'),?,?,?,?,?)",
                new Object[]{
                    comm.getTicketId(),
                    comm.getCommentAuthor().getUserId(),
                    comm.getCommentDate(),
                    comm.getCommentText(),
                    comm.isNotForPlainUser()
                }
            );
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }
}

class QueryBuilder {

    private static Log log = LogFactory.getLog(QueryBuilder.class);
    ShowTicketsFilterForm filter;

    public QueryBuilder() {
    }

    public QueryBuilder(ShowTicketsFilterForm filterForm) {
        filter = filterForm;
    }

    public String getFilteredQuery(boolean countOnly) {
        StringBuffer sb = new StringBuffer();
        if (!countOnly) {
            sb.append("SELECT * FROM ticket_list_view WHERE ");
        } else {
            sb.append("SELECT COUNT(*) FROM ticket_list_view WHERE ");
        }

        if ((filter.getStartDate() != null)
                && (filter.getStartDate().length() > 0)) {
            sb.append(" b_create_date >= '" + filter.getStartDate() + "' AND ");
        }
        if ((filter.getEndDate() != null)
                && (filter.getEndDate().length() > 0)) {
            sb.append(" b_create_date <= '" + filter.getEndDate() + "' AND ");
        }
        if ((filter.getCategories() != null)
                && (filter.getCategories().size() > 0)) {
            String cat_ = "";
            for (TicketCategory cat : filter.getCategories()) {
                cat_ += cat.getTicketCategoryId().intValue() + ",";
            }
            cat_ += "-1";
            sb.append(" c_id IN(" + cat_ + ") AND ");
        }

        if ((filter.getNotifyiers() != null)
                && (filter.getNotifyiers().size() > 0)) {
            String not_ = "";
            for (User user : filter.getNotifyiers()) {
                not_ += user.getUserId().intValue() + ",";
            }
            not_ += "-1";
            sb.append(" n_id IN(" + not_ + ") AND ");
        }

        if ((filter.getSaviours() != null)
                && (filter.getSaviours().size() > 0)) {
            String sav_ = "";
            for (User user : filter.getSaviours()) {
                sav_ += user.getUserId().intValue() + ",";
            }
            sav_ += "-1";
            sb.append(" s_id IN(" + sav_ + ") AND ");
        }

        if ((filter.getPriorities() != null)
                && (filter.getPriorities().size() > 0)) {
            String priors_ = "";
            for (TicketPriority pr : filter.getPriorities()) {
                priors_ += pr.getPriorityId() + ",";
            }
            priors_ += "-1";
            sb.append(" p_id IN(" + priors_ + ") AND ");
        }

        if ((filter.getStatuses() != null)
                && (filter.getStatuses().size() > 0)) {
            String stat_ = "";
            for (TicketStatus stat : filter.getStatuses()) {
                stat_ += stat.getStatusId() + ",";
            }
            stat_ += "-1";
            sb.append(" b_status IN(" + stat_ + ") AND ");
        }

        sb.append(" true ");
        if (!countOnly) {
            sb.append("ORDER BY b_create_date DESC LIMIT ? OFFSET ?");
        }

        log.debug(sb.toString());
        return sb.toString();
    }

    public String getAllQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ticket_list_view ORDER BY b_create_date DESC");
        return sb.toString();
    }
}
