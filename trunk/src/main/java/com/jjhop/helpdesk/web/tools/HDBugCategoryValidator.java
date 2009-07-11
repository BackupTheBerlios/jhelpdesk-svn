package com.jjhop.helpdesk.web.tools;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jjhop.helpdesk.model.BugCategory;

public class HDBugCategoryValidator implements Validator {

	public boolean supports( Class clazz ) {
		return BugCategory.class.equals( clazz );
	}

	public void validate( Object category, Errors errors ) {
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "categoryName", "errors.category.categoryName" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "categoryDesc", "errors.category.categoryDesc" );
	}
}
