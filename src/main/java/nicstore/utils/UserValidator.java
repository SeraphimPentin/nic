package nicstore.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nicstore.dto.auth.RegisterRequest;
import nicstore.exceptions.auth.RegisterValidationException;
import nicstore.exceptions.auth.UserAlreadyExistException;
import nicstore.models.User;
import nicstore.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @SneakyThrows
    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        RegisterRequest registerRequest = (RegisterRequest) target;
        if (registerRequest.getFirstname().length() < 3) {
             throw new RegisterValidationException("Имя должно быть более 3 символов");
        }
        if (registerRequest.getFirstname().length() > 100) {
            throw new RegisterValidationException("Имя не должно быть более 100 символов");
        }
        if (registerRequest.getLastname().length() < 3) {
             throw new RegisterValidationException("Фамилия должно быть более 3 символов");
        }
        if (registerRequest.getLastname().length() > 100) {
            throw new RegisterValidationException("Фамилия не должно быть более 100 символов");
        }
        if (userRepository.findUserByEmail(registerRequest.getEmail()).isPresent()){
            throw new UserAlreadyExistException("Пользвоатель с почтой " + registerRequest.getEmail() + " уже зарегистрирован");
        }
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(null, (BindingResult) errors);
        }
    }
}
