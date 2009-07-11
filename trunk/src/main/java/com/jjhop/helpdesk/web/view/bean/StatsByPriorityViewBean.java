package com.jjhop.helpdesk.web.view.bean;

public class StatsByPriorityViewBean {
	private Long priorityId;
	private String priorityName;
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
		this.amount = amount;
	}
	/**
	 * @return Returns the priorityId.
	 */
	public Long getPriorityId() {
		return priorityId;
	}
	/**
	 * @param priorityId The priorityId to set.
	 */
	public void setPriorityId(Long priorityId) {
		this.priorityId = priorityId;
	}
	/**
	 * @return Returns the priorityName.
	 */
	public String getPriorityName() {
		return priorityName;
	}
	/**
	 * @param priorityName The priorityName to set.
	 */
	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}
}
