package nicstore.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsernameAndPasswordAuthenticationRequest {

    @Size(max = 100, message = "Максимум 100 символов")
    private String email;

    @NotBlank
    private String password;

}
