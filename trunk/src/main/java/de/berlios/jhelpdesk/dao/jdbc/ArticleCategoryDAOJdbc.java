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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.ArticleCategoryDAO;
import de.berlios.jhelpdesk.model.Article;
import de.berlios.jhelpdesk.model.ArticleCategory;

//@Repository("articleCategoryDAO")
//@Qualifier("jdbc")
@Deprecated
public class ArticleCategoryDAOJdbc extends AbstractJdbcTemplateSupport implements ArticleCategoryDAO {

    @Autowired
    public ArticleCategoryDAOJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @SuppressWarnings("unchecked")
    public List<ArticleCategory> getAllCategories() {
        return getJdbcTemplate().query(
            "SELECT * FROM article_category ORDER BY category_position DESC",
            new RowMapper() {
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ArticleCategory category = new ArticleCategory();
                    category.setArticleCategoryId(rs.getLong("article_category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    // to do zmiany niemalze od razu (potrzeby tylko id i tytulow)
                    category.setArticles(
                            getSectionArticles(category.getArticleCategoryId()));
                    category.setArticlesCount(rs.getInt("articles_count"));
                    return category;
                }
            }
        );
    }

    @SuppressWarnings("unchecked")
    public List<ArticleCategory> getAllShortSections() {
        return getJdbcTemplate().query(
            "SELECT * FROM article_category ORDER BY category_position DESC",
            new RowMapper() {
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ArticleCategory category = new ArticleCategory();
                    category.setArticleCategoryId(rs.getLong("article_category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    category.setArticlesCount(rs.getInt("articles_count"));
                    return category;
                }
            }
        );
    }

    @SuppressWarnings("unchecked")
    private Set<Article> getSectionArticles(final Long categoryId) {
        return new HashSet<Article>(getJdbcTemplate().query(
            "SELECT * FROM article WHERE article_category_id=?",
            new Object[]{categoryId},
            new RowMapper() {
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Article article = new Article();
                    article.setArticleId(rs.getLong("article_id"));
                    article.setTitle(rs.getString("title"));
                    article.setLead(rs.getString("lead"));
                    return article;
                }
            })
        );
    }

    public ArticleCategory getById(Long categoryId) {
        return (ArticleCategory) getJdbcTemplate().queryForObject(
            "SELECT * FROM article_category WHERE article_category_id=?",
            new Object[]{categoryId},
            new RowMapper() {
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ArticleCategory category = new ArticleCategory();
                    category.setArticleCategoryId(rs.getLong("article_category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    category.setArticles(
                            getSectionArticles(category.getArticleCategoryId()));
                    return category;
                }
            }
        );
    }

    public void moveDown(Long categoryId) {
        getJdbcTemplate().queryForLong(
            "SELECT category_move_down(?)",
            new Object[]{categoryId}
        );
    }

    public void moveUp(Long categoryId) {
        getJdbcTemplate().queryForLong(
            "SELECT category_move_up(?)",
            new Object[]{categoryId}
        );
    }

    public void saveOrUpdate(final ArticleCategory category) {
        if (category.getArticleCategoryId() != null) {
            getJdbcTemplate().update(
                "UPDATE article_category SET category_name=? WHERE article_category_id=?",
                new Object[]{
                    category.getCategoryName(),
                    category.getArticleCategoryId()
                }
            );
        } else {
            getJdbcTemplate().execute(
                new ConnectionCallback() {
                    public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
                        conn.setAutoCommit(false);
                        PreparedStatement pstmt = conn.prepareStatement(
                                "INSERT INTO article_category(" +
                                "article_category_id, category_name,category_position,articles_count) " +
                                "VALUES(nextval('article_category_id_seq'),?," +
                                "COALESCE((SELECT max(category_position) FROM article_category),0)+1,0)");
                        pstmt.setString(1, category.getCategoryName());
                        pstmt.executeUpdate();
                        Statement stmt = conn.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs = stmt.executeQuery("SELECT currval('article_category_id_seq')");
                        if (rs.first()) {
                            category.setArticleCategoryId(rs.getLong(1));
                        }
                        conn.commit();
                        return null;
                    }
                }
            );
        }
    }

    public void delete(Long categoryId) {
        // tutaj nalezaloby jeszcze zmienic category_position dla wszystkich
        // rekordow powyzej category_position - odjac 1 dla kazdemu
        getJdbcTemplate().update(
            "DELETE FROM article_category WHERE article_category_id=?",
            new Object[]{categoryId}
        );
    }
}
