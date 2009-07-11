package de.berlios.jhelpdesk.web.tools;

import java.beans.PropertyEditorSupport;

import de.berlios.jhelpdesk.model.Role;

public class HDRoleEditor extends PropertyEditorSupport {
	
	@Override
	public String getAsText() {
		if( getValue() != null )
			return String.valueOf( ( ( Role )getValue() ).toInt() );
		else
			return null;
	}
	
	@Override
	public void setAsText( String text ) {
		setValue( Role.fromInt( Integer.parseInt( text ) ) );
	}
}
