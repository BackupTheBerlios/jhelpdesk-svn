package de.berlios.jhelpdesk.web.manager.users;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.HDRoleEditor;

public class EditUserController extends SimpleFormController {
	private static Log log = LogFactory.getLog( EditUserController.class );
	private UserDAO userDAO;
	
	@Override
	protected void initBinder( HttpServletRequest request, ServletRequestDataBinder binder ) throws Exception {
		log.info( "initBinder()->start" );
		binder.registerCustomEditor( Role.class, new HDRoleEditor() );
		binder.registerCustomEditor( Long.class, null, new CustomNumberEditor( Long.class, NumberFormat.getNumberInstance(), true ) );
		binder.registerCustomEditor( Boolean.class, null, new CustomBooleanEditor( true ) );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData( HttpServletRequest request ) throws Exception {
		Map data = new HashMap();
		data.put( "roles", Role.getRoles() );
		return data;
	}
	
	@Override
	protected ModelAndView onSubmit( HttpServletRequest request, HttpServletResponse response, Object command, BindException errors ) throws Exception {
		User user = (User)command;
		userDAO.save( user );
		return super.onSubmit( request, response, command, errors );
	}
	
	@Override
	protected Object formBackingObject( HttpServletRequest request ) throws Exception {
		User user = new User();
		try {
			user = userDAO.getById( Long.parseLong( request.getParameter( "userId" ) ) );
		} catch( Exception ex ) {
			log.error( "user not found" );
		}
		return user;
	}
	
	/** @param userDAO the userDAO to set */
	public void setUserDAO( UserDAO userDAO ) {
		this.userDAO = userDAO;
	}
}
