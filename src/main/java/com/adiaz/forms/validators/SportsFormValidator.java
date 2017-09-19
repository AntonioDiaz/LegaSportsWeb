package com.adiaz.forms.validators;

import com.adiaz.entities.Sport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by toni on 19/09/2017.
 */
@Component
public class SportsFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return aClass.isAssignableFrom(Sport.class);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tag", "field_required");
	}
}
