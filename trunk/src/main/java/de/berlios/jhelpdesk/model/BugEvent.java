package de.berlios.jhelpdesk.model;

import java.util.Date;

/**
 * @hibernate.class
 * 		table="hd_bug_event"
 * 
 * @author jjhop
 *
 */
public class BugEvent {
	
	private Long bugEventId;
	private Long bugId;
	private Date evtDate;
	private User evtAuthor;
	private String evtSubject;
	private EventType eventType;
	
	/**
	 * @hibernate.id
	 * 		generator-class="sequence"
	 * 		column="event_id"
	 * 		type="java.lang.Long"
	 * 
	 * @return Returns the bugEventId.
	 */	
	public Long getBugEventId() {
		return bugEventId;
	}
	/**
	 * @param bugEventId The bugEventId to set.
	 */
	public void setBugEventId(Long bugEventId) {
		this.bugEventId = bugEventId;
	}
	
	/**
	 * @hibernate.many-to-one
	 * 		column="user_id"
	 * 		not-null="true"
	 * @return Returns the evtAuthor.
	 */
	public User getEvtAuthor() {
		return evtAuthor;
	}
	/**
	 * @param evtAuthor The evtAuthor to set.
	 */
	public void setEvtAuthor(User evtAuthor) {
		this.evtAuthor = evtAuthor;
	}
	
	/**
	 * @hibernate.property
	 * 		column="event_date"
	 * 		type="java.util.Date"
	 * 		not-null="true"
	 * 
	 * @return Returns the evtDate.
	 */
	public Date getEvtDate() {
		return evtDate;
	}
	/**
	 * @param evtDate The evtDate to set.
	 */
	public void setEvtDate(Date evtDate) {
		this.evtDate = evtDate;
	}
	
	/**
	 * @hibernate.property
	 * 		column="event_subject"
	 * 		type="java.lang.String"
	 * 		length="255"
	 * 		not-null="true"
	 * 
	 * @return Returns the evtSubject.
	 */
	public String getEvtSubject() {
		return evtSubject;
	}
	
	/**
	 * @param evtSubject The evtSubject to set.
	 */
	public void setEvtSubject(String evtSubject) {
		this.evtSubject = evtSubject;
	}
	
	/**
	 * @hibernate.property
	 * 		column="event_type"
	 * 		not-null="true"
	 * 
	 * @return Returns the eventType.
	 */
	public EventType getEventType() {
		return eventType;
	}
	
	/**
	 * @param eventType The eventType to set.
	 */
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
	/**
	 * @hibernate.property
	 * 		column="hd_bug_id"
	 * 		type="java.lang.Long"
	 * 		not-null="true"
	 * 
	 * @return Returns the bugId.
	 */
	public Long getBugId() {
		return bugId;
	}

	/**
	 * @param bugId The bugId to set.
	 */
	public void setBugId(Long bugId) {
		this.bugId = bugId;
	}
	
	public String toString( ) {
		return "[" + bugEventId + "] => [" +  evtSubject + "]" ;
	}
}