package de.berlios.jhelpdesk.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import de.berlios.jhelpdesk.dao.InformationDAO;
import de.berlios.jhelpdesk.model.Information;

public class InformationDAOJdbc extends JdbcDaoSupport implements InformationDAO {
	
	private static Log log = LogFactory.getLog( InformationDAOJdbc.class );
	
	public void delete( Long informationId ) {
		// usuwamy tylko dane z tabeli hd_infomation,
		// ewentualne dane z tabeli hd_information_body
		// usuwane sa za pomoca odpowiedniego triggera
		// funkcja SQL -> drop_information_body()
		// trigger     -> drop_information_body_trg
		getJdbcTemplate().update(
			"DELETE FROM hd_information WHERE information_id=?",
			new Object[] {
				informationId
			}
		);
	}

	public void delete( Information information ) {
		// usuwamy tylko dane z tabeli hd_infomation,
		// ewentualne dane z tabeli hd_information_body
		// usuwane sa za pomoca odpowiedniego triggera
		// funkcja SQL -> drop_information_body()
		// trigger     -> drop_information_body_trg
		getJdbcTemplate().update(
			"DELETE FROM hd_information WHERE information_id=?",
			new Object[] {
				information.getInformationId()
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Information> getAll() {
		return getJdbcTemplate().query(
			"SELECT * FROM information_view ORDER BY create_date DESC",
			new HDInformationRowMapper()
		);
	}

	public Information getById( Long informationId ) {
		return (Information)getJdbcTemplate().queryForObject(
			"SELECT * FROM information_view WHERE information_id=?",
			new Object[] {
				informationId
			},
			new HDInformationRowMapper()
		);
	}

	@SuppressWarnings("unchecked")
	public List<Information> getLastFew( int howMuch ) {
		return getJdbcTemplate().query(
			"SELECT * FROM information_view ORDER BY create_date DESC LIMIT ?",
			new Object[] {
				howMuch
			},
			new HDInformationRowMapper()
		);
	}

	public void save( final Information information ) {
		if( information.getInformationId() != null ) {
			getJdbcTemplate().execute(
				new ConnectionCallback() {
					public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
						conn.setAutoCommit( false );
						try {
							PreparedStatement pstmt =
								conn.prepareStatement(
									"UPDATE hd_information SET title=?, lead=? WHERE information_id=?"
								);
							pstmt.setString( 1, information.getTitle() );
							pstmt.setString( 2, information.getLead() );
							pstmt.setLong(   3, information.getInformationId() );
							pstmt.executeUpdate();
							
							PreparedStatement pstmt2 = null;
							if(( information.getBody() != null ) && ( information.getBody().length() > 0 ) ) {
								pstmt2 =
									conn.prepareStatement( "UPDATE hd_information_body SET body=? WHERE information_id=?" );
								pstmt2.setString( 1, information.getBody() );
								pstmt2.setLong( 2, information.getInformationId() );
							} else {
								pstmt2 =
									conn.prepareStatement( "DELETE FROM hd_information_body WHERE information_id=?" );
								pstmt2.setLong( 1, information.getInformationId() );
							}
							pstmt2.executeUpdate();
							conn.commit();
						} catch( Exception ex ) {
							log.error( ex );
							conn.rollback();
						}
						return null;
					}
				}
			);
		} else {
			getJdbcTemplate().execute(
				new ConnectionCallback() {
					public Object doInConnection( Connection conn ) throws SQLException, DataAccessException {
						conn.setAutoCommit( false );
						try {
							PreparedStatement pstmt =
								conn.prepareStatement(
									"INSERT INTO hd_information(information_id,create_date,title,lead) " +
									"VALUES(nextval('information_id_seq'),now(),?,?)"
								);
							pstmt.setString( 1, information.getTitle() );
							pstmt.setString( 2, information.getLead() );
							pstmt.executeUpdate();
							
							Statement stmt = 
								conn.createStatement( 
									ResultSet.TYPE_SCROLL_INSENSITIVE, 
									ResultSet.CONCUR_READ_ONLY 
								);
							ResultSet rs = stmt.executeQuery( "SELECT currval('information_id_seq')" );
							if( rs.first() ) {
								information.setInformationId( rs.getLong( 1 ) );
							}
							
							if(( information.getBody() != null ) && ( information.getBody().length() > 0 ) ) {
								PreparedStatement pstmt3 = 
									conn.prepareStatement( "INSERT INTO hd_information_body(information_id,body) VALUES(?,?)" );
								pstmt3.setLong( 1, information.getInformationId() );
								pstmt3.setString( 2, information.getBody() );
								pstmt3.executeUpdate();
							}
							conn.commit();
						} catch( Exception ex ) {
							log.error( ex );
							conn.rollback();
							information.setInformationId( null );
						}
						return null;
					}
				}
			);
		}
	}
	
	private class HDInformationRowMapper implements RowMapper {
		public Object mapRow( ResultSet rs, int row ) throws SQLException {
			Information information = new Information();
			information.setInformationId( rs.getLong( "information_id" ) );
			information.setCreateDate( rs.getDate( "create_date" ) );
			information.setTitle( rs.getString( "title" ) );
			information.setLead( rs.getString( "lead" ) );
			information.setBody( rs.getString( "body" ) );
			return information;
		}
	}
}
