package de.berlios.jhelpdesk.web.tools;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.berlios.jhelpdesk.model.User;

public class HDUserValidator implements Validator {

	public boolean supports(@SuppressWarnings("unchecked") Class clazz) { // implementujemy Validator.supports(Class)
		return User.class.equals(clazz);
	}

	public void validate(Object user, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "errors.hduser.firstName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "errors.hduser.lastName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "errors.hduser.login");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "errors.hduser.password");
		// TODO: reszta walidacji
	}
}
