package nicstore.service;

import nicstore.exceptions.auth.UserAlreadyExistException;
import nicstore.models.Cart;
import nicstore.models.User;
import nicstore.exceptions.auth.UserNotFoundException;
import nicstore.repository.CartRepository;
import nicstore.repository.UserRepository;
import nicstore.security.Role;
import nicstore.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, CartRepository cartRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("Пользователь с email: '" + email + "' не найден"));
    }

    public void userNotExist(String email) {
        if (userRepository.findUserByEmail(email).isPresent()){
            throw new UserAlreadyExistException("Пользователь с email: '" + email + "' уже существует");
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        userRepository.save(user);
    }
}