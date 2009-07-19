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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.model.Knowledge;

/**
 * @author jjhop
 */
@Repository("knowledgeDAO")
public class KnowledgeDAOJdbc extends AbstractJdbcTemplateSupport implements KnowledgeDAO {

    @Autowired
    public KnowledgeDAOJdbc(DataSource dataSource) {
        super(dataSource);
    }

	/* (non-Javadoc)
	 * @see de.berlios.jhelpdesk.dao.ifc.IHDKnowledgeDAO#getById(java.lang.Long)
	*/
	public Knowledge getById(Long pk) {
		return (Knowledge)getJdbcTemplate().queryForObject(
			"SELECT * FROM knowledge WHERE knowledge_id=?",
			new Object[] { pk },
			new RowMapper() {
				public Object mapRow(ResultSet rs, int row) throws SQLException {
					Knowledge knowledge = new Knowledge();
					knowledge.setKnowledgeId(rs.getLong("knowledge_id"));
					knowledge.setTitle(rs.getString("title"));
					knowledge.setCreateDate(rs.getDate("create_date"));
					knowledge.setLead(rs.getString("lead"));
					knowledge.setBody(rs.getString("body"));
					knowledge.setAssociatedTickets(null);
					// user, associated tickets and comments not yet implemented
					knowledge.setAuthor(null);
					knowledge.setComments(null);
					knowledge.setAssociatedTickets(null);
					return knowledge;
				}
			}
		);
	}
	
	/* (non-Javadoc)
	 * @see de.berlios.jhelpdesk.dao.ifc.IHDKnowledgeDAO#delete(de.berlios.jhelpdesk.model.Knowledge)
	*/
	public void delete(Knowledge knowledge) {
		delete(knowledge.getKnowledgeId());
	}
	
	/* (non-Javadoc)
	 * @see de.berlios.jhelpdesk.dao.ifc.IHDKnowledgeDAO#delete(java.lang.Long)
	*/
	public void delete(Long knowledgeId) {
		getJdbcTemplate().update(
			"DELETE FROM knowledge WHERE knowledge_id=?",
			new Object[] { knowledgeId }
		);
	}
	
	/* (non-Javadoc)
	 * @see de.berlios.jhelpdesk.dao.ifc.IHDKnowledgeDAO#save(de.berlios.jhelpdesk.model.Knowledge)
	*/
	public void saveOrUpdate(final Knowledge knowledge) {
		if(knowledge.getKnowledgeId() != null) {
			// if knowledgeId is set to null we have a new object
			// and we have to save it as new one (and return with 
			// knowledgeId set to database id)
			getJdbcTemplate().update(
				"UPDATE knowledge SET title=?,knowledge_section_id=?," +
				"create_date=?,lead=?,body=?,user_id=? WHERE knowledge_id=?",
				new Object[] {
					knowledge.getTitle(), knowledge.getKnowledgeSectionId(),
					knowledge.getCreateDate(), knowledge.getLead(), knowledge.getBody(),
					knowledge.getAuthor().getUserId(), knowledge.getKnowledgeId()
				}
			);
		} else {
			// and if knowledgeId is set we have an existing 
			// object and we have to update it
			getJdbcTemplate().execute(
				new ConnectionCallback() {
					public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
						conn.setAutoCommit(false);
						PreparedStatement pstmt = 
							conn.prepareStatement(
								"INSERT INTO knowledge(knowledge_id,title,knowledge_section_id," +
								"create_date,lead,body,user_id) VALUES(nextval('knowledge_id_seq'),?,?,?,?,?,?)"
							);
						pstmt.setString(1, knowledge.getTitle());
						pstmt.setLong(2, knowledge.getKnowledgeSectionId());
						pstmt.setDate(3, new Date(knowledge.getCreateDate().getTime()));
						pstmt.setString(4, knowledge.getLead());
						pstmt.setString(5, knowledge.getBody());
						pstmt.setLong(6, knowledge.getAuthor().getUserId());
						pstmt.executeUpdate();
						
						Statement stmt = 
							conn.createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE, 
								ResultSet.CONCUR_READ_ONLY 
							);
						ResultSet rs = stmt.executeQuery("SELECT currval('knowledge_id_seq')");
						if(rs.first()) {
							knowledge.setKnowledgeId(rs.getLong(1));
						}
						conn.commit();
						return null;
					}
				}
			);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Knowledge> getForSection(Long sectionId) {
		return getJdbcTemplate().query(
			"SELECT * FROM knowledge WHERE knowledge_section_id=?",
			new Object[] {
				sectionId
			},
			new RowMapper() {
				public Object mapRow(ResultSet rs, int row) throws SQLException {
					Knowledge article = new Knowledge();
					article.setKnowledgeId(rs.getLong("knowledge_section_id"));
					article.setTitle(rs.getString("title"));
					/// itd...
					return article;
				}
			}
		);
	}
	
	@SuppressWarnings("unchecked")
	public List<Knowledge> getLastAddedArticles(int howMuch) {
		return getJdbcTemplate().query(
			"SELECT * FROM knowledge ORDER BY create_date DESC LIMIT ?",
			new Object[] {
				howMuch
			},
			new RowMapper() {
				public Object mapRow(ResultSet rs, int row) throws SQLException {
					Knowledge article = new Knowledge();
					article.setKnowledgeId(rs.getLong("knowledge_id"));
					article.setTitle(rs.getString("title"));
					article.setCreateDate(rs.getDate("create_date"));
					/// itd...
					return article;
				}
			}
		);
	}
}