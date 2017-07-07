package com.adiaz.forms;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SportCourtFormValidator implements Validator {

	
	
	@Override
	public boolean supports(Class<?> clazz) {		
		return SportsCourtForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		SportsCourtForm sportCourtForm = (SportsCourtForm)object;
		ValidationUtils.rejectIfEmpty(errors, "name", "field_required");
		if (sportCourtForm.getCourtsSports()==null || sportCourtForm.getCourtsSports().length==0) {
			errors.rejectValue("courtsSports", "no_sports");
		}
		
	}
}
