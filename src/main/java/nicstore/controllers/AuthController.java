package nicstore.controllers;

import nicstore.models.User;
import nicstore.dto.auth.*;
import nicstore.service.AuthServiceImpl;
import nicstore.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;
    private final UserValidator userValidator;

    @Autowired
    public AuthController(AuthServiceImpl authService, UserValidator userValidator) {
        this.authService = authService;
        this.userValidator = userValidator;
    }

    @GetMapping("/show-authorized-user")
    public String getCurrentAuthorizedUser() {
       return authService.getCurrentAuthorizedUser().getUsername();
    }

    @GetMapping("/show-user-info")
    public List<UserResponse> showUserInfo() {
        return authService.showUserInfo();
    }

    @GetMapping("/show-users")
    public List<User> showUser() {
        return authService.showUsers();
    }

    @PostMapping( value = "/register")
    public ResponseEntity<AuthenticationResponse> registration(@RequestBody @Valid RegisterRequest registerRequest, BindingResult bindingResult) {
        userValidator.validate(registerRequest, bindingResult);
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
