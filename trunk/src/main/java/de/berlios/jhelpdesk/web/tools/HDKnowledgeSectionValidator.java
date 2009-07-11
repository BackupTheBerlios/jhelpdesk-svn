package de.berlios.jhelpdesk.web.tools;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.berlios.jhelpdesk.model.KnowledgeSection;

public class HDKnowledgeSectionValidator implements Validator {

	public boolean supports( Class clazz ) {
		return KnowledgeSection.class.equals( clazz );
	}

	public void validate( Object command, Errors errors ) {
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "sectionName", "errors.kbase.sectionName" );
	}
}
