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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import de.berlios.jhelpdesk.dao.BugEventDAO;
import de.berlios.jhelpdesk.model.Bug;
import de.berlios.jhelpdesk.model.BugEvent;
import de.berlios.jhelpdesk.model.EventType;
import de.berlios.jhelpdesk.model.User;

public class BugEventDAOJdbc extends JdbcDaoSupport implements BugEventDAO {

	private static Log log = LogFactory.getLog(  BugEventDAOJdbc.class );
	
	public List<BugEvent> getByBug( Bug bug ) {
		log.debug( "getByBug( HDBug bug ) => " + bug.getBugId() );
		return getByBug( bug.getBugId() );
	}

	@SuppressWarnings("unchecked")
	public List<BugEvent> getByBug( Long bugId ) {
		log.debug( "getByBug( Long bugId ) => " + bugId );
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug_event WHERE bug_id=?",
			new Object[] {
				bugId
			},
			new HDBugEventRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<BugEvent> getByDate( Date date ) {
		log.debug( "getByDate( Date date ) => " + date );
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug_event WHERE event_date=?",
			new Object[] {
				date
			},
			new HDBugEventRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<BugEvent> getByDate( Date from, Date to ) {
		log.debug( "getByDate( Date from, Date to ) => " + from + ", " + to );
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug_event WHERE event_date BETWEEN ? AND ?",
			new Object[] {
				from,
				to
			},
			new HDBugEventRowMapper()
		);
	}

	public BugEvent getById( Long eventId ) {
		log.debug( "getById( Long eventId ) => " + eventId );
		return ( BugEvent ) getJdbcTemplate().queryForObject(
			"SELECT * FROM hd_bug_event WHERE event_id=?",
			new Object[] {
				eventId,
			},
			new HDBugEventRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<EventType> getByType( EventType type ) {
		log.debug( "getByType( HDEventType type ) => " + type.toInt() );
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug_event WHERE =?",
			new Object[] {
				type.toInt(),
			},
			new HDBugEventRowMapper()
		);
	}

	public List<BugEvent> getByUser( User user ) {
		log.debug( "getByUser( HDUser user ) => " + user.getUserId() );
		return getByUser( user.getUserId() );
	}

	@SuppressWarnings("unchecked")
	public List<BugEvent> getByUser( Long userId ) {
		log.debug( "getByUser( Long userId ) => " + userId );
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug_event WHERE user_id=?",
			new Object[] {
				userId,
			},
			new HDBugEventRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<BugEvent> getLastFewEvents( int howMuch ) {
		log.debug( "getLastFewEvents( int howMuch ) => " + howMuch );
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug_event ORDER BY event_date DESC LIMIT ?",
			new Object[] {
				howMuch
			},
			new HDBugEventRowMapper()
		);
	}

	public void save( BugEvent event ) {
		if( event.getBugEventId() == null ) {
			log.debug( "save( HDBugEvent event ) => " + event );
			getJdbcTemplate().update(
				"INSERT INTO hd_bug_event(bug_id,event_type,user_id,event_date,event_subject) VALUES(?,?,?,?,?)",
				new Object[] {
					event.getBugId(),
					event.getEventType(),
					event.getEvtAuthor().getUserId(),
					event.getEvtDate(),
					event.getEvtSubject()
				}
			);
		} else {
			log.fatal( "HDBugEvent object is not updatable." );
			throw new RuntimeException( "HDBugEvent object is not updatable." );
		}
	}
	
	class HDBugEventRowMapper implements RowMapper {
		public Object mapRow( ResultSet rs, int row ) throws SQLException {
			BugEvent evt = new BugEvent();
			evt.setBugEventId( rs.getLong( "event_id" ) );
			evt.setBugId( rs.getLong( "bug_id" ) );
			evt.setEvtSubject( rs.getString( "event_subject" ) );
			evt.setEventType( EventType.fromInt( rs.getInt( "event_type" ) ) );
			evt.setEvtDate( rs.getDate( "event_date" ) );
			//evt.setEvtAuthor(  )
			return evt;
		}
	}
}