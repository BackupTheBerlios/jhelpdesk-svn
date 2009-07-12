package de.berlios.jhelpdesk.web.tools;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.berlios.jhelpdesk.model.BugCategory;

public class BugCategoryValidator implements Validator {

	public boolean supports(@SuppressWarnings("unchecked") Class clazz) { // implementujemy Validator.supports(Class)
		return BugCategory.class.equals(clazz);
	}

	public void validate(Object category, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "errors.category.categoryName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryDesc", "errors.category.categoryDesc");
	}
}
