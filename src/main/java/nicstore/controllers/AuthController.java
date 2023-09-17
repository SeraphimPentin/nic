package nicstore.controllers;


import nicstore.dto.auth.LoginRequest;
import nicstore.dto.auth.RegisterRequest;
import nicstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


//    @GetMapping("/show-user")
//    @ResponseBody
//    public String showUserInfo() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return userDetails.getUsername();
//    }

    @GetMapping("/show-user")
    public ResponseEntity<String> showUserInfo() {
        return ResponseEntity.ok(authService.getCurrentAuthentication().getPrincipal().toString());
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> performRegistration(@RequestBody @Valid RegisterRequest registerRequest, BindingResult bindingResult) {
        return new ResponseEntity<>(Collections.singletonMap("jwt-token", authService.register(registerRequest, bindingResult)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) {
        return new ResponseEntity<>(Collections.singletonMap("jwt-token", authService.login(loginRequest, bindingResult)), HttpStatus.OK);
    }
}
