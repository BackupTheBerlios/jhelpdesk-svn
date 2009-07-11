package de.berlios.jhelpdesk.web.tools;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.berlios.jhelpdesk.model.Bug;

public class HDBugValidator implements Validator {

	public boolean supports(@SuppressWarnings("unchecked") Class clazz) { // implementujemy Validator.supports(Class)
		return Bug.class.equals(clazz);
	}

	public void validate(Object command, Errors errors) {
		Bug hdBug = (Bug) command;
		if (hdBug.getSubject().trim().isEmpty())
			errors.reject("pofakany.subject");
		if (hdBug.getBugPriority() == null)
			errors.reject("pofakany.priorytet");
		if (hdBug.getBugCategory() == null)
			errors.reject("pofakana.kategoria");
	}
}
