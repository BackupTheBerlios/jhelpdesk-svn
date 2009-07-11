package de.berlios.jhelpdesk.web.view.bean;

public class StatsByCategoryViewBean {
	private Long categoryId;
	private String categoryName;
	private Long amount;
	private Long catLeft;
	
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
	
	/**
	 * @return the catLeft
	 */
	public Long getCatLeft() {
		return catLeft;
	}
	
	/**
	 * @param catLeft the catLeft to set
	 */
	public void setCatLeft( Long catLeft ) {
		this.catLeft = catLeft;
	}
}
