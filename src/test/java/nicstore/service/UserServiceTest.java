package nicstore.service;

import nicstore.models.Cart;
import nicstore.models.User;
import nicstore.exceptions.auth.UserNotFoundException;
import nicstore.repository.CartRepository;
import nicstore.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void testFindUserByEmail() {
        String email = "existing@example.com";
        User existingUser = new User();
        existingUser.setEmail(email);
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(existingUser));

        User user = userService.findUserByEmail(email);
        verify(userRepository).findUserByEmail(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail("none@mail.com"));
    }


    @Test
    void testFindAllUsers() {
        User user1 = new User();
        User user2 = new User();
        ArrayList<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        when(userRepository.findAll()).thenReturn(expected);
        List<User> actual = userService.findAllUsers();
        verify(userRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void testSaveUser() {
        User user = User.builder()
                .firstname("name")
                .lastname("lastname")
                .password("password")
                .email("email")
                .build();
        userService.saveUser(user);

        verify(passwordEncoder).encode("password");
        verify(cartRepository).save(any(Cart.class));
        verify(userRepository).save(user);
    }
}