package com.jjhop.helpdesk.dao.impl.jdbc;

import java.util.ArrayList;
import java.util.List;

import com.jjhop.helpdesk.dao.BugStatusDAO;
import com.jjhop.helpdesk.model.BugStatus;

public class BugStatusDAOJdbc implements BugStatusDAO {
	
	public List<BugStatus> getAllStatuses() {
		List<BugStatus> list2Return = new ArrayList<BugStatus>( 4 );
		list2Return.add( BugStatus.NOTIFIED );
		list2Return.add( BugStatus.ATTACHED );
		list2Return.add( BugStatus.REJECTED );
		list2Return.add( BugStatus.RESOLVED );
		list2Return.add( BugStatus.CLOSED );
		return list2Return;
	}

	public List<BugStatus> getNonOpenedStatuses() {
		List<BugStatus> list2Return = new ArrayList<BugStatus>( 2 );
		list2Return.add( BugStatus.NOTIFIED );
		list2Return.add( BugStatus.ATTACHED );
		return list2Return;
	}

	public BugStatus getById( Long id ) {
		return getById( id.intValue() );
	}

	public BugStatus getById( int id ) {
		return BugStatus.fromInt( id );
	}
}
