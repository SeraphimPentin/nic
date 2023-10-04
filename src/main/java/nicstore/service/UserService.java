package nicstore.service;

import nicstore.Models.Cart;
import nicstore.Models.User;
import nicstore.dto.auth.RegisterRequest;
import nicstore.exceptions.auth.UserAlreadyExistException;
import nicstore.exceptions.auth.UserNotFoundException;
import nicstore.repository.CartRepository;
import nicstore.repository.UserRepository;
import nicstore.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, CartRepository cartRepository ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public void emailAlreadyExist(RegisterRequest registerRequest){
       if (userRepository.findUserByEmail(registerRequest.getEmail()).isPresent()){
           throw new UserAlreadyExistException("Пользвоатель с почтой " + registerRequest.getEmail() + " уже зарегистрирован");
       }
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(()-> new UserNotFoundException("Пользователь с email " + email + " не найден"));
    }

    public List<User> findAllUsers(){
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