package com.adiaz.forms.validators;

import com.adiaz.forms.TownForm;
import com.adiaz.utils.MuniSportsConstants;
import com.adiaz.utils.MuniSportsUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
		MuniSportsUtils.validateNotEmptyAndFormat(errors, townForm.getPhone(), "phone", "phone_format_error", MuniSportsConstants.PHONE_PATTERN);
		MuniSportsUtils.validateNotEmptyAndFormat(errors, townForm.getEmail(), "email", "email_format_error", MuniSportsConstants.EMAIL_PATTERN);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "field_required");
	}
}
