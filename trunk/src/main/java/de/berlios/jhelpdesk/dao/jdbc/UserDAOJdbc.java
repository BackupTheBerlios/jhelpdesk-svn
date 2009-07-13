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
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;

/**
 * @inheritDoc 
 * @author jjhop
*/
@Repository("userDAO")
public class UserDAOJdbc extends AbstractJdbcTemplateSupport implements UserDAO {

    private static Log log = LogFactory.getLog(UserDAOJdbc.class);

    @Autowired
    public UserDAOJdbc(DataSource dataSource) {
        super(dataSource);
    }
			
	public boolean checkLoginAndPassw( String login, String passw ) {
		return 
			getJdbcTemplate().queryForLong(
				"SELECT COUNT(*) AS COUNT FROM hd_user WHERE login=? AND passw=?",
				new Object[] {
					login,
					DigestUtils.shaHex( passw )
				}
			) == 1 ? true : false;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_user",
			new HDUserRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUserWithLastNameStartsWithLetter( String letter ) {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_user WHERE last_name ~* '^?'",
			new Object[] { letter },
			new HDUserRowMapper()
		);
	}

	public User getById( Long id ) {
		return ( User )getJdbcTemplate().queryForObject(
			"SELECT * FROM hd_user WHERE user_id=?",
			new Object[] { id },
			new HDUserRowMapper()
		);
	}

	public User getById( int id ) {
		return getById( new Long( id ) );
	}

	public User getByLogin( String login ) {
		if( getJdbcTemplate().queryForLong( 
				"SELECT COUNT(*) FROM hd_user WHERE login=?", 
				new Object[] { login } ) > 0 ) {
			return ( User )getJdbcTemplate().queryForObject(
				"SELECT * FROM hd_user WHERE login=?",
				new Object[] { login },
				new HDUserRowMapper()
			);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<User> getByRole( Role role ) {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_user WHERE role=?",
			new Object[] {
				role.toInt()
			},
			new HDUserRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<User> getSaviours() {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_user WHERE role=?",
			new Object[] {
				Role.BUGKILLER.toInt()
			},
			new HDUserRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<User> getSavioursWithLastNameStartsWithLetter( String letter ) {
		log.info( letter );
		return getJdbcTemplate().query(
			"SELECT * FROM hd_user WHERE role=? AND last_name ~* '^[" + letter + "]'",
			new Object[] {
				Role.BUGKILLER.toInt()//,
			},
			new HDUserRowMapper()
		);
	}

	public void loginUser( String login, Date date ) {
		getJdbcTemplate().update(
			"UPDATE hd_user SET last_login=? WHERE login=?",
			new Object[] {
				date,
				login
			}
		);
	}

	public void saveOrUpdate( final User user ) {
		if( user.getUserId() != null ) {
			// update
			getJdbcTemplate().update(
				"UPDATE hd_user SET login=?, first_name=?, last_name=?, " +
				"phone=?, mobile=?, email=?, role=?, is_active=? WHERE user_id=?",
				new Object[] {
					user.getLogin(),
					user.getFirstName(),
					user.getLastName(),
					user.getPhone(),
					user.getMobile(),
					user.getEmail(),
					user.getUserRole().toInt(),
					user.isActive(),
					user.getUserId()
				}
			);
		} else {
			// saveOrUpdate
			getJdbcTemplate().execute(
				new ConnectionCallback() {
					public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
						conn.setAutoCommit( false );
						PreparedStatement pstmt = 
							conn.prepareStatement(
								"INSERT INTO hd_user(user_id,login,first_name,last_name,phone," +
								"mobile,email,role,is_active) " +
								"VALUES(nextval('user_id_seq'),?,?,?,?,?,?,?,?)"
							);
						pstmt.setString( 1, user.getLogin() );
						pstmt.setString( 2, user.getFirstName() );
						pstmt.setString( 3, user.getLastName() );
						pstmt.setString( 4, user.getPhone() );
						pstmt.setString( 5, user.getMobile() );
						pstmt.setString( 6, user.getEmail() );
						pstmt.setLong( 7, user.getUserRole().toInt() );
						pstmt.setBoolean( 8, user.isActive() );
						pstmt.executeUpdate();
						
						Statement stmt = 
							conn.createStatement( 
								ResultSet.TYPE_SCROLL_INSENSITIVE, 
								ResultSet.CONCUR_READ_ONLY 
							);
						ResultSet rs = stmt.executeQuery( "SELECT currval('user_id_seq')" );
						if( rs.first() ) {
							user.setUserId( rs.getLong( 1 ) );
						}
						conn.commit();
						return null;
					}
				}
			);
		}
	}
	
	class HDUserRowMapper implements RowMapper {
		public Object mapRow( ResultSet rs, int row ) throws SQLException {
			User user = new User();
			user.setUserId( rs.getLong( "user_id" ) );
			user.setFirstName( rs.getString( "first_name" ) );
			user.setLastName( rs.getString( "last_name" ) );
			user.setEmail( rs.getString( "email" ) );
			user.setPhone( rs.getString( "phone" ) );
			user.setMobile( rs.getString( "mobile" ) );
			user.setLogin( rs.getString( "login" ) );
			user.setUserRole( Role.fromInt( rs.getInt( "role" ) ) );
			user.setActive( rs.getBoolean( "is_active" ) );
			return user;
		}
	}	
}
