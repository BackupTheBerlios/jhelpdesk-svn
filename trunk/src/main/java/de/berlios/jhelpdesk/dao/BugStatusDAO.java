package de.berlios.jhelpdesk.dao;

import java.util.List;

import de.berlios.jhelpdesk.model.BugStatus;

public interface BugStatusDAO {
	List<BugStatus> getAllStatuses();
	List<BugStatus> getNonOpenedStatuses();
	BugStatus getById( Long id );
	BugStatus getById( int id );
}
