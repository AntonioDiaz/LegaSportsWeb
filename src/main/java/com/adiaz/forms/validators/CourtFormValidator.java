package com.adiaz.forms.validators;

import com.adiaz.forms.CourtForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CourtFormValidator implements Validator {

	
	
	@Override
	public boolean supports(Class<?> clazz) {		
		return CourtForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		CourtForm sportCourtForm = (CourtForm)object;
		ValidationUtils.rejectIfEmpty(errors, "name", "field_required");
		if (sportCourtForm.getCourtsSports()==null || sportCourtForm.getCourtsSports().length==0) {
			errors.rejectValue("courtsSports", "no_sports");
		}
		
	}
}
