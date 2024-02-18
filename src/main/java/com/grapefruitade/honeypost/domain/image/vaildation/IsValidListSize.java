package com.grapefruitade.honeypost.domain.image.vaildation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListSizeValidator.class)
public @interface IsValidListSize {
    String message() default "";

    int max() default 3;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
