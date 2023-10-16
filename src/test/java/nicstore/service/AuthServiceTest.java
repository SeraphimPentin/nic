package nicstore.service;

import nicstore.Models.User;
import nicstore.dto.auth.RegisterRequest;
import nicstore.dto.mapper.ConvertorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private ConvertorMapper mapper;

    @Mock
    private UserService userService;


    @Test
    public void registerTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email("email")
                .password("password")
                .build();

        User user = new User();
//        User mapUser = mapper.getMapper().map(registerRequest, User.class);
//        when(mapUser).thenReturn(user);
//        authService.register(registerRequest);
//        verify(userService).saveUser(user);


//        authService.register(registerRequest);
//        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//        verify(userService).saveUser(userArgumentCaptor.capture());
//        User capturedUser = userArgumentCaptor.getValue();
//        assertThat(capturedUser.getEmail()).isEqualTo("test@gmail.com");

        when(mapper.getMapper().map(registerRequest, User.class)).thenReturn(user);
        verify(userService).saveUser(user);
        assertNotNull(authService.register(registerRequest));
    }

    @Test
    void login() {
    }
}