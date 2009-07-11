package com.jjhop.helpdesk.userdata.impl.ldap;

//import net.sf.ldaptemplate.LdapTemplate;

import com.jjhop.helpdesk.userdata.ifc.IUDataHelpder;

public class LdapUDataHelper implements IUDataHelpder {

    public boolean loginUser(String name, String passw) {
        return true;
    }
//	@SuppressWarnings("unused")
//	private LdapTemplate ldapTemplate;
//
//	public boolean loginUser( String name, String passw ) {
//		return false;
//	}
//
//	public void setLdapTemplate( LdapTemplate ldapTemplate ) {
//		this.ldapTemplate = ldapTemplate;
//	}
}
