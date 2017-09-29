package com.adiaz.forms.validators;

import com.adiaz.entities.Sanction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by toni on 28/09/2017.
 */
@Component
public class SanctionFormValidator implements Validator {


	@Override
	public boolean supports(Class<?> aClass) {
		return aClass.isAssignableFrom(Sanction.class);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idTeam", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idCompetition", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "points", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field_required");
	}
}
