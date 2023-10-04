package nicstore.dto.auth;

import lombok.*;
import nicstore.security.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String email;
    private String firstname;
    private String lastname;
    private Role role;

}