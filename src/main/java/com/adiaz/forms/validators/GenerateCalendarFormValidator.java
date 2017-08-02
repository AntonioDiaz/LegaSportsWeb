package com.adiaz.forms.validators;

import com.adiaz.forms.GenerateCalendarForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by toni on 27/07/2017.
 */
@Component
public class GenerateCalendarFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return GenerateCalendarForm.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idCourt", "field_required");
	}
}
