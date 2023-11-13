package nicstore.service.impl;

import nicstore.Models.User;
import nicstore.dto.auth.RegisterRequest;
import javax.transaction.Transactional;
import java.util.List;

public interface UserServiceImpl  {

    void emailAlreadyExist(RegisterRequest registerRequest);

    User findUserByEmail(String email);

    List<User> findAllUsers();

    @Transactional
    void saveUser(User user);
}
