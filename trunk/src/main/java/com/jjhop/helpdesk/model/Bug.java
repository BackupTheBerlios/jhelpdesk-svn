package com.jjhop.helpdesk.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

/**
 * @hibernate.class
 * 		table="hd_bug"
 */
public class Bug {
	private Long bugId;
	
	private Date createDate;
	
	private User notifier;
	private User saviour;
	private User inputer;
	
	private String addPhone;
	
	private String subject;
	private String description;
	private String stepByStep; // kroki by powtorzyc
	
	private BugStatus bugStatus;
	private BugPriority bugPriority;
	private BugCategory bugCategory;
	
	private Set comments;
	private Set events;
	private List<AdditionalFile> addFilesList;
	
	
	private MultipartFile uploadedFile; // if object is used as commandObject
	
	public Bug() {
		this.comments = new HashSet();
		this.addFilesList = new ArrayList<AdditionalFile>();
	}
	
	/**
	 * @hibernate.property
	 * 		column="add_phone"
	 * 		type="java.lang.String"
	 * 		length="20"
	 */
	public String getAddPhone() {
		return addPhone;
	}
	public void setAddPhone(String addPhone) {
		this.addPhone = addPhone;
	}
	/**
	 * @hibernate.many-to-one
	 * 		column="bug_category"
	 */
	public BugCategory getBugCategory() {
		return bugCategory;
	}
	public void setBugCategory(BugCategory bugCategory) {
		this.bugCategory = bugCategory;
	}
	/**
	 * @hibernate.property
	 * 		column="create_date"
	 * 		type="java.util.Date"
	 * 		not-null="true"
	 */
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate( Date bugCreateDate ) {
		this.createDate = bugCreateDate;
	}
	/**
	 * @hibernate.id
	 * 		generator-class="sequence"
	 * 		column="hd_bug_id"
	 * @hibernate.generator-param
	 * 		name="sequence"
	 * 		value="hd_bug_hd_bug_id_seq"
	 */
	public Long getBugId() {
		return bugId;
	}
	public void setBugId(Long bugId) {
		this.bugId = bugId;
	}
	/**
	 * 
	 * @hibernate.many-to-one
	 * 		column="bug_priority"
	 */
	public BugPriority getBugPriority() {
		return bugPriority;
	}
	public void setBugPriority(BugPriority bugPriority) {
		this.bugPriority = bugPriority;
	}
	/**
	 * 
	 * @hibernate.property
	 * 		column="bug_status"
	 * 		not-null="true"
	 */
	public BugStatus getBugStatus() {
		return bugStatus;
	}
	public void setBugStatus(BugStatus bugStatus) {
		this.bugStatus = bugStatus;
	}
	/**
	 * @hibernate.property
	 * 		column="description"
	 * 		type="java.lang.String"
	 * 		not-null="true"
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @hibernate.many-to-one
	 * 		column="saviour"
	 */
	public User getSaviour() {
		return saviour;
	}
	public void setSaviour(User rozwiazujacy) {
		this.saviour = rozwiazujacy;
	}
	/**
	 * @hibernate.property
	 * 		column="step_by_step"
	 * 		type="java.lang.String"
	 * 		not-null="false"
	 */
	public String getStepByStep() {
		return stepByStep;
	}
	public void setStepByStep(String stepByStep) {
		this.stepByStep = stepByStep;
	}
	/**
	 * @hibernate.property
	 * 		column="subject"
	 * 		type="java.lang.String"
	 * 		length="255"
	 * 		not-null="true"
	 */
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 
	 * @hibernate.many-to-one
	 * 		column="inputer"
	 */
	public User getInputer() {
		return inputer;
	}
	public void setInputer(User wprowadzajacy) {
		this.inputer = wprowadzajacy;
	}
	/**
	 * 
	 * @hibernate.many-to-one
	 * 		column="notifier"
	 */
	public User getNotifier() {
		return notifier;
	}
	public void setNotifier(User zglaszajacy) {
		this.notifier = zglaszajacy;
	}
	
	/**
	 * @hibernate.set
	 * 		lazy="false"
	 * 		cascade="all"
	 * 		order-by="comment_date"
	 * @hibernate.collection-one-to-many
	 * 		class="com.jjhop.helpdesk.model.BugComment"
	 * @hibernate.collection-key
	 * 		column="bug_id"
	 * 
	 * @return Returns the comments.
	 */
	public Set getComments() {
		return comments;
	}
	/**
	 * @param comments The comments to set.
	 */
	public void setComments( Set comments ) {
		this.comments = comments;
	}
	
	/**
	 * 
	 * @param comment
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private void addComment( BugComment comment ) {
		if( this.comments == null )
			this.comments = new HashSet();
		this.comments.add( comment );
	}
	
	/**
	 * @hibernate.set
	 * 		lazy="false"
	 * 		cascade="all"
	 * 		order-by="event_date"
	 * @hibernate.collection-one-to-many
	 * 		class="com.jjhop.helpdesk.model.BugEvent"
	 * @hibernate.collection-key
	 * 		column="hd_bug_id"
	 * 
	 * @return Returns the events.
	 */
	public Set getEvents() {
		return events;
	}
	/**
	 * @param events The events to set.
	 */
	public void setEvents( Set events ) {
		this.events = events;
	}
	
	/**
	 * 
	 * @param event
	 */
	
	@SuppressWarnings({ "unchecked", "unused" })
	private void addEvent( BugEvent event ) {
		if( this.events == null )
			this.events = new HashSet();
		this.events.add( event );
	}

	/**
	 * @param addFilesList the addFilesList to set
	 */
	public void setAddFilesList( List<AdditionalFile> addFilesList ) {
		this.addFilesList = addFilesList;
	}

	/**
	 * @param uploadedFile the uploadedFile to set
	 */
	public void setUploadedFile( MultipartFile uploadedFile ) {
		this.uploadedFile = uploadedFile;
	}

	/**
	 * @return the addFilesList
	 */
	public List<AdditionalFile> getAddFilesList() {
		return addFilesList;
	}

	/**
	 * @return the uploadedFile
	 */
	public MultipartFile getUploadedFile() {
		return uploadedFile;
	}
	
}
