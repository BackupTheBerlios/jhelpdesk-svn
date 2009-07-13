/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright: (C) 2006 jHelpdesk Developers Team
 */
package de.berlios.jhelpdesk.web.manager.users;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.RoleEditor;

public class EditUserController extends SimpleFormController {
	
	private static Log log = LogFactory.getLog(EditUserController.class);

    @Autowired
	private UserDAO userDAO;

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
			throws Exception {
		log.info("initBinder()->start");
		binder.registerCustomEditor(Role.class, new RoleEditor());
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, 
				NumberFormat.getNumberInstance(), true));
		binder.registerCustomEditor(Boolean.class, null, new CustomBooleanEditor(true));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("roles", Role.getRoles());
		return data;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, 
			HttpServletResponse response, Object command, BindException errors) 
			throws Exception {
		User user = (User) command;
		userDAO.saveOrUpdate(user);
		return super.onSubmit(request, response, command, errors);
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		User user = new User();
		try {
			user = userDAO.getById(Long.parseLong(request.getParameter("userId")));
		} catch (Exception ex) {
			log.error("user not found");
		}
		return user;
	}

}
