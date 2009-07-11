package de.berlios.jhelpdesk.model;

import java.util.Date;

/**
 * @author jjhop
 */
public class BugEvent {

	private Long bugEventId;
	private Long bugId;
	private Date evtDate;
	private User evtAuthor;
	private String evtSubject;
	private EventType eventType;

	/**
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

	public String toString() {
		return "[" + bugEventId + "] => [" + evtSubject + "]";
	}
}