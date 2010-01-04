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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.ArticleDAO;
import de.berlios.jhelpdesk.model.Article;
import de.berlios.jhelpdesk.model.User;

/**
 * @author jjhop
 */
//@Repository("articleDAO")
//@Qualifier("jdbc")
public class ArticleDAOJdbc extends AbstractJdbcTemplateSupport implements ArticleDAO {

    @Autowired
    public ArticleDAOJdbc(DataSource dataSource) {
        super(dataSource);
    }

    public Article getById(Long pk) {
        return (Article) getJdbcTemplate().queryForObject(
            "SELECT * FROM article WHERE article_id=?",
            new Object[]{pk},
            new RowMapper() {
                public Object mapRow(ResultSet rs, int row) throws SQLException {
                    Article article = new Article();
                    article.setArticleId(rs.getLong("article_id"));
                    article.setTitle(rs.getString("title"));
                    article.setCreateDate(rs.getDate("create_date"));
                    article.setLead(rs.getString("lead"));
                    article.setBody(rs.getString("body"));
                    article.setAssociatedTickets(null);
                    // user, associated tickets and comments not yet implemented
                    article.setAuthor(null);
                    article.setComments(null);
                    article.setAssociatedTickets(null);
                    return article;
                }
            }
        );
    }

    public void delete(Article article) {
        delete(article.getArticleId());
    }

    public void delete(Long articleId) {
        getJdbcTemplate().update(
            "DELETE FROM article WHERE article_id=?",
            new Object[]{articleId}
        );
    }

    public void saveOrUpdate(final Article article) {
        if (article.getArticleId() != null) {
            // if articleId is set to null we have a new object
            // and we have to save it as new one (and return with
            // articleId set to database id)
            getJdbcTemplate().update(
                "UPDATE article SET title=?,article_category_id=?," +
                "create_date=?,lead=?,body=?,user_id=? WHERE article_id=?",
                new Object[]{
                    article.getTitle(), article.getArticleSectionId(),
                    article.getCreateDate(), article.getLead(), article.getBody(),
                    article.getAuthor().getUserId(), article.getArticleId()
                }
            );
        } else {
            // and if articleId is set we have an existing
            // object and we have to update it
            getJdbcTemplate().execute(
                new ConnectionCallback() {
                    public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
                        conn.setAutoCommit(false);
                        PreparedStatement pstmt =
                            conn.prepareStatement(
                            "INSERT INTO article(article_id,title,article_category_id," +
                            "create_date,lead,body,user_id) VALUES(nextval('article_id_seq'),?,?,?,?,?,?)");
                        pstmt.setString(1, article.getTitle());
                        pstmt.setLong(2, article.getArticleSectionId());
                        pstmt.setDate(3, new Date(System.currentTimeMillis()));
                        pstmt.setString(4, article.getLead());
                        pstmt.setString(5, article.getBody());
                        pstmt.setLong(6, article.getAuthor().getUserId());
                        pstmt.executeUpdate();
                        Statement stmt =
                                conn.createStatement(
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs = stmt.executeQuery("SELECT currval('article_id_seq')");
                        if (rs.first()) {
                            article.setArticleId(rs.getLong(1));
                        }
                        conn.commit();
                        return null;
                    }
                }
            );
        }
    }

    @SuppressWarnings("unchecked")
    public List<Article> getForSection(Long categoryId) {
        return getJdbcTemplate().query(
            "SELECT article.*, users.login,users.first_name, users.last_name " +
            "FROM article, users " +
            "WHERE article_category_id=? AND article.user_id=users.user_id",
            new Object[]{categoryId},
            new RowMapper() {
                public Object mapRow(ResultSet rs, int row) throws SQLException {
                    Article article = new Article();
                    article.setArticleId(rs.getLong("article_id"));
                    article.setArticleSectionId(rs.getLong("article_category_id"));
                    article.setTitle(rs.getString("title"));
                    article.setCreateDate(rs.getTimestamp("create_date"));
                    article.setAuthor(
                            new User(
                            rs.getLong("user_id"),
                            rs.getString("login"),
                            rs.getString("first_name"),
                            rs.getString("last_name")));
                    /// itd...
                    return article;
                }
            }
        );
    }

    @SuppressWarnings("unchecked")
    public List<Article> getLastAddedArticles(int howMuch) {
        return getJdbcTemplate().query(
            "SELECT * FROM article ORDER BY create_date DESC LIMIT ?",
            new Object[]{howMuch},
            new RowMapper() {
                public Object mapRow(ResultSet rs, int row) throws SQLException {
                    Article article = new Article();
                    article.setArticleId(rs.getLong("article_id"));
                    article.setTitle(rs.getString("title"));
                    article.setCreateDate(rs.getDate("create_date"));
                    /// itd...
                    return article;
                }
            }
        );
    }
}
