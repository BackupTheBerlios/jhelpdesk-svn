package de.berlios.jhelpdesk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author jjhop
 */
public class DateUtil {
	
	private Date target;
	private Calendar cal = Calendar.getInstance();
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Default constructor. Create object with current time.
	 */
	public DateUtil() {
		target = new Date(System.currentTimeMillis());
	}

	/**
	 * Constructor with a date as string
	 * 
	 * @param dateToParse target date (with expected format as yyyy-MM-dd)
	 */
	public DateUtil(String dateToParse) {
		try {
			target = formatter.parse(dateToParse);
		} catch (Exception ex) {
			ex.printStackTrace(); // TODO: obsługa wyjątku
		}
	}

	/**
	 * Constructor with a date as param.
	 * 
	 * @param date target date
	 */
	public DateUtil(Date date) {
		target = date;
	}

	/**
	 * Return date of first day for week with a choosed day
	 * 
	 * @return - date as string formatted as yyyy-MM-dd
	 */
	public String getWeekStartDate() {
		cal.setTime(target);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return formatter.format(cal.getTime());
	}

	/**
	 * Return date of last day for week with a choosed day
	 * 
	 * @return - date as string formatted as yyyy-MM-dd
	 */
	public String getWeekEndDate() {
		cal.setTime(target);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 6);
		return formatter.format(cal.getTime());
	}

	/**
	 * Return date of first day of month with a choosed day
	 * 
	 * @return - date as string formatted as yyyy-MM-dd
	 */
	public String getMonthStartDate() {
		cal.setTime(target);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatter.format(cal.getTime());
	}

	/**
	 * Return date of last day of month with a choosed day
	 * 
	 * @return - date as string formatted as yyyy-MM-dd
	 */
	public String getMonthEndDate() {
		cal.setTime(target);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return formatter.format(cal.getTime());
	}

	public String get3MonthsEarlierDate() {
		cal.setTime(target);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.MONTH, -3);
		return formatter.format(cal.getTime());
	}

	public String getPreviousMonthLastDayDate() {
		cal.setTime(target);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.MONTH, -1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return formatter.format(cal.getTime());
	}
}
