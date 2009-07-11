/**
 * 
 */
package de.berlios.jhelpdesk.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import de.berlios.jhelpdesk.dao.KnowledgeDAO;
import de.berlios.jhelpdesk.model.Knowledge;


/**
 * @author jjhop
 *
 */
public class KnowledgeDAOJdbc extends JdbcDaoSupport implements KnowledgeDAO {

	/* (non-Javadoc)
	 * @see de.berlios.jhelpdesk.dao.ifc.IHDKnowledgeDAO#getById(java.lang.Long)
	*/
	public Knowledge getById( Long pk ) {
		return ( Knowledge )getJdbcTemplate().queryForObject(
			"SELECT * FROM hd_knowledge WHERE knowledge_id=?",
			new Object[] { pk },
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Knowledge knowledge = new Knowledge();
					knowledge.setKnowledgeId( rs.getLong( "knowledge_id" ) );
					knowledge.setTitle( rs.getString( "title" ) );
					knowledge.setCreateDate( rs.getDate( "create_date" ) );
					knowledge.setLead( rs.getString( "lead" ) );
					knowledge.setBody( rs.getString( "body" ) );
					knowledge.setAssociatedBugs( null );
					// user, associatet bugs and comments not yet implemented
					knowledge.setAuthor( null );
					knowledge.setComments( null );
					knowledge.setAssociatedBugs( null );
					return knowledge;
				}
			}
		);
	}
	
	/* (non-Javadoc)
	 * @see de.berlios.jhelpdesk.dao.ifc.IHDKnowledgeDAO#delete(de.berlios.jhelpdesk.model.Knowledge)
	*/
	public void delete( Knowledge knowledge ) {
		delete( knowledge.getKnowledgeId() );
	}
	
	/* (non-Javadoc)
	 * @see de.berlios.jhelpdesk.dao.ifc.IHDKnowledgeDAO#delete(java.lang.Long)
	*/
	public void delete( Long knowledgeId ) {
		getJdbcTemplate().update(
			"DELETE FROM hd_knowledge WHERE knowledge_id=?",
			new Object[] { knowledgeId }
		);
	}
	
	/* (non-Javadoc)
	 * @see de.berlios.jhelpdesk.dao.ifc.IHDKnowledgeDAO#save(de.berlios.jhelpdesk.model.Knowledge)
	*/
	public void save( final Knowledge knowledge ) {
		if( knowledge.getKnowledgeId() != null ) {
			// if knowledgeId is set to null we have a new object
			// and we have to save it as new one (and return with 
			// knowledgeId set to database id)
			getJdbcTemplate().update(
				"UPDATE hd_knowledge SET title=?,hd_knowledge_section_id=?," +
				"create_date=?,lead=?,body=?,hd_user_id=? WHERE knowledge_id=?",
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
					public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
						conn.setAutoCommit( false );
						PreparedStatement pstmt = 
							conn.prepareStatement(
								"INSERT INTO hd_knowledge(knowledge_id,title,hd_knowledge_section_id," +
								"create_date,lead,body,hd_user_id) VALUES(nextval('knowledge_id_seq'),?,?,?,?,?,?)"
							);
						pstmt.setString( 1, knowledge.getTitle() );
						pstmt.setLong( 2, knowledge.getKnowledgeSectionId() );
						pstmt.setDate( 3, new Date( knowledge.getCreateDate().getTime() ) );
						pstmt.setString( 4, knowledge.getLead() );
						pstmt.setString( 5, knowledge.getBody() );
						pstmt.setLong( 6, knowledge.getAuthor().getUserId() );
						pstmt.executeUpdate();
						
						Statement stmt = 
							conn.createStatement( 
								ResultSet.TYPE_SCROLL_INSENSITIVE, 
								ResultSet.CONCUR_READ_ONLY 
							);
						ResultSet rs = stmt.executeQuery( "SELECT currval('knowledge_id_seq')" );
						if( rs.first() ) {
							knowledge.setKnowledgeId( rs.getLong( 1 ) );
						}
						conn.commit();
						return null;
					}
				}
			);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Knowledge> getForSection( Long sectionId ) {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_knowledge WHERE hd_knowledge_section_id=?",
			new Object[] {
				sectionId
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Knowledge article = new Knowledge();
					article.setKnowledgeId( rs.getLong( "hd_knowledge_section_id" ) );
					article.setTitle( rs.getString( "title" ) );
					/// itd...
					return article;
				}
			}
		);
	}
	
	@SuppressWarnings("unchecked")
	public List<Knowledge> getLastAddedArticles( int howMuch ) {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_knowledge ORDER BY create_date DESC LIMIT ?",
			new Object[] {
				howMuch
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					Knowledge article = new Knowledge();
					article.setKnowledgeId( rs.getLong( "knowledge_id" ) );
					article.setTitle( rs.getString( "title" ) );
					article.setCreateDate( rs.getDate( "create_date" ) );
					/// itd...
					return article;
				}
			}
		);
	}
}