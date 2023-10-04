package nicstore.controllers;


import nicstore.Models.User;
import nicstore.dto.auth.*;
import nicstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/show-authorized-user")
    public String getCurrentAuthorizedUser() {
       return authService.getCurrentAuthorizedUser().getUsername();
    }

    @GetMapping("/show-user-info")
    public List<UserResponse> showUserInfo() {
        return authService.showUserInfo();
    }

    @GetMapping("/show-user")
    public List<User> showUser() {
        return authService.showUsers();
    }

    @PostMapping( value = "/register", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> performRegistration(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    @PostMapping(value = "/login", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> performLogin(@RequestBody @Valid AuthenticationRequest loginRequest) throws AuthException {
        authService.login(loginRequest);
        return ResponseEntity.ok("Авторизация пройдена");
    }
}
