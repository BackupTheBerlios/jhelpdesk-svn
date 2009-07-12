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

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import de.berlios.jhelpdesk.dao.KnowledgeSectionDAO;
import de.berlios.jhelpdesk.model.Knowledge;
import de.berlios.jhelpdesk.model.KnowledgeSection;

public class KnowledgeSectionDAOJdbc extends JdbcDaoSupport implements KnowledgeSectionDAO {

	@SuppressWarnings("unchecked")
	public List<KnowledgeSection> getAllSections() {
		return  getJdbcTemplate().query( 
			"SELECT * FROM hd_knowledge_section ORDER BY section_position DESC", 
			new RowMapper() {
				public Object mapRow( ResultSet rs, int rowNum ) throws SQLException {
					KnowledgeSection section = new KnowledgeSection();
					section.setKnowledgeSectionId( rs.getLong( "knowledge_section_id" ) );
					section.setSectionName( rs.getString( "section_name" ) );
					// to do zmiany niemalze od razu (potrzeby tylko id i tytulow)
					section.setArticles( 
						getSectionArticles( section.getKnowledgeSectionId() )
					);
					section.setArticlesCount( rs.getInt( "articles_count" ) );
					return section;
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<KnowledgeSection> getAllShortSections() {
		return  getJdbcTemplate().query( 
			"SELECT * FROM hd_knowledge_section ORDER BY section_position DESC", 
			new RowMapper() {
				public Object mapRow( ResultSet rs, int rowNum ) throws SQLException {
					KnowledgeSection section = new KnowledgeSection();
					section.setKnowledgeSectionId( rs.getLong( "knowledge_section_id" ) );
					section.setSectionName( rs.getString( "section_name" ) );
					section.setArticlesCount( rs.getInt( "articles_count" ) );
					return section;
				}
			}
		);
	}
	
	@SuppressWarnings("unchecked")
	private Set<Knowledge> getSectionArticles( final Long sectionId ) {
		return new HashSet<Knowledge>(getJdbcTemplate().query(
			"SELECT * FROM hd_knowledge WHERE hd_knowledge_section_id=?",
			new Object[] {
				sectionId
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int rowNum ) throws SQLException {
					Knowledge article = new Knowledge();
					article.setKnowledgeId( rs.getLong( "knowledge_id" ) );
					article.setTitle( rs.getString( "title" ) );
					article.setLead( rs.getString( "lead" ) );
					return article;
				}
			}
		));
	}

	public KnowledgeSection getById( Long sectionId ) {
		return ( KnowledgeSection ) getJdbcTemplate().queryForObject(
			"SELECT * FROM hd_knowledge_section WHERE knowledge_section_id=?",
			new Object[] {
				sectionId
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int rowNum ) throws SQLException {
					KnowledgeSection section = new KnowledgeSection();
					section.setKnowledgeSectionId( rs.getLong( "knowledge_section_id" ) );
					section.setSectionName( rs.getString( "section_name" ) );
					section.setArticles( 
						getSectionArticles( section.getKnowledgeSectionId() )
					);
					return section;
				}
			}
		);
	}

	public void moveDown( Long sectionId ) {
		getJdbcTemplate().queryForLong(
			"SELECT section_move_down(?)",
			new Object[] {
				sectionId
			}
		);
	}

	public void moveUp( Long sectionId ) {
		getJdbcTemplate().queryForLong(
			"SELECT section_move_up(?)",
			new Object[] {
				sectionId
			}
		);
	}

	public void saveOrUpdate( final KnowledgeSection section ) {
		if( section.getKnowledgeSectionId() != null ) {
			getJdbcTemplate().update(
				"UPDATE hd_knowledge_section SET section_name=? WHERE knowledge_section_id=?",
				new Object[] {
					section.getSectionName(),
					section.getKnowledgeSectionId()
				}
			);
		} else {
			getJdbcTemplate().execute( 
				new ConnectionCallback() {
					public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
						conn.setAutoCommit( false );
						PreparedStatement pstmt = conn.prepareStatement(
							"INSERT INTO hd_knowledge_section( knowledge_section_id, section_name,section_position,articles_count) " +
							"VALUES(nextval('knowledge_section_id_seq'),?,max(hd_knowledge_section.section_position)+1,0)"
						);
						
						pstmt.setString( 1, section.getSectionName() );
						pstmt.executeUpdate();
						Statement stmt = 
							conn.createStatement( 
								ResultSet.TYPE_SCROLL_INSENSITIVE, 
								ResultSet.CONCUR_READ_ONLY 
							);
						ResultSet rs = stmt.executeQuery( "SELECT currval('knowledge_section_id_seq')" );
						if( rs.first() ) {
							section.setKnowledgeSectionId( rs.getLong( 1 ) );
						}
						conn.commit();
						return null;
					}
				}
			);
		}
	}

	public void delete( Long sectionId ) {
		// tutaj nalezaloby jeszcze zmienic section_position dla wszystkich
		// rekordow powyzej section_position - odjac 1 dla kazdemu
		getJdbcTemplate().update(
			"DELETE FROM hd_knowledge_section WHERE knowledge_section_id=?",
			new Object[] {
				sectionId
			}
		);
	}
}
