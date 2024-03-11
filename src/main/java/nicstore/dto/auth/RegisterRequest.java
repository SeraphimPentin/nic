package nicstore.dto.auth;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(min = 2, message = "Имя минимум из 2 символов")
    @Size(max = 100, message = "Не должно быть более 100 символов")
    private String firstname;

    @NotBlank
    @Size(min = 2, message = "Фамилия минимум из 3 символов")
    @Size(max = 100, message = "Фамилия не должна быть более 100 символов")
    private String lastname;

    @NotBlank(message = "Email не указан")
    @Size(max = 100, message = "Email не более 100 символов")
    @Email(message = "Поле email должен иметь формат адреса электронной почты")
    private String email;

    @NotBlank(message = "Поле для пароля не может быть пустым")
    @Size(min = 8, message = "Пароль должен быть не меньше 8 символов")
    @Size(max = 100, message = "Пароль максимум 100 символов")
    private String password;
}
