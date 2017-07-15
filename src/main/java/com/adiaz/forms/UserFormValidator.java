package com.adiaz.forms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adiaz.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.adiaz.utils.ConstantsLegaSport;

@Component
public class UserFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		if (StringUtils.isEmpty(user.getUsername())) {
			errors.rejectValue("username", "field_required");
		} else {
			Pattern pattern = Pattern.compile(ConstantsLegaSport.USERNAME_PATTERN);
			Matcher matcher = pattern.matcher(user.getUsername());
			if (!matcher.matches()){
				errors.rejectValue("username", "username_format_error");
			}
		}
		if (StringUtils.compare(user.getPassword01(), user.getPassword02())!=0) {
			errors.rejectValue("password", "password_distinct");
		}
		if (user.isUpdatePassword()) {
			if (StringUtils.isEmpty(user.getPassword01()) || StringUtils.isEmpty(user.getPassword02())) {
				errors.rejectValue("password", "password_distinct");
			} else {
				Pattern pattern = Pattern.compile(ConstantsLegaSport.PASSWORD_PATTERN);
				Matcher matcher = pattern.matcher(user.getPassword01());
				if (!matcher.matches()){
					errors.rejectValue("password", "password_too_easy");
				}
			}
		}
	}
}
