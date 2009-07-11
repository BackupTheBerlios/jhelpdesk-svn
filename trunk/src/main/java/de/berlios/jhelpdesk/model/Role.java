package de.berlios.jhelpdesk.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.PersistentEnum;

@SuppressWarnings("deprecation")
public class Role implements PersistentEnum {
	private final int code;
	private final String roleName;
	private final static List<Role> roles = new ArrayList<Role>(3);
	
	// TODO: nalezy lokalizowac nazwy rol i umiescic je w plikach role.properties
	public static final Role CLIENT    = new Role( 1,   "Użytkownik" ); // dodaje i ogląda
	public static final Role BUGKILLER = new Role( 10,  "Pracownik helpdesku" ); // moze edytowac
	public static final Role MANAGER   = new Role( 100, "Helpdesk manager" ); // moze wszystko

	public static List<Role> getRoles() {
		return roles;
	}
	
	public static Role fromInt( int code ) {
		switch ( code ) {
			case 1: return CLIENT;
			case 10: return BUGKILLER;
			case 100: return MANAGER;
			default: throw new RuntimeException( "Nieznana rola." );
		}
	}
	
	static {
		roles.add( CLIENT );
		roles.add( BUGKILLER );
		roles.add( MANAGER );
	}
	
	public Role( int code, String roleName ) {
		this.code = code;
		this.roleName = roleName;
	}

	public int toInt( ) {
		return code;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public int getRoleCode() {
		return code;
	}
}
