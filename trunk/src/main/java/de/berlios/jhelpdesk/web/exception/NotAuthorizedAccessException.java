package de.berlios.jhelpdesk.web.exception;

public class NotAuthorizedAccessException extends RuntimeException {
	
	/** */
	private static final long serialVersionUID = -7396029049123879745L;

	public NotAuthorizedAccessException( String msg ) {
		super( msg );
	}
}
