package nicstore.dto.auth;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


//@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Введите имя")
    @Size(max = 100, message = "Максимум 100 символов")
    private String firstName;

    @NotBlank(message = "Введите фамилию")
    @Size(max = 100, message = "Максимум 100 символов")
    private String lastName;

    @NotBlank(message = "Email не указан")
    @Size(max = 100, message = "Максимум 100 символов")
    @Email
    private String email;

    @NotBlank(message = "Поле для пароля не может быть пустым") 
    @Size(max = 100, message = "Максимум 100 символов")
    private String password;
}
