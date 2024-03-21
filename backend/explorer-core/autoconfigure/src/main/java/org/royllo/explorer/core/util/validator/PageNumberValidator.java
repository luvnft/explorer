package org.royllo.explorer.core.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for a page number for services (starts at 1).
 */
public class PageNumberValidator implements ConstraintValidator<PageNumber, Integer> {

    /** First page number. */
    private static final int FIRST_PAGE_NUMBER = 1;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value >= FIRST_PAGE_NUMBER;
    }

}
