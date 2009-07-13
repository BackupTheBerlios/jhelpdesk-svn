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
import de.berlios.jhelpdesk.pao.StatsCategoryPAO;
import de.berlios.jhelpdesk.web.view.bean.StatsByCategoryViewBean;

@Repository("statsCategoryPAO")
public class StatsCategoryPAOPgSql extends AbstractJdbcTemplateSupport implements StatsCategoryPAO {

    private static Log log = LogFactory.getLog(StatsCategoryPAOPgSql.class);

    @Autowired
    public StatsCategoryPAOPgSql(DataSource dataSource) {
        super(dataSource);
    }

	@SuppressWarnings("unchecked")
    public Map<String, Long> getStatsForCurrentWeek() {
        DateUtil du = new DateUtil();
        log.info("du.getWeekStartDate() => " + du.getWeekStartDate());
        log.info("du.getWeekEndDate() => " + du.getWeekEndDate());
		String query = 
			new StatsCategoryQueryBuilder().getQueryForDates(
				du.getWeekStartDate(),
                du.getWeekEndDate());
		Map<String, Long> tr = new HashMap<String, Long>();
		List<StatsByCategoryViewBean> list = 
			new StatForAllQuery(
				getDataSource(),
				query).execute();
        for (StatsByCategoryViewBean bean : list) {
            tr.put(bean.getCategoryName(), bean.getAmount());
        }
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Long> getStatsForCurrentMonth() {
		DateUtil du = new DateUtil();
		String query = 
			new StatsCategoryQueryBuilder().getQueryForDates(
				du.getMonthStartDate(),
				du.getMonthEndDate());
		Map<String, Long> tr = new HashMap<String, Long>();
		List<StatsByCategoryViewBean> list = 
			new StatForAllQuery(
				getDataSource(),
				query).execute();
        for (StatsByCategoryViewBean bean : list) {
            tr.put(bean.getCategoryName(), bean.getAmount());
        }
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Long> getStatsForThreePreviousMonths() {
		DateUtil du = new DateUtil();
		String query = 
			new StatsCategoryQueryBuilder().getQueryForDates(
				du.get3MonthsEarlierDate(),
				du.getPreviousMonthLastDayDate() );
		
		Map<String, Long> tr = new HashMap<String, Long>();
		List<StatsByCategoryViewBean> list = 
			new StatForAllQuery(
				getDataSource(),
				query).execute();
        for (StatsByCategoryViewBean bean : list) {
            tr.put(bean.getCategoryName(), bean.getAmount());
        }
		return tr;
	}

	// TODO
	@SuppressWarnings("unchecked")
	public Map<String, Long> getStatsForLastYear() {
		DateUtil du = new DateUtil();
		String query = 
			new StatsCategoryQueryBuilder().getQueryForDates(
				du.getPreviousMonthLastDayDate(),
				du.getMonthEndDate());
		Map<String, Long> tr = new HashMap<String, Long>();
		List<StatsByCategoryViewBean> list = 
			new StatForAllQuery(
				getDataSource(),
				query).execute();
        for (StatsByCategoryViewBean bean : list) {
            tr.put(bean.getCategoryName(), bean.getAmount());
        }
		return tr;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Long> getStatsForAll() {
		Map<String, Long> tr = new HashMap<String, Long>();
		List<StatsByCategoryViewBean> list =
			new StatForAllQuery( 
				getDataSource(),
				new StatsCategoryQueryBuilder().getQueryForAll()
		).execute();
        for (StatsByCategoryViewBean bean : list) {
            tr.put(bean.getCategoryName(), bean.getAmount());
        }
		return tr;
	}
}
class StatsCategoryQueryBuilder {
	private final static StringBuffer sb = 
		new StringBuffer("SELECT category_id,t_left,category_name,count(*) as amount ")
			.append("FROM hd_bug ")
			.append("LEFT OUTER JOIN hd_bug_category ")
			.append("ON hd_bug.bug_category = hd_bug_category.category_id ");

	public String getQueryForAll() {
        StringBuffer s = new StringBuffer(sb.toString())
			.append("GROUP BY category_name,category_id,t_left ")
			.append("ORDER BY category_name ASC ");
		return s.toString();
	}
	public String getQueryForDates(String start_date, String end_date) {
		StringBuffer s = new StringBuffer(sb.toString())
			.append("WHERE create_date >= '" + start_date + "' AND ")
			.append(" create_date <= '" + end_date + "' ")
			.append("GROUP BY category_name,category_id,t_left ")
			.append("ORDER BY category_name ASC ");
		return s.toString();
	}
}

class StatForAllQuery extends MappingSqlQuery {

    public StatForAllQuery(DataSource ds, String query) {
        super(ds, query);
        compile();
    }

    @Override
    protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        StatsByCategoryViewBean bean = new StatsByCategoryViewBean();
        bean.setCategoryId(rs.getLong("category_id"));
        bean.setCategoryName(rs.getString("category_name"));
        bean.setCatLeft(rs.getLong("t_left"));
        bean.setAmount(rs.getLong("amount"));
        return bean;
    }
}