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
import java.sql.Types;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.model.Bug;
import de.berlios.jhelpdesk.model.BugCategory;
import de.berlios.jhelpdesk.model.BugComment;
import de.berlios.jhelpdesk.model.BugEvent;
import de.berlios.jhelpdesk.model.BugPriority;
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.model.EventType;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.form.ShowBugsFilterForm;


public class BugDAOJdbc extends JdbcDaoSupport implements BugDAO {

	private static Log log = LogFactory.getLog(  BugDAOJdbc.class );
	
	@SuppressWarnings("unchecked")
	public Bug getBugById( final Long bugId ) {
		log.debug( "getBugById( final Long bugId ) => " + bugId );		
		Bug bug = ( Bug ) getJdbcTemplate().queryForObject(
			"SELECT hd_bug.*,hd_bug_desc.description " +
			"FROM hd_bug LEFT OUTER JOIN hd_bug_desc ON hd_bug.bug_id=hd_bug_desc.bug_id WHERE hd_bug.bug_id=?",
			new Object[] {
				bugId
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug hdBug = new Bug();
					hdBug.setBugId( rs.getLong( "bug_id" ) );
					hdBug.setSubject( rs.getString( "subject" ) );
					hdBug.setCreateDate( rs.getDate( "create_date" ) );
					hdBug.setInputer( new User( rs.getLong( "inputer" ), null,null,null ) );
					hdBug.setNotifier( new User( rs.getLong( "notifyier" ), null,null,null ) );
					if( rs.getLong( "saviour" ) != 0 ) {
						hdBug.setSaviour( new User( rs.getLong( "saviour" ), null,null,null ) );
					}
					hdBug.setDescription( rs.getString( "description" ) );
					return hdBug;
				}
			}
		);
		bug.setNotifier(
			(User)getJdbcTemplate().queryForObject(
				"SELECT user_id,first_name,last_name,login,email,mobile,phone FROM hd_user WHERE user_id=?",
				new Object[] { 
					bug.getNotifier().getUserId()
				},
				new RowMapper() {
					public Object mapRow( ResultSet rs, int row ) throws SQLException {
						User user = new User();
						user.setUserId( rs.getLong( "user_id" ) );
						user.setFirstName( rs.getString("first_name") );
						user.setLastName( rs.getString("last_name") );
						user.setLogin( rs.getString("login") );
						user.setEmail( rs.getString("email") );
						user.setPhone( rs.getString("phone") );
						user.setMobile( rs.getString("mobile") );
						return user;
					}
				}
			)
		);
		
		bug.setInputer(
			(User)getJdbcTemplate().queryForObject(
				"SELECT user_id,first_name,last_name,login,email,mobile,phone FROM hd_user WHERE user_id=?",
				new Object[] { 
					bug.getInputer().getUserId()
				},
				new RowMapper() {
					public Object mapRow( ResultSet rs, int row ) throws SQLException {
						User user = new User();
						user.setUserId( rs.getLong( "user_id" ) );
						user.setFirstName( rs.getString("first_name") );
						user.setLastName( rs.getString("last_name") );
						user.setLogin( rs.getString("login") );
						user.setEmail( rs.getString("email") );
						user.setPhone( rs.getString("phone") );
						user.setMobile( rs.getString("mobile") );
						return user;
					}
				}
			)
		);
		
		if( bug.getSaviour() != null ) {
			bug.setSaviour(
				(User)getJdbcTemplate().queryForObject(
					"SELECT user_id,first_name,last_name,login,email,mobile,phone FROM hd_user WHERE user_id=?",
					new Object[] { 
						bug.getSaviour().getUserId()
					},
					new RowMapper() {
						public Object mapRow( ResultSet rs, int row ) throws SQLException {
							User user = new User();
							user.setUserId( rs.getLong( "user_id" ) );
							user.setFirstName( rs.getString("first_name") );
							user.setLastName( rs.getString("last_name") );
							user.setLogin( rs.getString("login") );
							user.setEmail( rs.getString("email") );
							user.setPhone( rs.getString("phone") );
							user.setMobile( rs.getString("mobile") );
							return user;
						}
					}
				)		
			);
		}
		
		bug.setEvents( 
			new HashSet(
				getJdbcTemplate().query(
					// TODO: dorzucic wyswietlania kmiecia (autora zdarzenia)
					"SELECT * FROM hd_bug_event WHERE bug_id=?", 
					new Object[] {
						bugId
					},
					new RowMapper() {
						public Object mapRow( ResultSet rs, int row ) throws SQLException {
							BugEvent evt = new BugEvent();
							evt.setBugEventId( rs.getLong( "event_id" ) );
							evt.setBugId( bugId );
							evt.setEvtSubject( rs.getString( "event_subject" ) );
							evt.setEventType( EventType.fromInt( rs.getInt( "event_type" ) ) );
							evt.setEvtDate( rs.getDate( "event_date" ) );
							return evt;
						}
					}
				)
			)
		);
		bug.setComments(
			new HashSet(
				getJdbcTemplate().query(
					//TODO: dorzucic wyswietlania kmiecia (autora komentarza)
					"SELECT hd_bug_comment.* ,hd_user.first_name,hd_user.last_name,hd_user.user_id,hd_user.login " +
					"FROM hd_bug_comment " + 
					"LEFT OUTER JOIN hd_user " +
					"ON hd_bug_comment.comment_author=hd_user.user_id " +
					"WHERE bug_id=?",
					new Object[] {
						bugId
					},
					new RowMapper() {
						public Object mapRow( ResultSet rs, int row ) throws SQLException {
							BugComment comment = new BugComment();
							comment.setBugId( bugId );
							comment.setBugCommentId( rs.getLong( "comment_id" ) );
							comment.setCommentDate( rs.getDate( "comment_date" ) );
							comment.setCommentText( rs.getString( "comment_text" ) );
							comment.setNotForPlainUser( rs.getBoolean( "not_for_plain_user" ) );
							comment.setCommentAuthor( 
								new User(
									rs.getLong( "user_id" ), rs.getString( "login" ),
									rs.getString( "first_name" ), rs.getString( "last_name" ) 
								)
							);
							return comment;
						}
					}
				)
			)
		);
		return bug;
	}

	public Bug getLastAddedBug() {
		// TODO: zaimplementowac zeby dzialal powiadamiacz
		// SELECT * FROM hd_bug ORDER BY bug_id DESC LIMIT 1
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Bug> getBugsByDate( Date date ) {
		return getJdbcTemplate().query(
			"SELECT....",
			new Object[] { date },
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug bug = new Bug();
					bug.setBugId( rs.getLong( "bug_id" ) );
					bug.setSubject( rs.getString( "bug_subject" ) );
					// itd...
					return bug;
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Bug> getBugsByStatus( BugStatus bugStatus ) {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug WHERE bug_status=? " +
			"ORDER BY create_date DESC",
			new Object[] {
				bugStatus.toInt()
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug hdBug = new Bug();
					hdBug.setBugId( rs.getLong( "bug_id" ) );
					hdBug.setSubject( rs.getString( "subject" ) );
					hdBug.setCreateDate( rs.getDate( "create_date" ) );
					return hdBug;
				}
			}
		);
	}
	
	@SuppressWarnings("unchecked")
	public List<Bug> getBugsByStatus( BugStatus bugStatus, int howMuch ) {
		return getJdbcTemplate().query(
			"SELECT * FROM bug_list_view WHERE b_status=? " +
			"ORDER BY b_create_date DESC LIMIT ?",
			new Object[] {
				bugStatus.toInt(),
				howMuch
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug hdBug = new Bug();
					hdBug.setBugId( rs.getLong( "b_id" ) );
					hdBug.setSubject( rs.getString( "b_subject" ) );
					/* zgłaszający */
					hdBug.setNotifier(
						new User(
							rs.getLong( "n_id" ), 
							rs.getString( "n_login" ),
							rs.getString( "n_first_name"), 
							rs.getString( "n_last_name") 
						)
					);
					/* wprowadzajacy */
					hdBug.setInputer(
						new User(
							rs.getLong( "i_id" ),
							rs.getString( "i_login" ),
							rs.getString( "i_first_name" ),
							rs.getString( "i_last_name" )
						)
					);
					/* rozwiazujacy */
					hdBug.setSaviour(
						new User(
							rs.getLong( "s_id" ),
							rs.getString( "s_login" ),
							rs.getString( "s_first_name" ),
							rs.getString( "s_last_name" )
						)
					);
					/* bugCategory */
					BugCategory category = new BugCategory();
					category.setCategoryName( rs.getString( "c_name" ) );
					hdBug.setBugCategory( category );
					/* bugPriority */
					hdBug.setBugPriority( BugPriority.fromInt( rs.getInt( "p_id" ) ) );
					hdBug.setCreateDate( rs.getDate( "b_create_date" ) );
					return hdBug;
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Bug> getBugsByPriority( BugPriority bugPriority ) {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug WHERE bug_priority=? " +
			"ORDER BY create_date DESC",
			new Object[] {
				bugPriority.getPriorityId()
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug hdBug = new Bug();
					hdBug.setBugId( rs.getLong( "bug_id" ) );
					hdBug.setSubject( rs.getString( "subject" ) );
					return hdBug;
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Bug> getBugsByCategory( BugCategory bugCategory ) {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug WHERE bug_category=? " +
			"ORDER BY create_date DESC",
			new Object[] {
				bugCategory.getBugCategoryId()
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug hdBug = new Bug();
					hdBug.setBugId( rs.getLong( "bug_id" ) );
					hdBug.setSubject( rs.getString( "subject" ) );
					return hdBug;
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Bug> getBugsNotifyiedByUser( User user ) {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug WHERE notifyier=? " +
			"ORDER BY create_date DESC",
			new Object[] {
				user.getUserId()
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug hdBug = new Bug();
					hdBug.setBugId( rs.getLong( "bug_id" ) );
					hdBug.setSubject( rs.getString( "subject" ) );
					return hdBug;
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Bug> getBugsResolvedByUser( User user ) {
		log.debug( "getBugsResolvedByUser( HDUser user ) => " + user.getUserId() );
		// TODO: trzeba to zaimplementowac zeby kontroler dzialal
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug WHERE",
			new Object[] {
				user.getUserId()
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug hdBug = new Bug();
					hdBug.setBugId( rs.getLong( "bug_id" ) );
					hdBug.setSubject( rs.getString( "subject" ) );
					return hdBug;
				}	
			}
		);
	}

	public void removeBug( Bug bug2Del ) throws Exception {
		remove( bug2Del.getBugId() );
	}

	public void remove( Long bug2DelId ) {
		getJdbcTemplate().update(
			"DELETE FROM hd_bug WHERE bug_id=?", 
			new Object[] { 
				bug2DelId 
			}
		);
	}

	public void save( final Bug bugToSave ) throws Exception {
		getJdbcTemplate().execute(
			new ConnectionCallback() {
				public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
					conn.setAutoCommit( false );
					PreparedStatement pstmt = 
						conn.prepareStatement(
							"INSERT INTO hd_bug(bug_id,add_phone,bug_category,bug_priority,bug_status," +
							"saviour,notifyier,inputer,create_date,description,step_by_step,subject) " +
							"VALUES(nextval('bug_id_seq'),?,?,?,?,?,?,?,?,?,?,?)"
						);
					pstmt.setString( 1, bugToSave.getAddPhone() );
					pstmt.setLong( 2, bugToSave.getBugCategory().getBugCategoryId() );
					pstmt.setLong( 3, bugToSave.getBugPriority().getPriorityId() );
					pstmt.setLong( 4, bugToSave.getBugStatus().getStatusId() );
					
					if( bugToSave.getSaviour() != null )
						pstmt.setLong( 5, bugToSave.getSaviour().getUserId() );
					else
						pstmt.setNull( 5, Types.INTEGER );
					
					pstmt.setLong( 6, bugToSave.getNotifier().getUserId() );
					pstmt.setLong( 7, bugToSave.getInputer().getUserId() );
					
					pstmt.setDate( 8, new java.sql.Date( bugToSave.getCreateDate().getTime() ) );
					if( bugToSave.getDescription() != null )
						pstmt.setString( 9, bugToSave.getDescription() );
					else
						pstmt.setNull( 9, Types.VARCHAR );
					
					if( bugToSave.getStepByStep() != null )
						pstmt.setString( 10, bugToSave.getStepByStep() );
					else
						pstmt.setNull( 10, Types.VARCHAR );
					if( bugToSave.getSubject() != null )
						pstmt.setString( 11, bugToSave.getSubject() );
					else
						pstmt.setNull( 11, Types.VARCHAR );
					pstmt.executeUpdate();
					
					Statement stmt = 
						conn.createStatement( 
							ResultSet.TYPE_SCROLL_INSENSITIVE, 
							ResultSet.CONCUR_READ_ONLY 
						);
					ResultSet rs = stmt.executeQuery( "SELECT currval('bug_id_seq')" );
					if( rs.first() ) {
						bugToSave.setBugId( rs.getLong( 1 ) );
					}
					conn.commit();
					return null;
				}
			}
		);
	}
	
	@SuppressWarnings("unchecked")
	public List<Bug> getAllBugs() {
		return getJdbcTemplate().query(
			new QueryBuilder().getAllQuery(),
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug b = new Bug();
					b.setBugId( rs.getLong( "b_id" ) );
					b.setSubject( rs.getString( "b_subject" ) );
					b.setDescription( rs.getString( "b_description") );
					b.setCreateDate( rs.getDate( "b_create_date" ) );
					b.setBugStatus( BugStatus.fromInt( rs.getInt( "s_id" ) ) );
					b.setBugCategory( 
						new BugCategory(
							-1,
							rs.getString( "c_name" )
						) 
					);
//						b.setPriority( 
//							new BugPriority(
//								-1,
//								rs.getString( "p_name" )
//							)
//						);
					//b.setNotifierId( new Long( rs.getInt( "n_id" ) ) );
					//b.setNotifier( rs.getString( "notifier_login" ) );
					return b;
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Bug> getBugsWithFilter( ShowBugsFilterForm filterForm, int limit, long offset ) {
		return getJdbcTemplate().query(
			new QueryBuilder( filterForm ).getFilteredQuery(false),
			new Object[] {
				limit,
				offset
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Bug hdBug = new Bug();
					hdBug.setBugId( rs.getLong( "b_id" ) );
					hdBug.setSubject( rs.getString( "b_subject" ) );
					hdBug.setDescription( rs.getString( "b_description") );
					hdBug.setCreateDate( rs.getDate( "b_create_date" ) );
					hdBug.setBugStatus( BugStatus.fromInt( rs.getInt( "b_status" ) ) );
					hdBug.setBugCategory( 
						new BugCategory(
							rs.getInt( "c_id" ),
							rs.getString( "c_name" )
						) 
					);
					hdBug.setBugPriority( BugPriority.fromInt( rs.getInt( "p_id" ) ) );
					/* zgłaszający */
					hdBug.setNotifier(
						new User(
							rs.getLong( "n_id" ), 
							rs.getString( "n_login" ),
							rs.getString( "n_first_name"), 
							rs.getString( "n_last_name") 
						)
					);
					/* wprowadzajacy */
					hdBug.setInputer(
						new User(
							rs.getLong( "i_id" ),
							rs.getString( "i_login" ),
							rs.getString( "i_first_name" ),
							rs.getString( "i_last_name" )
						)
					);
					/* rozwiazujacy */
					hdBug.setSaviour(
						new User(
							rs.getLong( "s_id" ),
							rs.getString( "s_login" ),
							rs.getString( "s_first_name" ),
							rs.getString( "s_last_name" )
						)
					);
					return hdBug;
				}
			}
		);
	}

	public Integer countBugsWithFilter( ShowBugsFilterForm filterForm ) {
		return getJdbcTemplate().queryForInt( 
			new QueryBuilder( filterForm ).getFilteredQuery( true )
		);
	}

	public void addComment( BugComment comm ) {
		getJdbcTemplate().update(
			"INSERT INTO hd_bug_comment( comment_id, bug_id, comment_author, comment_date, comment_text, not_for_plain_user ) " +
			"VALUES (nextval('bug_comment_id_seq'),?,?,?,?,?)",
			new Object[] {
				comm.getBugId(),
				comm.getCommentAuthor().getUserId(),
				comm.getCommentDate(),
				comm.getCommentText(),
				comm.isNotForPlainUser()
			}
		);
	}
}
class QueryBuilder {
	private static Log log = LogFactory.getLog( QueryBuilder.class );
	ShowBugsFilterForm filter;
	
	public QueryBuilder() {
		
	}
	
	public QueryBuilder( ShowBugsFilterForm filterForm ) {
		filter = filterForm;
	}
	
	public String getFilteredQuery( boolean countOnly ) {
		StringBuffer sb = new StringBuffer();
		if( !countOnly )
			sb.append( "SELECT * FROM bug_list_view WHERE " );
		else
			sb.append( "SELECT COUNT(*) FROM bug_list_view WHERE " );

		if( ( filter.getStartDate() != null ) && ( filter.getStartDate().length() > 0 ) ) {
			sb.append( " b_create_date >= '" + filter.getStartDate() + "' AND " );
		}
		if( ( filter.getEndDate() != null ) && ( filter.getEndDate().length() > 0 ) ) {
			sb.append( " b_create_date <= '" + filter.getEndDate() + "' AND " );
		}
		if( ( filter.getCategories() != null ) && ( filter.getCategories().size() > 0 ) ) {
			String cat_ = "";
			for( BugCategory cat : filter.getCategories() ) {
				cat_ += cat.getBugCategoryId().intValue() + ",";
			}
			cat_ += "-1";
			sb.append( " c_id IN(" + cat_ + ") AND " );
		}
		
		if( ( filter.getNotifyiers() != null) && ( filter.getNotifyiers().size() > 0 ) ) {
			String not_ = "";
			for( User user : filter.getNotifyiers() ) {
				not_ += user.getUserId().intValue() + ",";
			}
			not_ += "-1";
			sb.append( " n_id IN(" + not_ + ") AND " );
		}
		
		if( ( filter.getSaviours() != null ) && ( filter.getSaviours().size() > 0 ) ) {
			String sav_ = "";
			for( User user : filter.getSaviours() ) {
				sav_ += user.getUserId().intValue() + ",";
			}
			sav_ += "-1";
			sb.append( " s_id IN(" + sav_ + ") AND " );
		}
		
		if( ( filter.getPriorities() != null ) && ( filter.getPriorities().size() > 0 ) ) {
			String priors_ = "";
			for( BugPriority pr : filter.getPriorities() ) {
				priors_ += pr.getPriorityId() + ",";
			}
			priors_ += "-1";
			sb.append( " p_id IN(" + priors_ + ") AND " );
		}
		
		if( ( filter.getStatuses() != null ) && ( filter.getStatuses().size() > 0 ) ) {
			String stat_ = "";
			for( BugStatus stat : filter.getStatuses() ) {
				stat_ += stat.getStatusId() + ","; 
			}
			stat_ += "-1";
			sb.append( " b_status IN(" + stat_ + ") AND " );
		}
		
		sb.append( " true " );
		if( !countOnly )
			sb.append( "ORDER BY b_create_date DESC LIMIT ? OFFSET ?" );
		
		log.debug( sb.toString() );
		return sb.toString();
	}
	
	public String getAllQuery() {
		StringBuffer sb = new StringBuffer();
		sb.append( "SELECT * FROM bug_list_view ORDER BY b_create_date DESC" );
		return sb.toString();
	}
}