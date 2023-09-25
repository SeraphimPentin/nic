package nicstore.service;

import lombok.RequiredArgsConstructor;
import nicstore.Models.User;
import nicstore.dto.auth.JwtResponse;
import nicstore.dto.auth.UsernameAndPasswordAuthenticationRequest;
import nicstore.dto.auth.RegisterRequest;
import nicstore.dto.auth.UserInfoResponse;
import nicstore.exceprions.auth.UserNotFoundException;
import nicstore.security.JwtAuthentication;
import nicstore.security.jwt.JwtUtil;
import nicstore.utils.FormValidator;
import nicstore.utils.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserValidator userValidator;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final FormValidator formValidator;


    public JwtAuthentication getCurrentAuthentication() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
    public User getCurrentAuthorizedUser() {
        JwtAuthentication authentication = getCurrentAuthentication();
        if (authentication != null && authentication.getEmail() != null) {
            return userService.findUserByEmail(getCurrentAuthentication().getEmail());
        } else {
            throw new UserNotFoundException("Пользоатель не найден");
        }
    }

    public List<User> showUsers() {
        return userService.findAllUsers();
    }

    public List<UserInfoResponse> showUserInfo() {
        List<User> users = userService.findAllUsers();
        List<UserInfoResponse> userInfoResponses = new ArrayList<>();

        for (User user : users) {
            UserInfoResponse userInfoResponse = convertToUserInfoResponse(user);
            userInfoResponses.add(userInfoResponse);
        }
        return userInfoResponses;
    }

    public String getUsernameCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByEmail(auth.getName()).getUsername();
    }


    public String register(RegisterRequest registerRequest, BindingResult bindingResult) {
        userValidator.validate(registerRequest, bindingResult);
        formValidator.checkFormBindingResult(bindingResult);
        User user = convertRegisterRequestToUser(registerRequest);
        userService.saveUser(user);
        return jwtUtil.generateToken(registerRequest.getEmail());
    }

    public void login(UsernameAndPasswordAuthenticationRequest usernameAndPasswordAuthenticationRequest) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                usernameAndPasswordAuthenticationRequest.getEmail(),
                usernameAndPasswordAuthenticationRequest.getPassword()
        );
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Введены неверные данные");
        }
    }

    private User convertRegisterRequestToUser(RegisterRequest registerRequest) {
        return modelMapper.map(registerRequest, User.class);
    }

    public UserInfoResponse convertToUserInfoResponse(User user) {
        return modelMapper.map(user, UserInfoResponse.class);
    }
}