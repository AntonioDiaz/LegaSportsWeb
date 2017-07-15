package com.adiaz.forms;

import com.adiaz.entities.Town;
import com.adiaz.utils.ConstantsLegaSport;
import com.adiaz.utils.UtilsLegaSport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		UtilsLegaSport.validateNotEmptyAndFormat(errors, townForm.getPhone(), "phone", "phone_format_error", ConstantsLegaSport.PHONE_PATTERN);
		UtilsLegaSport.validateNotEmptyAndFormat(errors, townForm.getEmail(), "email", "email_format_error", ConstantsLegaSport.EMAIL_PATTERN);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "field_required");
	}
}
