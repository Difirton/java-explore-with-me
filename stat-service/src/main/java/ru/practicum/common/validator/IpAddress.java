package ru.practicum.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IpAddressValidator.class)
@Documented
public @interface IpAddress {

    String message() default "{Invalid ip address}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
