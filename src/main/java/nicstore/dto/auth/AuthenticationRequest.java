package nicstore.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Email не указан")
    @Size(max = 100, message = "Максимум 100 символов")
    @Email
    private String email;

    @NotBlank(message = "Поле для пароля не может быть пустым")
    @Size(max = 100, message = "Максимум 100 символов")
    private String password;
}
