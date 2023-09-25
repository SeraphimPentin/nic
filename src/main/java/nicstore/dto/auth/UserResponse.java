package nicstore.dto.auth;

import lombok.Data;
import nicstore.security.Role;

@Data
public class UserResponse {

    private String firstname;
    private String lastname;
    private String email;
    private Role role;

}