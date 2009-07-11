package de.berlios.jhelpdesk.pao.plpgsql;

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

import de.berlios.jhelpdesk.DateUtil;
import de.berlios.jhelpdesk.pao.ifc.IStatsPriority;
import de.berlios.jhelpdesk.web.view.bean.StatsByPriorityViewBean;

public class StatsPriorityPAOPgSql extends JdbcDaoSupport implements IStatsPriority {

	private static Log log = LogFactory.getLog( StatsPriorityPAOPgSql.class );
	
	@SuppressWarnings("unchecked")
	public Map<String, Long> getStatsForCurrentWeek() {
		DateUtil du = new DateUtil();
		log.info( "du.getWeekStartDate() => " + du.getWeekStartDate() );
		log.info( "du.getWeekEndDate() => " + du.getWeekEndDate() );
		String query = 
			new StatsPriorityQueryBuilder().getQueryForDates(
				du.getWeekStartDate(),
				du.getWeekEndDate() );
		Map<String, Long> tr = new HashMap<String, Long>();
		List<StatsByPriorityViewBean> list = 
			new PriorityStatForAllQuery(
				getDataSource(),
				query).execute();
		for( StatsByPriorityViewBean bean : list ) {
			tr.put( bean.getPriorityName(), bean.getAmount() );
		}
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Long> getStatsForCurrentMonth() {
		DateUtil du = new DateUtil();
		String query = 
			new StatsPriorityQueryBuilder().getQueryForDates(
				du.getMonthStartDate(),
				du.getMonthEndDate() );
		Map<String, Long> tr = new HashMap<String, Long>();
		List<StatsByPriorityViewBean> list = 
			new PriorityStatForAllQuery(
				getDataSource(),
				query).execute();
		for( StatsByPriorityViewBean bean : list ) {
			tr.put( bean.getPriorityName(), bean.getAmount() );
		}
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Long> getStatsForThreePreviousMonths() {
		DateUtil du = new DateUtil();
		String query = 
			new StatsPriorityQueryBuilder().getQueryForDates(
				du.get3MonthsEarlierDate(),
				du.getPreviousMonthLastDayDate() );
		
		Map<String, Long> tr = new HashMap<String, Long>();
		List<StatsByPriorityViewBean> list = 
			new PriorityStatForAllQuery(
				getDataSource(),
				query).execute();
		for( StatsByPriorityViewBean bean : list ) {
			tr.put( bean.getPriorityName(), bean.getAmount() );
		}
		return tr;
	}

	public Map<String, Long> getStatsForLastYear() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map getStatsForAll() {
		Map<String, Long> tr = new HashMap<String, Long>();
		List<StatsByPriorityViewBean> list =
			new PriorityStatForAllQuery(
				getDataSource(),
				new StatsPriorityQueryBuilder().getQueryForAll()).execute();
		for( StatsByPriorityViewBean bean : list ) {
			tr.put( bean.getPriorityName(), bean.getAmount() );
		}
		return tr;
	}
}

class StatsPriorityQueryBuilder {
	private final static StringBuffer sb = 
		new StringBuffer( "SELECT priority_id,priority_name,count(*) as amount " )
			.append( "FROM hd_bug " )
			.append( "LEFT OUTER JOIN hd_bug_priority " )
			.append( "ON hd_bug.bug_priority = hd_bug_priority.priority_id " );

	public String getQueryForAll() {
		StringBuffer s = new StringBuffer( sb.toString() )
			.append( "GROUP BY priority_name,priority_id " )
			.append( "ORDER BY amount DESC " );
		return s.toString();
	}
	public String getQueryForDates( String start_date, String end_date ) {
		StringBuffer s = new StringBuffer( sb.toString() )
			.append( "WHERE create_date >= '" + start_date +  "' AND " )
			.append( " create_date <= '" + end_date + "' " )
			.append( "GROUP BY priority_name,priority_id " )
			.append( "ORDER BY amount DESC " );
		return s.toString();
	}
}

class PriorityStatForAllQuery extends MappingSqlQuery {

	public PriorityStatForAllQuery( DataSource ds, String query ) {
		super( ds, query );
		compile();
	}
	
	@Override
	protected Object mapRow( ResultSet rs, int rowNum ) throws SQLException {
		StatsByPriorityViewBean bean = new StatsByPriorityViewBean();
		bean.setPriorityId( rs.getLong( "priority_id" ) );
		bean.setPriorityName( rs.getString( "priority_name" ) );
		bean.setAmount( rs.getLong( "amount" ) );
		return bean;
	}
}