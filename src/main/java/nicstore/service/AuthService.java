package nicstore.service;

import nicstore.Models.User;
import nicstore.dto.auth.LoginRequest;
import nicstore.dto.auth.RegisterRequest;
import nicstore.dto.auth.UserInfoResponse;
import nicstore.security.JwtAuthentication;
import nicstore.security.jwt.JwtUtil;
import nicstore.security.services.UserDetailsServiceImpl;
import nicstore.utils.FormValidator;
import nicstore.utils.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserValidator userValidator;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final FormValidator formValidator;


    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserService userService, UserDetailsServiceImpl userDetailsService, UserValidator userValidator, JwtUtil jwtUtil, ModelMapper modelMapper, FormValidator formValidator) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.userValidator = userValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.formValidator = formValidator;
    }

    public JwtAuthentication getCurrentAuthentication() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
    public User getCurrentAuthorizedUser() {
        return userService.findUserByEmail(getCurrentAuthentication().getEmail());
    }

    public void register(RegisterRequest registerRequest) {
        User user = convertRegisterRequestToUser(registerRequest);
        userService.saveUser(user);
    }


    public String getUsernameCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDetailsService.loadUserByUsername(auth.getName()).getUsername();
    }


    public String register(RegisterRequest registerRequest, BindingResult bindingResult) {
        userValidator.validate(registerRequest, bindingResult);
        formValidator.checkFormBindingResult(bindingResult);
        User user = convertRegisterRequestToUser(registerRequest);
        userService.saveUser(user);
        return jwtUtil.generateToken(registerRequest.getEmail());
    }

    public String login(LoginRequest loginRequest, BindingResult bindingResult) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        formValidator.checkFormBindingResult(bindingResult);
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Введены неверные данные");
        }
        return jwtUtil.generateToken(loginRequest.getEmail());
    }

    private User convertRegisterRequestToUser(RegisterRequest registerRequest) {
        return modelMapper.map(registerRequest, User.class);
    }

    private UserInfoResponse convertToUserInfoResponse(User user) {
        return modelMapper.map(user, UserInfoResponse.class);
    }
}