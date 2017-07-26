package com.adiaz.forms.validators;

import com.adiaz.forms.TeamForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by toni on 25/07/2017.
 */
@Component
public class TeamFormValidator implements Validator {


	@Override
	public boolean supports(Class<?> aClass) {
		return TeamForm.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idCategory", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idTown", "field_required");
	}
}
