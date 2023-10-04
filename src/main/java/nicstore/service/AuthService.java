package nicstore.service;

import lombok.RequiredArgsConstructor;
import nicstore.Models.User;
import nicstore.dto.auth.*;
import nicstore.exceptions.auth.UserNotFoundException;
import nicstore.security.jwt.JwtService;
import nicstore.utils.FormValidator;
import nicstore.utils.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserService userService;
    private final UserValidator userValidator;
    private final ModelMapper modelMapper;
    private final FormValidator formValidator;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User getCurrentAuthorizedUser() {
        Authentication authentication = getCurrentAuthentication();
        if (authentication != null && authentication.getPrincipal() != null)  {
            return userService.findUserByEmail(getCurrentAuthentication().getName());
        } else {
            throw new UserNotFoundException("Пользоатель не найден");
        }
    }

    public List<User> showUsers() {
        return userService.findAllUsers();
    }

    public List<UserResponse> showUserInfo() {
        List<User> users = userService.findAllUsers();
        List<UserResponse> userRespons = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = convertToUserInfoResponse(user);
            userRespons.add(userResponse);
        }
        return userRespons;
    }

    public String getUsernameCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByEmail(auth.getName()).getUsername();
    }


//    public String register(RegisterRequest registerRequest, BindingResult bindingResult) {
//        userValidator.validate(registerRequest, bindingResult);
//        formValidator.checkFormBindingResult(bindingResult);
//        User user = convertRegisterRequestToUser(registerRequest);
//        userService.saveUser(user);
//        return jwtUtil.generateToken(registerRequest.getEmail());
//    }

    public String register(@RequestBody RegisterRequest request) {

        userService.emailAlreadyExist(request);
        User user = convertRegisterRequestToUser(request);
        userService.saveUser(user);
        String jwtToken = jwtService.generateToken(user);
        return jwtToken;
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
    }

    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userService.findUserByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private User convertRegisterRequestToUser(RegisterRequest registerRequest) {
        return modelMapper.map(registerRequest, User.class);
    }

    public UserResponse convertToUserInfoResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}