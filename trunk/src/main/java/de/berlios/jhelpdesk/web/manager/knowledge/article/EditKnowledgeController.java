package de.berlios.jhelpdesk.web.manager.knowledge.article;

import org.springframework.web.servlet.mvc.SimpleFormController;

import de.berlios.jhelpdesk.dao.KnowledgeDAO;

public class EditKnowledgeController extends SimpleFormController {
	private KnowledgeDAO knowledgeDAO;

	/**
	 * @param knowledgeDAO the knowledgeDAO to set
	 */
	public void setKnowledgeDAO( KnowledgeDAO knowledgeDAO ) {
		this.knowledgeDAO = knowledgeDAO;
	}
}
