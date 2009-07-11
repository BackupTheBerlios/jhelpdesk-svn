package com.jjhop.helpdesk.web.tools;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jjhop.helpdesk.model.Bug;

public class HDBugValidator implements Validator {

	public boolean supports( Class clazz ) {
		return Bug.class.equals( clazz );
	}

	public void validate( Object command, Errors errors ) {
		Bug hdBug = ( Bug ) command;
		if( hdBug.getSubject().trim().isEmpty() )
			errors.reject( "pofakany.subject" );
		if( hdBug.getBugPriority() == null )
			errors.reject( "pofakany.priorytet" );
		if(  hdBug.getBugCategory() == null )
			errors.reject( "pofakana.kategoria" );
	}
}
