package com.honeywell.smartbuilding.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class SensorTypeValidator implements ConstraintValidator<ValidSensorType, String> {

    private final List<String> allowedTypes = Arrays.asList("Temperature", "Humidity", "Smoke", "Motion");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return allowedTypes.contains(value);
    }
}
