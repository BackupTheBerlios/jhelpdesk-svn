package com.jjhop.helpdesk.dao;

import java.util.List;

import com.jjhop.helpdesk.model.BugStatus;

public interface BugStatusDAO {
	List<BugStatus> getAllStatuses();
	List<BugStatus> getNonOpenedStatuses();
	BugStatus getById( Long id );
	BugStatus getById( int id );
}
