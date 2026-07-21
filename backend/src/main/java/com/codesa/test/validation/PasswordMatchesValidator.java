package com.codesa.test.validation;

import com.codesa.test.dto.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRequest> {

    @Override
    public boolean isValid(UserRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        boolean isValid = Objects.equals(request.getPassword(), request.getRepassword());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La contraseña y su confirmación deben coincidir")
                    .addPropertyNode("repassword")
                    .addConstraintViolation();
        }

        return isValid;
    }

}
