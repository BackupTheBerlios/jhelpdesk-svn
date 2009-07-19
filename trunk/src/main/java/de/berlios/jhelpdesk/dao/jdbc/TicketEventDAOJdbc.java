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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.TicketEventDAO;
import de.berlios.jhelpdesk.model.EventType;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketEvent;
import de.berlios.jhelpdesk.model.User;

@Repository("ticketEventDAO")
public class TicketEventDAOJdbc extends AbstractJdbcTemplateSupport implements TicketEventDAO {
    
    private static Log log = LogFactory.getLog(TicketEventDAOJdbc.class);

    @Autowired
    public TicketEventDAOJdbc(DataSource dataSource) {
        super(dataSource);
    }

	public List<TicketEvent> getByTicket( Ticket ticket ) {
		log.debug("getByTicket(Ticket) => " + ticket.getTicketId());
		return getByTicket( ticket.getTicketId() );
	}

	@SuppressWarnings("unchecked")
    public List<TicketEvent> getByTicket(Long ticketId) {
		log.debug("getByTicket(Long ticketId) => " + ticketId);
		return getJdbcTemplate().query(
			"SELECT * FROM ticket_event WHERE ticket_id=?",
			new Object[] {
				ticketId
			},
			new TicketEventRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<TicketEvent> getByDate( Date date ) {
		log.debug("getByDate(Date) => " + date);
		return getJdbcTemplate().query(
			"SELECT * FROM ticket_event WHERE event_date=?",
			new Object[] {
				date
			},
			new TicketEventRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<TicketEvent> getByDate( Date from, Date to ) {
        log.debug("getByDate( Date from, Date to ) => " + from + ", " + to);
		return getJdbcTemplate().query(
			"SELECT * FROM ticket_event WHERE event_date BETWEEN ? AND ?",
			new Object[] {
				from,
				to
			},
			new TicketEventRowMapper()
		);
	}

	public TicketEvent getById( Long eventId ) {
        log.debug("getById( Long eventId ) => " + eventId);
        return (TicketEvent) getJdbcTemplate().queryForObject(
			"SELECT * FROM ticket_event WHERE event_id=?",
			new Object[] {
				eventId,
			},
			new TicketEventRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
    public List<EventType> getByType(EventType type) {
        log.debug("getByType( HDEventType type ) => " + type.toInt());
		return getJdbcTemplate().query(
			"SELECT * FROM ticket_event WHERE =?",
			new Object[] {
				type.toInt(),
			},
			new TicketEventRowMapper()
		);
	}

    public List<TicketEvent> getByUser(User user) {
        log.debug("getByUser( HDUser user ) => " + user.getUserId());
		return getByUser( user.getUserId() );
	}

	@SuppressWarnings("unchecked")
    public List<TicketEvent> getByUser(Long userId) {
        log.debug("getByUser( Long userId ) => " + userId);
		return getJdbcTemplate().query(
			"SELECT * FROM ticket_event WHERE user_id=?",
			new Object[] {
				userId,
			},
			new TicketEventRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
    public List<TicketEvent> getLastFewEvents(int howMuch) {
        log.debug("getLastFewEvents( int howMuch ) => " + howMuch);
		return getJdbcTemplate().query(
			"SELECT * FROM ticket_event ORDER BY event_date DESC LIMIT ?",
			new Object[] {
				howMuch
			},
			new TicketEventRowMapper()
		);
	}

    public void save(TicketEvent event) {
        if (event.getTicketEventId() == null) {
            log.debug("save(TicketEvent) => " + event);
			getJdbcTemplate().update(
				"INSERT INTO ticket_event(ticket_id,event_type,user_id,event_date,event_subject) VALUES(?,?,?,?,?)",
				new Object[] {
					event.getTicketId(),
					event.getEventType(),
					event.getEvtAuthor().getUserId(),
					event.getEvtDate(),
					event.getEvtSubject()
				}
			);
		} else {
			log.fatal( "TicketEvent object is not updatable." );
			throw new RuntimeException( "TicketEvent object is not updatable." );
		}
	}
	
    class TicketEventRowMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int row) throws SQLException {
            TicketEvent evt = new TicketEvent();
            evt.setTicketEventId(rs.getLong("event_id"));
            evt.setTicketId(rs.getLong("ticket_id"));
            evt.setEvtSubject(rs.getString("event_subject"));
            evt.setEventType(EventType.fromInt(rs.getInt("event_type")));
            evt.setEvtDate(rs.getDate("event_date"));
            // evt.setEvtAuthor( )
            return evt;
        }
	}
}