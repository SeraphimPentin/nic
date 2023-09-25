package nicstore.controllers;


import nicstore.Models.User;
import nicstore.dto.auth.*;
import nicstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/show-user-info")
    public List<UserInfoResponse> showUserInfo() {
        return authService.showUserInfo();
    }

    @GetMapping("/show-user")
    public List<User> showUser() {
        return authService.showUsers();
    }

    @PostMapping( value = "/register", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid RegisterRequest registerRequest, BindingResult bindingResult) {
        authService.register(registerRequest, bindingResult);
        return ResponseEntity.ok("Регистрация пройдена");
    }
    @PostMapping(value = "/login", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> performLogin(@RequestBody @Valid UsernameAndPasswordAuthenticationRequest loginRequest) throws AuthException {
        authService.login(loginRequest);
        return ResponseEntity.ok("Авторизация пройдена");
    }
}
