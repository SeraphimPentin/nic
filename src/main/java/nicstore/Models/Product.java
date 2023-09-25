package nicstore.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255, message = "Название товара не более 255 символов")
    private String name;

    @Size(max = 2048, message = "Описание товара не более 2048 символов")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank
    @Column(nullable = false)
    private Integer quantity;

    @NotBlank
    @Column(nullable = false)
    private BigDecimal price;


//    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductImage> images;


//    @Builder.Default
    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(name = "products_categories", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    public void clearCategories() {
        categories.clear();
    }
}