package nicstore.dto.auth;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Size(min = 3, message = "Минимум из 3 символов")
    @Size(max = 100, message = "Не должно быть более 100 символов")
    private String firstname;

    @Size(min = 3, message = "Минимум из 3 символов")
    @Size(max = 100, message = "Не должно быть более 100 символов")
    private String lastname;

    @NotBlank(message = "Email не указан")
    @Size(max = 100, message = "Максимум 100 символов")
    @Email
    private String email;

    @NotBlank(message = "Поле для пароля не может быть пустым")
    @Size(max = 100, message = "Максимум 100 символов")
    private String password;
}
