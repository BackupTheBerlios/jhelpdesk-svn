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
package de.berlios.jhelpdesk.web.tools.view;

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
