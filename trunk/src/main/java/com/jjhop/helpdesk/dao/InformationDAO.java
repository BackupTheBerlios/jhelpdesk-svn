package com.jjhop.helpdesk.dao;

import java.util.List;

import com.jjhop.helpdesk.model.Information;

public interface InformationDAO {
	Information getById( Long informationId );
	List<Information> getAll();
	List<Information> getLastFew( int howMuch );
	void save( Information information );
	void delete( Long informationId );
	void delete( Information information );
}
