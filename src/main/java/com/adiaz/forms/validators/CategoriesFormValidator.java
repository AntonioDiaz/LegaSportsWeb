package com.adiaz.forms.validators;

import com.adiaz.forms.CategoriesForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by toni on 04/09/2017.
 */
@Component
public class CategoriesFormValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
		return CategoriesForm.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "order", "field_required");
	}
}
