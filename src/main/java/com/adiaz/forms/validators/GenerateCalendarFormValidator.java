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
		GenerateCalendarForm form = (GenerateCalendarForm) o;
		if (form.getNumTeams()==null) {
			errors.rejectValue("numTeams", "field_required");
		} else {
			if (form.getNumTeams()<4 || form.getNumTeams()>24 || form.getNumTeams()%2==1) {
				errors.rejectValue("numTeams", "teams_number_error");
			} else {
				if (form.getNumTeams()!=form.getTeams().length) {
					errors.rejectValue("teams", "teams_selected_error");
				}
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idCourt", "field_required");
	}
}
