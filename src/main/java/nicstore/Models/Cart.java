package nicstore.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "catrs")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> items;

    public boolean productInCart(Product product) {
        return items.containsKey(product);
    }

    public void addProductToCart(Product product) {
        items.put(product, 1);
    }

    public void removeProductFromCart(Product product){
        items.remove(product);
    }

    public void incProduct(Product product) {
        items.put(product, items.get(product) + 1);
    }

    public void decProduct(Product product) {
        items.put(product, items.get(product) - 1);
    }

    public Integer getQuantityProduct(Product product) {
        return items.get(product);
    }
}
