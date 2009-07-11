package com.jjhop.helpdesk.web.manager.knowledge.article;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.jjhop.helpdesk.dao.KnowledgeDAO;

public class EditKnowledgeController extends SimpleFormController {
	private KnowledgeDAO knowledgeDAO;

	/**
	 * @param knowledgeDAO the knowledgeDAO to set
	 */
	public void setKnowledgeDAO( KnowledgeDAO knowledgeDAO ) {
		this.knowledgeDAO = knowledgeDAO;
	}
}
