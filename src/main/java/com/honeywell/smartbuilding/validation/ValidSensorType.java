package com.honeywell.smartbuilding.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SensorTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSensorType {
    String message() default "Invalid sensor type. Allowed values are: Temperature, Humidity, Smoke, Motion";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
