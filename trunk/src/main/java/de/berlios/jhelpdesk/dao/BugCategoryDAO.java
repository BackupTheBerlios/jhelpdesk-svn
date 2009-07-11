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
