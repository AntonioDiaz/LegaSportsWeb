package com.adiaz.forms.validators;

import com.adiaz.entities.Competition;
import com.adiaz.forms.CompetitionsForm;
import com.adiaz.forms.CompetitionsInitForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.beans.ConstructorProperties;

/**
 * Created by toni on 09/09/2017.
 */
@Component
public class CompetitionInitFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return CompetitionsInitForm.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//CompetitionsInitForm competitionsInitForm = (CompetitionsInitForm) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idTown", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idCategory", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idSport", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teamsCount", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matchesTxt", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inputFormat", "field_required");
	}
}
