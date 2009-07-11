package de.berlios.jhelpdesk.model;

public enum BugPriority {
	
	NORMAL(1,"normalny"),
	WAZNY(2,"wazny"),
	BARDZO_WAZNY(3,"bardzo wazny"),
	CRITICAL(4,"krytyczny");
	
	private final int priorityId;
	private final String priorityName;

	private BugPriority( int id, String name ) {
		this.priorityId = id;
		this.priorityName = name;
	}

	public int getPriorityId() {
		return priorityId;
	}

	public String getPriorityName() {
		return priorityName;
	}
	
	public static BugPriority fromInt( int id ) {
		for( BugPriority value : BugPriority.values() ) {
			if(value.getPriorityId() == id )
				return value;
		}
		throw new IllegalArgumentException("Wartosc spoza zakresu. Dostepne wartosci to: 1, 2, 3, 4" );
	}
	
	@Override
	public String toString() {
		return priorityName;
	}

}
