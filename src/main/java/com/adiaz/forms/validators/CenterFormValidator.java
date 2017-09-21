package com.adiaz.forms.validators;

import com.adiaz.entities.Center;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CenterFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Center.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idTown", "field_required");
	}
}
