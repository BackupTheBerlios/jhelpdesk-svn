package com.jjhop.helpdesk.model;

import java.util.Set;

/**
 * @hibernate.class
 * 		table="hd_knowledge_section"
 */
public class KnowledgeSection {
	private Long hdKnowledgeSectionId;
	private Long sectionPosition;
	private String sectionName;
	private int articlesCount;
	private Set articles;
	
	/**
	 * @hibernate.id
	 * 		generator-class="sequence"
	 * 		column="hd_knowledge_section_id"
	 * @hibernate.generator-param
	 * 		name="sequence"
	 * 		value="hd_knowledge_section_hd_knowledge_section_is_seq"
	 * 
	 * @return Returns the hdKnowledgeSectionId.
	 */
	public Long getHdKnowledgeSectionId() {
		return hdKnowledgeSectionId;
	}
	/**
	 * @param hdKnowledgeSectionId The hdKnowledgeSectionId to set.
	 */
	public void setHdKnowledgeSectionId(Long hdKnowledgeSectionId) {
		this.hdKnowledgeSectionId = hdKnowledgeSectionId;
	}
	
	/**
	 * @hibernate.property
	 * 		column="section_name"
	 * 		type="java.lang.String"
	 * 		not-null="true"
	 * 
	 * @return Returns the sectionName.
	 */
	public String getSectionName() {
		return sectionName;
	}
	/**
	 * @param sectionName The sectionName to set.
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	/**
	 * @hibernate.property
	 * 		column="section_position"
	 * 		type="java.lang.Long"
	 * 		not-null="true"
	 * @return Returns the sectionPosition.
	 */
	public Long getSectionPosition() {
		return sectionPosition;
	}
	/**
	 * @param sectionPosition The sectionPosition to set.
	 */
	public void setSectionPosition(Long sectionPosition) {
		this.sectionPosition = sectionPosition;
	}
	
	/**
	 * @return the articlesCount
	 */
	public int getArticlesCount() {
		if( articles != null )
			articlesCount = articles.size();
		return articlesCount;
	}
	
	/**
	 * @param articlesCount the articlesCount to set
	 */
	public void setArticlesCount( int articlesCount ) {
		this.articlesCount = articlesCount;
	}
	
	/**
	 * @hibernate.set
	 * 		lazy="false"
	 * 		cascade="all"
	 * @hibernate.collection-one-to-many
	 * 		class="com.jjhop.helpdesk.model.HDKnowledge"
	 * @hibernate.collection-key
	 * 		column="hd_knowledge_section_id"
	 * 
	 * @return Returns the articles.
	 */
	public Set getArticles() {
		return articles;
	}
	/**
	 * @param articles The articles to set.
	 */
	public void setArticles(Set articles) {
		this.articles = articles;
	}
	
}
