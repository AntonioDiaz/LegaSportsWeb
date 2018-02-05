package com.adiaz.forms.validators;

import com.adiaz.forms.CompetitionsForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CompetitionsFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return CompetitionsForm.class.isAssignableFrom(arg0);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CompetitionsForm competitionsForm = (CompetitionsForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idSport", "field_required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idCategory", "field_required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idTown", "field_required");
        if (competitionsForm.getTeams() == null || competitionsForm.getTeams().length < 3 || competitionsForm.getTeams().length > 24) {
            errors.rejectValue("teams", "teams_number_error");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "punctuationType", "field_required");
    }
}
