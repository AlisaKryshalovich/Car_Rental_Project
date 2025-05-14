package com.project.validator;

import com.project.dto.CreateUserDto;
import com.project.entity.Role;

public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    private CreateUserValidator() {}

    @Override
    public ValidationResult isValid(CreateUserDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (object.getFirstName() == null || object.getFirstName().isEmpty()) {
            validationResult.add(Error.of("invalid.firstname", "First name is invalid"));
        }
        if (object.getLastName() == null || object.getLastName().isEmpty()) {
            validationResult.add(Error.of("invalid.lastname", "Last name is invalid"));
        }
        if (object.getAge() == null || object.getAge().isEmpty() || (Integer.parseInt(object.getAge()) <= 18)) {
            validationResult.add(Error.of("invalid.age", "Age is invalid"));
        }
        if (object.getEmail() == null || object.getEmail().isEmpty()) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if (object.getPassword() == null || object.getPassword().isEmpty()) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        if (Role.find(object.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
