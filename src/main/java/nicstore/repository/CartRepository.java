package nicstore.repository;

import lombok.RequiredArgsConstructor;
import nicstore.Models.Cart;
import nicstore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUser(User user);
}
