package de.berlios.jhelpdesk.web.view.bean;

/*
 * Klasa pomocnicza widoku do prezentacji tabeli 
 * pelnej statystyki rozwiazujacego;
 * 
 * @author jjhop
 */
public class SaviourStatsFullViewBean {
	private String categoryName;
	private Long   categoryId;
	private Long   currentWeekClosed;
	private Long   currentWeekOpened;
	private Long   currentMonthClosed;
	private Long   currentMonthOpened;
	private Long   prev3MonthsClosed;
	private Long   prev3MonthsOpened;
	private Long   allClosed;
	private Long   allOpened;
	
	/**
	 * @return Returns the allClosed.
	 */
	public Long getAllClosed() {
		return allClosed;
	}
	/**
	 * @param allClosed The allClosed to set.
	 */
	public void setAllClosed(Long allClosed) {
		this.allClosed = allClosed;
	}
	/**
	 * @return Returns the allOpened.
	 */
	public Long getAllOpened() {
		return allOpened;
	}
	/**
	 * @param allOpened The allOpened to set.
	 */
	public void setAllOpened(Long allOpened) {
		this.allOpened = allOpened;
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
	/**
	 * @return Returns the currentMonthClosed.
	 */
	public Long getCurrentMonthClosed() {
		return currentMonthClosed;
	}
	/**
	 * @param currentMonthClosed The currentMonthClosed to set.
	 */
	public void setCurrentMonthClosed(Long currentMonthClosed) {
		this.currentMonthClosed = currentMonthClosed;
	}
	/**
	 * @return Returns the currentMonthOpened.
	 */
	public Long getCurrentMonthOpened() {
		return currentMonthOpened;
	}
	/**
	 * @param currentMonthOpened The currentMonthOpened to set.
	 */
	public void setCurrentMonthOpened(Long currentMonthOpened) {
		this.currentMonthOpened = currentMonthOpened;
	}
	/**
	 * @return Returns the currentWeekClosed.
	 */
	public Long getCurrentWeekClosed() {
		return currentWeekClosed;
	}
	/**
	 * @param currentWeekClosed The currentWeekClosed to set.
	 */
	public void setCurrentWeekClosed(Long currentWeekClosed) {
		this.currentWeekClosed = currentWeekClosed;
	}
	/**
	 * @return Returns the currentWeekOpened.
	 */
	public Long getCurrentWeekOpened() {
		return currentWeekOpened;
	}
	/**
	 * @param currentWeekOpened The currentWeekOpened to set.
	 */
	public void setCurrentWeekOpened(Long currentWeekOpened) {
		this.currentWeekOpened = currentWeekOpened;
	}
	/**
	 * @return Returns the prev3MonthsClosed.
	 */
	public Long getPrev3MonthsClosed() {
		return prev3MonthsClosed;
	}
	/**
	 * @param prev3MonthsClosed The prev3MonthsClosed to set.
	 */
	public void setPrev3MonthsClosed(Long prev3MonthsClosed) {
		this.prev3MonthsClosed = prev3MonthsClosed;
	}
	/**
	 * @return Returns the prev3MonthsOpened.
	 */
	public Long getPrev3MonthsOpened() {
		return prev3MonthsOpened;
	}
	/**
	 * @param prev3MonthsOpened The prev3MonthsOpened to set.
	 */
	public void setPrev3MonthsOpened(Long prev3MonthsOpened) {
		this.prev3MonthsOpened = prev3MonthsOpened;
	}
}
