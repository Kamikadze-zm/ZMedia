package ru.kamikadze_zm.zmedia.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class MatchValidator implements ConstraintValidator<Match, Object> {

    private String field;
    private String confirmationField;

    @Override
    public void initialize(Match constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.confirmationField = constraintAnnotation.confirmationField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        Object confirmationFieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(confirmationField);
        if (fieldValue == null && confirmationFieldValue
                == null) {
            return true;
        }

        boolean valid = fieldValue != null && fieldValue.equals(confirmationFieldValue);

        if (!valid) {
            String message = context.getDefaultConstraintMessageTemplate();
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(field)
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmationField)
                    .addConstraintViolation();
        }

        return valid;
    }
}
