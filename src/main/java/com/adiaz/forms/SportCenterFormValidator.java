package com.adiaz.forms;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.adiaz.entities.SportCenter;

@Component
public class SportCenterFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return SportCenter.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SportCenterForm centerForm = (SportCenterForm)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "field_required");
		if (centerForm.isAdmin()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idTown", "field_required");
		}
	}
}
