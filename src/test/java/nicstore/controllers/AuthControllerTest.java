package nicstore.controllers;

import nicstore.dto.auth.AuthenticationRequest;
import nicstore.dto.auth.AuthenticationResponse;
import nicstore.dto.auth.RegisterRequest;
import nicstore.models.User;
import nicstore.service.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthServiceImpl authService;
    @Test
    void testGetCurrentAuthorizedUser() {
        // Создаем заглушку пользователя
        User dummyUser = new User();
        dummyUser.setEmail("test@mail.com");

        // Мокируем метод getCurrentAuthorizedUser
        Mockito.when(authService.getCurrentAuthorizedUser()).thenReturn(dummyUser);

        String result = authController.getCurrentAuthorizedUser();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals("test@mail.com", result);
    }

    @Test
    void testShowUser() {
        List<User> dummyUsers = new ArrayList<>();
        dummyUsers.add(new User());
        dummyUsers.add(new User());
        Mockito.when(authService.showUsers()).thenReturn(dummyUsers);
        List<User> result = authController.showUser();

        assertEquals(dummyUsers, result);
    }
    @Test
    void testRegistration() {
        // Создаем заглушку запроса на регистрацию
        BindingResult bindingResult = mock(BindingResult.class);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password");

        // Создаем заглушку ответа
        AuthenticationResponse dummyResponse = AuthenticationResponse.builder()
                .token("dummyToken")
                .build();

        // Мокируем метод register
        Mockito.when(authService.register(registerRequest)).thenReturn(dummyResponse);

        // Вызываем метод контроллера
        ResponseEntity<?> responseEntity = authController.registration(registerRequest, bindingResult);
        // Проверяем, что результат соответствует ожиданиям
        assertEquals(dummyResponse, responseEntity.getBody());
    }

    @Test
    void testLogin() {
        // Создаем заглушку запроса на вход
        AuthenticationRequest loginRequest = new AuthenticationRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("password");

        // Создаем заглушку ответа
        AuthenticationResponse dummyResponse = AuthenticationResponse.builder()
                .token("dummyToken")
                .build();

        // Мокируем метод login
        Mockito.when(authService.login(loginRequest)).thenReturn(dummyResponse);

        // Вызываем метод контроллера
        ResponseEntity<AuthenticationResponse> responseEntity = authController.login(loginRequest);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(dummyResponse, responseEntity.getBody());
    }
}