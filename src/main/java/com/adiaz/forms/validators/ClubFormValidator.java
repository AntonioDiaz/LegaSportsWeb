package com.adiaz.forms.validators;

import com.adiaz.entities.Club;
import com.adiaz.forms.ClubForm;
import com.adiaz.utils.ConstantsLegaSport;
import com.adiaz.utils.UtilsLegaSport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by toni on 25/07/2017.
 */
@Component
public class ClubFormValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
		return ClubForm.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ClubForm clubForm = (ClubForm) o;
		ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "name", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "contactPerson", "field_required");
		UtilsLegaSport.validateNotEmptyAndFormat(
				errors, clubForm.getContactEmail(), "contactEmail", "email_format_error", ConstantsLegaSport.EMAIL_PATTERN);
		ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "contactAddress", "field_required");
		UtilsLegaSport.validateNotEmptyAndFormat(
				errors, clubForm.getContactPhone(), "contactPhone", "phone_format_error", ConstantsLegaSport.PHONE_PATTERN);
		ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "idTown", "field_required");
	}
}
