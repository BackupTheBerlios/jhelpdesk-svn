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
package de.berlios.jhelpdesk.pao.plpgsql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Repository;

import de.berlios.jhelpdesk.DateUtil;
import de.berlios.jhelpdesk.dao.jdbc.AbstractJdbcTemplateSupport;
import de.berlios.jhelpdesk.pao.StatsPriorityPAO;
import de.berlios.jhelpdesk.web.tools.view.StatsByPriorityViewBean;

@Repository("statsPriorityPAO")
public class StatsPriorityPAOPgSql extends AbstractJdbcTemplateSupport implements StatsPriorityPAO {

	private static Log log = LogFactory.getLog( StatsPriorityPAOPgSql.class );

    @Autowired
    public StatsPriorityPAOPgSql(DataSource dataSource) {
        super(dataSource);
    }
	
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