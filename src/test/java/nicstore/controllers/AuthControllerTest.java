package nicstore.controllers;

import nicstore.dto.auth.RegisterRequest;

import nicstore.service.AuthServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCurrentAuthorizedUser() {
    }

    @Test
    void showUserInfo() {
    }

    @Test
    void showUser() {
    }

    //public ResponseEntity<AuthenticationResponse> registration(@RequestBody @Valid RegisterRequest registerRequest) {
    //   return ResponseEntity.ok(authService.register(registerRequest));
    @Test
    void testRegistration() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("who")
                .password("password")
                .firstname("user")
                .lastname("user")
                .build();

//        doThrow(new UserAlreadyExistException("Пользвоатель с почтой " + registerRequest.getEmail() + " уже зарегистрирован")).when(authService).register(registerRequest);
        Mockito.doNothing().when(authService).register(registerRequest);

        ResponseEntity<?> responseEntity = authController.registration(registerRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(anyString(), responseEntity.getBody());
        verify(authService).register(registerRequest);
    }

    @Test
    void login() {
    }
}