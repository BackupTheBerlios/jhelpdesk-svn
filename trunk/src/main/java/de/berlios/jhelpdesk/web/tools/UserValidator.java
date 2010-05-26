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
package de.berlios.jhelpdesk.web.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.berlios.jhelpdesk.model.User;

@Component("userValidator")
public class UserValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    public void validate(Object user, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "errors.hduser.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "errors.hduser.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "errors.hduser.login");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "errors.hduser.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "errors.hduser.email");

        User u = (User) user;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = u.getEmail();
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (!matcher.matches()) {
            errors.rejectValue("email", "errors.hduser.email.invalid");
        }
        if (u.getUserRole() == null) {
            errors.rejectValue("userRole", "errors.hduser.userRole.notset");
        }
    }
}

