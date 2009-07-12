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
package de.berlios.jhelpdesk.dao;

import java.util.List;

import de.berlios.jhelpdesk.model.BugCategory;

/**
 * 
 * @author jjhop
 */
public interface BugCategoryDAO {

    /**
     *
     * @return
     */
    List<BugCategory> getAllCategories();

    /**
     * 
     * @return
     */
    List<BugCategory> getAllCategoriesForView();

    /**
     *
     * @param id
     * @return
     */
    BugCategory getById(Long id);

    /**
     * 
     * @param id
     * @return
     */
    BugCategory getById(int id);

    /**
     *
     * @param rootCategory
     */
    void insertRootCategory(BugCategory rootCategory);

    /**
     *
     * @param category
     * @param parent
     */
    void insertCategory(BugCategory category, BugCategory parent);

    /**
     *
     * @param category
     */
    void deleteCategory(BugCategory category);

    /**
     *
     * @param category
     */
    void updateCategory(BugCategory category);

    /**
     *
     * @param categoryId
     */
    void moveUp(Long categoryId);

    /**
     *
     * @param categoryId
     */
    void moveDown(Long categoryId);
}
