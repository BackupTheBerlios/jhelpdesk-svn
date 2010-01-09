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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;

@Deprecated
public class UserDAOJdbc extends AbstractJdbcTemplateSupport implements UserDAO {

    private static Log log = LogFactory.getLog(UserDAOJdbc.class);

    @Autowired
    public UserDAOJdbc(DataSource dataSource) {
        super(dataSource);
    }

    public boolean authenticate(String login, String passw) {
        return getJdbcTemplate().queryForLong(
            "SELECT COUNT(*) AS COUNT FROM users WHERE login=? AND passw=?",
            new Object[]{login, passw}
        ) == 1 ? true : false;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUser() {
        return getJdbcTemplate().query(
            "SELECT * FROM users",
            new UserRowMapper()
        );
    }

    public User getById(Long id) {
        return (User) getJdbcTemplate().queryForObject(
            "SELECT * FROM users WHERE user_id=?",
            new Object[]{id},
            new UserRowMapper()
        );
    }

    public User getById(int id) {
        return getById(new Long(id));
    }

    public User getByLogin(String login) {
        if (getJdbcTemplate().queryForLong(
                "SELECT COUNT(*) FROM users WHERE login=?",
                new Object[]{login}) > 0) {
            return (User) getJdbcTemplate().queryForObject(
                "SELECT * FROM users WHERE login=?",
                new Object[]{login},
                new UserRowMapper()
            );
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<User> getByRole(Role role) {
        return getJdbcTemplate().query(
            "SELECT * FROM users WHERE role=?",
            new Object[]{role.toInt()},
            new UserRowMapper()
        );
    }

    public void loginUser(String login, Date date) {
        getJdbcTemplate().update(
            "UPDATE users SET last_login=? WHERE login=?",
            new Object[]{date,login}
        );
    }

    public void saveOrUpdate(final User user) {
        if (user.getUserId() != null) {
            getJdbcTemplate().update(
                "UPDATE users SET login=?, passw=?, first_name=?, last_name=?, "
                + "phone=?, mobile=?, email=?, role=?, is_active=? WHERE user_id=?",
                new Object[]{
                    user.getLogin(),
                    user.getPassword(),
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
            getJdbcTemplate().execute(
                new ConnectionCallback() {
                    public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
                        conn.setAutoCommit(false);
                        PreparedStatement pstmt =
                            conn.prepareStatement(
                            "INSERT INTO users(user_id,login,passw,first_name,last_name,phone,"
                            + "mobile,email,role,is_active) "
                            + "VALUES(nextval('user_id_seq'),?,?,?,?,?,?,?,?,?)");
                        pstmt.setString(1, user.getLogin());
                        pstmt.setString(2, user.getPassword());
                        pstmt.setString(3, user.getFirstName());
                        pstmt.setString(4, user.getLastName());
                        pstmt.setString(5, user.getPhone());
                        pstmt.setString(6, user.getMobile());
                        pstmt.setString(7, user.getEmail());
                        pstmt.setLong(8, user.getUserRole().toInt());
                        pstmt.setBoolean(9, user.isActive());
                        pstmt.executeUpdate();

                        Statement stmt =
                            conn.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs = stmt.executeQuery("SELECT currval('user_id_seq')");
                        if (rs.first()) {
                            user.setUserId(rs.getLong(1));
                        }
                        conn.commit();
                        return null;
                    }
                }
            );
        }
    }

    class UserRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int row) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setMobile(rs.getString("mobile"));
            user.setLogin(rs.getString("login"));
            user.setUserRole(Role.fromInt(rs.getInt("role")));
            user.setActive(rs.getBoolean("is_active"));
            return user;
        }
    }
}
