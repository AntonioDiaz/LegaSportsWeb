package com.adiaz.forms;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CompetitionsFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return CompetitionsForm.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sportId", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryId", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");
	}
}
