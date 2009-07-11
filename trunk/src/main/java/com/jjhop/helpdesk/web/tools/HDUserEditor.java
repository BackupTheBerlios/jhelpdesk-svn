package com.jjhop.helpdesk.web.tools;

import java.beans.PropertyEditorSupport;

import com.jjhop.helpdesk.dao.UserDAO;
import com.jjhop.helpdesk.model.User;

public class HDUserEditor extends PropertyEditorSupport {
	private UserDAO userDAO;
	
	@Override
	public String getAsText() {
		if( getValue() != null )
			return ( ( User )getValue() ).getLogin();
		else
			return null;
	}
	
	@Override
	public void setAsText( String text ) {
		User user = userDAO.getByLogin( text );
		setValue( user );
	}
	
	public void setUserDAO( UserDAO userDAO ) {
		this.userDAO = userDAO;
	}
}