package com.jjhop.helpdesk.dao;

import java.util.List;

import com.jjhop.helpdesk.model.BugCategory;

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
