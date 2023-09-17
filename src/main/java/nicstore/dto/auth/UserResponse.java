package nicstore.dto.auth;

import lombok.Data;
import nicstore.security.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class UserResponse {

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

    private Role role;

}