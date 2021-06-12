package elerick.springbreak.testentities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true)
    public String name;
    @ManyToOne(optional = false)
    public Category category;
    public int stock;

    public Product(String name, Category category, int stock) {
        this.name = name;
        this.category = category;
        this.stock = stock;

    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Stock: %d", id, name, stock);
    }
}
