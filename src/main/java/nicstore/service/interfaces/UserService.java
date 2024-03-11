package nicstore.service.interfaces;

import nicstore.models.User;
import javax.transaction.Transactional;
import java.util.List;

public interface UserService {

    void userNotExist(String email);

    User findUserByEmail(String email);

    List<User> findAllUsers();

    @Transactional
    void saveUser(User user);
}
