package de.berlios.jhelpdesk.model;

public class AdditionalFile {
	
	private Long fileId;
	private String originalFileName;
	private String hashedFileName;
	private Long fileSize;
	private String contentType;
	private byte[] fileDate;
	
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType( String contentType ) {
		this.contentType = contentType;
	}
	
	/**
	 * @return the fileDate
	 */
	public byte[] getFileDate() {
		return fileDate;
	}
	
	/**
	 * @param fileDate the fileDate to set
	 */
	public void setFileDate( byte[] fileDate ) {
		this.fileDate = fileDate;
	}
	
	/**
	 * @return the fileId
	 */
	public Long getFileId() {
		return fileId;
	}
	
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId( Long fileId ) {
		this.fileId = fileId;
	}
	
	/**
	 * @return the fileSize
	 */
	public Long getFileSize() {
		return fileSize;
	}
	
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize( Long fileSize ) {
		this.fileSize = fileSize;
	}
	
	/**
	 * @return the hashedFileName
	 */
	public String getHashedFileName() {
		return hashedFileName;
	}
	
	/**
	 * @param hashedFileName the hashedFileName to set
	 */
	public void setHashedFileName( String hashedFileName ) {
		this.hashedFileName = hashedFileName;
	}
	
	/**
	 * @return the originalFileName
	 */
	public String getOriginalFileName() {
		return originalFileName;
	}
	
	/**
	 * @param originalFileName the originalFileName to set
	 */
	public void setOriginalFileName( String originalFileName ) {
		this.originalFileName = originalFileName;
	}
}