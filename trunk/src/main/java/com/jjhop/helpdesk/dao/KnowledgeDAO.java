package com.jjhop.helpdesk.dao;

import java.util.List;

import com.jjhop.helpdesk.model.Knowledge;

public interface KnowledgeDAO {
	Knowledge getById( Long knowledgeId );
	void save( Knowledge knowledge );
	void delete( Knowledge knowledge );
	void delete( Long knowledgeId );
	List<Knowledge> getForSection( Long sectionId );
	List<Knowledge> getLastAddedArticles( int howMuch );
}
