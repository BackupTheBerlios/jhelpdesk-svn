package de.berlios.jhelpdesk.model;

import net.sf.hibernate.PersistentEnum;

@SuppressWarnings("deprecation")
public class EventType implements PersistentEnum {

	private final int code;
	private final String typeName;
	
	public EventType( int code, String typeName ) {
		this.code = code;
		this.typeName = typeName;
	}
	
	//TODO: wszystkie nazwy trzeba zaciągnąć z wlasciwych propertisow
	public static final EventType CREATEEVENT         = new EventType( 1, "Zgłoszenie problemu." );
	public static final EventType ASSIGNEVENT         = new EventType( 2, "Przypisanie problemu." );
	public static final EventType REASSIGNEVENT       = new EventType( 3, "Zmiana przypisania problemu." );
	public static final EventType CLOSEVENT           = new EventType( 4, "Zamknięcie/rozwiązanie problemu." );
	public static final EventType REJECTEVENT         = new EventType( 5, "Odrzucenie problemu." );
	public static final EventType CATEGORYCHANGEEVENT = new EventType( 6, "Zmiana kategorii." );
	public static final EventType PRIORITYCHANGEEVENT = new EventType( 7, "Zmiana ważności." );
	public static final EventType STATUSCHANGEEVENT   = new EventType( 8, "Zmiana statusu." );
	public static final EventType COMMENTADDEVENT     = new EventType( 9, "Dodanie komentarza." );

	public int toInt( ) {
		return code;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public static EventType fromInt( int code ) {
		switch ( code ) {
			case 1: return CREATEEVENT;
			case 2: return ASSIGNEVENT;
			case 3: return REASSIGNEVENT;
			case 4: return CLOSEVENT;
			case 5: return REJECTEVENT;
			case 6: return CATEGORYCHANGEEVENT;
			case 7: return PRIORITYCHANGEEVENT;
			case 8: return STATUSCHANGEEVENT;
			case 9: return COMMENTADDEVENT;
			default: throw new RuntimeException( "Nieznany typ zdarzenia." );
		}
	}
	
	public String toString() {
		return typeName;
	}
}
