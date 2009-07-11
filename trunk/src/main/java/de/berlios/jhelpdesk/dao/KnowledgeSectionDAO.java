package de.berlios.jhelpdesk.dao;

import java.util.List;

import de.berlios.jhelpdesk.model.KnowledgeSection;

public interface KnowledgeSectionDAO {
	public List<KnowledgeSection> getAllSections();
	public KnowledgeSection getById( Long sectionId );
	public void delete( Long sectionId );
	public void moveUp( Long sectionId );
	public void moveDown( Long sectionId );
	public void save( KnowledgeSection section );
	public List<KnowledgeSection> getAllShortSections();
}
