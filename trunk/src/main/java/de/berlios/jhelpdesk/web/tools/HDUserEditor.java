package de.berlios.jhelpdesk.web.tools;

import java.beans.PropertyEditorSupport;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.User;

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