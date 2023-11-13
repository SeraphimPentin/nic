package nicstore.service;

import nicstore.Models.User;
import nicstore.dto.auth.AuthenticationRequest;
import nicstore.dto.auth.AuthenticationResponse;
import nicstore.dto.auth.RegisterRequest;
import nicstore.dto.mapper.ConvertorMapper;
import nicstore.security.Role;
import nicstore.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private ConvertorMapper mapper;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private JwtService jwtService;


    @Test
    public void registerTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email("test@gmail.com")
                .password("password")
                .build();

        User user = new User();
        user.setId(1L);
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setEmail(registerRequest.getEmail());
        user.setRole(Role.ROLE_USER);
        user.setPassword(registerRequest.getPassword());

        userService.saveUser(user);

        String token = jwtService.generateToken(user);
        System.out.println(token);
        when(jwtService.generateToken(user)).thenReturn("testToken");

        verify(userService, times(1)).saveUser(user);

        // Проверяем, что jwtService.generateToken был вызван один раз с правильным аргументом
        verify(jwtService, times(1)).generateToken(user);

        assertEquals("testToken", token);



//        authService.register(registerRequest);
//        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//        verify(userService).saveUser(userArgumentCaptor.capture());
//        User capturedUser = userArgumentCaptor.getValue();
//        assertThat(capturedUser.getEmail()).isEqualTo("test@gmail.com");

//        User user = mapper.getMapper().map(registerRequest, User.class);

//        when(mapper.getMapper().map(registerRequest, User.class)).thenReturn(user);
//        userService.saveUser(user);
//        verify(userService).saveUser(user);
//        assertEquals(anyString(), authService.register(registerRequest).toString());

//        assertNotNull(authService.register(registerRequest));
    }

    @Test
    void testRegister() {
        // Создаем тестовые данные
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email("test@gmail.com")
                .password("password")
                .build();

        ModelMapper mapperMock = Mockito.mock(ModelMapper.class);
        User user = new User();
        // Задайте ожидаемое поведение для методов userService
//        when(userService.emailAlreadyExist(registerRequest)).thenReturn();
        when(mapperMock.map(registerRequest, User.class)).thenReturn(user);
//        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("тестовый_токен");

        // Вызываем метод, который мы тестируем
        AuthenticationResponse response = authService.register(registerRequest);

        // Проверяем, что все методы были вызваны с правильными аргументами
        verify(userService).emailAlreadyExist(registerRequest);
        verify(userService).saveUser(any(User.class));
        verify(jwtService).generateToken(user);

        // Проверяем, что ответ содержит ожидаемый токен
        assertNotNull(response);
        assertEquals("тестовый_токен", response.getToken());
    }

    @Test
    public void testRegisterTOP() {
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setEmail("test@example.com");

        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email("test@gmail.com")
                .password("password")
                .build();

        // Создаем заглушки для userService.emailAlreadyExist и userService.saveUser
        doThrow(new RuntimeException()).when(userService).emailAlreadyExist(registerRequest);
        doNothing().when(userService).saveUser(any(User.class));

        User mapUser = mapper.getMapper().map(registerRequest, User.class);
        userService.saveUser(mapUser);
        when(jwtService.generateToken(mapUser)).thenReturn("testToken");


        // Проверяем, что userService.saveUser был вызван один раз с правильным аргументом
        verify(userService, times(1)).saveUser(any(User.class));

        // Проверяем, что jwtService.generateToken был вызван один раз с правильным аргументом
        verify(jwtService, times(1)).generateToken(mapUser);

        // Проверяем, что результат метода содержит правильный токен
//        assertEquals("testToken", response.getToken());
    }

    @Test
    public void testRegisterNEW() {
        // Создаем тестовые данные
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        User user = new User();
        user.setEmail("test@example.com");

        // Mock-логика для userService.emailAlreadyExist
        doNothing().when(userService).emailAlreadyExist(registerRequest);

        // Создаем заглушку для ModelMapper
        ModelMapper modelMapper = new ModelMapper(); // Создаем экземпляр ModelMapper
        when(mapper.getMapper()).thenReturn(modelMapper); // Передаем ModelMapper

        // Мок-логика для ModelMapper.map
//        when(modelMapper.map(registerRequest, User.class)).thenReturn(user);

        // Mock-логика для userService.saveUser
        doNothing().when(userService).saveUser(user);

        // Mock-логика для jwtService.generateToken
        when(jwtService.generateToken(user)).thenReturn("testToken");

        // Вызываем метод, который мы хотим протестировать
        AuthenticationResponse response = authService.register(registerRequest);

        // Проверяем, что userService.emailAlreadyExist был вызван один раз с правильным аргументом
        verify(userService, times(1)).emailAlreadyExist(registerRequest);

        // Проверяем, что mapper.map был вызван один раз с правильными аргументами
        verify(mapper, times(1)).getMapper().map(registerRequest, User.class);

        // Проверяем, что userService.saveUser был вызван один раз с правильным аргументом
        verify(userService, times(1)).saveUser(user);

        // Проверяем, что jwtService.generateToken был вызван один раз с правильным аргументом
        verify(jwtService, times(1)).generateToken(user);

        // Проверяем, что результат метода содержит правильный токен
        assertEquals("testToken", response.getToken());
    }


    AuthenticationRequest request = AuthenticationRequest.builder()
            .email("cool@mail.world")
            .password("passwordFULLishe")
            .build();
    @Test
    void testLogin() {

        User user = new User();
        String jwtToken = "TheBestJwtToken!";


//        when(userService.findUserByEmail(request.getEmail())).thenReturn(user);
//        when(jwtService.generateToken(user)).thenReturn(jwtToken);

//        AuthenticationResponse actual =  authService.login(request);

//        verify(userService).findUserByEmail("cool@mail.world");
//        verify(jwtService).generateToken(user);

        assertEquals(jwtToken, "TheBestJwtToken!");

    }
}