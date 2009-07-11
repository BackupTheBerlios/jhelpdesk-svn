package de.berlios.jhelpdesk.model;

import java.util.Date;

public class Information {
	private Long informationId;
	private Date createDate;
	private String title;
	private String lead;
	private String body;
	
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * @param body the body to set
	 */
	public void setBody( String body ) {
		this.body = body;
	}
	
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate( Date createDate ) {
		this.createDate = createDate;
	}
	
	/**
	 * @return the informationId
	 */
	public Long getInformationId() {
		return informationId;
	}
	
	/**
	 * @param informationId the informationId to set
	 */
	public void setInformationId( Long informationId ) {
		this.informationId = informationId;
	}
	
	/**
	 * @return the lead
	 */
	public String getLead() {
		return lead;
	}
	
	/**
	 * @param lead the lead to set
	 */
	public void setLead( String lead ) {
		this.lead = lead;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle( String title ) {
		this.title = title;
	}
}
