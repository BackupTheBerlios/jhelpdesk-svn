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
