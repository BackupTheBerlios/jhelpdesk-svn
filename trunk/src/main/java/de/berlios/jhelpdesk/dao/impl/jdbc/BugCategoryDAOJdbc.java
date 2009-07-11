package de.berlios.jhelpdesk.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;
import de.berlios.jhelpdesk.model.BugCategory;

public class BugCategoryDAOJdbc extends JdbcDaoSupport implements BugCategoryDAO {
	
	private static Log log = LogFactory.getLog( BugCategoryDAOJdbc.class );

	@SuppressWarnings("unchecked")
	public List<BugCategory> getAllCategories() {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug_category ORDER BY t_left ASC",
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					BugCategory hdBugCategory = new BugCategory();
					hdBugCategory.setBugCategoryId( rs.getLong( "category_id" ) );
					hdBugCategory.setParentCategory( rs.getLong( "parent_category" ) );
					hdBugCategory.setCategoryName( rs.getString( "category_name" ) );
					hdBugCategory.setCategoryDesc( rs.getString( "category_desc" ) );
					hdBugCategory.setDepth( rs.getInt( "t_depth" ) );
					hdBugCategory.setActive( rs.getBoolean( "is_active" ) );
					return hdBugCategory;
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<BugCategory> getAllCategoriesForView() {
		return getJdbcTemplate().query(
			"SELECT * FROM hd_bug_category WHERE is_active IS true",
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					BugCategory hdBugCategory = new BugCategory();
					hdBugCategory.setBugCategoryId( rs.getLong( "category_id" ) );
					hdBugCategory.setParentCategory( rs.getLong( "parent_category" ) );
					hdBugCategory.setCategoryName( rs.getString( "category_name" ) );
					hdBugCategory.setCategoryDesc( rs.getString( "category_desc" ) );
					return hdBugCategory;
				}
			}
		);
	}

	public BugCategory getById( Long categoryId ) {
		return ( BugCategory ) getJdbcTemplate().queryForObject(
			"SELECT * FROM hd_bug_category WHERE category_id=?",
			new Object[] {
				categoryId
			},
			new RowMapper() {
				public Object mapRow( ResultSet rs, int row ) throws SQLException {
					BugCategory hdBugCategory = new BugCategory();
					hdBugCategory.setBugCategoryId( rs.getLong( "category_id" ) );
					hdBugCategory.setParentCategory( rs.getLong( "parent_category" ) );
					hdBugCategory.setCategoryName( rs.getString( "category_name" ) );
					hdBugCategory.setCategoryDesc( rs.getString( "category_desc" ) );
					hdBugCategory.setLeft( rs.getLong( "t_left" ) );
					hdBugCategory.setRight( rs.getLong( "t_right" ) );
					hdBugCategory.setDepth( rs.getInt( "t_depth" ) );
					hdBugCategory.setActive( rs.getBoolean( "is_active" ) );
					return hdBugCategory;
				}
			}
		);
	}

	public BugCategory getById( int categoryId ) {
		return getById(  new Long( categoryId ) );
	}
	
	public void insertRootCategory( BugCategory category ) {
        final long maxRight = 
        	getJdbcTemplate().queryForLong(
        		"SELECT max(t_right) FROM hd_bug_category"
        );
        getJdbcTemplate().update(
        	"INSERT INTO hd_bug_category (category_name,category_desc,is_active,t_left,t_right,t_depth) VALUES (?,?,?,?,?,?)", 
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
	
	public void deleteCategory( final BugCategory category ) {
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
								"UPDATE hd_bug_category SET t_left=t_left-2 WHERE t_left>? AND t_left<?"
						);
						pstmt.setLong( 1, category.getRight() );
						pstmt.setLong( 2, ( ( getNodeCount() * 2 ) + 1 ) );
						pstmt.executeUpdate();
						
						PreparedStatement pstmt2 =
							conn.prepareStatement(
								"UPDATE hd_bug_category SET t_right=t_right-2 WHERE t_right>=? AND t_right<=?"
						);
						pstmt2.setLong( 1, category.getRight() );
						pstmt2.setLong( 2, ( getNodeCount() * 2 ) );
						pstmt2.executeUpdate();
						
						PreparedStatement pstmt3 =
							conn.prepareStatement(
								"DELETE FROM hd_bug_category WHERE category_id=?"
						);
						pstmt3.setLong( 1, category.getBugCategoryId() );
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

	public void insertCategory( final BugCategory category, final BugCategory parent ) {
		final long nodeCount = getNodeCount();
		final BugCategory parentCat = getById( parent.getBugCategoryId() );
		getJdbcTemplate().execute(
			new ConnectionCallback() {
				public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
					conn.setAutoCommit( false );
					try {
						PreparedStatement pstmt =
							conn.prepareStatement(
								"UPDATE hd_bug_category SET t_right=t_right+2 WHERE t_right>=? AND t_right<=?"
						);
						pstmt.setLong( 1, parentCat.getRight() );
						pstmt.setLong( 2, nodeCount * 2 );
						pstmt.executeUpdate();
						
						PreparedStatement pstmt2 =
							conn.prepareStatement(
								"UPDATE hd_bug_category SET t_left=t_left+2 WHERE t_left>? AND t_left<?"						
						);
						pstmt2.setLong( 1, parentCat.getRight() );
						pstmt2.setLong( 2, ( nodeCount + 1 ) * 2 );
						pstmt2.executeUpdate();
						
						PreparedStatement pstmt3 =
							conn.prepareStatement(
								"INSERT INTO hd_bug_category (category_name,category_desc,parent_category,is_active,t_left,t_right,t_depth) " +
								"VALUES (?,?,?,?,?,?,?)"
						);
						pstmt3.setString( 1, category.getCategoryName() );
						pstmt3.setString( 2, category.getCategoryDesc() );
						pstmt3.setLong( 3, parentCat.getBugCategoryId() );
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

	public void updateCategory( BugCategory category ) {
		getJdbcTemplate().update(
			"UPDATE hd_bug_category SET category_name=?, category_desc=? WHERE category_id=?",
			new Object[] {
				category.getCategoryName(),
				category.getCategoryDesc(),
				category.getBugCategoryId()
			}
		);
	}
	
	private long getNodeCount() {
		return getJdbcTemplate().queryForLong(
			"SELECT count(*) FROM hd_bug_category" );
	}
	
	private void deleteChildNodes( final BugCategory category ) {
        final long nodeCount = getNodeCount();
        final long subtreeNodeCount = (category.getRight() - category.getLeft()) / 2;
        
        getJdbcTemplate().execute(
        	new ConnectionCallback() {
        		public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
        			conn.setAutoCommit( false );
        			try {
        				PreparedStatement pstmt = 
    						conn.prepareStatement(
    							"DELETE FROM hd_bug_category WHERE t_left > ? AND t_right < ?"			
    					);
        				pstmt.setLong( 1, category.getLeft() );
        				pstmt.setLong( 2, category.getRight() );
        				pstmt.executeUpdate();
        				
        				PreparedStatement pstmt2 = 
    						conn.prepareStatement(
    							"UPDATE hd_bug_category SET t_left = t_left - ? WHERE t_left > ? AND t_left < ?"			
    					);
        				pstmt2.setLong( 1, subtreeNodeCount * 2 );
        				pstmt2.setLong( 2, category.getLeft() );
        				pstmt2.setLong( 3, nodeCount * 2 );
        				pstmt2.executeUpdate();
        				
        				PreparedStatement pstmt3 = 
    						conn.prepareStatement(
    							"UPDATE hd_bug_category SET t_right = t_right - ? WHERE t_right >= ? AND t_right <= ?"			
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
