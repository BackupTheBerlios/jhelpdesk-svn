package com.jjhop.helpdesk.dao;

import java.util.Date;
import java.util.List;

import com.jjhop.helpdesk.model.Role;
import com.jjhop.helpdesk.model.User;

/**
 * Podstawowe DAO dla obiektów User.
 * @author jjhop
*/
public interface UserDAO {
	List<User> getAllUserWithLastNameStartsWithLetter( String letter );
	List<User> getSavioursWithLastNameStartsWithLetter( String letter );
	List<User> getAllUser();
	List<User> getByRole( Role role );
	List<User> getSaviours();
	User getById( Long id );
	User getById( int id );
	User getByLogin( String login );
	boolean checkLoginAndPassw(String login, String passw);
	void loginUser(String login, Date date);
	void save( User user );
}
