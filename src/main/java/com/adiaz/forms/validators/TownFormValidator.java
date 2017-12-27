package com.adiaz.forms.validators;

import com.adiaz.forms.TownForm;
import com.adiaz.utils.LocalSportsConstants;
import com.adiaz.utils.LocalSportsUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by toni on 14/07/2017.
 */

@Component
public class TownFormValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
		return TownForm.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		TownForm townForm = (TownForm) o;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPerson", "field_required");
		LocalSportsUtils.validateNotEmptyAndFormat(errors, townForm.getPhone(), "phone", "phone_format_error", LocalSportsConstants.PHONE_PATTERN);
		LocalSportsUtils.validateNotEmptyAndFormat(errors, townForm.getEmail(), "email", "email_format_error", LocalSportsConstants.EMAIL_PATTERN);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "field_required");
		if (!townForm.getColorPrimary().isEmpty()) {
			Pattern pattern = Pattern.compile(LocalSportsConstants.HEXADECIMAL_PATTERN);
			Matcher matcher = pattern.matcher(townForm.getColorPrimary());
			if (!matcher.matches()){
				errors.rejectValue("colorPrimary", "color_format_error");
			}
		}
		if (!townForm.getColorAccent().isEmpty()) {
			Pattern pattern = Pattern.compile(LocalSportsConstants.HEXADECIMAL_PATTERN);
			Matcher matcher = pattern.matcher(townForm.getColorAccent());
			if (!matcher.matches()){
				errors.rejectValue("colorAccent", "color_format_error");
			}
		}
	}
}
