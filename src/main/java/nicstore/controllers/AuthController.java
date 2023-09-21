package nicstore.controllers;


import nicstore.Models.User;
import nicstore.dto.auth.LoginRequest;
import nicstore.dto.auth.RegisterRequest;
import nicstore.dto.auth.UserInfoResponse;
import nicstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) {
        authService.login(loginRequest, bindingResult);
        return ResponseEntity.ok("Авторизация пройдена");
    }
}
