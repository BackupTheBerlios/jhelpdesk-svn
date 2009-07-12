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

public interface BugCategoryDAO {
	List<BugCategory> getAllCategories();
	List<BugCategory> getAllCategoriesForView();
	BugCategory getById( Long id );
	BugCategory getById( int id );
	
	void insertRootCategory( BugCategory rootCategory );
	void insertCategory( BugCategory category, BugCategory parent );
	void deleteCategory( BugCategory category );
	void updateCategory( BugCategory category );
	
	void moveUp( Long categoryId );
	void moveDown( Long categoryId );

}
