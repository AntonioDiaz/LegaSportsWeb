package com.adiaz.forms.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adiaz.entities.User;
import com.adiaz.utils.LocalSportsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.adiaz.utils.LocalSportsConstants;

@Component
public class UserFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		if (!user.isAdmin() && (user.getTownEntity()==null || user.getTownEntity().getId()==null)) {
			errors.rejectValue("town", "field_required");
		}
		LocalSportsUtils.validateNotEmptyAndFormat(
				errors, user.getUsername(), "username", "username_format_error", LocalSportsConstants.USERNAME_PATTERN);
		if (StringUtils.compare(user.getPassword01(), user.getPassword02())!=0) {
			errors.rejectValue("password", "password_distinct");
		}
		if (user.isUpdatePassword()) {
			if (StringUtils.isEmpty(user.getPassword01()) || StringUtils.isEmpty(user.getPassword02())) {
				errors.rejectValue("password", "password_distinct");
			} else {
				Pattern pattern = Pattern.compile(LocalSportsConstants.PASSWORD_PATTERN);
				Matcher matcher = pattern.matcher(user.getPassword01());
				if (!matcher.matches()){
					errors.rejectValue("password", "password_too_easy");
				}
			}
		}
	}
}
