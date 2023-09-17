package nicstore.utils;

import lombok.NonNull;

import nicstore.Models.User;
import nicstore.dto.auth.RegisterRequest;
import nicstore.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(@NonNull Object target, Errors errors) {
        RegisterRequest registerRequest = (RegisterRequest) target;
        if (userRepository.findUserByEmail(registerRequest.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Человек с такой почтой пользователя уже существует");
        }
    }
}