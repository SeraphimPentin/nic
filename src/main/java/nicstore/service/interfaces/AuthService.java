package nicstore.service.interfaces;

import nicstore.models.User;
import nicstore.dto.auth.AuthenticationRequest;
import nicstore.dto.auth.AuthenticationResponse;
import nicstore.dto.auth.RegisterRequest;
import nicstore.dto.auth.UserResponse;
import nicstore.exceptions.auth.UserNotFoundException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AuthService {
    Authentication getCurrentAuthentication();

    User getCurrentAuthorizedUser() throws UserNotFoundException;

    List<User> showUsers();

    List<UserResponse> showUserInfo();

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(AuthenticationRequest request);
}
