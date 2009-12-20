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
package de.berlios.jhelpdesk.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jjhop
 */
@Entity
@Table(name = "ticket_category")
public class TicketCategory implements Serializable, Comparable<TicketCategory> {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long ticketCategoryId;

    /**
     *
     */
    @Column(name = "parent_category") // TODO: przerobić na obiekt po prostu
    private Long parentCategory; // != null jesli jest podkategorią

    /**
     *
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     *
     */
    @Column(name = "category_desc")
    private String categoryDesc;

    /**
     *
     */
    @Column(name = "t_left")
    private Long left;

    /**
     *
     */
    @Column(name = "t_right")
    private Long right;

    /**
     *
     */
    @Column(name = "t_depth")
    private Integer depth;

    /**
     *
     */
    @Column(name = "is_active")
    private boolean isActive;

    /**
     *
     */
    public TicketCategory() {
    }

    /**
     *
     * @param categoryId
     * @param categoryName
     */
    public TicketCategory(int categoryId, String categoryName) {
        this.ticketCategoryId = new Long(categoryId);
        this.categoryName = categoryName;
    }

    /**
     * @return Returns the ticketCategoryId.
     */
    public Long getTicketCategoryId() {
        return ticketCategoryId;
    }

    /**
     * @param ticketCategoryId The ticketCategoryId to set.
     */
    public void setTicketCategoryId(Long ticketCategoryId) {
        this.ticketCategoryId = ticketCategoryId;
    }

    /**
     * @return Returns the categoryDesc.
     */
    public String getCategoryDesc() {
        return categoryDesc;
    }

    /**
     * @param categoryDesc
     *            The categoryDesc to set.
     */
    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    /**
     * @return Returns the categoryName.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName
     *            The categoryName to set.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the depth
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * @param depth
     *            the depth to set
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * @return the left
     */
    public Long getLeft() {
        return left;
    }

    /**
     * @param left
     *            the left to set
     */
    public void setLeft(Long left) {
        this.left = left;
    }

    /**
     * @return the right
     */
    public Long getRight() {
        return right;
    }

    /**
     * @param right
     *            the right to set
     */
    public void setRight(Long right) {
        this.right = right;
    }

    /**
     * @return Returns the isActive.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param isActive
     *            The isActive to set.
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return Returns the parentCategory.
     */
    public Long getParentCategory() {
        return parentCategory;
    }

    /**
     * @param parentCategory
     *            The parentCategory to set.
     */
    public void setParentCategory(Long parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public String toString() {
        return categoryName;
    }

    public boolean hasChildNodes() {
        return left + 1 != right;
    }

    /**
     * {@inheritDoc }
     */
    public int compareTo(TicketCategory o) {
        return getLeft().compareTo((o).getLeft());
    }
}
