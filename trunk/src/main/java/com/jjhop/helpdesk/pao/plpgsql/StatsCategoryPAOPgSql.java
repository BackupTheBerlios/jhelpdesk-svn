package com.jjhop.helpdesk.pao.plpgsql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.jjhop.helpdesk.DateUtil;
import com.jjhop.helpdesk.pao.ifc.IStatsCategory;
import com.jjhop.helpdesk.web.view.bean.StatsByCategoryViewBean;

public class StatsCategoryPAOPgSql extends JdbcDaoSupport implements IStatsCategory {
	
	private static Log log = LogFactory.getLog( StatsCategoryPAOPgSql.class );
	
	@SuppressWarnings("unchecked")
	public Map getStatsForCurrentWeek() {
		DateUtil du = new DateUtil();
		log.info( "du.getWeekStartDate() => " + du.getWeekStartDate() );
		log.info( "du.getWeekEndDate() => " + du.getWeekEndDate() );
		String query = 
			new StatsCategoryQueryBuilder().getQueryForDates(
				du.getWeekStartDate(),
				du.getWeekEndDate() );
		Map tr = new HashMap();
		List<StatsByCategoryViewBean> list = 
			new StatForAllQuery(
				getDataSource(),
				query).execute();
		for( StatsByCategoryViewBean bean : list ) {
			tr.put( bean.getCategoryName(), bean.getAmount() );
		}
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map getStatsForCurrentMonth() {
		DateUtil du = new DateUtil();
		String query = 
			new StatsCategoryQueryBuilder().getQueryForDates(
				du.getMonthStartDate(),
				du.getMonthEndDate() );
		Map tr = new HashMap();
		List<StatsByCategoryViewBean> list = 
			new StatForAllQuery(
				getDataSource(),
				query).execute();
		for( StatsByCategoryViewBean bean : list ) {
			tr.put( bean.getCategoryName(), bean.getAmount() );
		}
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map getStatsForThreePreviousMonths() {
		DateUtil du = new DateUtil();
		String query = 
			new StatsCategoryQueryBuilder().getQueryForDates(
				du.get3MonthsEarlierDate(),
				du.getPreviousMonthLastDayDate() );
		
		Map tr = new HashMap();
		List<StatsByCategoryViewBean> list = 
			new StatForAllQuery(
				getDataSource(),
				query).execute();
		for( StatsByCategoryViewBean bean : list ) {
			tr.put( bean.getCategoryName(), bean.getAmount() );
		}
		return tr;
	}

	// TODO
	@SuppressWarnings("unchecked")
	public Map getStatsForLastYear() {
		DateUtil du = new DateUtil();
		String query = 
			new StatsCategoryQueryBuilder().getQueryForDates(
				du.getPreviousMonthLastDayDate(),
				du.getMonthEndDate() );
		Map tr = new HashMap();
		List<StatsByCategoryViewBean> list = 
			new StatForAllQuery(
				getDataSource(),
				query).execute();
		for( StatsByCategoryViewBean bean : list ) {
			tr.put( bean.getCategoryName(), bean.getAmount() );
		}
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map getStatsForAll() {
		Map tr = new HashMap();
		List<StatsByCategoryViewBean> list =
			new StatForAllQuery( 
				getDataSource(),
				new StatsCategoryQueryBuilder().getQueryForAll()
		).execute();
		
		for( StatsByCategoryViewBean bean : list ) {
			tr.put( bean.getCategoryName(), bean.getAmount() );
		}
		return tr;
	}
}

class StatsCategoryQueryBuilder {
	private final static StringBuffer sb = 
		new StringBuffer( "SELECT category_id,t_left,category_name,count(*) as amount " )
			.append( "FROM hd_bug " )
			.append( "LEFT OUTER JOIN hd_bug_category " )
			.append( "ON hd_bug.bug_category = hd_bug_category.category_id " );

	public String getQueryForAll() {
		StringBuffer s = new StringBuffer( sb.toString() )
			.append( "GROUP BY category_name,category_id,t_left " )
			.append( "ORDER BY category_name ASC " );
		return s.toString();
	}
	public String getQueryForDates( String start_date, String end_date ) {
		StringBuffer s = new StringBuffer( sb.toString() )
			.append( "WHERE create_date >= '" + start_date +  "' AND " )
			.append( " create_date <= '" + end_date + "' " )
			.append( "GROUP BY category_name,category_id,t_left " )
			.append( "ORDER BY category_name ASC " );
		return s.toString();
	}
}

class StatForAllQuery extends MappingSqlQuery {

	public StatForAllQuery( DataSource ds, String query ) {
		super( ds, query );
		compile();
	}
	
	@Override
	protected Object mapRow( ResultSet rs, int rowNum ) throws SQLException {
		StatsByCategoryViewBean bean = new StatsByCategoryViewBean();
		bean.setCategoryId( rs.getLong( "category_id" ) );
		bean.setCategoryName( rs.getString( "category_name" ) );
		bean.setCatLeft( rs.getLong( "t_left" ) );
		bean.setAmount( rs.getLong( "amount" ) );
		return bean;
	}
}