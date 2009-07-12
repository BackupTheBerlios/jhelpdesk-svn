package de.berlios.jhelpdesk.pao.plpgsql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

import de.berlios.jhelpdesk.DateUtil;
import de.berlios.jhelpdesk.model.BugCategory;
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.pao.StatsSaviourPAO;
import de.berlios.jhelpdesk.web.view.bean.SaviourStatsFullViewBean;

public class StatsSaviourPAOPgSql extends JdbcDaoSupport implements StatsSaviourPAO {
	private static Log log = LogFactory.getLog( StatsSaviourPAOPgSql.class );
	 private String saviourId; 
	 private String statusId;
	 
	 public void setSaviourId( String sav ) {
		 this.saviourId = sav;
	 }

	@SuppressWarnings("unchecked")
	public Map getStatsForCurrentWeek() {
		log.info( "getStatsForCurrentWeek->start" );
		DateUtil du = new DateUtil();
		String query = 
			new StatsSaviourQueryBuilder( saviourId, statusId )
				.getQueryForDates( 	du.getWeekStartDate(),	du.getWeekEndDate() );
		Map tr = new HashMap();
		List<TempBean> list =
			new StatsSaviourQuery( getDataSource(), query ).execute();
		for( TempBean tb : list ) {
			tr.put( new BugCategory( tb.getCategoryId().intValue(), tb.getCategoryName() ), tb );
		}
		log.info( "getStatsForCurrentWeek->return [size=" + tr.size() + "]" );
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map getStatsForCurrentMonth() {
		log.info( "getStatsForCurrentMonth->start" );
		DateUtil du = new DateUtil();
		String query = 
			new StatsSaviourQueryBuilder( saviourId, statusId )
				.getQueryForDates( 	du.getMonthStartDate(),	du.getMonthEndDate() );
		Map tr = new HashMap();
		List<TempBean> list =
			new StatsSaviourQuery( getDataSource(), query ).execute();
		for( TempBean tb : list ) {
			tr.put( new BugCategory( tb.getCategoryId().intValue(), tb.getCategoryName() ), tb );
		}
		log.info( "getStatsForCurrentMonth->return [size=" + tr.size() + "]" );
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map getStatsForThreePreviousMonths() {
		log.info( "getStatsForThreePreviousMonths->start" );
		DateUtil du = new DateUtil();
		String query = 
			new StatsSaviourQueryBuilder( saviourId, statusId )
				.getQueryForDates( du.get3MonthsEarlierDate(), du.getPreviousMonthLastDayDate());
		log.info( "statsFor3PrevMonths query => [" + query + "]" );
		Map tr = new HashMap();
		
		List<TempBean> list =
			new StatsSaviourQuery( getDataSource(), query ).execute();
		log.info( "getStatsForThreePreviousMonths->rezultat [linia 78] => rozmiar listy => " + list.size() );
		for( TempBean tb : list ) {
			tr.put( new BugCategory( tb.getCategoryId().intValue(), tb.getCategoryName() ), tb );
		}
		
		log.info( "getStatsForThreePreviousMonths->return [size=" + tr.size() + "]" );
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map getStatsForLastYear() {
		throw new NotImplementedException(" metoda nie zostala zaimplementowana " );
	}

	@SuppressWarnings("unchecked")
	public Map getStatsForAll() {
		log.info( "getStatsForAll->start" );
		Map tr = new HashMap();
		String query = new StatsSaviourQueryBuilder( saviourId, statusId ).getQueryForAll();
		log.info( "getStatsForAll query => [" + query + "]" );
		List<TempBean> list =
			new StatsSaviourQuery( getDataSource(), query ).execute();
		log.info( "getStatsForAll->rezultat [linia 78] => rozmiar listy => " + list.size() );
		for( TempBean tb : list ) {
			tr.put( new BugCategory( tb.getCategoryId().intValue(), tb.getCategoryName() ) , tb );
		}
		log.info( "getStatsForAll->return [size=" + tr.size() + "]" );
		return tr;
	}

	/**
	 *
	*/
	public Map getFullStats() {
		log.info( "getFullStats->start" );
		Map<String, SaviourStatsFullViewBean> fullStats = new HashMap<String, SaviourStatsFullViewBean>();

		// najpierw status ustawiamy na otwarty
		this.statusId = BugStatus.ATTACHED.getStatusId()+"";

		Map statsForCurrWeek = getStatsForCurrentWeek();
		Map statsForCurrMonth = getStatsForCurrentMonth();
		Map statsFor3Month = getStatsForThreePreviousMonths();
		Map statsForAll = getStatsForAll();
		
		for( Object key : statsForCurrWeek.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long curWeekAmount = ( Long ) statsForCurrWeek.get( key );
			SaviourStatsFullViewBean _tmp = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setCurrentWeekOpened( new Long( _tmp.getCurrentWeekOpened().longValue() + curWeekAmount.longValue() ) );
			} else { // jeszcze nie ma klucza wiec nie ma rowiez beana :)
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setCurrentWeekOpened( curWeekAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		} // end for
		
		for( Object key : statsForCurrMonth.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long currMonthAmount = ( Long ) statsForCurrWeek.get( key );
			SaviourStatsFullViewBean _tmp = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setCurrentMonthOpened( new Long( _tmp.getCurrentMonthOpened().longValue() + currMonthAmount.longValue() ) );
			} else {
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setCurrentMonthOpened( currMonthAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		} // end for
		
		for( Object key : statsFor3Month.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long threeMonthAmount = ( Long ) statsFor3Month.get( key );
			SaviourStatsFullViewBean _tmp = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setPrev3MonthsOpened( new Long( _tmp.getPrev3MonthsOpened().longValue() + threeMonthAmount.longValue() ) );
			} else {
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setPrev3MonthsOpened( threeMonthAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		}
		
		for( Object key : statsForAll.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long fullAmount = ( Long ) statsForAll.get( key );
			SaviourStatsFullViewBean _tmp  = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setAllOpened( new Long( _tmp.getAllOpened().longValue() + fullAmount.longValue() ) );
			} else {
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setAllOpened( fullAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		}
		
		// najpierw status ustawiamy na otwarty
		this.statusId = BugStatus.RESOLVED.getStatusId()+"";

		statsForCurrWeek = getStatsForCurrentWeek();
		statsForCurrMonth = getStatsForCurrentMonth();
		statsFor3Month = getStatsForThreePreviousMonths();
		statsForAll = getStatsForAll();
		
		for( Object key : statsForCurrWeek.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long curWeekAmount = ( Long ) statsForCurrWeek.get( key );
			SaviourStatsFullViewBean _tmp = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setCurrentWeekClosed( new Long( _tmp.getCurrentWeekClosed().longValue() + curWeekAmount.longValue() ) );
			} else { // jeszcze nie ma klucza wiec nie ma rowiez beana :)
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setCurrentWeekClosed( curWeekAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		} // end for
		
		for( Object key : statsForCurrMonth.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long currMonthAmount = ( Long ) statsForCurrWeek.get( key );
			SaviourStatsFullViewBean _tmp = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setCurrentMonthClosed( new Long( _tmp.getCurrentMonthClosed().longValue() + currMonthAmount.longValue() ) );
			} else {
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setCurrentMonthClosed( currMonthAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		} // end for
		
		for( Object key : statsFor3Month.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long threeMonthAmount = ( Long ) statsFor3Month.get( key );
			SaviourStatsFullViewBean _tmp = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setPrev3MonthsClosed( new Long( _tmp.getPrev3MonthsClosed().longValue() + threeMonthAmount.longValue() ) );
			} else {
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setPrev3MonthsClosed( threeMonthAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		}
		
		for( Object key : statsForAll.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long fullAmount = ( Long ) statsForAll.get( key );
			SaviourStatsFullViewBean _tmp  = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setAllClosed( new Long( _tmp.getAllClosed().longValue() + fullAmount.longValue() ) );
			} else {
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setAllClosed( fullAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		}
		
		// najpierw status ustawiamy na otwarty
		this.statusId = BugStatus.REJECTED.getStatusId() + "";

		statsForCurrWeek = getStatsForCurrentWeek();
		statsForCurrMonth = getStatsForCurrentMonth();
		statsFor3Month = getStatsForThreePreviousMonths();
		statsForAll = getStatsForAll();
		
		for( Object key : statsForCurrWeek.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long curWeekAmount = ( Long ) statsForCurrWeek.get( key );
			SaviourStatsFullViewBean _tmp = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setCurrentWeekClosed( new Long( _tmp.getCurrentWeekClosed().longValue() + curWeekAmount.longValue() ) );
			} else { // jeszcze nie ma klucza wiec nie ma rowiez beana :)
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setCurrentWeekClosed( curWeekAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		} // end for
		
		for( Object key : statsForCurrMonth.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long currMonthAmount = ( Long ) statsForCurrWeek.get( key );
			SaviourStatsFullViewBean _tmp = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setCurrentMonthClosed( new Long( _tmp.getCurrentMonthClosed().longValue() + currMonthAmount.longValue() ) );
			} else {
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setCurrentMonthClosed( currMonthAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		} // end for
		
		for( Object key : statsFor3Month.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long threeMonthAmount = ( Long ) statsFor3Month.get( key );
			SaviourStatsFullViewBean _tmp = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setPrev3MonthsClosed( new Long( _tmp.getPrev3MonthsClosed().longValue() + threeMonthAmount.longValue() ) );
			} else {
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setPrev3MonthsClosed( threeMonthAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		}
		
		for( Object key : statsForAll.keySet() ) {
			BugCategory cat = ( BugCategory ) key;
			Long fullAmount = ( Long ) statsForAll.get( key );
			SaviourStatsFullViewBean _tmp  = fullStats.get( cat.getCategoryName() );
			if( _tmp != null ) { // juz jest
				_tmp.setAllClosed( new Long( _tmp.getAllClosed().longValue() + fullAmount.longValue() ) );
			} else {
				SaviourStatsFullViewBean vb = new SaviourStatsFullViewBean();
				vb.setAllClosed( fullAmount );
				fullStats.put( cat.getCategoryName(), vb );
			}
		}
		log.info( "getFullStats->koniec" );
		return fullStats;
	}
}

/*
 * Klasa mapujaca wynik zapytania na wlasciwy ViewBean
 */
class StatsSaviourQuery extends MappingSqlQuery {
	private static Log log = LogFactory.getLog( StatsSaviourQuery.class );
	public StatsSaviourQuery( DataSource ds, String queryString ) {
		super( ds, queryString );
		compile();
		log.info( "Konstruktor wykonany" );
	}
	@Override
	protected Object mapRow( ResultSet rs, int rowNum ) throws SQLException {
		log.info( "wchodze do mapRow( " + rs.toString() +", " + rowNum + ")" );
		log.info( "ilosc kolmun w wyniku => " + rs.getMetaData().getColumnCount() );
		TempBean viewBean = new TempBean();
		log.info( "viewBean zostal utworzony" );
		viewBean.setCategoryId( rs.getLong( "category_id" ) );
		log.info( "setCategoryId zrobione" );
		viewBean.setCategoryName( rs.getString( "category_name" ) );
		log.info( "setCategoryName zrobione" );
		viewBean.setAmount( new Long( 888 ) );
		log.info( "setAmount zrobione" );
		log.info( "wychodze z mapRow( " + rs.toString() +", " + rowNum + ")" );
		return viewBean;
	}
}

class TempBean {
	private static Log log = LogFactory.getLog( TempBean.class );
	private Long categoryId;
	private String categoryName;
	private Long amount;
	
	/**
	 * @return Returns the amount.
	 */
	public Long getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(Long amount) {
		log.info( "wykonuje setAmount(" + amount + ")" );
		this.amount = amount;
	}
	/**
	 * @return Returns the categoryId.
	 */
	public Long getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId The categoryId to set.
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return Returns the categoryName.
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName The categoryName to set.
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}

class StatsSaviourQueryBuilder {
	private StringBuffer sb = 
		new StringBuffer( "SELECT category_id,category_name,count(*) AS amount " )
			.append( "FROM hd_bug " )
			.append( "LEFT OUTER JOIN hd_bug_category " )
			.append( "ON hd_bug.bug_category = hd_bug_category.category_id " );	
	
	private StringBuffer query;
	
	public StatsSaviourQueryBuilder( String saviourId, String statusId ) {
		query = sb.append( "WHERE saviour=" ).append( saviourId ).append( " AND bug_status=" ).append( statusId ).append( " " );
	}

	public String getQueryForAll() {
		StringBuffer s = new StringBuffer( query )
			.append( "GROUP BY category_name,category_id " )
			.append( "ORDER BY category_name DESC " );
		return s.toString();
	}
	
	public String getQueryForDates( String start_date, String end_date ) {
		StringBuffer s = new StringBuffer( query )
			.append( "AND create_date >= '" + start_date +  "' AND " )
			.append( " create_date <= '" + end_date + "' " )
			.append( "GROUP BY category_name,category_id " )
			.append( "ORDER BY category_name DESC " );
		return s.toString();
	}
}