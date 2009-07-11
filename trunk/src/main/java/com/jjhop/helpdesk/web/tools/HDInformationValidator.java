package com.jjhop.helpdesk.web.tools;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jjhop.helpdesk.model.Information;

public class HDInformationValidator implements Validator {

	public boolean supports( Class clazz ) {
		return Information.class.equals( clazz );
	}

	public void validate( Object information, Errors errors ) {
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "title", "errors.information.title" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "lead",  "errors.information.lead" );
	}
}
