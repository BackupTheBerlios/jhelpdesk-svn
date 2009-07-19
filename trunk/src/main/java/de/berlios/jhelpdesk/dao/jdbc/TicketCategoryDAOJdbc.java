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
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.model.TicketCategory;

@Repository("ticketCategoryDAO")
public class TicketCategoryDAOJdbc extends AbstractJdbcTemplateSupport implements TicketCategoryDAO {
	
    private static Log log = LogFactory.getLog(TicketCategoryDAOJdbc.class);

    @Autowired
    public TicketCategoryDAOJdbc(DataSource dataSource) {
        super(dataSource);
    }

	@SuppressWarnings("unchecked")
	public List<TicketCategory> getAllCategories() {
		return getJdbcTemplate().query(
			"SELECT * FROM ticket_category WHERE category_id>0 ORDER BY t_left ASC",
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					TicketCategory ticketCategory = new TicketCategory();
					ticketCategory.setTicketCategoryId( rs.getLong( "category_id" ) );
					ticketCategory.setParentCategory( rs.getLong( "parent_category" ) );
					ticketCategory.setCategoryName( rs.getString( "category_name" ) );
					ticketCategory.setCategoryDesc( rs.getString( "category_desc" ) );
					ticketCategory.setDepth( rs.getInt( "t_depth" ) );
					ticketCategory.setActive( rs.getBoolean( "is_active" ) );
					return ticketCategory;
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<TicketCategory> getAllCategoriesForView() {
		return getJdbcTemplate().query(
			"SELECT * FROM ticket_category WHERE is_active IS true",
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					TicketCategory ticketCategory = new TicketCategory();
					ticketCategory.setTicketCategoryId( rs.getLong( "category_id" ) );
					ticketCategory.setParentCategory( rs.getLong( "parent_category" ) );
					ticketCategory.setCategoryName( rs.getString( "category_name" ) );
					ticketCategory.setCategoryDesc( rs.getString( "category_desc" ) );
					return ticketCategory;
				}
			}
		);
	}

	public TicketCategory getById( Long categoryId ) {
		return ( TicketCategory ) getJdbcTemplate().queryForObject(
			"SELECT * FROM ticket_category WHERE category_id=?",
			new Object[] {
				categoryId
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					TicketCategory ticketCategory = new TicketCategory();
					ticketCategory.setTicketCategoryId( rs.getLong( "category_id" ) );
					ticketCategory.setParentCategory( rs.getLong( "parent_category" ) );
					ticketCategory.setCategoryName( rs.getString( "category_name" ) );
					ticketCategory.setCategoryDesc( rs.getString( "category_desc" ) );
					ticketCategory.setLeft( rs.getLong( "t_left" ) );
					ticketCategory.setRight( rs.getLong( "t_right" ) );
					ticketCategory.setDepth( rs.getInt( "t_depth" ) );
					ticketCategory.setActive( rs.getBoolean( "is_active" ) );
					return ticketCategory;
				}
			}
		);
	}

	public TicketCategory getById( int categoryId ) {
		return getById(  new Long( categoryId ) );
	}
	
	public void insertRootCategory( TicketCategory category ) {
        final long maxRight = 
        	getJdbcTemplate().queryForLong(
        		"SELECT max(t_right) FROM ticket_category"
        );
        getJdbcTemplate().update(
        	"INSERT INTO ticket_category (category_name,category_desc,is_active,t_left,t_right,t_depth) VALUES (?,?,?,?,?,?)", 
        	new Object[] {
        		category.getCategoryName(),
        		category.getCategoryDesc(),
        		Boolean.TRUE,
        		maxRight + 1,
        		maxRight + 2,
        		0
        	}
        );
	}
	
	public void deleteCategory( final TicketCategory category ) {
        if (category.hasChildNodes()) {
            deleteChildNodes(category);
            category.setRight(category.getLeft()+1);
        }
        
        getJdbcTemplate().execute(
        	new ConnectionCallback() {
				public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
					conn.setAutoCommit( false );
					try {
						PreparedStatement pstmt =
							conn.prepareStatement(
								"UPDATE ticket_category SET t_left=t_left-2 WHERE t_left>? AND t_left<?"
						);
						pstmt.setLong( 1, category.getRight() );
						pstmt.setLong( 2, ( ( getNodeCount() * 2 ) + 1 ) );
						pstmt.executeUpdate();
						
						PreparedStatement pstmt2 =
							conn.prepareStatement(
								"UPDATE ticket_category SET t_right=t_right-2 WHERE t_right>=? AND t_right<=?"
						);
						pstmt2.setLong( 1, category.getRight() );
						pstmt2.setLong( 2, ( getNodeCount() * 2 ) );
						pstmt2.executeUpdate();
						
						PreparedStatement pstmt3 =
							conn.prepareStatement(
								"DELETE FROM ticket_category WHERE category_id=?"
						);
						pstmt3.setLong( 1, category.getTicketCategoryId() );
						pstmt3.executeUpdate();
						conn.commit();
					} catch( Exception ex ) {
						log.error( ex );
						conn.rollback();
					}
					return null;
				}
        	}
        );
	}

	public void insertCategory( final TicketCategory category, final TicketCategory parent ) {
		final long nodeCount = getNodeCount();
		final TicketCategory parentCat = getById( parent.getTicketCategoryId() );
		getJdbcTemplate().execute(
			new ConnectionCallback() {
				public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
					conn.setAutoCommit( false );
					try {
						PreparedStatement pstmt =
							conn.prepareStatement(
								"UPDATE ticket_category SET t_right=t_right+2 WHERE t_right>=? AND t_right<=?"
						);
						pstmt.setLong( 1, parentCat.getRight() );
						pstmt.setLong( 2, nodeCount * 2 );
						pstmt.executeUpdate();
						
						PreparedStatement pstmt2 =
							conn.prepareStatement(
								"UPDATE ticket_category SET t_left=t_left+2 WHERE t_left>? AND t_left<?"						
						);
						pstmt2.setLong( 1, parentCat.getRight() );
						pstmt2.setLong( 2, ( nodeCount + 1 ) * 2 );
						pstmt2.executeUpdate();
						
						PreparedStatement pstmt3 =
							conn.prepareStatement(
								"INSERT INTO ticket_category (category_name,category_desc,parent_category,is_active,t_left,t_right,t_depth) " +
								"VALUES (?,?,?,?,?,?,?)"
						);
						pstmt3.setString( 1, category.getCategoryName() );
						pstmt3.setString( 2, category.getCategoryDesc() );
						pstmt3.setLong( 3, parentCat.getTicketCategoryId() );
						pstmt3.setBoolean( 4, Boolean.TRUE );
						pstmt3.setLong( 5, parentCat.getRight() );
						pstmt3.setLong( 6, parentCat.getRight() + 1 );
						pstmt3.setLong( 7, parentCat.getDepth() + 1 );
						pstmt3.executeUpdate();
						conn.commit();
					} catch( Exception ex ) {
						log.error( ex );
						conn.rollback();
					}
					return null;
				}
			}
		);
	}

	public void moveDown( Long categoryId ) {
		// TODO Auto-generated method stub
		
	}

	public void moveUp( Long categoryId ) {
		// TODO Auto-generated method stub
		
	}

	public void updateCategory( TicketCategory category ) {
		getJdbcTemplate().update(
			"UPDATE ticket_category SET category_name=?, category_desc=? WHERE category_id=?",
			new Object[] {
				category.getCategoryName(),
				category.getCategoryDesc(),
				category.getTicketCategoryId()
			}
		);
	}
	
	private long getNodeCount() {
		return getJdbcTemplate().queryForLong(
			"SELECT count(*) FROM ticket_category" );
	}
	
	private void deleteChildNodes( final TicketCategory category ) {
        final long nodeCount = getNodeCount();
        final long subtreeNodeCount = (category.getRight() - category.getLeft()) / 2;
        
        getJdbcTemplate().execute(
        	new ConnectionCallback() {
        		public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
        			conn.setAutoCommit( false );
        			try {
        				PreparedStatement pstmt = 
    						conn.prepareStatement(
    							"DELETE FROM ticket_category WHERE t_left > ? AND t_right < ?"			
    					);
        				pstmt.setLong( 1, category.getLeft() );
        				pstmt.setLong( 2, category.getRight() );
        				pstmt.executeUpdate();
        				
        				PreparedStatement pstmt2 = 
    						conn.prepareStatement(
    							"UPDATE ticket_category SET t_left = t_left - ? WHERE t_left > ? AND t_left < ?"			
    					);
        				pstmt2.setLong( 1, subtreeNodeCount * 2 );
        				pstmt2.setLong( 2, category.getLeft() );
        				pstmt2.setLong( 3, nodeCount * 2 );
        				pstmt2.executeUpdate();
        				
        				PreparedStatement pstmt3 = 
    						conn.prepareStatement(
    							"UPDATE ticket_category SET t_right = t_right - ? WHERE t_right >= ? AND t_right <= ?"			
    					);
        				pstmt3.setLong( 1, subtreeNodeCount*2 );
        				pstmt3.setLong( 2, category.getRight() );
        				pstmt3.setLong( 3, nodeCount * 2 );
        				pstmt3.executeUpdate();
        				
        				conn.commit();
        			} catch( Exception ex ) {
						conn.rollback();
					}
        			return null;
        		}
        	}
        );
	}
}
