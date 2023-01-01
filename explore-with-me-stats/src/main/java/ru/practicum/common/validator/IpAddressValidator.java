package ru.practicum.common.validator;

import org.apache.commons.validator.routines.InetAddressValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        InetAddressValidator inetAddressValidator = new InetAddressValidator();
        return inetAddressValidator.isValid(value);
    }
}
